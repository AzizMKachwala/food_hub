package com.example.foodhub;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodhub.R;

public class VerificationActivity extends AppCompatActivity {

    TextView txtVerificationSentTo, txtResendCode;
    EditText etvOtp1,etvOtp2,etvOtp3,etvOtp4;
    Button btnSubmit;
    ImageView imgBack;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        txtVerificationSentTo = findViewById(R.id.txtVerificationSentTo);
        txtResendCode = findViewById(R.id.txtResendCode);
        etvOtp1 = findViewById(R.id.etvOtp1);
        etvOtp2 = findViewById(R.id.etvOtp2);
        etvOtp3 = findViewById(R.id.etvOtp3);
        etvOtp4 = findViewById(R.id.etvOtp4);
        btnSubmit = findViewById(R.id.btnSubmit);
        imgBack = findViewById(R.id.imgBack);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        VerificationNo = getIntent().getStringExtra("phone");
//        String Country = getIntent().getStringExtra("countryId");
        txtVerificationSentTo.setText(getIntent().getStringExtra("phone"));

        txtResendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(VerificationActivity.this, "Resend Code Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etvOtp1.getText().toString().isEmpty() && !etvOtp2.getText().toString().isEmpty()
                        && !etvOtp3.getText().toString().isEmpty() && !etvOtp4.getText().toString().isEmpty()) {
                    Toast.makeText(VerificationActivity.this, "OTP Entered", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(VerificationActivity.this, "Enter Valid OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}