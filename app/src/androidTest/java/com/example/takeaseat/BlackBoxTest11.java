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

public class BlackBoxTest11 {
    // Written by Kaitlyn Werden
    // Checking that the profile page has both a log in and register option
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void verifyProfileOptions() {

        // Start the activity
        ActivityScenario<MainActivity> scenario = mActivityScenarioRule.getScenario();

        // navigate to profile
        onView(withId(R.id.fragment2btn)).perform(click());
        onView(withId(R.id.loginlayout)).check(matches(isDisplayed()));

        // verify both login and register buttons exist
        Espresso.onView(ViewMatchers.withId(R.id.registerbtn))
                .check(matches(ViewMatchers.isDisplayed()))
                .check(matches(ViewMatchers.isClickable()));

        Espresso.onView(ViewMatchers.withId(R.id.loginbtn))
                .check(matches(ViewMatchers.isDisplayed()))
                .check(matches(ViewMatchers.isClickable()));

    }
}