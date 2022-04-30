package com.capsaicin.sunhan.View.activity;

//activity_sunhanst_store.xml

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.capsaicin.sunhan.Model.MenuItem;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.adapter.MenuAdapter;
import com.capsaicin.sunhan.View.fragment.StoreInfoFragment;
import com.capsaicin.sunhan.View.fragment.StoreLetterFragment;
import com.capsaicin.sunhan.View.fragment.StoreMenuFragment;
import com.google.android.material.tabs.TabLayout;

import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.ButtonObject;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.FeedTemplate;
import com.kakao.message.template.LinkObject;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;

import java.util.HashMap;
import java.util.Map;

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

    public void btnClick(View view){

        TextView textStorename = findViewById(R.id.text_storename);
        String strStore = textStorename.getText().toString();
        strStore = getIntent().getStringExtra("strStore");
        //가게 이름 저장한거

        FeedTemplate params = FeedTemplate
                .newBuilder(ContentObject.newBuilder("SUNHAN",
                        "https://ifh.cc/g/GG1KNy.png",
                        LinkObject.newBuilder().setWebUrl("https://developers.kakao.com")
                                .setMobileWebUrl("https://developers.kakao.com").build())
                        .setDescrption(strStore+"\n가게를 확인해보세요!")
                        .build())
                .addButton(new ButtonObject("웹에서 보기", LinkObject.newBuilder().setWebUrl("https://developers.kakao.com").setMobileWebUrl("https://developers.kakao.com").build()))
                .addButton(new ButtonObject("앱에서 보기", LinkObject.newBuilder()
                        .setWebUrl("https://developers.kakao.com")
                        .setMobileWebUrl("https://developers.kakao.com")
                        .setAndroidExecutionParams("key1=value1")
                        .setIosExecutionParams("key1=value1")
                        .build()))
                .build();

        Map<String, String> serverCallbackArgs = new HashMap<String, String>();
        serverCallbackArgs.put("user_id", "${current_user_id}");
        serverCallbackArgs.put("product_id", "${shared_product_id}");


        KakaoLinkService.getInstance().sendDefault(this, params, new ResponseCallback <KakaoLinkResponse>() {
            @Override
            public void onFailure(ErrorResult errorResult) {}

            @Override
            public void onSuccess(KakaoLinkResponse result) {
            }
        });

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

/*    ImageView heart_img;
    ImageView heart_full_img;

    private int likeCount=0;
    private String likeAction="";*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sunhanst_store);
        toolbar = findViewById(R.id.store_detail_toolbar);
        setToolbar();

        heart_img=findViewById(R.id.heart_img);
        heart_full_img=findViewById(R.id.heart_full_img);

        TextView textStorename = findViewById(R.id.text_storename);
        TextView textStoreaddrs = findViewById(R.id.text_storeaddrs);

        ViewGroup findRoadLayout = (ViewGroup) findViewById(R.id.store_findroad);
        ViewGroup heartLayout = (ViewGroup) findViewById(R.id.store_heart);
        ViewGroup shareLayout = (ViewGroup) findViewById(R.id.store_share);

        /*heart_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(likeAction == ""){
                    likeCount += 1;
                    likeAction = "liked";
                    heart_img.setVisibility(View.VISIBLE);
                    heart_full_img.setVisibility(View.INVISIBLE);

                } else if (likeAction == "liked"){
                    likeCount -= 1;
                    likeAction = "";
                    heart_img.setVisibility(View.INVISIBLE);
                    heart_full_img.setVisibility(View.VISIBLE);
                }
            }
        });*/



        findRoadLayout.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                // 네이버 지도 API 사용하기
                //Intent intent = new Intent(this, SubActivity.class);
                //startActivity(intent);
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

    int imageIndex=0;
    ImageView heart_img;
    ImageView heart_full_img;

    public void onHeartClicked(View v){
        changeImage();
    }
    public void changeImage(){
        if (imageIndex == 0) {
            heart_img.setVisibility(View.VISIBLE);
            heart_full_img.setVisibility(View.INVISIBLE);
            imageIndex = 1;
        } else if (imageIndex== 1) {
            heart_img.setVisibility(View.INVISIBLE);
            heart_full_img.setVisibility(View.VISIBLE);
            imageIndex = 0;
        }

    }


}