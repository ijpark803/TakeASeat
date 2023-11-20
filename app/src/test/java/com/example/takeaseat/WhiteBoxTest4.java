package com.example.takeaseat;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class WhiteBoxTest4 {
    @Test
    public void testDecrementOutdoor() {

        Building mockBuilding = new Building();
        BookingPage bookingPage = BookingPage.newInstance("param1", "param2", mockBuilding);
        List<Integer> selectedSlots = Arrays.asList(0, 1);
        bookingPage.decrementOutdoor(selectedSlots);

        // Assert the results
        assertEquals(4, mockBuilding.timeSlots.get("8:00").getOutdoor());
        assertEquals(4, mockBuilding.timeSlots.get("8:30").getOutdoor());
    }
}
