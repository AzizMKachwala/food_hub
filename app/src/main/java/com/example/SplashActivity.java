package com.example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.foodhub.MainActivity;
import com.example.foodhub.R;

public class SplashActivity extends AppCompatActivity {

    ImageView splash_image,appName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splash_image = findViewById(R.id.splash_image);
        appName = findViewById(R.id.appName);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.zoom);
        splash_image.startAnimation(animation);
        appName.setAnimation(animation1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 3000);
    }
}