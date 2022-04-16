package com.example.front_sunhan.View.activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.front_sunhan.R;
import com.example.front_sunhan.View.fragment.BottomNaviMainFragment;
import com.example.front_sunhan.View.fragment.CommunityFragment;
import com.example.front_sunhan.View.fragment.HeartFragment;
import com.example.front_sunhan.View.fragment.MyPageFragment;
import com.example.front_sunhan.View.fragment.FindStoreFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarMenuView;
import com.google.android.material.navigation.NavigationBarView;
import androidx.appcompat.widget.Toolbar;
public class BottomNavigationActivity extends AppCompatActivity {

    MyPageFragment myPageFragment;
    HeartFragment heartFragment;
    FindStoreFragment findStoreFragment;
    CommunityFragment communityFragment;
    BottomNaviMainFragment bottomNaviMainFragment;
    NavigationBarView navigationBarView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi);

        myPageFragment = new MyPageFragment();
        heartFragment = new HeartFragment();
        findStoreFragment = new FindStoreFragment();
        communityFragment = new CommunityFragment();
        bottomNaviMainFragment = new BottomNaviMainFragment();
        navigationBarView = findViewById(R.id.bottomNavi);
        toolbar = findViewById (R.id.toolbar);

        setToolbar();

        getSupportFragmentManager().beginTransaction().add(R.id.main_frame,heartFragment).addToBackStack(null).commit();
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_heart:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, heartFragment).commit();
                        return true;
                    case R.id.action_findstore:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, findStoreFragment).commit();
                        return true;
                    case R.id.action_community:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, communityFragment).commit();
                        return true;
                    case R.id.action_mypage:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, myPageFragment).commit();
                        return true;
                    case R.id.action_bottomnavi:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, bottomNaviMainFragment).commit();
                        return true;
                }
                    return false;
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
