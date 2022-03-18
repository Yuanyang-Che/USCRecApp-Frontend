package com.life.hacker.uscrecapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.life.hacker.uscrecapp.network.MessageCenter;

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    //private TextView Info;
    private Button login;
    private Button toSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.etEmail);
        password = (EditText) findViewById(R.id.etPassword);
        login = (Button) findViewById(R.id.btnLogin);
        toSignUp = (Button) findViewById(R.id.btnToSignUp);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(email.getText().toString(), password.getText().toString());
            }
        });

        toSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toSignUp();
            }
        });
    }

    private void toSignUp() {
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
    }

    private void validate(String userName, String userPassword) {

        MessageCenter.GetInstance().LoginRequest(email.getText().toString(), password.getText().toString());

        //For now, always success
        startActivity(new Intent(LoginActivity.this, MapsActivity.class));
//
//        if ((userName.equals("Admin")) && (userPassword.equals("1234"))) {
////            Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
////            startActivity(intent);
//        } else {
//            //counter--;
//
//            //Info.setText("No of attempts remaining: " + String.valueOf(counter));
//
////            if (counter == 0) {
////                Login.setEnabled(false);
////            }
    }
}

