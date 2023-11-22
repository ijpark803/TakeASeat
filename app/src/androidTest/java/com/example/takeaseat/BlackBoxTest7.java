package com.example.takeaseat;

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

public class BlackBoxTest7 {
    // Written by Kevin Arackaparambil
    // checking that the profile page displays all the required components
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void verifyProfilePage() {
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

        // verify all the components of the profile page exist
        Espresso.onView(ViewMatchers.withId(R.id.profileHeader))
                .check(matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.imageView))
                .check(matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.photo))
                .check(matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.uscname))
                .check(matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.uscid))
                .check(matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.uscaffiliation))
                .check(matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.upcomingReservationsLabel))
                .check(matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.pastReservationsLabel))
                .check(matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.cancelbtn))
                .check(matches(ViewMatchers.isDisplayed()));
    }
}
