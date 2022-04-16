package com.example.front_sunhan.View.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.front_sunhan.Model.MypageItem;
import com.example.front_sunhan.R;
import com.example.front_sunhan.View.adapter.MypageEtcAdapter;
import com.example.front_sunhan.View.adapter.MypageMylogsAdapter;
import com.example.front_sunhan.View.adapter.MypageAdapter;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    ArrayList<MypageItem> mypageList = new ArrayList<>();
//    ArrayList<MypageItem> mypageMylogsList = new ArrayList<>();
//    ArrayList<MypageItem> mypageEtcList = new ArrayList<>();

    public static MypageAdapter mypageAdapter;
//    public static MypageMylogsAdapter mypageMylogsAdapter;
//    public static MypageEtcAdapter mypageEtcAdapter;
    Button kakao_btn;
    Button google_btn;
    Button naver_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setList();
        naver_btn = findViewById(R.id.login_naver);
        naver_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BottomNavigationActivity.class);
                startActivity(intent);
            }
        });
    }

    void setList(){
        mypageAdapter = new MypageAdapter(getApplicationContext(), mypageList);
//        mypageMylogsAdapter = new MypageMylogsAdapter(getApplicationContext(), mypageMylogsList);
//        mypageEtcAdapter = new MypageEtcAdapter(getApplicationContext(), mypageEtcList);
        setData();
    }

    void setData(){

        mypageAdapter.addItem(new MypageItem("알림설정"));
        mypageAdapter.addItem(new MypageItem("아동급식카드등록"));
        mypageAdapter.addItem(new MypageItem("차단관리"));
        mypageAdapter.addItem(new MypageItem("로그아웃"));
        mypageAdapter.addItem(new MypageItem("탈퇴하기"));

        mypageAdapter.addItem(new MypageItem("내 활동보기"));

        mypageAdapter.addItem(new MypageItem("약관"));
        mypageAdapter.addItem(new MypageItem("정책"));
    }
}
