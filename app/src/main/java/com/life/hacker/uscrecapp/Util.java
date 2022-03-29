package com.life.hacker.uscrecapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.Toast;

import org.apache.commons.validator.routines.EmailValidator;

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
        return (timeIdx < 10 ? "0" : "") + timeIdx + ":00:00";
    }

    public static String convertTimeIdxToHour(int timeindex) {
        return timeindex + ":00 - " + (timeindex + 1) + ":00";
    }

    public static Bitmap decompressBytesToBitmap(byte[] arr) {
        if (arr == null) return null;
        return BitmapFactory.decodeByteArray(arr, 0, arr.length);
    }

    public static byte[] compressBitMapToBytes(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    public static String convertBitmapToString(Bitmap bitmap) {
        byte[] b = compressBitMapToBytes(bitmap);
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static Bitmap convertStringToBitmap(String strBase64) {
        byte[] imgBytes = Base64.decode(strBase64.getBytes(), Base64.DEFAULT);
        return decompressBytesToBitmap(imgBytes);
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

    private static String formatDate(Date date, String pattern) {
        DateFormat dateFormatter = new SimpleDateFormat(pattern, Locale.US);
        return dateFormatter.format(date);
    }

    public static void takeToastMessage(Context context, String message) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    private static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;

    public static Date findNextDay(Date date) {
        return new Date(date.getTime() + MILLIS_IN_A_DAY);
    }

    public static Date findPrevDay(Date date) {
        return new Date(date.getTime() - MILLIS_IN_A_DAY);
    }


    public static final String prefKey = "Pref",
            usernameKey = "username",
            netidKey = "netid",
            emailKey = "email",
            avatarKey = "avatar",
            tokenKey = "token";

    /**
     * Call after SessionData is populated
     */
    public static void storeSessionToStorage(Context context) {
        try {
            // Storing data into SharedPreferences
            SharedPreferences sharedPreferences = context.getSharedPreferences(Util.prefKey, Context.MODE_PRIVATE);

            // Creating an Editor object to edit(write to the file)
            SharedPreferences.Editor myEdit = sharedPreferences.edit();

            String username = SessionData.getInstance().getUser().getUsername();
            String email = SessionData.getInstance().getUser().getEmail();
            String netid = SessionData.getInstance().getUser().getNetid();
            Bitmap avatar = SessionData.getInstance().getUser().getAvatar();
            String avatarStr = Util.convertBitmapToString(avatar);
            String token = SessionData.getInstance().getToken();

            // Storing the key and its value as the data fetched from edittext
            myEdit.putString(Util.usernameKey, username);
            myEdit.putString(Util.emailKey, email);
            myEdit.putString(Util.netidKey, netid);
            myEdit.putString(Util.avatarKey, avatarStr);
            myEdit.putString(Util.tokenKey, token);

            // Once the changes have been made,
            // we need to commit to apply those changes made,
            // otherwise, it will throw an error
            myEdit.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Call at the beginning of our application
     */
    public static void retreiveSessionFromStorage(Context context) {
        SharedPreferences sh = context.getSharedPreferences(Util.prefKey, Context.MODE_PRIVATE);

        //This will leave SessionData all null
        if (!sh.contains(Util.tokenKey)) return;

        String username = sh.getString(Util.usernameKey, "");
        String email = sh.getString(Util.emailKey, "");
        String netid = sh.getString(Util.netidKey, "");
        String avatarStr = sh.getString(Util.avatarKey, "");
        Bitmap avatar = Util.convertStringToBitmap(avatarStr);

        String token = sh.getString(Util.tokenKey, "");

        SessionData.getInstance().setUser(email, username, netid, avatar);
        SessionData.getInstance().setToken(token);
    }

    public static void clearSessionFromStorage(Context context) {
        SharedPreferences sh = context.getSharedPreferences(Util.prefKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        editor.remove(Util.tokenKey);
        editor.remove(Util.usernameKey);
        editor.remove("orderId");
        editor.commit();
    }

    public static boolean validEmail(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }

    public static boolean validNetID(String netid) {
        //length == 10 and all number
        return (netid.length() == 10) && (netid.matches("[0-9]+"));
    }
}
