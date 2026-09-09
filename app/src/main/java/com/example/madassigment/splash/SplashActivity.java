package com.example.madassigment.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.madassigment.R;
import com.example.madassigment.auth.LoginActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        ImageView logo = findViewById(R.id.splash_logo);
        TextView title = findViewById(R.id.splash_title);

        logo.setAlpha(0f);
        title.setAlpha(0f);

        logo.animate().alpha(1f).scaleX(1.2f).scaleY(1.2f).setDuration(1500).start();
        title.animate().alpha(1f).translationY(-50f).setDuration(1500).setStartDelay(500).start();

        new Handler().postDelayed(()->{
           Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
           startActivity(intent);
           finish();
        },3000);
    }
}
