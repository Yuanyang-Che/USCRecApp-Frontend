package com.life.hacker.uscrecapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

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
}
