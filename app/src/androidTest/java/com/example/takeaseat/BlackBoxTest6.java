package com.example.takeaseat;

import org.junit.Rule;
import org.junit.Test;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class BlackBoxTest6 {
    // Written by Kevin Arackaparambil
    // checking that the registration page has all the required components
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void verifyRegistration() {

        // Start the activity scenario
        ActivityScenario<MainActivity> scenario = mActivityScenarioRule.getScenario();

        // navigate to profile
        onView(withId(R.id.fragment2btn)).perform(click());
        onView(withId(R.id.loginlayout)).check(matches(isDisplayed()));

        // navigate to registration
        onView(withId(R.id.registerbtn)).perform(click());
        onView(withId(R.id.registrationlayout)).check(matches(isDisplayed()));

        // verify all the fields exist
        Espresso.onView(ViewMatchers.withId(R.id.email))
                .check(matches(ViewMatchers.isDisplayed()))
                .check(matches(ViewMatchers.isClickable()));

        Espresso.onView(ViewMatchers.withId(R.id.password))
                .check(matches(ViewMatchers.isDisplayed()))
                .check(matches(ViewMatchers.isClickable()));

        Espresso.onView(ViewMatchers.withId(R.id.uscid))
                .check(matches(ViewMatchers.isDisplayed()))
                .check(matches(ViewMatchers.isClickable()));

        Espresso.onView(ViewMatchers.withId(R.id.affiliation))
                .check(matches(ViewMatchers.isDisplayed()))
                .check(matches(ViewMatchers.isClickable()));

        Espresso.onView(ViewMatchers.withId(R.id.name))
                .check(matches(ViewMatchers.isDisplayed()))
                .check(matches(ViewMatchers.isClickable()));

        Espresso.onView(ViewMatchers.withId(R.id.btnUpload))
                .check(matches(ViewMatchers.isDisplayed()))
                .check(matches(ViewMatchers.isClickable()));
    }
}
