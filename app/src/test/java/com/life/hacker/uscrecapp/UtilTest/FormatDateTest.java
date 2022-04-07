package com.life.hacker.uscrecapp.UtilTest;

import static org.junit.Assert.assertEquals;

import com.life.hacker.uscrecapp.Util;

import org.junit.Test;

public class FormatDateTest {

    @Test
    public void timeFormatTestLessThan10() {
        assertEquals(Util.formatTimeIndex(1), "01:00:00");
    }

}
