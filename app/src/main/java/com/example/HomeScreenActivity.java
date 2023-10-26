package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodhub.R;

public class HomeScreenActivity extends AppCompatActivity {

    TextView txtUserName,txtUserEmail;
    ImageView imgProfilePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        txtUserName = findViewById(R.id.txtUserName);
        txtUserEmail = findViewById(R.id.txtUserEmail);
        imgProfilePhoto = findViewById(R.id.imgProfilePhoto);

//        Intent intent = getIntent();
//        if(intent != null){
//            String userName = intent.getStringExtra("user_name");
//            String userEmail = intent.getStringExtra("user_email");
//            String userPhoto = intent.getStringExtra("user_photo");
//
//            txtUserName.setText("User Name :" + userName);
//            txtUserEmail.setText("User Email :" + userEmail);
//
//            Glide
//                    .with(this)
//                    .load(userPhoto)
//                    .into(imgProfilePhoto);
//        }
    }
}