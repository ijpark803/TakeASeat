package com.example.takeaseat;

import android.content.Intent;

import org.junit.Rule;
import org.junit.Test;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


public class BlackBoxTest8 {
    // Written by Kevin Arackaparambil
    // checking that the registration functionality works
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void verifyNotLoggedIn() {
        // launch main activity
        ActivityScenario<MainActivity> scenario = mActivityScenarioRule.getScenario();

        // click on profile button
        onView(withId(R.id.fragment2btn)).perform(click());
        // verify was redirected to login page instead of profile page
        Espresso.onView(ViewMatchers.withId(R.id.loginlayout))
                .check(matches(ViewMatchers.isDisplayed()));
    }
}
