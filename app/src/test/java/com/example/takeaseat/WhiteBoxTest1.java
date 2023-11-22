package com.example.takeaseat;
import com.example.takeaseat.BookingPage;


import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class WhiteBoxTest1 {
    // Irene Park
    @Test
    public void testParseValidTime() {

        String validTimeString = "12:34";
        ProfileView pv = new ProfileView();

        Date parsedDate = pv.parseTime(validTimeString);

        assertNotNull(parsedDate);
        assertEquals(validTimeString, formatTime(parsedDate));
    }

    @Test
    public void testParseInvalidTime() {
        // Arrange
        String invalidTimeString = "invalidTime";
        ProfileView pv = new ProfileView();
        // Act
        Date parsedDate = pv.parseTime(invalidTimeString);

        // Assert
        assertNull(parsedDate);
    }

    private String formatTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.US);
        return dateFormat.format(date);
    }
}
