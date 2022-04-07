package com.life.hacker.uscrecapp.UtilTest;

import static org.junit.Assert.assertEquals;

import com.life.hacker.uscrecapp.Util;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatDateToStardardTest {
    @Test
    public void dateFormatTest() {
        Date date = null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        try {
            date = format.parse("2022-08-08");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assertEquals("2022-08-08", Util.formatDateToStardard(date));

        try {
            date = format.parse("2000-08-31");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assertEquals("2000-08-31", Util.formatDateToStardard(date));

        try {
            date = format.parse("1999-12-20");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assertEquals("1999-12-20", Util.formatDateToStardard(date));

        try {
            date = format.parse("0123-05-02");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assertEquals("0123-05-02", Util.formatDateToStardard(date));

        try {
            date = format.parse("0023-01-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assertEquals("0023-01-01", Util.formatDateToStardard(date));

        try {
            date = format.parse("0003-12-12");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assertEquals("0003-12-12", Util.formatDateToStardard(date));
    }
}
