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

    //?????????????????? ??? ?????? ????????????????????? ?????? ??????
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
        tokenRetrofitInstance = RetrofitInstance.getRetrofitInstance();//?????????

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
                    // ??? ????????? ????????? ??? ????????? ??????
                    getHeartOnData();
                } else{
                    AlertDialog.Builder dlg = new AlertDialog.Builder(StoreDetailActivity.this);
                    dlg.setMessage("????????? ??? ??????????????????!");
                    dlg.setPositiveButton("??????",new DialogInterface.OnClickListener(){
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
                // ??? ????????? ???????????? ????????? ??????
                getHeartOffData();

            }
        });


        shareLayout.setOnClickListener(new View.OnClickListener() { // ???????????? ????????????
            @Override
            public void onClick(View view) {
                ShareBtnClick(view);
            }
        });


        letterLayout.setOnClickListener(new View.OnClickListener() { // ??????????????? ????????????
            @Override public void onClick(View v) {
                if(LoginActivity.userAccessToken!=null){
                    Intent intent = new Intent(StoreDetailActivity.this, WriteLetterActivity.class);
                    intent.putExtra("_id",id);
                    intent.putExtra("whichStore", whichStore);
                    startActivity(intent);
                }else{ // ????????? ??? ?????? ??????????????? ???
                    AlertDialog.Builder dlg = new AlertDialog.Builder(StoreDetailActivity.this);
                    dlg.setMessage("????????? ??? ??????????????????!"); // ?????????
                    dlg.setPositiveButton("??????",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dlg.show();
                }
            }
        });


        // ???????????????
        TabLayout tabs = findViewById(R.id.store_detail_tapLayout);

        tabs.addTab(tabs.newTab().setText("??????"));
        tabs.addTab(tabs.newTab().setText("????????????"));

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
                    if(whichStore ==0) { //0??? ?????? ????????? ?????? ???????????????
                        screenChange(childrenStoreInfoFragment);
                    }
                    else{
                        // 1????????? ????????????????????? ??????????????? -> ?????? ????????? ????????? ?????? ??????
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


    private void initLikedSunhanData() { //?????? ??????????????? ???????????? ??????
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
                                if (scrap.get_id().indexOf(id) != -1) { // ???????????? ?????????
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
                        Toast.makeText(getApplicationContext(), "??????????????? ??????????????????!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    private void initLikedChildData() { //?????? ??????????????? ??????
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
                                if (scrap.get_id().indexOf(id) != -1) { // ???????????? ?????????
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
                        Toast.makeText(getApplicationContext(), "??????????????? ??????????????????!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    private void getDetailData()
    {
        if(tokenRetrofitInstance!=null && whichStore==0){
            Log.d("???????????? id", id);
                Call<CardStoreDetailResponse> call = RetrofitInstance.getRetrofitService().getChildrenStoreDetail(id);
                call.enqueue(new Callback<CardStoreDetailResponse>() {
                    @Override
                    public void onResponse(Call<CardStoreDetailResponse> call, Response<CardStoreDetailResponse> response) {
                        if (response.isSuccessful()) {
                            CardStoreDetailResponse result = response.body();
                            storeName.setText(result.getCardStoreItem().getName());
                            storeAddress.setText(result.getCardStoreItem().getAddress());
                            Log.d("??????", new Gson().toJson(response.body()));
                        } else {
                            if(response.message().equals("Unauthorized")){
                                checkAuthorized();
                                getDetailData();
                            }
                            Log.d("???????????????????????????", response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<CardStoreDetailResponse> call, Throwable t) {
                        Log.d("REST ERROR!", t.getMessage());
                    }
                });
            } else if(tokenRetrofitInstance!=null && whichStore==1){ // ???????????????
            Call<SunHanStoreDetailResponse> call = RetrofitInstance.getRetrofitService().getSunHansStoreDetail(id);
            call.enqueue(new Callback<SunHanStoreDetailResponse>() {
                @Override
                public void onResponse(Call<SunHanStoreDetailResponse> call, Response<SunHanStoreDetailResponse> response) {
                    if (response.isSuccessful()) {
                        SunHanStoreDetailResponse result = response.body();
                        storeName.setText(result.getSunHanDetailItem().getName());
                        storeAddress.setText(result.getSunHanDetailItem().getAddress());
                        Log.d("??????", new Gson().toJson(response.body()));
                    } else {
                        if(response.message().equals("Unauthorized")){
                            checkAuthorized();
                            getDetailData();
                        }
                        Log.d("???????????????????????????", response.message());
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
                    Log.d("??????????????????", new Gson().toJson(response.body()));
                } else {
                    Log.d("?????????????????? ??????", response.message());
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.d("REST ERROR!", t.getMessage());
            }
        });
    }


    void setToolbar(){
        setSupportActionBar (toolbar); //??????????????? ??????(App Bar)??? ??????
        ActionBar actionBar = getSupportActionBar (); //?????? ????????? ?????? ?????? ?????????
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled (true); // ????????? ???????????? ?????? ?????????
    }

    public void ShareBtnClick(View view) {
        if (whichStore == 0) {

            share_kakao_template();

        } else if (whichStore==1) {

            String name = storeName.getText().toString();
            String address = storeAddress.getText().toString();

            FeedTemplate params = FeedTemplate
                    .newBuilder(ContentObject.newBuilder(name + "??? ??????????????????!",
                            "https://ifh.cc/g/fT1GYp.png",
                            LinkObject.newBuilder().setWebUrl("https://developers.kakao.com")
                                    .setMobileWebUrl("https://developers.kakao.com").build())
                            .setDescrption(address)
                            .build())
                    .addButton(new ButtonObject("????????? ?????? ??????", LinkObject.newBuilder()
                            .setWebUrl("https://www.?????????????????????.com")
                            .setMobileWebUrl("https://www.?????????????????????.com")
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
                    Log.d("kakao share", "???????????? ???????????? ??????\n" + errorResult);
                }

                @Override
                public void onSuccess(KakaoLinkResponse result) {
                    Log.d("kakao share", "???????????? ?????? ??????!");
                }
            });
        }
    }

    public void share_kakao_template(){
        // ????????? ?????? :  ??????, ?????????, ?????????
        String templateId = "77055"; // ????????? ???????????????

        String name = storeName.getText().toString();
        String address = storeAddress.getText().toString();

        // ???????????????  ????????? ????????? ???
        Map<String, String> templateArgs = new HashMap<String, String>();
        //templateArgs.put("APP_KEY", APP_KEY);
        templateArgs.put("title",name+ "??? ??????????????????");
        templateArgs.put("content",address);

        KakaoLinkService.getInstance().sendCustom(this, templateId, templateArgs, new ResponseCallback<KakaoLinkResponse>() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                Log.e("EOTEST",errorResult.toString());
            }

            @Override
            public void onSuccess(KakaoLinkResponse result) {
                // ????????? ?????????????????? ?????? ????????? ??????????????? ??????
                Log.d("kakao share", "???????????? ?????? ??????!");
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
                        Toast.makeText(getApplicationContext(), "????????? ??????", Toast.LENGTH_SHORT).show();
                        Log.d("?????? ?????? ?????? ?????? ??????", new Gson().toJson(response.body()));
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
                        Toast.makeText(getApplicationContext(), "????????? ??????", Toast.LENGTH_SHORT).show();
                        Log.d("?????? ?????? ?????? ??????", new Gson().toJson(response.body()));
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
                        Toast.makeText(getApplicationContext(), "????????? ??????", Toast.LENGTH_SHORT).show();
                        Log.d("?????? ??????????????? ?????? ??????", new Gson().toJson(response.body()));
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
                        Toast.makeText(getApplicationContext(), "????????? ??????", Toast.LENGTH_SHORT).show();
                        Log.d("?????? ?????? ?????? ??????", new Gson().toJson(response.body()));
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