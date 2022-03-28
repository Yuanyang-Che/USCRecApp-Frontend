package com.life.hacker.uscrecapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Util {

    public static final int Capacity = 2;

    /**
     * This function converts a timeIdx to hh:mm:ss format
     *
     * @param timeIdx, range from 0-23, the ith hour of a day
     * @return
     */
    public static String formatTimeIndex(int timeIdx) {
        return (timeIdx < 10) ? "0" : "" + timeIdx + ":00:00";
    }

    public static String convertTimeIdxToHour(int timeindex) {
        return timeindex + ":00 - " + (timeindex + 1) + ":00";
    }

    public static Bitmap decompress(byte[] arr) {
        if (arr == null) return null;
        return BitmapFactory.decodeByteArray(arr, 0, arr.length);
    }

    public static byte[] compress(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * Convert a date to yyyy-MM-dd format
     *
     * @param date
     * @return
     */
    public static String formatDateToStardard(Date date) {
        return formatDate(date, "yyyy-MM-dd");
    }

    public static String formatDate(Date date, String pattern) {
        DateFormat dateFormatter = new SimpleDateFormat(pattern, Locale.US);
        return dateFormatter.format(date);
    }

    public static void takeToastMessage(Context context, String message) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }
}
