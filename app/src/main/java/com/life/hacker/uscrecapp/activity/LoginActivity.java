package com.life.hacker.uscrecapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.life.hacker.uscrecapp.R;
import com.life.hacker.uscrecapp.SessionData;
import com.life.hacker.uscrecapp.Util;
import com.life.hacker.uscrecapp.network.MessageCenter;

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button login;
    private Button toSignUp;
    private TextView errorMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Try to login
        Util.retreiveSessionFromStorage(this);

        if (SessionData.getInstance().getUser() != null) {
            MessageCenter.getInstance().loginSuccess(LoginActivity.this);
        }

        email = findViewById(R.id.loginEditTextEmail);
        password = findViewById(R.id.loginEditTextPassword);
        login = findViewById(R.id.loginButtonLogin);
        toSignUp = findViewById(R.id.loginButtonToSignUp);
        errorMsg = findViewById(R.id.loginTextViewErrorMessage);

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                clearErrorMessage();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                clearErrorMessage();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        login.setOnClickListener(view -> {
            clearErrorMessage();
            validate(email.getText().toString(), password.getText().toString());
        });

        toSignUp.setOnClickListener(view -> {
            clearErrorMessage();
            toSignUp();
        });
    }

    private void toSignUp() {
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
    }

    private void validate(String email, String userPassword) {
        //Validate Email format first
        if (!Util.validEmail(email)) {
            runOnUiThread(() -> errorMsg.setText(R.string.loginInvalidEmail));
            return;
        }

        if (userPassword.isEmpty()) {
            runOnUiThread(() -> errorMsg.setText(R.string.loginInvalidPassword));
            return;
        }

        //TODO
//        email = "test@usc.edu";
//        userPassword = "pw";
        MessageCenter.getInstance().LoginRequest(email, userPassword, LoginActivity.this);
    }

    private void clearErrorMessage() {
        takeErrorMessage("");
    }

    public void takeErrorMessage(String msg) {
        runOnUiThread(() -> {
            try {
                errorMsg.setText(msg);
            } catch (Exception e) {
                // Do something
                e.printStackTrace();
            }
        });
    }
}

