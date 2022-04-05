package com.life.hacker.uscrecapp.UtilTest;

import static org.junit.Assert.assertEquals;

import com.life.hacker.uscrecapp.Util;

import org.junit.Test;

public class TimeIndexFormatTest {
    @Test
    public void timeFormatTestLessThan10() {
        assertEquals(Util.formatTimeIndex(1), "01:00:00");
        assertEquals(Util.formatTimeIndex(2), "02:00:00");
        assertEquals(Util.formatTimeIndex(3), "03:00:00");
        assertEquals(Util.formatTimeIndex(4), "04:00:00");
    }

    @Test
    public void timeFormatTestGreaterThan10() {
        assertEquals(Util.formatTimeIndex(13), "13:00:00");
        assertEquals(Util.formatTimeIndex(11), "11:00:00");
        assertEquals(Util.formatTimeIndex(10), "10:00:00");
        assertEquals(Util.formatTimeIndex(18), "18:00:00");
    }
}
