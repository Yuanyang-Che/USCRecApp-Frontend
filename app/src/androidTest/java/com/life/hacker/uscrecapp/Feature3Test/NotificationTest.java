package com.life.hacker.uscrecapp.Feature3Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static java.lang.Thread.sleep;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.life.hacker.uscrecapp.Notification.NotificationEntry;
import com.life.hacker.uscrecapp.Notification.NotificationQueue;
import com.life.hacker.uscrecapp.R;
import com.life.hacker.uscrecapp.activity.LoginActivity;
import com.life.hacker.uscrecapp.network.NotificationCenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

@RunWith(AndroidJUnit4.class)
public class NotificationTest {
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
            e.printStackTrace();
        }
    }

    @After
    public void after() {
        try {
            sleep(activity_delay_time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tryLogout();
    }


    @Test
    public void notificationThreadTest() {
        if (!NotificationCenter.GetInstance().IsStart()) {
            assert false;
        }
    }

    @Test
    public void notificationCenter() {
        NotificationQueue.getInstance().addTimeslot(new NotificationEntry(8, Calendar.getInstance().getTime(), "Lyon Center") {});

        try {
            sleep(activity_delay_time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.mapButtonGoToNotification)).check(matches(isDisplayed()));
        onView(withId(R.id.mapButtonGoToNotification)).perform(click());

        try {
            sleep(activity_delay_time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.notificationBackToMapBtn)).check((matches(isDisplayed())));
        onView(withId(R.id.notificationBackToMapBtn)).perform(click());

    }
}
