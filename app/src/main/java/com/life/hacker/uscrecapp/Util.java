package com.life.hacker.uscrecapp;

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
}
