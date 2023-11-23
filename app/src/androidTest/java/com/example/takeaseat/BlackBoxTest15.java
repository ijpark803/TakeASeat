package com.example.takeaseat;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)

public class BlackBoxTest15 {

    // Written by Kaitlyn Werden
    // Verifying user cannot login without a registered email and password
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testBadLogin() {
        onView(withId(R.id.fragment2btn)).perform(click());

        String email = "joe";
        String password = "123";

        // Navigate to the profile view
        Espresso.onView(ViewMatchers.withId(R.id.fragment2btn)).perform(ViewActions.click());

        // Input email and password
        Espresso.onView(ViewMatchers.withId(R.id.email))
                .perform(ViewActions.typeText(email), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.password))
                .perform(ViewActions.typeText(password), ViewActions.closeSoftKeyboard());

        // Click on the login button and confirm page doesn't change/nothing happens
        onView(withId(R.id.loginbtn)).perform(click());
        onView(withId(R.id.loginlayout)).check(matches(isDisplayed()));

    }

}
