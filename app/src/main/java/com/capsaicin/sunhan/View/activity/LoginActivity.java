package com.capsaicin.sunhan.View.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.capsaicin.sunhan.Model.CommunityItem;
import com.capsaicin.sunhan.Model.MypageItem;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.Model.TokenResponse;
import com.capsaicin.sunhan.Model.UserResponse;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.adapter.CommunityAdapter;
import com.capsaicin.sunhan.View.adapter.MypageAdapter;
import com.capsaicin.sunhan.View.fragment.MyPageFragment;
import com.google.gson.Gson;
import com.kakao.sdk.auth.LoginClient;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    ArrayList<MypageItem> mypageList = new ArrayList<>();
    ArrayList<CommunityItem> communityItemArrayList= new ArrayList<>();


    public static Context mContext  ;
    public static MypageAdapter mypageAdapter;
    public static CommunityAdapter communityAdapter;

    private static final String TAG = "LoginActivity";

    public static String token;
    public static String userAccessToken;
    public static String userRefreshToken;
    Button kakao_btn;
    Button no_user_login;


    private RetrofitInstance tokenRetrofitInstance ;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        mContext = getApplicationContext();
        kakao_btn = findViewById(R.id.kakao_login);
        no_user_login=findViewById(R.id.no_user_login);
        tokenRetrofitInstance=RetrofitInstance.getRetrofitInstance(); //???????????? ?????????

        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if(oAuthToken != null) {
                    //???????????? ????????? ??? ??????????????? ???
                    token = oAuthToken.getAccessToken();
                    sendToken(token); // ?????? ????????? ??????
                }
                if( throwable != null) {
                    //????????? ????????? ??? ??????????????? ???
                    Toast.makeText(LoginActivity.this, "??????????????? ??????????????????!", Toast.LENGTH_LONG).show();
                    Log.w(TAG, "invoke: " + throwable.getLocalizedMessage());

                }
                return null;
            }
        };

        kakao_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(LoginClient.getInstance().isKakaoTalkLoginAvailable(LoginActivity.this)){
                    LoginClient.getInstance().loginWithKakaoTalk(LoginActivity.this, callback);
                } else {
                    LoginClient.getInstance().loginWithKakaoAccount(LoginActivity.this, callback);
                }

            }
        });



        no_user_login.setOnClickListener(new View.OnClickListener() { //????????? ?????????
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LocationSettingActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }

    private void requestUserData(){
        if(LoginActivity.userAccessToken!=null){
            if(tokenRetrofitInstance!=null){
                Call<UserResponse> call = RetrofitInstance.getRetrofitService().getUser("Bearer "+LoginActivity.userAccessToken);
                call.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if (response.isSuccessful()) {
                            UserResponse result = response.body();
                            MyPageFragment.userId = result.getUserItem().get_id();
                            MyPageFragment.user_id = result.getUserItem().get_id(); //????????? ?????? id ?????? ??????
                            Log.d("??????", new Gson().toJson(response.body()));
                        } else {
                            Log.d("REST FAILED MESSAGE", response.message());
                        }
                    }
                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Log.d("REST ERROR!", t.getMessage());
                        Toast.makeText(getApplicationContext(), "??????????????? ??????????????????!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    private void sendToken(String token){
        if(tokenRetrofitInstance!=null){
            Call<TokenResponse> call = RetrofitInstance.getRetrofitService().getkakaoToken("Bearer "+token);
            call.enqueue(new Callback<TokenResponse>() {
                @Override
                public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                    if (response.isSuccessful()) {
                        TokenResponse result = response.body();
                        userAccessToken = result.getTokenItem().getAccessToken();
                        userRefreshToken = result.getTokenItem().getRefreshToken();
                        updateKakaoLoginUi();
                        Log.d("??????", new Gson().toJson(response.body()));
                    } else {
                        Log.d("REST FAILED MESSAGE", response.message());
                    }
                }

                @Override
                public void onFailure(Call<TokenResponse> call, Throwable t) {
                     Log.d("REST ERROR!", t.getMessage());
                }
            });
        }
    }

    private void updateKakaoLoginUi() {
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                if ( user != null) { //????????? ?????? ??? ?????? ??????
                    requestUserData(); //??????????????? ????????? ??????
                    Intent intent = new Intent(getApplicationContext(), BottomNavigationActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.w(TAG, "invoke: " + throwable.getLocalizedMessage());
                }
                return  null;
            }
        });
    }

    void init(){
        mypageAdapter = new MypageAdapter(getApplicationContext(), mypageList);
        communityAdapter = new CommunityAdapter(getApplicationContext(),communityItemArrayList);
        setData();
    }

    void setData(){
        mypageAdapter.addItem(new MypageItem("????????????",R.drawable.card_check));
        mypageAdapter.addItem(new MypageItem("?????????",R.drawable.my_logs));
        mypageAdapter.addItem(new MypageItem("????????????",R.drawable.block_user));
        mypageAdapter.addItem(new MypageItem("???????????????",R.drawable.policy));
        mypageAdapter.addItem(new MypageItem("????????????",R.drawable.logout));
        mypageAdapter.addItem(new MypageItem("????????????",R.drawable.delete_user));
    }
}
