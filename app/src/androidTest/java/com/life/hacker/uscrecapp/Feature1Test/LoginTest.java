package com.life.hacker.uscrecapp.Feature1Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.life.hacker.uscrecapp.R;
import com.life.hacker.uscrecapp.activity.LoginActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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
        onView(withId(R.id.loginTextViewErrorMessage)).check(matches(withText(R.string.loginInvalidEmail)));
    }

    @Test
    public void loginTestEmptyPassword() {
        onView(withId(R.id.loginEditTextEmail)).perform(typeText("test@usc.edu"));
        onView(withId(R.id.loginTextViewErrorMessage)).check(matches(withText(R.string.loginInvalidPassword)));
    }

    @Test
    public void loginTestWrongEmail() {
        onView(withId(R.id.loginEditTextEmail)).perform(typeText("test@usc.edu"));
        onView(withId(R.id.loginEditTextPassword)).perform(typeText("wrong pw"));
        onView(withId(R.id.loginTextViewErrorMessage)).check(matches(withText("Incorrect Email or password")));
    }

    @Test
    public void loginTestSuccess() {
        onView(withId(R.id.loginEditTextEmail)).perform(typeText("test@usc.edu"));
        onView(withId(R.id.loginEditTextPassword)).perform(typeText("pw"));

        onView(withId(R.id.loginButtonLogin)).perform(click());

        pressBack();
        //onView(withId(R.id.mapButtonLogout)).check(matches(isDisplayed()));
    }
}
