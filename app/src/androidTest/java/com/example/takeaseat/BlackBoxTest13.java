package com.example.takeaseat;

import org.junit.Test;
import org.junit.Rule;


import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;



public class BlackBoxTest13 {
    // Written by Kaitlyn Werden
    // Verifying profile picture functionality

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void verifyPictureFunctionality() {
        // Start the activity
        ActivityScenario<MainActivity> scenario = mActivityScenarioRule.getScenario();

        // Navigate to profile
        onView(withId(R.id.fragment2btn)).perform(click());

        // Navigate to registration
        onView(withId(R.id.registerbtn)).perform(click());

        onView(withId(R.id.btnUpload))
                .check(matches(isDisplayed()))
                .check(matches(isClickable()));

        // Click on the upload button
        onView(withId(R.id.btnUpload)).perform(click());


    }

}