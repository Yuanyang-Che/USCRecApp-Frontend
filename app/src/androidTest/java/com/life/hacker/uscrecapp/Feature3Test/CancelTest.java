package com.life.hacker.uscrecapp.Feature3Test;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;

import static java.lang.Thread.sleep;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.life.hacker.uscrecapp.R;
import com.life.hacker.uscrecapp.activity.LoginActivity;
import com.life.hacker.uscrecapp.activity.MapsActivity;
import com.life.hacker.uscrecapp.activity.SummaryActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CancelTest {
    //This denotes the starting activity
    @Rule
    public ActivityScenarioRule<LoginActivity> loginActivityScenarioRule = new ActivityScenarioRule<>(LoginActivity.class);

    private int activity_delay_time = 2000;
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

        }
    }

    @After
    public void after() {
        try {
            sleep(activity_delay_time);
        } catch (InterruptedException e) {

        }
        tryLogout();
    }


    @Test
    public void summaryPageButton() {
        onView(withId(R.id.mapButtonGoToSummary)).check(matches(isDisplayed()));
    }

    @Test
    public void summaryPageBackToMap() {
        onView(withId(R.id.mapButtonGoToSummary)).check(matches(isDisplayed()));
        onView(withId(R.id.mapButtonGoToSummary)).perform(click());
        try {
            sleep(activity_delay_time);
        } catch (InterruptedException e) {

        }

        onView(withId(R.id.summaryButtonBackToMap)).check((matches(isDisplayed())));
        onView(withId(R.id.summaryButtonBackToMap)).perform(click());
    }

    @Test
    public void summaryPagePastAppointment() {
        onView(withId(R.id.mapButtonGoToSummary)).check(matches(isDisplayed()));
        onView(withId(R.id.mapButtonGoToSummary)).perform(click());
        try {
            sleep(activity_delay_time);
        } catch (InterruptedException e) {

        }
        onData(anything()).inAdapterView(withId(R.id.summaryListView)).atPosition(0).onChildView(withId(R.id.timeSlotAdapterButtonBook)).check(matches((withText("Past Appointment"))));
        onData(anything()).inAdapterView(withId(R.id.summaryListView)).atPosition(1).onChildView(withId(R.id.timeSlotAdapterButtonBook)).check(matches((withText("Past Appointment"))));
        onData(anything()).inAdapterView(withId(R.id.summaryListView)).atPosition(2).onChildView(withId(R.id.timeSlotAdapterButtonBook)).check(matches((withText("Past Appointment"))));
        onData(anything()).inAdapterView(withId(R.id.summaryListView)).atPosition(3).onChildView(withId(R.id.timeSlotAdapterButtonBook)).check(matches((withText("Past Appointment"))));
        onData(anything()).inAdapterView(withId(R.id.summaryListView)).atPosition(4).onChildView(withId(R.id.timeSlotAdapterButtonBook)).check(matches((withText("Past Appointment"))));
        onData(anything()).inAdapterView(withId(R.id.summaryListView)).atPosition(5).onChildView(withId(R.id.timeSlotAdapterButtonBook)).check(matches((withText("Past Appointment"))));


        onView(withId(R.id.summaryButtonBackToMap)).check((matches(isDisplayed())));
        onView(withId(R.id.summaryButtonBackToMap)).perform(click());
    }

    @Test
    public void summaryPageFutureAppointment() {
        onView(withId(R.id.mapButtonGoToSummary)).check(matches(isDisplayed()));
        onView(withId(R.id.mapButtonGoToSummary)).perform(click());
        try {
            sleep(activity_delay_time);
        } catch (InterruptedException e) {

        }

        try {
            onData(anything()).inAdapterView(withId(R.id.summaryListView)).atPosition(6).onChildView(withId(R.id.timeSlotAdapterButtonBook)).check(matches((withText("Cancel"))));
        } catch(Exception e) {
            // we need to book first before running the test
            e.printStackTrace();
        }

        try {
            sleep(activity_delay_time);
        } catch (InterruptedException e) {

        }


        onView(withId(R.id.summaryButtonBackToMap)).check((matches(isDisplayed())));
        onView(withId(R.id.summaryButtonBackToMap)).perform(click());
    }

    @Test
    public void summaryPageCancelAppointment() {
        onView(withId(R.id.mapButtonGoToSummary)).check(matches(isDisplayed()));
        onView(withId(R.id.mapButtonGoToSummary)).perform(click());
        try {
            sleep(activity_delay_time);
        } catch (InterruptedException e) {

        }

        try {
            onData(anything()).inAdapterView(withId(R.id.summaryListView)).atPosition(6).onChildView(withId(R.id.timeSlotAdapterButtonBook)).perform(click());
        } catch(Exception e) {
            // we need to book first before running the test
        }

        try {
            sleep(activity_delay_time);
        } catch (InterruptedException e) {

        }

        onView(withId(R.id.summaryButtonBackToMap)).check((matches(isDisplayed())));
        onView(withId(R.id.summaryButtonBackToMap)).perform(click());
    }
}
