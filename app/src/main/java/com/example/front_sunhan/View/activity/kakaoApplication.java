package com.example.front_sunhan.View.activity;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class kakaoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        KakaoSdk.init(this,"11b33a8e699d97bd408aa024de68650b");
    }
}
