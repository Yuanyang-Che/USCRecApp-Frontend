package com.life.hacker.uscrecapp;

import android.graphics.Bitmap;

import com.life.hacker.uscrecapp.model.User;

public class SessionData {
    //Store user info
    private User user = null;
    private String token = null;

    private static final class InstanceHolder {
        private static final SessionData instance = new SessionData();
    }

    public static SessionData getInstance() { return InstanceHolder.instance; }


    public void clearSession() {
        user = null; token = null;
    }

    public void setUser(String e, String u, String n, Bitmap m) {
        user = new User(e, u, n, m);
    }

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String t) {
        token = t;
    }

}
