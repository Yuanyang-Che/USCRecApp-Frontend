package com.life.hacker.uscrecapp.Feature1Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.life.hacker.uscrecapp.R;
import com.life.hacker.uscrecapp.activity.SignUpActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SignUpTest {
    @Rule
    public ActivityScenarioRule<SignUpActivity> mActivityRule = new ActivityScenarioRule<>(SignUpActivity.class);

    @Test
    public void signUpTestEmptyEmail() {
        onView(withId(R.id.signUpButtonSignUp)).perform(click());
        onView(withId(R.id.signUpTextViewErrorMessage)).check(matches(withText("Invalid Email Address")));
    }

}
