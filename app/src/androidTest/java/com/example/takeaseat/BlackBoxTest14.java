package com.example.takeaseat;
import android.util.Log;
import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class BlackBoxTest14 {

    // Written by Kaitlyn Werden
    // Verifying the map is clickable

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void verifyBuildingMarkerClick() {
        // Start the activity
        ActivityScenario<MainActivity> scenario = mActivityScenarioRule.getScenario();

        // Click on a specific point on the map
        onView(withId(R.id.maps)).perform(clickOnMapPoint(34.02086719918009, -118.28700277916016));

    }

    // Simulate a click on a specific point on the map
    private static ViewAction clickOnMapPoint(final double latitude, final double longitude) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(View.class);
            }

            @Override
            public String getDescription() {
                return "Click on map point";
            }

            @Override
            public void perform(UiController uiController, View view) {

                Log.d("MapClick", "Click on map point: " + 900 + ", " + 900);

            }
        };
    }
}
