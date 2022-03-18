package com.life.hacker.uscrecapp.model;

import android.media.Image;

public class User {
    public User(String e, String u, String p, int i) {
        email = e;
        username = u;
        password = p;
        id = i;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email, username, password;
    private int id;
    private Image avatar;
}
