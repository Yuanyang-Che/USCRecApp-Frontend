package com.life.hacker.uscrecapp.model;

import android.graphics.Bitmap;
import android.media.Image;

public class User {
    public User(String e, String u, String n, Bitmap m) {
        email = e;
        username = u;
        netid = n;
        avatar = m;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNetid() {
        return netid;
    }

    public void setNetid(String netid) {
        this.netid = netid;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email, username, netid;
    private Bitmap avatar;
}
