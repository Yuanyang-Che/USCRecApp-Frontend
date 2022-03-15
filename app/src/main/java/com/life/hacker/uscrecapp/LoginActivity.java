package com.life.hacker.uscrecapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private EditText Name;
    private EditText Password;
    //private TextView Info;
    private Button Login;
    private Button ToSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Name = (EditText) findViewById(R.id.etEmail);
        Password = (EditText) findViewById(R.id.etPassword);
        Login = (Button) findViewById(R.id.btnLogin);
        ToSignUp = (Button) findViewById(R.id.btnToSignUp);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });

        ToSignUp.setOnClickListener(new View.OnClickListener() {
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

