package com.example.takeaseat;

import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class BlackBoxTest10 {
    // Written by Kevin Arackaparambil
    // verifying that when user is logged in, the profile button takes them to the profile page
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void verifyLoginFunctionality() {
        // log in to an account
        onView(withId(R.id.fragment2btn)).perform(click());

        String email = "irene";
        String password = "park";
        // Navigate to the ProfileView fragment
        Espresso.onView(ViewMatchers.withId(R.id.fragment2btn)).perform(ViewActions.click());

        // Input email and password
        Espresso.onView(ViewMatchers.withId(R.id.email))
                .perform(ViewActions.typeText(email), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.password))
                .perform(ViewActions.typeText(password), ViewActions.closeSoftKeyboard());

        // Click on the login button
        Espresso.onView(ViewMatchers.withId(R.id.loginbtn)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.profileHeader)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // navigate to the map view
        Espresso.onView(ViewMatchers.withId(R.id.fragment1btn)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.mapview)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // navigate to the profile view through the profile button
        // since user is loggedin, user should go to profile page when button is pressed
        Espresso.onView(ViewMatchers.withId(R.id.fragment2btn)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.profileview)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
