package com.capsaicin.sunhan.View.activity;

//activity_sunhanst_store.xml

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.capsaicin.sunhan.Model.CardStoreDetailResponse;
import com.capsaicin.sunhan.Model.MenuItem;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.Model.SunHanStoreDetailResponse;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.adapter.MenuAdapter;
import com.capsaicin.sunhan.View.fragment.ChildrenStoreInfoFragment;
import com.capsaicin.sunhan.View.fragment.StoreInfoFragment;
import com.capsaicin.sunhan.View.fragment.StoreLetterFragment;
//import com.capsaicin.sunhan.View.fragment.StoreMenuFragment;
import com.google.android.material.tabs.TabLayout;

import com.google.gson.Gson;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreDetailActivity extends AppCompatActivity {
    public static MenuAdapter menuAdapter;
    ArrayList<MenuItem> menuList=new ArrayList<MenuItem>();
    RecyclerView StoreDetailRecyclerView;
    Toolbar toolbar;

    StoreInfoFragment storeInfoFragment;
    StoreLetterFragment storeLetterFragment;
    ChildrenStoreInfoFragment childrenStoreInfoFragment;
    private RetrofitInstance tokenRetrofitInstance ;

    //상세페이지의 두 하위 프래그먼트에서 쓰일 에정
    public static String id ;
    public static int whichStore;


    TextView storeName ;
    TextView storeAddress;

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

        storeName = findViewById(R.id.text_storename);
        storeAddress = findViewById(R.id.text_storeaddrs);

        heart_img=findViewById(R.id.heart_img);
        heart_full_img=findViewById(R.id.heart_full_img);
        tokenRetrofitInstance = RetrofitInstance.getRetrofitInstance();//싱글톤
        Intent intent = getIntent();
        id = intent.getStringExtra("_id");
        whichStore = intent.getIntExtra("whichStore",0);

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

        storeInfoFragment = new StoreInfoFragment();
        storeLetterFragment = new StoreLetterFragment();
        childrenStoreInfoFragment = new ChildrenStoreInfoFragment();

        getData();

        if(whichStore==0)
            getSupportFragmentManager().beginTransaction().replace(R.id.tabs_storedetail_container, childrenStoreInfoFragment).commit();
        else
            getSupportFragmentManager().beginTransaction().replace(R.id.tabs_storedetail_container, storeInfoFragment).commit();


        TabLayout tabs = findViewById(R.id.store_detail_tapLayout);

        tabs.addTab(tabs.newTab().setText("정보"));
        tabs.addTab(tabs.newTab().setText("감사편지"));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                Fragment selected = null;

                if(position == 0){
                    if(whichStore ==0) { //0일 경우 가맹점 정보 프래그먼트

                        selected = childrenStoreInfoFragment;
                    }
                    else{
                        // 1일경우 선한영향력정보 프래그먼트 -> 두개 정보다 달라서 따로 만듦
                        selected = storeInfoFragment;
                    }


                }
                else if(position==1) {
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


    private void getData()
    {
        if(tokenRetrofitInstance!=null && whichStore==0){
            Log.d("상세정보 id", id);
                Call<CardStoreDetailResponse> call = RetrofitInstance.getRetrofitService().getChildrenStoreDetail(id);
                call.enqueue(new Callback<CardStoreDetailResponse>() {
                    @Override
                    public void onResponse(Call<CardStoreDetailResponse> call, Response<CardStoreDetailResponse> response) {
                        if (response.isSuccessful()) {
                            CardStoreDetailResponse result = response.body();
                            String weektime =result.getCardStoreItem().getWeekdayStartTime()+"-"+result.getCardStoreItem().getWeekdayEndTime();
                            String weekendtime = result.getCardStoreItem().getWeekendStartTime()+"-"+result.getCardStoreItem().getWeekendEndTime();
                            String holidaytime = result.getCardStoreItem().getHolydayStartTime()+"-"+result.getCardStoreItem().getHolydayEndTime();

                            ChildrenStoreInfoFragment.storeName.setText(result.getCardStoreItem().getName());
                            storeName.setText(result.getCardStoreItem().getName());
                            ChildrenStoreInfoFragment.weekdayTime.setText(weektime);
                            ChildrenStoreInfoFragment.weekendTime.setText(weekendtime);
                            ChildrenStoreInfoFragment.holidayTime.setText(holidaytime);
                            ChildrenStoreInfoFragment.address.setText(result.getCardStoreItem().getAddress());
                            storeAddress.setText(result.getCardStoreItem().getAddress());
                            ChildrenStoreInfoFragment.phone.setText(result.getCardStoreItem().getPhoneNumber());
                            Log.d("성공", new Gson().toJson(response.body()));
                        } else {

                            Log.d("가맹점상세정보실패", response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<CardStoreDetailResponse> call, Throwable t) {
                        Log.d("REST ERROR!", t.getMessage());
                    }
                });
            } else if(tokenRetrofitInstance!=null && whichStore==1){
            Call<SunHanStoreDetailResponse> call = RetrofitInstance.getRetrofitService().getSunHansStoreDetail(id);
            call.enqueue(new Callback<SunHanStoreDetailResponse>() {
                @Override
                public void onResponse(Call<SunHanStoreDetailResponse> call, Response<SunHanStoreDetailResponse> response) {
                    if (response.isSuccessful()) {
                        SunHanStoreDetailResponse result = response.body();

                        storeName.setText(result.getSunHanDetailItem().getName());
                        storeAddress.setText(result.getSunHanDetailItem().getAddress());
                        StoreInfoFragment.sunhan_Name.setText(result.getSunHanDetailItem().getName());
                        StoreInfoFragment.sunhan_addr.setText(result.getSunHanDetailItem().getAddress());
                        StoreInfoFragment.sunhan_phone.setText(result.getSunHanDetailItem().getPhoneNumber());
                        StoreInfoFragment.sunhan_time.setText(result.getSunHanDetailItem().getOpeningHours());
                        StoreInfoFragment.sunhan_target.setText(result.getSunHanDetailItem().getTatget());
                        StoreInfoFragment.sunhan_offer.setText(result.getSunHanDetailItem().getOffer());

                        Log.d("성공", new Gson().toJson(response.body()));
                    } else {

                        Log.d("가맹점상세정보실패", response.message());
                    }
                }

                @Override
                public void onFailure(Call<SunHanStoreDetailResponse> call, Throwable t) {
                    Log.d("REST ERROR!", t.getMessage());
                }
            });


        }
        }


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