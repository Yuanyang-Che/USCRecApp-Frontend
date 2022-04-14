package com.life.hacker.uscrecapp.Feature1Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.life.hacker.uscrecapp.R;
import com.life.hacker.uscrecapp.activity.LoginActivity;
import com.life.hacker.uscrecapp.model.Appointment;
import com.life.hacker.uscrecapp.model.Center;
import com.life.hacker.uscrecapp.model.Day;
import com.life.hacker.uscrecapp.model.MapData;
import com.life.hacker.uscrecapp.model.Timeslot;
import com.life.hacker.uscrecapp.model.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class LoginTest {
    //This denotes the starting activity
    @Rule
    public ActivityScenarioRule<LoginActivity> loginActivityScenarioRule = new ActivityScenarioRule<>(LoginActivity.class);

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
    }

    @After
    public void after() {
        tryLogout();
    }


    @Test
    public void loginTestEmptyEmail() {
        onView(withId(R.id.loginButtonLogin)).perform(click());

        Timeslot timeslot = new Timeslot(0, 0, 0, new Day(), false, false, false);
        Appointment appointment = new Appointment(0, timeslot);
        assertEquals(appointment.getId(), 0);
        assertEquals(appointment.getTimeslot(), timeslot);
        appointment.setTimeslot(timeslot);
        assertEquals(appointment.getTimeslot(), timeslot);

        timeslot.setBooked(true);
        timeslot.setWaitListed(true);
        assertFalse(timeslot.isBookable());
        assertTrue(new Timeslot(0, 2, 0, new Day(), false, false, false).isBookable());



        onView(withId(R.id.loginTextViewErrorMessage)).check(matches(withText(R.string.loginInvalidEmail)));
    }

    @Test
    public void loginTestEmptyPassword() {
        onView(withId(R.id.loginEditTextEmail)).perform(typeText("test@usc.edu"));

        Center center = new Center(0, "name", new Day[]{}, 0, 0);
        center.setId(0);
        center.setName("name");
        center.setDays(new Day[]{});
        center.setLatitude(0);
        center.setLongitude(0);
        assertEquals(center.getId(), 0);
        assertArrayEquals(center.getDays(), new Day[]{});

        onView(withId(R.id.loginButtonLogin)).perform(click());
        onView(withId(R.id.loginTextViewErrorMessage)).check(matches(withText(R.string.loginInvalidPassword)));
    }

    @Test
    public void loginTestWrongEmail() {
        onView(withId(R.id.loginEditTextEmail)).perform(typeText("test@usc.edu"));
        onView(withId(R.id.loginEditTextPassword)).perform(typeText("wrong pw"));

        User user = new User(null, null, null, null);
        user.setEmail("");
        user.setNetid("");
        user.setUsername("");
        user.setAvatar(null);

        List<Center> centers = MapData.getInstance().getCenters();
        assertNull(MapData.getInstance().findCenterByName("Something impossible to find and would return null"));

        onView(withId(R.id.loginButtonLogin)).perform(click());
        onView(withId(R.id.loginTextViewErrorMessage)).check(matches(withText(R.string.loginIncorrectPassword)));
    }

    @Test
    public void loginTestSuccess() {
        onView(withId(R.id.loginEditTextEmail)).perform(typeText("test@usc.edu"));
        onView(withId(R.id.loginEditTextPassword)).perform(typeText("pw"));

        Day day = new Day();
        Date date = Calendar.getInstance().getTime();
        day.setDate(date);
        day.setCenter(new Center(0, "name", new Day[]{}, 0, 0));
        assertEquals(day.getDate(), date);
        Timeslot[] timeslots = new Timeslot[]{};
        day.setTimeslots(timeslots);
        assertArrayEquals(day.getTimeslots(), timeslots);

        onView(withId(R.id.loginButtonLogin)).perform(click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.mapButtonLogout)).perform(click());
    }
}
