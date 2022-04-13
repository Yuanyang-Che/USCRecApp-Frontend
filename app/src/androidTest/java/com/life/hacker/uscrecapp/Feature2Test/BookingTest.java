package com.life.hacker.uscrecapp.Feature2Test;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.anything;
import static java.lang.Thread.sleep;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import com.life.hacker.uscrecapp.R;
import com.life.hacker.uscrecapp.Util;
import com.life.hacker.uscrecapp.activity.LoginActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;

@RunWith(AndroidJUnit4.class)
public class BookingTest {
    //This denotes the starting activity
    @Rule
    public ActivityScenarioRule<LoginActivity> loginActivityScenarioRule = new ActivityScenarioRule<>(LoginActivity.class);

    private int activity_delay_time = 3000;

    private void waitAndSleep() {
        try {
            sleep(activity_delay_time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void tryLogout() {
        try {
            onView(withId(R.id.mapButtonLogout)).check(matches(isDisplayed()));
            //if we are in map view, means we've login already
            onView(withId(R.id.mapButtonLogout)).perform(click());
        } catch (NoMatchingViewException e) {
            //we are in login page
        }
    }

    @Before
    public void setUp() {
        tryLogout();
        //Check everything is there
        onView(withId(R.id.loginEditTextEmail)).check(matches(isDisplayed()));
        onView(withId(R.id.loginEditTextPassword)).check(matches(isDisplayed()));
        onView(withId(R.id.loginButtonLogin)).check(matches(isDisplayed()));
        onView(withId(R.id.loginButtonToSignUp)).check(matches(isDisplayed()));

        onView(withId(R.id.loginEditTextEmail)).perform(typeText("realchen@usc.edu"));
        onView(withId(R.id.loginEditTextPassword)).perform(typeText("12345678"));
        onView(withId(R.id.loginButtonLogin)).perform(click());
        try {
            sleep(activity_delay_time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void after() {
        waitAndSleep();
        tryLogout();
    }

    private void clickOnCenter(String str) {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UiObject marker = device.findObject(new UiSelector().descriptionContains(str));
        try {
            marker.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void bookingTestCheckBasicElements() {
        clickOnCenter("Lyon Center");

        onView(withId(R.id.bookingButtonPrevDay)).check(matches(isDisplayed()));
        onView(withId(R.id.bookingButtonNextDay)).check(matches(isDisplayed()));
        onView(withId(R.id.bookingButtonBacktoMap)).check(matches(isDisplayed()));
        onView(withId(R.id.bookingButtonBacktoMap)).perform(click());
    }

    @Test
    public void bookingTestCheckDatePicker() {
        clickOnCenter("Lyon Center");

        Calendar cal = Calendar.getInstance();
        Date currDate = cal.getTime();
        String currDateString = Util.formatDateToStandard(currDate);

        //Test 1: Correct Today's Date
        onView(withId(R.id.bookingTextViewSelectedDate)).check(matches(withText(currDateString)));


        //Test 2: Prev on Current Date, should do nothing
        onView(withId(R.id.bookingButtonPrevDay)).perform(click());
        onView(withId(R.id.bookingTextViewSelectedDate)).check(matches(withText(currDateString)));


        //Test 3: Check next day
        onView(withId(R.id.bookingButtonNextDay)).perform(click());
        cal.setTime(currDate);
        cal.add(Calendar.DATE, 1);

        String expectedNextDay = Util.formatDateToStandard(cal.getTime());

        onView(withId(R.id.bookingTextViewSelectedDate)).check(matches(withText(expectedNextDay)));
    }


    @Test
    public void bookingTestBooking() throws Exception {
        clickOnCenter("Lyon Center");

        Calendar cal = Calendar.getInstance();

        onData(anything()).inAdapterView(withId(R.id.bookingListView)).atPosition(99);

        //click on next day for 1 times
        DataInteraction d = null;
        //String bookedDateString = null;
        int i;
        do {
            onView(withId(R.id.bookingButtonNextDay)).perform(click());
            cal.add(Calendar.DATE, 1);
            for (i = 0; i < 10; ++i) {
                try {
                    onData(anything()).inAdapterView(withId(R.id.bookingListView)).atPosition(i).onChildView(withId(R.id.timeSlotAdapterButtonBook))
                            .check(matches(anyOf(withText(R.string.bookingButtonBook), withText(R.string.bookingButtonBookAndWaitlisted))));

                    d = onData(anything()).inAdapterView(withId(R.id.bookingListView)).atPosition(i);

                    break;
                } catch (Exception | AssertionError ignored) { }
            }
        }
        while (d == null);

        String selectedDateString = Util.formatDateToStandard(cal.getTime());
        String selectedTimeSlot = Util.convertTimeIndexToHour(i + 8);

        //Click on book btn
        d.onChildView(withId(R.id.timeSlotAdapterButtonBook)).perform(click());
        waitAndSleep();

        //Check confirm dialog is up
        onView(withText(R.string.confirmBookingMessage)).check(matches(isDisplayed()));

        //Check and click on final confirm
        onView(withText("Confirm")).check(matches(isDisplayed())).perform(click());
        waitAndSleep();

        //Make sure we are back at main screen, and a pop up of success was shown
        onView(withId(R.id.mapButtonGoToSummary)).check(matches(isDisplayed())).perform(click());

        //Assume we just succeed
        waitAndSleep();

        //To go summary page
        onView(withId(R.id.summaryListView)).check(matches(isDisplayed()));

        final int CAP = 30;
        for (i = 0; i < CAP; ++i) {
            try {
                onData(anything()).inAdapterView(withId(R.id.summaryListView)).atPosition(i).
                        onChildView(withId(R.id.timeslotAdapterTextViewDate)).check(matches(withText(selectedDateString)));
                onData(anything()).inAdapterView(withId(R.id.summaryListView)).atPosition(i).
                        onChildView(withId(R.id.timeslotAdapterTextViewTimeIndex)).check(matches(withText(selectedTimeSlot)));
                break;
            } catch (Exception | AssertionError ignored) {}
        }

        if (i == CAP) throw new Exception("Failed to cancel"); //failed

        //Finish up
        onData(anything()).inAdapterView(withId(R.id.summaryListView)).atPosition(i).
                onChildView(withId(R.id.timeSlotAdapterButtonBook)).perform(click());
    }
}
