package com.example.takeaseat;
import com.example.takeaseat.BookingPage;


import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class WhiteBoxTest1 {
    public void testDecrementIndoor() {
    // Written by Irene Park
        Building mockBuilding = new Building();
        BookingPage bookingPage = BookingPage.newInstance("param1", "param2", mockBuilding);
        List<Integer> selectedSlots = Arrays.asList(0, 1);
        bookingPage.decrementIndoor(selectedSlots);

        // Assert the results
        assertEquals(4, mockBuilding.timeSlots.get("8:00").getIndoor());
        assertEquals(4, mockBuilding.timeSlots.get("8:30").getIndoor());
    }
}