package com.life.hacker.uscrecapp.UtilTest;

import static org.junit.Assert.assertEquals;

import com.life.hacker.uscrecapp.Util;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class DatePickerTest {
    @Test
    public void findNextDayCommonTest() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, 10, 15, 10, 59, 59);
        assertEquals("2022-11-16", Util.formatDateToStardard(Util.findNextDay(calendar.getTime())));
        calendar.set(2019, 1, 1, 9, 30, 27);
        assertEquals("2019-02-02", Util.formatDateToStardard(Util.findNextDay(calendar.getTime())));
    }

    @Test
    public void findPrevDayCommonTest() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, 10, 15, 10, 59, 59);
        assertEquals("2022-11-14", Util.formatDateToStardard(Util.findPrevDay(calendar.getTime())));
        calendar.set(2019, 1, 2, 9, 30, 27);
        assertEquals("2019-02-01", Util.formatDateToStardard(Util.findPrevDay(calendar.getTime())));
    }

    @Test
    public void findNextDayEdgeTest() {
        // Month Boundary
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, 10, 30, 10, 59, 59);
        assertEquals("2022-12-01", Util.formatDateToStardard(Util.findNextDay(calendar.getTime())));
        // Year Boundary
        calendar.set(2019, 11, 31, 9, 30, 27);
        assertEquals("2020-01-01", Util.formatDateToStardard(Util.findNextDay(calendar.getTime())));
    }

    @Test
    public void findPrevDayEdgeTest() {
        // Month Boundary
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, 10, 1, 10, 59, 59);
        assertEquals("2022-10-31", Util.formatDateToStardard(Util.findPrevDay(calendar.getTime())));
        // Year Boundary
        calendar.set(2019, 0, 1, 9, 30, 27);
        assertEquals("2018-12-31", Util.formatDateToStardard(Util.findPrevDay(calendar.getTime())));
    }
}
