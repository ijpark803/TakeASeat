package com.example.takeaseat;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
//import static androidx.test.espresso.matcher.ViewMatchers.not;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.not;
import androidx.test.ext.junit.rules.ActivityScenarioRule;


import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.runner.RunWith;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;


@RunWith(AndroidJUnit4.class)
public class BlackBoxTest3 {
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void testFragment1Interaction() {

        onView(withId(R.id.fragment1btn)).perform(click());
        onView(withId(R.id.mapview)).check(matches(isDisplayed()));

    }
    @Test
    public void testFragment2Interaction() {

        onView(withId(R.id.fragment2btn)).perform(click());
        onView(withId(R.id.loginlayout)).check(matches(isDisplayed()));

    }

    @Test
    public void testReserveButtons() {


        onView(withId(R.id.description)).check(matches(isDisplayed()));

        // Check if the reserve buttons are initially disabled
        onView(withId(R.id.indoorreservebtn)).check(matches(not(isEnabled())));
        onView(withId(R.id.outdoorreservebtn)).check(matches(not(isEnabled())));

        // Select four consecutive time slots
        onView(withId(0)).perform(click());
        onView(withId(1)).perform(click());
        onView(withId(2)).perform(click());
        onView(withId(3)).perform(click());

        // Check if the reserve buttons are now enabled
        onView(withId(R.id.indoorreservebtn)).check(matches(isEnabled()));
        onView(withId(R.id.outdoorreservebtn)).check(matches(isEnabled()));
    }


}
