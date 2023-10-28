package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodhub.MainActivity;
import com.example.foodhub.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeScreenActivity extends AppCompatActivity {

    TextView txtUserName, txtUserEmail;
    ImageView imgProfilePhoto;
    Button btnLogOut;
    FirebaseAuth auth;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        txtUserName = findViewById(R.id.txtUserName);
        txtUserEmail = findViewById(R.id.txtUserEmail);
        imgProfilePhoto = findViewById(R.id.imgProfilePhoto);
        btnLogOut = findViewById(R.id.btnLogOut);
        auth = FirebaseAuth.getInstance();

        btnLogOut.setOnClickListener(view -> {
//                FirebaseAuth.getInstance().signOut();
//            Toast.makeText(HomeScreenActivity.this, "Logout Button Clicked", Toast.LENGTH_SHORT).show();
            auth.signOut();
            startActivity(new Intent(HomeScreenActivity.this, MainActivity.class));
            finish();
        });

        Intent intent = getIntent();
        if (intent != null) {
            String userName = intent.getStringExtra("user_name");
            String userEmail = intent.getStringExtra("user_email");
            String userPhoto = intent.getStringExtra("user_photo");

            txtUserName.setText(userName);
            txtUserEmail.setText(userEmail);

            Glide
                    .with(this)
                    .load(userPhoto)
                    .into(imgProfilePhoto);
        }
    }
}