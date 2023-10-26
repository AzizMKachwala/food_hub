package com.example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodhub.R;

public class LoginActivity extends AppCompatActivity {

    ImageView imgBack, imgPasswordCloseEye;
    EditText etvEmailLI, etvPasswordLI;
    Button btnForgotPassword, btnLogin;
    TextView txtSignUp;
    CardView cvFacebook, cvGoogle;
    String password = "Hide";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imgBack = findViewById(R.id.imgBack);
        imgPasswordCloseEye = findViewById(R.id.imgPasswordCloseEye);
        etvEmailLI = findViewById(R.id.etvEmailLI);
        etvPasswordLI = findViewById(R.id.etvPasswordLI);
        btnForgotPassword = findViewById(R.id.btnForgotPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtSignUp = findViewById(R.id.txtSignUp);
        cvFacebook = findViewById(R.id.cvFacebook);
        cvGoogle = findViewById(R.id.cvGoogle);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imgPasswordCloseEye.setOnClickListener(v -> {
            if (password.equals("Hide")) {
                password = "Show";
                etvPasswordLI.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                etvPasswordLI.setSelection(etvPasswordLI.length());
                imgPasswordCloseEye.setImageResource(R.drawable.ceye);
            } else {
                password = "Hide";
                etvPasswordLI.setTransformationMethod(PasswordTransformationMethod.getInstance());
                etvPasswordLI.setSelection(etvPasswordLI.length());
                imgPasswordCloseEye.setImageResource(R.drawable.baseline_eye_24);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
            }
        });

        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Forgot Password", Toast.LENGTH_SHORT).show();
            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
                finish();
            }
        });

        cvFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "FACEBOOK LOGIN", Toast.LENGTH_SHORT).show();
            }
        });

        cvGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "GOOGLE LOGIN", Toast.LENGTH_SHORT).show();
            }
        });
    }
}