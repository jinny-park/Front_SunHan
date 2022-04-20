package com.example.front_sunhan.View.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.front_sunhan.R;
import com.example.front_sunhan.View.fragment.CommunityDetailFragment;

public class CommunityDetailActivity extends AppCompatActivity {

    Toolbar toolbar;
//    CommunityDetailFragment CommunityDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        ImageView userProfile = findViewById(R.id.userProfile);;
        TextView userId = findViewById(R.id.userId);
        TextView uploadTime = findViewById(R.id.uploadTime);
        TextView content = findViewById(R.id.content);
        TextView commentNum = findViewById(R.id.commentNum);
        toolbar = findViewById(R.id.commu_detail_toolbar);
        setToolbar();

//        CommunityDetailFragment = new CommunityDetailFragment();

    }


    void setToolbar(){
        setSupportActionBar (toolbar); //액티비티의 앱바(App Bar)로 지정
        ActionBar actionBar = getSupportActionBar (); //앱바 제어를 위해 툴바 액세스
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setTitle("메인툴바");
        actionBar.setDisplayHomeAsUpEnabled (true); // 앱바에 뒤로가기 버튼 만들기
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //select back button
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
