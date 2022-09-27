package com.capsaicin.sunhan.View.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.fragment.SunhanstSunhanFragment;

public class LauncherActivity extends AppCompatActivity {
    private static int TIME_OUT = 2000;
    private RetrofitInstance tokenRetrofitInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        tokenRetrofitInstance=RetrofitInstance.getRetrofitInstance(); //레트로핏 싱글톤

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if(LoginActivity.userAccessToken!=null){
                    SunhanstSunhanFragment.category="한식";
                    intent = new Intent(LauncherActivity.this, BottomNavigationActivity.class);
                }
                else{
                    intent = new Intent(LauncherActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, TIME_OUT);
    }

}