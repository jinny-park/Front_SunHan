package com.example.front_sunhan.View.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.front_sunhan.Model.BlocekdItem;
import com.example.front_sunhan.Model.MypageItem;
import com.example.front_sunhan.Model.StoreItem;
import com.example.front_sunhan.R;
import com.example.front_sunhan.View.adapter.ManageBlockAdapter;
import com.example.front_sunhan.View.adapter.MypageAdapter;
import com.example.front_sunhan.View.adapter.SunhanStoreAdapter;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    ArrayList<MypageItem> mypageList = new ArrayList<>();
    ArrayList<BlocekdItem> blockedItemsList = new ArrayList<>();
    ArrayList<StoreItem> storeItemArrayList1= new ArrayList<>();
    ArrayList<StoreItem> storeItemArrayList2= new ArrayList<>();

    public static MypageAdapter mypageAdapter;
    public static ManageBlockAdapter manageBlockAdapter;
    public static SunhanStoreAdapter likedStoreAdapter1;
    public static SunhanStoreAdapter likedStoreAdapter2;
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
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    void setList(){
        mypageAdapter = new MypageAdapter(getApplicationContext(), mypageList);
        manageBlockAdapter = new ManageBlockAdapter (getApplicationContext(),blockedItemsList);
        likedStoreAdapter1 = new SunhanStoreAdapter(getApplicationContext(), storeItemArrayList1);
        likedStoreAdapter2 = new SunhanStoreAdapter(getApplicationContext(), storeItemArrayList2);
        setData();
    }

    void setData(){

        mypageAdapter.addItem(new MypageItem("알림설정"));
        mypageAdapter.addItem(new MypageItem("아동급식카드등록"));
        mypageAdapter.addItem(new MypageItem("내 활동보기"));
        mypageAdapter.addItem(new MypageItem("차단관리"));
        mypageAdapter.addItem(new MypageItem("로그아웃"));
        mypageAdapter.addItem(new MypageItem("탈퇴하기"));
        mypageAdapter.addItem(new MypageItem("약관및정책"));
        mypageAdapter.addItem(new MypageItem("버전정보 1.1"));


        manageBlockAdapter.addItem(new BlocekdItem("귤이"));
        manageBlockAdapter.addItem(new BlocekdItem("익명"));
        manageBlockAdapter.addItem(new BlocekdItem("이"));
        manageBlockAdapter.addItem(new BlocekdItem("박"));
        manageBlockAdapter.addItem(new BlocekdItem("수박"));
        manageBlockAdapter.addItem(new BlocekdItem("메론"));

        likedStoreAdapter1.addItem(new StoreItem("돈애랑장터순대국감자탕", "경기 수원시 영통구 동문3길 10",
                "0314299444","10:00-21:00"));
        likedStoreAdapter1.addItem(new StoreItem("낙원갈비집", "경기 수원시 영통구 1243 1층",
                "0314291234","11:30-22:00"));
        likedStoreAdapter1.addItem(new StoreItem("서브웨이", "경기 수원시 영통구 광교산로 22",
                "0314295687","7:00-22:00"));
        likedStoreAdapter1.addItem(new StoreItem("맘스터치", "경기 수원시 영통구 광교산로 154",
                "0314293333","9:00-21:30"));

        likedStoreAdapter2.addItem(new StoreItem("돈애랑장터순대국감자탕", "경기 수원시 영통구 동문3길 10",
                "0314299444","10:00-21:00"));
        likedStoreAdapter2.addItem(new StoreItem("낙원갈비집", "경기 수원시 영통구 1243 1층",
                "0314291234","11:30-22:00"));
        likedStoreAdapter2.addItem(new StoreItem("서브웨이", "경기 수원시 영통구 광교산로 22",
                "0314295687","7:00-22:00"));
        likedStoreAdapter2.addItem(new StoreItem("맘스터치", "경기 수원시 영통구 광교산로 154",
                "0314293333","9:00-21:30"));
    }
}
