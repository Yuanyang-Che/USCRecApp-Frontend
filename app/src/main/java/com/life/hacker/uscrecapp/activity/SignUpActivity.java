package com.life.hacker.uscrecapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.life.hacker.uscrecapp.R;
import com.life.hacker.uscrecapp.Util;
import com.life.hacker.uscrecapp.network.MessageCenter;

import java.io.IOException;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    private EditText email;
    private EditText uscid;
    private EditText password;
    private EditText username;
    private Button signUp;
    private Button toLogin;
    private ImageView imageView;
    private TextView errorMsg;

    private final int SELECT_PHOTO = 1;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.etEmail);
        uscid = findViewById(R.id.etUscId);
        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);

        signUp = findViewById(R.id.btnSignUp);
        signUp.setOnClickListener(view ->
                signUp(email.getText().toString(),
                        uscid.getText().toString(),
                        username.getText().toString(),
                        password.getText().toString()));

        errorMsg = findViewById(R.id.signupErrorMsg);

        toLogin = findViewById(R.id.btnToLogin);
        toLogin.setOnClickListener(view -> startActivity(new Intent(SignUpActivity.this, LoginActivity.class)));


        imageView = findViewById(R.id.etAvatar);
        imageView.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");

            galleryActivityResultLauncher.launch(intent);

            //startActivityForResult(intent, SELECT_PHOTO);
        });
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

    private final ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    //here we will handle the result of our intent
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        //image picked
                        //get uri of image
                        Intent data = result.getData();
                        Uri imageUri = Objects.requireNonNull(data).getData();

                        imageView.setImageURI(imageUri);
                    } else {
                        //cancelled
                        Toast.makeText(SignUpActivity.this, "Cancelled...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    private void signUp(String email, String id, String username, String pw) {
        if (!Util.validEmail(email)) {
            runOnUiThread(() -> errorMsg.setText("Invalid Email Address"));
            return;
        }

        if (!Util.validNetID(id)) {
            runOnUiThread(() -> errorMsg.setText("Invalid Net ID"));
            return;
        }

        if (username.isEmpty()) {
            runOnUiThread(() -> errorMsg.setText("Invalid Username"));
            return;
        }

        if (pw.isEmpty()) {
            runOnUiThread(() -> errorMsg.setText("Invalid password"));
            return;
        }

        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        byte[] imageInByte = Util.compressBitMapToBytes(bitmap);

        //TODO
//        email = "test@usc.edu";
//        id = "123";
//        username = "test";
//        pw = "pw";

        MessageCenter.getInstance().SignupRequest(email,
                id, username, pw, imageInByte, SignUpActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();

            try {
                ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), uri);
                Bitmap bitmap = ImageDecoder.decodeBitmap(source);
                imageView.setImageBitmap(bitmap);
            } catch (IOException fnfe) {
                fnfe.printStackTrace();
            }
        }
    }
}