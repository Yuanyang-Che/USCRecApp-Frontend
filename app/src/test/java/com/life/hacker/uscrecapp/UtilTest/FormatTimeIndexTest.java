package com.life.hacker.uscrecapp.UtilTest;

import static org.junit.Assert.assertEquals;

import com.life.hacker.uscrecapp.Util;

import org.junit.Test;

public class FormatTimeIndexTest {
    @Test
    public void timeFormatTestNotAfterNoon() {
        assertEquals("01:00:00", Util.formatTimeIndex(1));
        assertEquals("03:00:00", Util.formatTimeIndex(3));
        assertEquals("05:00:00", Util.formatTimeIndex(5));
        assertEquals("07:00:00", Util.formatTimeIndex(7));
        assertEquals("12:00:00", Util.formatTimeIndex(12));

    }

    @Test
    public void timeFormatTestAfterNoon() {
        assertEquals("13:00:00", Util.formatTimeIndex(13));
        assertEquals("15:00:00", Util.formatTimeIndex(15));
        assertEquals("23:00:00", Util.formatTimeIndex(23));
        assertEquals("18:00:00", Util.formatTimeIndex(18));
        assertEquals("12:00:00", Util.formatTimeIndex(12));
    }
}
