package com.capsaicin.sunhan.View.activity;

//activity_sunhanst_store.xml

//import static com.capsaicin.sunhan.Model.ScrapChildResponse.getScrapChildItem;
//import static com.capsaicin.sunhan.Model.ScrapsSunHanResponse.getScrapsItem;

import static com.capsaicin.sunhan.View.fragment.SunhanstMainFragment.storeId;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.capsaicin.sunhan.Model.CardStoreDetailResponse;
import com.capsaicin.sunhan.Model.LikedChildItem;
import com.capsaicin.sunhan.Model.LikedSunHanItem;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.Model.ScrapChildItem;
import com.capsaicin.sunhan.Model.ScrapOnOffResponse;
import com.capsaicin.sunhan.Model.ScrapsSunHanItem;
import com.capsaicin.sunhan.Model.SunHanStoreDetailResponse;
import com.capsaicin.sunhan.R;
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

    ImageView heart_img;
    ImageView heart_full_img;
    int imageIndex=0;

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

        ViewGroup letterLayout = (ViewGroup) findViewById(R.id.store_letter);
        ViewGroup heartLayout = (ViewGroup) findViewById(R.id.store_heart);
        ViewGroup shareLayout = (ViewGroup) findViewById(R.id.store_share);

        Log.d("imageIndex", String.valueOf(imageIndex));


        ArrayList<LikedChildItem> childArray = (ArrayList<LikedChildItem>) getIntent().getSerializableExtra("childArray");
        ArrayList<LikedSunHanItem> sunhanArray = (ArrayList<LikedSunHanItem>) getIntent().getSerializableExtra("sunhanArray");

        Log.d("child array", String.valueOf(childArray));
        Log.d("sunhan array", String.valueOf(sunhanArray));


        if (whichStore == 0 && childArray!=null) {
            //찜한가게 리사이클러뷰에 id가 포함된 경우 풀하트로 변경 (imageIndex=1)
            //ArrayList<ScrapChildItem> childArray = (ArrayList<ScrapChildItem>) getIntent().getSerializableExtra("scrapChild");
            //String childId = intent.getStringExtra("scrapChild");

            try{
                if (childArray.contains(id)) {
                    imageIndex = 1;
                    heart_img.setVisibility(View.INVISIBLE);
                    heart_full_img.setVisibility(View.VISIBLE);
                } else { //찜한가게 리사이클러뷰에 id가 포함 안 된 경우 빈하트로 변경 (imageIndex=0)
                    Log.d("imageIndex 아이디 체크", "childArray!=id\n"+"childArray="+String.valueOf(childArray)+"\nid="+id);
                    imageIndex = 0;
                    heart_img.setVisibility(View.VISIBLE);
                    heart_full_img.setVisibility(View.INVISIBLE);
                }

            } catch (NullPointerException e){
                imageIndex = 0;
                heart_img.setVisibility(View.VISIBLE);
                heart_full_img.setVisibility(View.INVISIBLE);
            }
        }

        else if (whichStore == 1 && sunhanArray!=null) {
            //ArrayList<ScrapsSunHanItem> sunhanArray = (ArrayList<ScrapsSunHanItem>) getIntent().getSerializableExtra("scrapSunhan");
            //String sunhanId = intent.getStringExtra("scrapSunhan");

            try {
                if (sunhanArray.contains(id)) {
                    imageIndex = 1;
                    heart_img.setVisibility(View.INVISIBLE);
                    heart_full_img.setVisibility(View.VISIBLE);
                } else {
                    Log.d("imageIndex 아이디 체크", "sunhanArray!=id\n"+"sunhanArray="+String.valueOf(sunhanArray)+"\nid="+id);
                    imageIndex = 0;
                    heart_img.setVisibility(View.VISIBLE);
                    heart_full_img.setVisibility(View.INVISIBLE);
                }
            } catch (NullPointerException e) {
                imageIndex = 0;
                heart_img.setVisibility(View.VISIBLE);
                heart_full_img.setVisibility(View.INVISIBLE);
            }
        }


        if(imageIndex==1){
            heart_img.setVisibility(View.INVISIBLE);
            heart_full_img.setVisibility(View.VISIBLE);
        } else if(imageIndex==0) {
            heart_img.setVisibility(View.VISIBLE);
            heart_full_img.setVisibility(View.INVISIBLE);
        }


        heart_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                heart_img.setVisibility(View.INVISIBLE);
                heart_full_img.setVisibility(View.VISIBLE);
                imageIndex = 1;
                Log.d("imageIndex", String.valueOf(imageIndex));
                // imageIndex ==0 -> imageIndex==1
                // 빈 하트를 눌렀을 때 스크랩 등록
                getHeartOnData();
                /*if(savedInstanceState != null){
                    imageIndex = savedInstanceState.getString("imageIndex", "1");
                }
                SharedPreferences sf=getSharedPreferences("imageIndex", MODE_PRIVATE);
                if(imageIndex!=null){
                    imageIndex=sf.getString("1", "0");
                }*/
            }
        });

        heart_full_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                heart_img.setVisibility(View.VISIBLE);
                heart_full_img.setVisibility(View.INVISIBLE);
                imageIndex = 0;
                Log.d("imageIndex", String.valueOf(imageIndex));
                // imageIndex ==1 -> imageIndex==0
                // 찬 하트를 눌렀을때 스크랩 취소
                getHeartOffData();
                /*if(savedInstanceState != null){
                    imageIndex = savedInstanceState.getString("imageIndex", "0");
                }
                SharedPreferences sf=getSharedPreferences("imageIndex", MODE_PRIVATE);
                if(imageIndex!=null){
                    imageIndex=sf.getString("1", "0");
                }*/
            }
        });


        shareLayout.setOnClickListener(new View.OnClickListener() { // 카카오톡 공유하기
            @Override
            public void onClick(View view) {
                ShareBtnClick(view);
            }
        });


        letterLayout.setOnClickListener(new View.OnClickListener() { // 편지쓰기로 이동하기
            @Override public void onClick(View v) {
                if(LoginActivity.userAccessToken!=null){
                    Intent intent = new Intent(StoreDetailActivity.this, WriteLetterActivity.class);
                    intent.putExtra("_id",id);
                    intent.putExtra("whichStore", whichStore);
                    startActivity(intent);
                }else{ // 로그인 안 하면 다이얼로그 뜸
                    AlertDialog.Builder dlg = new AlertDialog.Builder(StoreDetailActivity.this);
                    dlg.setMessage("로그인 후 이용해주세요!"); // 메시지
                    dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dlg.show();
                }
            }
        });


        // 탭레이아웃

        storeInfoFragment = new StoreInfoFragment();
        storeLetterFragment = new StoreLetterFragment();
        childrenStoreInfoFragment = new ChildrenStoreInfoFragment();

        getDetailData();

        if(whichStore==0)
            getSupportFragmentManager().beginTransaction().replace(R.id.tabs_storedetail_container, childrenStoreInfoFragment).commit();
        else
            getSupportFragmentManager().beginTransaction().replace(R.id.tabs_storedetail_container, storeInfoFragment).commit();

        //getLetterData();

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

    /*@Override
    protected void onStop() {
        super.onStop();

        // Activity가 종료되기 전에 저장한다.
        //SharedPreferences를 sFile이름, 기본모드로 설정
        SharedPreferences sharedPreferences = getSharedPreferences("imageIndex",MODE_PRIVATE);

        //저장을 하기위해 editor를 이용하여 값을 저장시켜준다.
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //String imageIndex = imageIndex.getText().toString(); // 사용자가 입력한 저장할 데이터
        editor.putString("imageIndex",imageIndex); // key, value를 이용하여 저장하는 형태

        editor.commit();

    }

    // indexImage 값 저장
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("TAG", "!!!onSavedInstanceState Called!!!");
        outState.putString("imageIndex",imageIndex);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("TAG", "!!!onRestoreInstanceState Called!!!");
        //outState.getString("imageIndex",imageIndex);
        imageIndex = savedInstanceState.getString("imageIndex", imageIndex);
    }*/

    /*protected void onPause(){
        super.onPause();
        SharedPreferences imageIndex = getSharedPreferences("imageIndex", imageIndex);

    }*/


    private void getDetailData()
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
                            ChildrenStoreInfoFragment.text_name.setText("가게이름: ");
                            ChildrenStoreInfoFragment.text_address.setText("가게주소: ");
                            ChildrenStoreInfoFragment.text_phone.setText("가게번호: ");
                            ChildrenStoreInfoFragment.text_weekday.setText("평일운영: ");
                            ChildrenStoreInfoFragment.text_weekend.setText("주말운영: ");
                            ChildrenStoreInfoFragment.text_holiday.setText("공휴일운영: ");
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
            } else if(tokenRetrofitInstance!=null && whichStore==1){ // 선한영향력
            Call<SunHanStoreDetailResponse> call = RetrofitInstance.getRetrofitService().getSunHansStoreDetail(id);
            call.enqueue(new Callback<SunHanStoreDetailResponse>() {
                @Override
                public void onResponse(Call<SunHanStoreDetailResponse> call, Response<SunHanStoreDetailResponse> response) {
                    if (response.isSuccessful()) {
                        SunHanStoreDetailResponse result = response.body();

                        StoreInfoFragment.text_sunhan_offer.setText("제공음식: ");
                        StoreInfoFragment.text_sunhan_Name.setText("가게이름: ");
                        StoreInfoFragment.text_sunhan_addr.setText("가게주소: ");
                        StoreInfoFragment.text_sunhan_target.setText("제공대상: ");
                        StoreInfoFragment.text_sunhan_time.setText("운영시간: ");
                        StoreInfoFragment.text_sunhan_phone.setText("가게번호: ");
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
        actionBar.setDisplayHomeAsUpEnabled (true); // 앱바에 뒤로가기 버튼 만들기
    }

    public void ShareBtnClick(View view) {
        if (whichStore == 0) {

            share_kakao_template();

        } else if (whichStore==1) {

            String name = storeName.getText().toString();
            String address = storeAddress.getText().toString();

            FeedTemplate params = FeedTemplate
                    .newBuilder(ContentObject.newBuilder(name + "을 확인해보세요!",
                            "https://ifh.cc/g/fT1GYp.png",
                            LinkObject.newBuilder().setWebUrl("https://developers.kakao.com")
                                    .setMobileWebUrl("https://developers.kakao.com").build())
                            .setDescrption(address)
                            .build())
                    .addButton(new ButtonObject("가맹점 보러 가기", LinkObject.newBuilder()
                            .setWebUrl("https://www.선한영향력가게.com")
                            .setMobileWebUrl("https://www.선한영향력가게.com")
                            .setAndroidExecutionParams("key1=value1")
                            .setIosExecutionParams("key1=value1")
                            .build()))
                    .build();

            Map<String, String> serverCallbackArgs = new HashMap<String, String>();
            serverCallbackArgs.put("user_id", "${current_user_id}");
            serverCallbackArgs.put("product_id", "${shared_product_id}");


            KakaoLinkService.getInstance().sendDefault(this, params, new ResponseCallback<KakaoLinkResponse>() {
                @Override
                public void onFailure(ErrorResult errorResult) {
                    Log.d("kakao share", "카카오톡 공유하기 실패\n" + errorResult);
                }

                @Override
                public void onSuccess(KakaoLinkResponse result) {
                    Log.d("kakao share", "카카오톡 공유 성공!");
                }
            });
        }
    }

    public void share_kakao_template(){
        // 템플릿 종류 :  피트, 리스크, 커머스
        String templateId = "77055"; // 템플릿 피드아이디

        String name = storeName.getText().toString();
        String address = storeAddress.getText().toString();

        // 템플릿에서  사용할 데이터 맵
        Map<String, String> templateArgs = new HashMap<String, String>();
        //templateArgs.put("APP_KEY", APP_KEY);
        templateArgs.put("title",name+ "을 확인해보세요");
        templateArgs.put("content",address);

        KakaoLinkService.getInstance().sendCustom(this, templateId, templateArgs, new ResponseCallback<KakaoLinkResponse>() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                Log.e("EOTEST",errorResult.toString());
            }

            @Override
            public void onSuccess(KakaoLinkResponse result) {
                // 템플릿 밸리데이션과 쿼터 체크가 성공적으로 끝남
                Log.d("kakao share", "카카오톡 공유 성공!");
            }
        });

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

/*

    public void onEmptyHeartClicked(View v){
        heart_img.setVisibility(View.INVISIBLE);
        heart_full_img.setVisibility(View.VISIBLE);
        imageIndex = 1;
        Log.d("imageIndex", String.valueOf(imageIndex));
        // imageIndex ==0 -> imageIndex==1
        // 빈 하트를 눌렀을 때 스크랩 등록
        getHeartOnData();

    }

    public void onFullHeartClicked(View v){
        heart_img.setVisibility(View.VISIBLE);
        heart_full_img.setVisibility(View.INVISIBLE);
        imageIndex = 0;
        Log.d("imageIndex", String.valueOf(imageIndex));
        // imageIndex ==1 -> imageIndex==0
        // 찬 하트를 눌렀을때 스크랩 취소
        getHeartOffData();
    }
*/

    private void getHeartOnData()
    {
        if(tokenRetrofitInstance!=null && whichStore==0){
            Call<ScrapOnOffResponse> call = RetrofitInstance.getRetrofitService().getChildrenScrapsOn("Bearer "+LoginActivity.userAccessToken,id, "children");
            call.enqueue(new Callback<ScrapOnOffResponse>() {
                @Override
                public void onResponse(Call<ScrapOnOffResponse> call, Response<ScrapOnOffResponse> response) {
                    if (response.isSuccessful()) {
                        ScrapOnOffResponse result = response.body();
                        Log.d("찜한 아동 급식 가게 등록", new Gson().toJson(response.body()));
                    } else {
                        Log.d("REST FAILED MESSAGE", response.message());
                    }
                }

                @Override
                public void onFailure(Call<ScrapOnOffResponse> call, Throwable t) {
                    Log.d("REST ERROR!", t.getMessage());
                }

            });
        } else if(tokenRetrofitInstance!=null && whichStore==1){
            Call<ScrapOnOffResponse> call = RetrofitInstance.getRetrofitService().getSunHanScrapsOn("Bearer "+LoginActivity.userAccessToken, id, "sunhan");
            call.enqueue(new Callback<ScrapOnOffResponse>() {
                @Override
                public void onResponse(Call<ScrapOnOffResponse> call, Response<ScrapOnOffResponse> response) {
                    if (response.isSuccessful()) {
                        ScrapOnOffResponse result = response.body();
                        Log.d("찜한 선한 가게 등록", new Gson().toJson(response.body()));
                    } else {
                        Log.d("REST FAILED MESSAGE", response.message());
                    }
                }

                @Override
                public void onFailure(Call<ScrapOnOffResponse> call, Throwable t) {
                    Log.d("REST ERROR!", t.getMessage());
                }
            });


        }
    }

    private void getHeartOffData()
    {
        if(tokenRetrofitInstance!=null && whichStore==0){
            Call<ScrapOnOffResponse> call = RetrofitInstance.getRetrofitService().getChildrenScrapsOff("Bearer "+LoginActivity.userAccessToken, id,"children");
            call.enqueue(new Callback<ScrapOnOffResponse>() {
                @Override
                public void onResponse(Call<ScrapOnOffResponse> call, Response<ScrapOnOffResponse> response) {
                    if (response.isSuccessful()) {
                        ScrapOnOffResponse result = response.body();
                        Log.d("찜한 아동가맹점 가게 취소", new Gson().toJson(response.body()));
                    } else {
                        Log.d("REST FAILED MESSAGE", response.message());
                    }
                }

                @Override
                public void onFailure(Call<ScrapOnOffResponse> call, Throwable t) {
                    Log.d("REST ERROR!", t.getMessage());
                }
            });
        } else if(tokenRetrofitInstance!=null && whichStore==1){
            Call<ScrapOnOffResponse> call = RetrofitInstance.getRetrofitService().getSunHanScrapsOff("Bearer "+LoginActivity.userAccessToken,id, "sunhan");
            call.enqueue(new Callback<ScrapOnOffResponse>() {
                @Override
                public void onResponse(Call<ScrapOnOffResponse> call, Response<ScrapOnOffResponse> response) {
                    if (response.isSuccessful()) {
                        ScrapOnOffResponse result = response.body();
                        Log.d("찜한 선한 가게 취소", new Gson().toJson(response.body()));
                    } else {
                        Log.d("REST FAILED MESSAGE", response.message());
                    }
                }

                @Override
                public void onFailure(Call<ScrapOnOffResponse> call, Throwable t) {
                    Log.d("REST ERROR!", t.getMessage());
                }
            });


        }
    }



}