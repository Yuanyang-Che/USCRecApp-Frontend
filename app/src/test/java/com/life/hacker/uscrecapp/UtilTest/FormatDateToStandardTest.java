package com.life.hacker.uscrecapp.UtilTest;

import static org.junit.Assert.assertEquals;

import com.life.hacker.uscrecapp.Util;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FormatDateToStandardTest {
    @Test
    public void dateFormatTest() {
        Date date = null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        List<String> dateStrings = Arrays.asList("2022-08-08", "2000-08-31", "1999-12-20",
                "0123-05-02", "0023-01-01", "0003-12-12");

        for (String dateString : dateStrings) {
            try {
                date = format.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            assertEquals(dateString, Util.formatDateToStandard(date));
        }
    }
}
