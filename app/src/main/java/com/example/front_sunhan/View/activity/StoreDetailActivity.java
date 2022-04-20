package com.example.front_sunhan.View.activity;

//activity_sunhanst_store.xml

import android.os.Bundle;
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
        actionBar.setTitle("메인툴바");
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

        ImageView storePic = findViewById(R.id.store_pic);;
        TextView textStorename = findViewById(R.id.text_storename);
        TextView textStoreaddrs = findViewById(R.id.text_storeaddrs);

        ImageButton imgStoreFindroad = findViewById(R.id.img_store_findroad);
        TextView textStoreFindroad = findViewById(R.id.text_store_findroad);

        ImageButton imgStoreHeart = findViewById(R.id.img_store_heart);
        TextView textStoreHeart = findViewById(R.id.text_store_heart);

        ImageButton img_store_share = findViewById(R.id.img_store_share);
        TextView text_store_share = findViewById(R.id.text_store_share);

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
