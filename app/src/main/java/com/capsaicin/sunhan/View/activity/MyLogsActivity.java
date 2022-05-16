package com.capsaicin.sunhan.View.activity;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.fragment.MyLogsCommentFragment;
import com.capsaicin.sunhan.View.fragment.MyLogsLetterFragment;
import com.capsaicin.sunhan.View.fragment.MyLogsPostFragment;
import com.google.android.material.tabs.TabLayout;

public class MyLogsActivity extends AppCompatActivity {

    Toolbar toolbar;

    MyLogsPostFragment myLogsPostFragment;
    MyLogsCommentFragment myLogsCommentFragment;
    MyLogsLetterFragment myLogsLetterFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);
        toolbar = findViewById(R.id.myLogs_toolbar);
        setToolbar();

        myLogsCommentFragment = new MyLogsCommentFragment();
        myLogsLetterFragment = new MyLogsLetterFragment();
        myLogsPostFragment = new MyLogsPostFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.myLogs_container, myLogsPostFragment).commit();

        TabLayout tabs = findViewById(R.id.myLogs_tab);
        tabs.addTab(tabs.newTab().setText("내가쓴게시글"));
        tabs.addTab(tabs.newTab().setText("내가쓴댓글"));
        tabs.addTab(tabs.newTab().setText("내가쓴편지"));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                Fragment selected = null;
                if(position == 0){
                    selected = myLogsPostFragment;
                } else if(position == 1){
                    selected = myLogsCommentFragment;
                } else {
                    selected = myLogsLetterFragment;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.myLogs_container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

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