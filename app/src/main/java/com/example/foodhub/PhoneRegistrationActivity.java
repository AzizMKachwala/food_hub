package com.example.foodhub;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

public class PhoneRegistrationActivity extends AppCompatActivity {

    EditText etvPhoneNo;
    Button btnGetOtp;
    ImageView imgBack;
    CountryCodePicker countryId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_registration);

        etvPhoneNo = findViewById(R.id.etvPhoneNo);
        btnGetOtp = findViewById(R.id.btnGetOtp);
        imgBack = findViewById(R.id.imgBack);
        countryId = findViewById(R.id.countryId);
      //  countryId.registerCarrierNumberEditText(etvPhoneNo);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnGetOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etvPhoneNo.length() == 10){
                    Intent intent = new Intent(PhoneRegistrationActivity.this, VerificationActivity.class);
                    intent.putExtra("phone", etvPhoneNo.getText().toString().trim());
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(PhoneRegistrationActivity.this, "Enter 10 Digits Phone No.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}