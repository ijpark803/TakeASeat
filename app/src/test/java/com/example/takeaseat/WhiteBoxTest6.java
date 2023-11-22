package com.example.takeaseat;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
public class WhiteBoxTest6 {
    // edge case where too many slots are selected
    // Written by Kevin Arackaparambil
    @Test
    public void checkTooManySlots() {
        // Test
        BookingPage bookingPage = new BookingPage();
        bookingPage.selectedSlots = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
        assertFalse(bookingPage.checkMaxSelected());
    }
}