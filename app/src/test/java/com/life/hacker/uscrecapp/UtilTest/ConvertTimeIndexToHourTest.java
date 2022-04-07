package com.life.hacker.uscrecapp.UtilTest;

import static org.junit.Assert.assertEquals;

import com.life.hacker.uscrecapp.Util;

import org.junit.Test;

public class ConvertTimeIndexToHourTest {
    @Test
    public void timeIndexFromMorningTo12() {
        assertEquals("8:00 - 9:00", Util.convertTimeIndexToHour(8));
        assertEquals("9:00 - 10:00", Util.convertTimeIndexToHour(9));
        assertEquals("10:00 - 11:00", Util.convertTimeIndexToHour(10));
        assertEquals("11:00 - 12:00", Util.convertTimeIndexToHour(11));
        assertEquals("12:00 - 13:00", Util.convertTimeIndexToHour(12));
    }

    @Test
    public void timeIndexAfter12() {
        assertEquals("13:00 - 14:00", Util.convertTimeIndexToHour(13));
        assertEquals("14:00 - 15:00", Util.convertTimeIndexToHour(14));
        assertEquals("15:00 - 16:00", Util.convertTimeIndexToHour(15));
        assertEquals("16:00 - 17:00", Util.convertTimeIndexToHour(16));
        assertEquals("17:00 - 18:00", Util.convertTimeIndexToHour(17));
    }
}
