package com.life.hacker.uscrecapp.Feature1Test;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.life.hacker.uscrecapp.R;
import com.life.hacker.uscrecapp.activity.SignUpActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(AndroidJUnit4.class)
public class SignUpTest {
    @Rule
    public ActivityScenarioRule<SignUpActivity> mActivityRule = new ActivityScenarioRule<>(SignUpActivity.class);

    private final String uniqueEmail;

    private void tryLogout() {
        try {
            onView(withId(R.id.mapButtonLogout)).check(matches(isDisplayed()));
            //if we are in map view, means we've login already
            onView(withId(R.id.mapButtonLogout)).perform(click());
        } catch (NoMatchingViewException e) {
            //we are in login page
        }
    }

    public SignUpTest() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date();
        uniqueEmail = dateFormat.format(date) + "@usc.edu";
    }

    @Before
    public void setUp() {
        tryLogout();
        onView(withId(R.id.signUpEditTextEmail)).check(matches(isDisplayed()));
        onView(withId(R.id.signUpEditTextUsername)).check(matches(isDisplayed()));
        onView(withId(R.id.signUpEditTextPassword)).check(matches(isDisplayed()));
        onView(withId(R.id.signUpEditTextNetID)).check(matches(isDisplayed()));

        onView(withId(R.id.signUpImageViewAvatar)).check(matches(isDisplayed()));

        onView(withId(R.id.signUpButtonSignUp)).check(matches(isDisplayed()));
        onView(withId(R.id.signUpButtonToLogin)).check(matches(isDisplayed()));
    }

    @After
    public void after() { tryLogout(); }

    @Test
    public void signUpTestEmptyEmail() {
        onView(withId(R.id.signUpButtonSignUp)).perform(click());
        onView(withId(R.id.signUpTextViewErrorMessage)).check(matches(withText(R.string.signUpInvalidEmail)));
    }

    @Test
    public void signUpTestInvalidEmail() {
        onView(withId(R.id.signUpEditTextEmail)).perform(replaceText("testc.edu"));
        closeSoftKeyboard();
        onView(withId(R.id.signUpButtonSignUp)).perform(click());
        onView(withId(R.id.signUpTextViewErrorMessage)).check(matches(withText(R.string.signUpInvalidEmail)));

        onView(withId(R.id.signUpEditTextEmail)).perform(replaceText("te42du"));
        closeSoftKeyboard();
        onView(withId(R.id.signUpButtonSignUp)).perform(click());
        onView(withId(R.id.signUpTextViewErrorMessage)).check(matches(withText(R.string.signUpInvalidEmail)));
    }

    @Test
    public void signUpTestEmptyNetId() {
        onView(withId(R.id.signUpEditTextEmail)).perform(typeText(uniqueEmail));
        closeSoftKeyboard();

        onView(withId(R.id.signUpButtonSignUp)).perform(click());
        onView(withId(R.id.signUpTextViewErrorMessage)).check(matches(withText(R.string.signUpInvalidNetID)));
    }

    @Test
    public void signUpTestInvalidNetId() {
        onView(withId(R.id.signUpEditTextEmail)).perform(typeText(uniqueEmail));
        closeSoftKeyboard();

        onView(withId(R.id.signUpEditTextNetID)).perform(typeText("123"));
        closeSoftKeyboard();
        onView(withId(R.id.signUpButtonSignUp)).perform(click());
        onView(withId(R.id.signUpTextViewErrorMessage)).check(matches(withText(R.string.signUpInvalidNetID)));

        onView(withId(R.id.signUpEditTextNetID)).perform(replaceText("abdd"));
        closeSoftKeyboard();
        onView(withId(R.id.signUpButtonSignUp)).perform(click());
        onView(withId(R.id.signUpTextViewErrorMessage)).check(matches(withText(R.string.signUpInvalidNetID)));

        onView(withId(R.id.signUpEditTextNetID)).perform(replaceText("1243464064643"));
        closeSoftKeyboard();
        onView(withId(R.id.signUpButtonSignUp)).perform(click());
        onView(withId(R.id.signUpTextViewErrorMessage)).check(matches(withText(R.string.signUpInvalidNetID)));
    }

    @Test
    public void signUpTestEmptyUsername() {
        onView(withId(R.id.signUpEditTextEmail)).perform(typeText(uniqueEmail));
        closeSoftKeyboard();
        onView(withId(R.id.signUpEditTextNetID)).perform(typeText("1234567890"));
        closeSoftKeyboard();

        onView(withId(R.id.signUpButtonSignUp)).perform(click());
        onView(withId(R.id.signUpTextViewErrorMessage)).check(matches(withText(R.string.signUpInvalidUsername)));
    }


    @Test
    public void signUpTestEmptyPassword() {
        onView(withId(R.id.signUpEditTextEmail)).perform(typeText(uniqueEmail));
        closeSoftKeyboard();
        onView(withId(R.id.signUpEditTextNetID)).perform(typeText("1234567890"));
        closeSoftKeyboard();

        onView(withId(R.id.signUpEditTextUsername)).perform(typeText("username"));
        closeSoftKeyboard();
        onView(withId(R.id.signUpButtonSignUp)).perform(click());
        onView(withId(R.id.signUpTextViewErrorMessage)).check(matches(withText(R.string.signUpInvalidPassword)));
    }

    @Test
    public void signUpTestSuccess() {
        onView(withId(R.id.signUpEditTextEmail)).perform(typeText(uniqueEmail));
        closeSoftKeyboard();
        onView(withId(R.id.signUpEditTextNetID)).perform(typeText("1234567890"));
        closeSoftKeyboard();
        onView(withId(R.id.signUpEditTextUsername)).perform(typeText("username"));
        closeSoftKeyboard();
        onView(withId(R.id.signUpEditTextPassword)).perform(typeText("123"));
        closeSoftKeyboard();
        onView(withId(R.id.signUpButtonSignUp)).perform(click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.mapButtonLogout)).perform(click());
    }
}
