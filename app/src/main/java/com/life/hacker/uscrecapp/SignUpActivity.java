package com.life.hacker.uscrecapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {
    private EditText Email;
    private EditText Name;
    private EditText Password;
    //private TextView Info;
    private Button SignUp;
    private Button ToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }
}