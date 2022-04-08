package com.example.front_sunhan.View.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.front_sunhan.R;

public class launcherActivity extends AppCompatActivity {
    private static int TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        final View myLayout = findViewById(R.id.launch);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(launcherActivity.this, longinActivity.class);
                startActivity(intent);
                finish();
            }
        }, TIME_OUT);
    }
}