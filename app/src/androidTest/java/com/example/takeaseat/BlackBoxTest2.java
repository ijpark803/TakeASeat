package com.example.takeaseat;

import static androidx.test.espresso.assertion.ViewAssertions.matches;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class BlackBoxTest2 {
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testButtons2Exist() {
        // Check that the first fragment button is displayed and clickable.
        Espresso.onView(ViewMatchers.withId(R.id.fragment2btn))
                .check(matches(ViewMatchers.isDisplayed()))
                .check(matches(ViewMatchers.isClickable()));
    }
}
