package com.capsaicin.sunhan.View.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitServiceApi;
import com.capsaicin.sunhan.Model.UserResponse;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.fragment.BottomNaviMainFragment;
import com.capsaicin.sunhan.View.fragment.CommunityFragment;
import com.capsaicin.sunhan.View.fragment.FindStoreFragment;
import com.capsaicin.sunhan.View.fragment.HeartFragment;
import com.capsaicin.sunhan.View.fragment.MyPageFragment;
import com.capsaicin.sunhan.View.fragment.SunhanstMainFragment;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomNavigationActivity extends AppCompatActivity {

    MyPageFragment myPageFragment;
    HeartFragment heartFragment;
    FindStoreFragment findStoreFragment;
    CommunityFragment communityFragment;
    BottomNaviMainFragment bottomNaviMainFragment;
    NavigationBarView navigationBarView;
    SunhanstMainFragment sunhanstMainFragment ;
    Toolbar toolbar;
    private RetrofitInstance tokenRetrofitInstance ;
    private RetrofitServiceApi retrofitServiceApi;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi);

        tokenRetrofitInstance=RetrofitInstance.getRetrofitInstance(); //레트로핏 싱글톤

        intent = getIntent();

        myPageFragment = new MyPageFragment();
        heartFragment = new HeartFragment();
        findStoreFragment = new FindStoreFragment();
        communityFragment = new CommunityFragment();
        bottomNaviMainFragment = new BottomNaviMainFragment();
        sunhanstMainFragment = new SunhanstMainFragment();
        navigationBarView = findViewById(R.id.bottomNavi);

        toolbar = findViewById (R.id.toolbar);
        setToolbar();

        getSupportFragmentManager().beginTransaction().add(R.id.main_frame,sunhanstMainFragment).addToBackStack(null).commit();
        navigationBarView.setItemIconTintList(null);
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
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, sunhanstMainFragment).commit();
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
        actionBar.setDisplayHomeAsUpEnabled (true); // 앱바에 뒤로가기 버튼 만들기
    }

//    public void setActionBarTitle(String title) {
//        setSupportActionBar (toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setTitle(title);
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //select back button
                finish();
                return true;
            case R.id.location_search:
                Intent intent=new Intent(getApplicationContext(), LocationSettingActivity.class);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
