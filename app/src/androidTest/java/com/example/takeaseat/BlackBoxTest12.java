package com.example.takeaseat;

import org.junit.Test;
import org.junit.Rule;


import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.action.ViewActions.click;

public class BlackBoxTest12 {
    // Written by Kaitlyn Werden
    // Verifying email and password sign in fields

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void verifySigninFields() {
        // Start the activity
        ActivityScenario<MainActivity> scenario = mActivityScenarioRule.getScenario();

        // Navigate to profile
        onView(withId(R.id.fragment2btn)).perform(click());

        // Check for the username field
        onView(withId(R.id.email))
                .check(matches(isDisplayed()));

        // Check for the password field
        onView(withId(R.id.password))
                .check(matches(isDisplayed()));

        // Confirm text can be typed for both
        onView(withId(R.id.email)).perform(ViewActions.typeText("testUsername"));
        onView(withId(R.id.password)).perform(ViewActions.typeText("testPassword"));
    }

}