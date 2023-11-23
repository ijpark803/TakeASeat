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

public class BlackBoxTest9 {
    // Written by Kevin Arackaparambil
    // verifying that when profile button is pressed when user is not logged in, user is taken to the login page
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void verifyNotLoggedInFunctionality() {
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

        // click on the logout button
        Espresso.onView(ViewMatchers.withId(R.id.logoutbtn)).perform(ViewActions.click());

        // verify that the fragment2 button takes back to login page
        Espresso.onView(ViewMatchers.withId(R.id.fragment2btn)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.loginlayout)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

}
