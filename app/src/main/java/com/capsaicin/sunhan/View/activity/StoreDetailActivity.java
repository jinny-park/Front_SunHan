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
import android.widget.Toast;

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
import com.capsaicin.sunhan.Model.ScrapChildResponse;
import com.capsaicin.sunhan.Model.ScrapOnOffResponse;
import com.capsaicin.sunhan.Model.ScrapsSunHanItem;
import com.capsaicin.sunhan.Model.ScrapsSunHanResponse;
import com.capsaicin.sunhan.Model.SunHanStoreDetailResponse;
import com.capsaicin.sunhan.Model.TokenResponse;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.fragment.ChildrenStoreInfoFragment;
import com.capsaicin.sunhan.View.fragment.StoreInfoFragment;
import com.capsaicin.sunhan.View.fragment.StoreLetterFragment;
//import com.capsaicin.sunhan.View.fragment.StoreMenuFragment;
import com.capsaicin.sunhan.View.fragment.SunhanstCardFragment;
import com.capsaicin.sunhan.View.fragment.SunhanstCategoryFragment;
import com.capsaicin.sunhan.View.fragment.SunhanstMainFragment;
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

    ArrayList<LikedChildItem> likedChildItems;
    ArrayList<LikedSunHanItem> scrapSunhan;

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

        ViewGroup letterLayout = (ViewGroup) findViewById(R.id.store_letter);
        ViewGroup shareLayout = (ViewGroup) findViewById(R.id.store_share);

        likedChildItems = new ArrayList<>();
        scrapSunhan = new ArrayList ();

        heart_img.setVisibility(View.VISIBLE);
        heart_full_img.setVisibility(View.INVISIBLE);

        if(whichStore==0)
            initLikedChildData();
        else
            initLikedSunhanData();

        heart_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LoginActivity.userAccessToken!=null) {
                    heart_img.setVisibility(View.INVISIBLE);
                    heart_full_img.setVisibility(View.VISIBLE);
                    // imageIndex ==0 -> imageIndex==1
                    // 빈 하트를 눌렀을 때 스크랩 등록
                    getHeartOnData();
                } else{
                    AlertDialog.Builder dlg = new AlertDialog.Builder(StoreDetailActivity.this);
                    dlg.setMessage("로그인 후 이용해주세요!");
                    dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dlg.show();
                    heart_img.setVisibility(View.VISIBLE);
                    heart_full_img.setVisibility(View.INVISIBLE);
                }

            }
        });

        heart_full_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                heart_img.setVisibility(View.VISIBLE);
                heart_full_img.setVisibility(View.INVISIBLE);
                // imageIndex ==1 -> imageIndex==0
                // 찬 하트를 눌렀을때 스크랩 취소
                getHeartOffData();

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
        TabLayout tabs = findViewById(R.id.store_detail_tapLayout);

        tabs.addTab(tabs.newTab().setText("정보"));
        tabs.addTab(tabs.newTab().setText("감사편지"));

        storeInfoFragment = new StoreInfoFragment();
        childrenStoreInfoFragment = new ChildrenStoreInfoFragment();

        getDetailData();

        if(whichStore==0)
            getSupportFragmentManager().beginTransaction().add(R.id.tabs_storedetail_container, childrenStoreInfoFragment).commit();
        else
            getSupportFragmentManager().beginTransaction().add(R.id.tabs_storedetail_container, storeInfoFragment).commit();

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if(position == 0){
                    if(whichStore ==0) { //0일 경우 가맹점 정보 프래그먼트
                        screenChange(childrenStoreInfoFragment);
                    }
                    else{
                        // 1일경우 선한영향력정보 프래그먼트 -> 두개 정보다 달라서 따로 만듦
                        screenChange(storeInfoFragment);
                    }
                }
                else if(position==1) {
                    if(storeLetterFragment==null){
                        storeLetterFragment = new StoreLetterFragment();
                        getSupportFragmentManager().beginTransaction().add(R.id.tabs_storedetail_container, storeLetterFragment).commit();
                    }
                    screenChange(storeLetterFragment);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {   }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {   }
        });
    }

    private void screenChange(Fragment fragment){
        allHideScreens();
        if(fragment!=null)
            getSupportFragmentManager().beginTransaction().show(fragment).commit();
    }

    private void allHideScreens(){
        if(whichStore==0){
            if(childrenStoreInfoFragment!=null)
                getSupportFragmentManager().beginTransaction().hide(childrenStoreInfoFragment).commit();
        }else{
            if(storeInfoFragment!=null)
                getSupportFragmentManager().beginTransaction().hide(storeInfoFragment).commit();
        }
        if(storeLetterFragment!=null)
            getSupportFragmentManager().beginTransaction().hide(storeLetterFragment).commit();
    }


    private void initLikedSunhanData() { //찜한 선한영향력 가게인지 판단
        if(LoginActivity.userAccessToken!=null){
            if(tokenRetrofitInstance!=null){
                Call<ScrapsSunHanResponse> call = RetrofitInstance.getRetrofitService().getSunHanScraps("Bearer "+LoginActivity.userAccessToken,"sunhan");
                call.enqueue(new Callback<ScrapsSunHanResponse>() {
                    @Override
                    public void onResponse(Call<ScrapsSunHanResponse> call, Response<ScrapsSunHanResponse> response) {
                        if (response.isSuccessful()) {
                            ScrapsSunHanResponse result = response.body();
                            scrapSunhan = result.getScrapsItem().getScrapSunhan();
                            for (LikedSunHanItem scrap: scrapSunhan) {
                                if (scrap.get_id().indexOf(id) != -1) { // 검색어가 존재함
                                    heart_img.setVisibility(View.INVISIBLE);
                                    heart_full_img.setVisibility(View.VISIBLE);
                                    return;
                                }
                                heart_img.setVisibility(View.VISIBLE);
                                heart_full_img.setVisibility(View.INVISIBLE);
                            }

                        } else {
                            if(response.message().equals("Unauthorized")){
                                checkAuthorized();
                                initLikedSunhanData();
                            }
                            Log.d("REST FAILED MESSAGE", response.message());
                        }
                    }
                    @Override
                    public void onFailure(Call<ScrapsSunHanResponse> call, Throwable t) {
                        Log.d("REST ERROR!", t.getMessage());
                        Toast.makeText(getApplicationContext(), "네트워크를 확인해주세요!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    private void initLikedChildData() { //찜한 가맹점인지 판단
        if(LoginActivity.userAccessToken!=null){
            if(tokenRetrofitInstance!=null){
                Call<ScrapChildResponse> call = RetrofitInstance.getRetrofitService().getChildrenScraps("Bearer "+LoginActivity.userAccessToken,"children");
                call.enqueue(new Callback<ScrapChildResponse>() {
                    @Override
                    public void onResponse(Call<ScrapChildResponse> call, Response<ScrapChildResponse> response) {
                        if (response.isSuccessful()) {
                            ScrapChildResponse result;
                            result = response.body();
                            likedChildItems = result.getScrapChildItem().getLikedChildItems();
                            for (LikedChildItem scrap: likedChildItems) {
                                if (scrap.get_id().indexOf(id) != -1) { // 검색어가 존재함
                                    heart_img.setVisibility(View.INVISIBLE);
                                    heart_full_img.setVisibility(View.VISIBLE);
                                    return;
                                }
                                heart_img.setVisibility(View.VISIBLE);
                                heart_full_img.setVisibility(View.INVISIBLE);
                            }
                        } else {
                            if(response.message().equals("Unauthorized")){
                                checkAuthorized();
                                initLikedChildData();
                            }
                            Log.d("REST FAILED MESSAGE", response.message());
                        }
                    }
                    @Override
                    public void onFailure(Call<ScrapChildResponse> call, Throwable t) {
                        Log.d("REST ERROR!", t.getMessage());
                        Toast.makeText(getApplicationContext(), "네트워크를 확인해주세요!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

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
                            storeName.setText(result.getCardStoreItem().getName());
                            storeAddress.setText(result.getCardStoreItem().getAddress());
                            Log.d("성공", new Gson().toJson(response.body()));
                        } else {
                            if(response.message().equals("Unauthorized")){
                                checkAuthorized();
                                getDetailData();
                            }
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
                        storeName.setText(result.getSunHanDetailItem().getName());
                        storeAddress.setText(result.getSunHanDetailItem().getAddress());
                        Log.d("성공", new Gson().toJson(response.body()));
                    } else {
                        if(response.message().equals("Unauthorized")){
                            checkAuthorized();
                            getDetailData();
                        }
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

    private void checkAuthorized(){
        Call<TokenResponse> call = RetrofitInstance.getRetrofitService().getRefreshToken("Bearer "+LoginActivity.userAccessToken,LoginActivity.userRefreshToken );
        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful()) {
                    TokenResponse result = response.body();
                    LoginActivity.userAccessToken = result.getTokenItem().getAccessToken();
                    LoginActivity.userRefreshToken = result.getTokenItem().getRefreshToken();
                    Log.d("리프레시성공", new Gson().toJson(response.body()));
                } else {
                    Log.d("리프레시토큰 실패", response.message());
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.d("REST ERROR!", t.getMessage());
            }
        });
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


    private void getHeartOnData()
    {
        if(tokenRetrofitInstance!=null && whichStore==0){
            Call<ScrapOnOffResponse> call = RetrofitInstance.getRetrofitService().getChildrenScrapsOn("Bearer "+LoginActivity.userAccessToken,id, "children");
            call.enqueue(new Callback<ScrapOnOffResponse>() {
                @Override
                public void onResponse(Call<ScrapOnOffResponse> call, Response<ScrapOnOffResponse> response) {
                    if (response.isSuccessful()) {
                        ScrapOnOffResponse result = response.body();
                        Toast.makeText(getApplicationContext(), "찜하기 완료", Toast.LENGTH_SHORT).show();
                        Log.d("찜한 아동 급식 가게 등록", new Gson().toJson(response.body()));
                    } else {
                        if(response.message().equals("Unauthorized")){
                            checkAuthorized();
                            getHeartOnData();
                        }
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
                        Toast.makeText(getApplicationContext(), "찜하기 완료", Toast.LENGTH_SHORT).show();
                        Log.d("찜한 선한 가게 등록", new Gson().toJson(response.body()));
                    } else {
                        if(response.message().equals("Unauthorized")){
                            checkAuthorized();
                            getHeartOnData();
                        }
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
                        Toast.makeText(getApplicationContext(), "찜하기 해제", Toast.LENGTH_SHORT).show();
                        Log.d("찜한 아동가맹점 가게 취소", new Gson().toJson(response.body()));
                    } else {
                        if(response.message().equals("Unauthorized")){
                            checkAuthorized();
                            getHeartOffData();
                        }
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
                        Toast.makeText(getApplicationContext(), "찜하기 해제", Toast.LENGTH_SHORT).show();
                        Log.d("찜한 선한 가게 취소", new Gson().toJson(response.body()));
                    } else {
                        if(response.message().equals("Unauthorized")){
                            checkAuthorized();
                            getHeartOffData();
                        }
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