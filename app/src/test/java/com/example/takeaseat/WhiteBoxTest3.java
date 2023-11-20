package com.example.takeaseat;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class WhiteBoxTest3 {
    // WRitten by Irene Park
    @Test
    public void testIsConsecutive() {
        BookingPage bookingPage = new BookingPage();

        assertTrue(bookingPage.isConsecutive(1, 2));
        assertFalse(bookingPage.isConsecutive(1, 3));
    }
}
