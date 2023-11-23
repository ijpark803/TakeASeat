package com.example.takeaseat;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
// Irene Park
public class WhiteBoxTest5 {
    @Test
    public void testDecrementIndoor() {

        Building mockBuilding = new Building();
        BookingPage bookingPage = BookingPage.newInstance("param1", "param2", mockBuilding);
        List<Integer> selectedSlots = Arrays.asList(0, 1);
        bookingPage.decrementIndoor(selectedSlots);

        // Assert the results
        assertEquals(4, mockBuilding.timeSlots.get("8:00").getIndoor());
        assertEquals(4, mockBuilding.timeSlots.get("8:30").getIndoor());
    }
}
