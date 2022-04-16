package com.example.front_sunhan.View.activity;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.front_sunhan.R;
import com.example.front_sunhan.View.fragment.BottomNaviMainFragment;
import com.example.front_sunhan.View.fragment.CommunityFragment;
import com.example.front_sunhan.View.fragment.HeartFragment;
import com.example.front_sunhan.View.fragment.MypageFragment;
import com.example.front_sunhan.View.fragment.FindStoreFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class BottomNavigationActivity extends AppCompatActivity {

    MypageFragment mypageFragment;
    HeartFragment heartFragment;
    FindStoreFragment findStoreFragment;
    CommunityFragment communityFragment;
    BottomNaviMainFragment bottomNaviMainFragment;
    NavigationBarView navigationBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi);

        mypageFragment = new MypageFragment();
        heartFragment = new HeartFragment();
        findStoreFragment = new FindStoreFragment();
        communityFragment = new CommunityFragment();
        bottomNaviMainFragment = new BottomNaviMainFragment();
        navigationBarView = findViewById(R.id.bottomNavi);


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
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, mypageFragment).commit();
                        return true;
                    case R.id.action_bottomnavi:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, bottomNaviMainFragment).commit();
                        return true;
                }
                    return false;
            }
        });
    }
}
