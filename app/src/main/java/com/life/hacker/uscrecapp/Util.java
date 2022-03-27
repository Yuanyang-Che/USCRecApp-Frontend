package com.life.hacker.uscrecapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Util {
    public static String convertTimeIdx(int timeIdx) {
        StringBuilder sb = new StringBuilder();
        if (timeIdx < 10) {
            sb.append("0");
            sb.append(timeIdx);
            sb.append(":00:00");
        } else {
            sb.append(timeIdx);
            sb.append(":00:00");
        }
        return sb.toString();
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

    public static String formatDate(Date date) {
        String pattern = "MM/dd/yyyy";
        DateFormat dateFormatter = new SimpleDateFormat(pattern, Locale.US);

        return dateFormatter.format(date);
    }
}
