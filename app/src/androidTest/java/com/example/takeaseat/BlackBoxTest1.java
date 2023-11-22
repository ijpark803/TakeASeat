package com.example.takeaseat;

import static androidx.test.espresso.assertion.ViewAssertions.matches;


import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import androidx.test.runner.AndroidJUnit4;

import com.example.takeaseat.MainActivity;
import com.example.takeaseat.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class BlackBoxTest1 {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testButtons1Exist() {

        // Start the activity scenario
        ActivityScenario<MainActivity> scenario = mActivityScenarioRule.getScenario();

        // Check that the first fragment button is displayed and clickable.
        Espresso.onView(ViewMatchers.withId(R.id.fragment1btn))
                .check(matches(ViewMatchers.isDisplayed()))
                .check(matches(ViewMatchers.isClickable()));
    }
}
