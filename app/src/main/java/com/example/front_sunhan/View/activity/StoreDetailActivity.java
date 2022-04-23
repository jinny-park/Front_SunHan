package com.example.front_sunhan.View.activity;

//activity_sunhanst_store.xml

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front_sunhan.Model.MenuItem;
import com.example.front_sunhan.Model.StoreItem;
import com.example.front_sunhan.R;
import com.example.front_sunhan.View.adapter.MenuAdapter;
import com.example.front_sunhan.View.adapter.SunhanStoreAdapter;
import com.example.front_sunhan.View.fragment.StoreInfoFragment;
import com.example.front_sunhan.View.fragment.StoreLetterFragment;
import com.example.front_sunhan.View.fragment.StoreMenuFragment;
import com.example.front_sunhan.View.fragment.SunhanstCardFragment;
import com.example.front_sunhan.View.fragment.SunhanstSunhanFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class StoreDetailActivity extends AppCompatActivity {
    public static MenuAdapter menuAdapter;
    ArrayList<MenuItem> menuList=new ArrayList<MenuItem>();
    RecyclerView StoreDetailRecyclerView;
    Toolbar toolbar;

    StoreInfoFragment storeInfoFragment;
    StoreLetterFragment storeLetterFragment;
    StoreMenuFragment storeMenuFragment;

    void setToolbar(){
        setSupportActionBar (toolbar); //액티비티의 앱바(App Bar)로 지정
        ActionBar actionBar = getSupportActionBar (); //앱바 제어를 위해 툴바 액세스
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setTitle("가게 상세");
        actionBar.setDisplayHomeAsUpEnabled (true); // 앱바에 뒤로가기 버튼 만들기
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull android.view.MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //select back button
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sunhanst_store);
        toolbar = findViewById(R.id.store_detail_toolbar);
        setToolbar();

        TextView textStorename = findViewById(R.id.text_storename);
        TextView textStoreaddrs = findViewById(R.id.text_storeaddrs);

        ViewGroup findRoadLayout = (ViewGroup) findViewById(R.id.store_findroad);
        ViewGroup heartLayout = (ViewGroup) findViewById(R.id.store_heart);
        ViewGroup shareLayout = (ViewGroup) findViewById(R.id.store_share);

        findRoadLayout.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                // 네이버 지도 API 사용하기
                //Intent intent = new Intent(this, SubActivity.class);
                //startActivity(intent);
            }
        });

        heartLayout.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                // 가게 찜하기 ( 토스트메세지 + 가게 정보 )
            }
        });

        shareLayout.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                //가게 링크 내보내기
            }
        });


        // 탭레이아웃

        storeMenuFragment = new StoreMenuFragment();
        storeInfoFragment = new StoreInfoFragment();
        storeLetterFragment = new StoreLetterFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.tabs_storedetail_container, storeMenuFragment).commit();

        TabLayout tabs = findViewById(R.id.store_detail_tapLayout);

        tabs.addTab(tabs.newTab().setText("메뉴"));
        tabs.addTab(tabs.newTab().setText("정보"));
        tabs.addTab(tabs.newTab().setText("감사편지"));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                Fragment selected = null;

                if(position == 0){
                    selected = storeMenuFragment;
                }
                else if(position==1) {
                    selected = storeInfoFragment;
                }
                else if(position==2){
                    selected = storeLetterFragment;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.tabs_storedetail_container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {   }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {   }
        });

    }




}
