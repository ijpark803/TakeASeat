package com.example.takeaseat;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)


public class BlackBoxTest4 {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testLogoutin() {
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


    }
    @Test
    public void testLogout(){

//        // Click on the logout button
//        Espresso.onView(ViewMatchers.withId(R.id.logoutbtn)).perform(ViewActions.click());
//
//        // Check if the MainActivity is displayed after logging out
//        Espresso.onView(ViewMatchers.withId(R.id.frameLayout)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
