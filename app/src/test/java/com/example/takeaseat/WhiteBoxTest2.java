package com.example.takeaseat;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

public class WhiteBoxTest2 {
    // tests the checkConsecutivenessOfSelectedSlots method
    //Written by Irene PArk
    @Test
    public void testCheckConsecutivenessOfSelectedSlots() {
        // Test
        BookingPage bookingPage = new BookingPage();
        bookingPage.selectedSlots = new HashSet<>(Arrays.asList(1, 2, 3));

        assertTrue(bookingPage.checkConsecutivenessOfSelectedSlots());

        bookingPage.selectedSlots = new HashSet<>(Arrays.asList(1, 4, 3));
        assertFalse(bookingPage.checkConsecutivenessOfSelectedSlots());
    }
}
