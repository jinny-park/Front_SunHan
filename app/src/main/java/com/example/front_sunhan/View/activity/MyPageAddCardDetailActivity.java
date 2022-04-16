package com.example.front_sunhan.View.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.front_sunhan.R;

public class MyPageAddCardDetailActivity extends AppCompatActivity {
    Button addCardDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_add_card_detail);

        addCardDetail = findViewById(R.id.add_card_detail_btn);
        addCardDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), BottomNavigationActivity.class);
//                startActivity(intent);
            }
        });
    }
}
