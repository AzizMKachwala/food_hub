package com.example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodhub.MainActivity;
import com.example.foodhub.R;

public class SignUpActivity extends AppCompatActivity {

    EditText etvFullName, etvEmail, etvPassword;
    TextView txtAlreadyLogin;
    ImageView imgPasswordCloseEye;
    Button btnSignUp;
    CardView cvFacebook, cvGoogle;
    String password = "Hide";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etvFullName = findViewById(R.id.etvFullNameSU);
        etvEmail = findViewById(R.id.etvEmailSU);
        etvPassword = findViewById(R.id.etvPasswordSU);
        txtAlreadyLogin = findViewById(R.id.txtAlreadyLogin);
        imgPasswordCloseEye =findViewById(R.id.imgPasswordCloseEye);
        btnSignUp = findViewById(R.id.btnSignUp);
        cvFacebook = findViewById(R.id.cvFacebook);
        cvGoogle = findViewById(R.id.cvGoogle);

        imgPasswordCloseEye.setOnClickListener(v -> {
            if (password.equals("Hide")) {
                password = "Show";
                etvPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                etvPassword.setSelection(etvPassword.length());
                imgPasswordCloseEye.setImageResource(R.drawable.ceye);
            } else {
                password = "Hide";
                etvPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                etvPassword.setSelection(etvPassword.length());
                imgPasswordCloseEye.setImageResource(R.drawable.baseline_eye_24);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etvFullName.getText().toString().trim().isEmpty()){
                    etvFullName.setError("Enter Full Name");
                    etvFullName.requestFocus();
                }
                else if (!Tools.isValidEmail(etvEmail.getText().toString().trim())) {
                    etvEmail.setError("Email Address must contain @ and .com in it");
                    etvEmail.requestFocus();
                }
                else if (!isValidPassword(etvPassword.getText().toString().trim())) {
                    etvPassword.setError("Password Must Consist Of Minimum length of 7 with At-least 1 UpperCase," +
                            "1 LowerCase, 1 Number & 1 Special Character");
                    etvPassword.requestFocus();
                }
                else {
                    Toast.makeText(SignUpActivity.this, "ADD USER", Toast.LENGTH_SHORT).show();
                }
            }

            private boolean isValidPassword(String password) {
                return password.length() >= 7
                        && password.matches(".*[A-Z].*")
                        && password.matches(".*[a-z].*")
                        && password.matches(".*\\d.*")
                        && password.matches(".*[@#$%^&+=].*");
            }
        });

        txtAlreadyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                finish();
            }
        });

        cvFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignUpActivity.this, "FACEBOOK LOGIN", Toast.LENGTH_SHORT).show();
            }
        });

        cvGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignUpActivity.this, "GOOGLE LOGIN", Toast.LENGTH_SHORT).show();
            }
        });

    }
}