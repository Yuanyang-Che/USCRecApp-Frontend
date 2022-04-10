package com.life.hacker.uscrecapp.Feature1Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.life.hacker.uscrecapp.R;
import com.life.hacker.uscrecapp.activity.LoginActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import kotlin.jvm.JvmField;

@RunWith(AndroidJUnit4.class)
public class LoginTest {
    @Rule
    @JvmField
    public ActivityScenarioRule<LoginActivity> loginActivityScenarioRule = new ActivityScenarioRule<>(LoginActivity.class);
    //public ActivityScenario<LoginActivity>


    @Before
    public void setUp() {
        //Check everything is there
        onView(withId(R.id.loginEditTextEmail)).check(matches(isDisplayed()));
        onView(withId(R.id.loginEditTextPassword)).check(matches(isDisplayed()));
        onView(withId(R.id.loginButtonLogin)).check(matches(isDisplayed()));
        onView(withId(R.id.loginButtonToSignUp)).check(matches(isDisplayed()));
    }

    @Test
    public void loginTestEmptyEmail() {
        onView(withId(R.id.loginButtonLogin)).perform(click());
        onView(withId(R.id.loginTextViewErrorMessage)).check(matches(withText("Invalid Email Address")));
    }

    @Test
    public void loginTestEmptyPassword() {
        onView(withId(R.id.loginEditTextEmail)).perform(typeText("test@usc.edu"));

        onView(withId(R.id.loginTextViewErrorMessage)).check(matches(withText("Invalid Password Address")));
    }


    @Test
    public void loginTestSuccess() {
        onView(withId(R.id.loginEditTextEmail)).perform(typeText("test@usc.edu"));
        onView(withId(R.id.loginEditTextPassword)).perform(typeText("pw"));

        onView(withId(R.id.loginButtonLogin)).perform(click());

        onView(withId(R.id.mapButtonGoToSummary)).check(matches(isDisplayed()));
    }
}
