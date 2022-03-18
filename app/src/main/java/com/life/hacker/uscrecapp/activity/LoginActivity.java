package com.life.hacker.uscrecapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.life.hacker.uscrecapp.R;
import com.life.hacker.uscrecapp.network.MessageCenter;

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
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

    public void takeErrorMessage(){

    }

    private void validate(String email, String userPassword) {
        email = "realchen@usc.edu";
        userPassword = "12345678";
        //LoginActivity.this.startActivity(new Intent(LoginActivity.this, MapsActivity.class));
        MessageCenter.GetInstance().LoginRequest(email, userPassword, LoginActivity.this);

        //For now, always success
        //startActivity(new Intent(LoginActivity.this, MapsActivity.class));
    }
}

