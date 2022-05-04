package com.capsaicin.sunhan.View.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.capsaicin.sunhan.Model.BlocekdItem;
import com.capsaicin.sunhan.Model.CardCheckItem;
import com.capsaicin.sunhan.Model.CommunityItem;
import com.capsaicin.sunhan.Model.MypageItem;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitServiceApi;
import com.capsaicin.sunhan.Model.StoreItem;
import com.capsaicin.sunhan.Model.TokenItem;
import com.capsaicin.sunhan.Model.TokenResponse;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.adapter.CardCheckAdapter;
import com.capsaicin.sunhan.View.adapter.CommunityAdapter;
import com.capsaicin.sunhan.View.adapter.ManageBlockAdapter;
import com.capsaicin.sunhan.View.adapter.MypageAdapter;
import com.capsaicin.sunhan.View.adapter.MypageMylogsAdapter;
import com.capsaicin.sunhan.View.adapter.SunhanStoreAdapter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.gson.Gson;
import com.kakao.sdk.auth.LoginClient;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import java.io.IOException;
import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Header;

public class LoginActivity extends AppCompatActivity {
    ArrayList<MypageItem> mypageList = new ArrayList<>();
    ArrayList<BlocekdItem> blockedItemsList = new ArrayList<>();
    ArrayList<StoreItem> storeItemArrayList1= new ArrayList<>();
    ArrayList<StoreItem> storeItemArrayList2= new ArrayList<>();
    ArrayList<CommunityItem> communityItemArrayList= new ArrayList<>();
    ArrayList<StoreItem> storeList =new ArrayList<StoreItem>();
    ArrayList<StoreItem> storeSunHanList=new ArrayList<StoreItem>();
    ArrayList<CommunityItem> postItemList = new ArrayList<>();
    ArrayList<CommunityItem> commentItemList = new ArrayList<>();
    ArrayList<CardCheckItem> cardCheckItemArrayList = new ArrayList<>();

    public static Context mContext  ;
    public static MypageAdapter mypageAdapter;
    public static ManageBlockAdapter manageBlockAdapter;
    public static SunhanStoreAdapter likedStoreAdapter1;
    public static SunhanStoreAdapter likedStoreAdapter2;
    public static CommunityAdapter communityAdapter;
    public static SunhanStoreAdapter storeCardAdapter;
    public static SunhanStoreAdapter storeSunhanAdapter ;
    public static MypageMylogsAdapter postLogsAdapter;
    public static MypageMylogsAdapter commentsLogsAdapter;
    public static CardCheckAdapter cardCheckAdapter;


    private static final String TAG = "LoginActivity";

    public static String token;
    public static String userAccessToken;
    public static String userRefreshToken;

    Button kakao_btn;
    private SignInButton google_btn; // 구글 로그인 버튼
    Button naver_btn;

    //구글 로그인 관련련
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth; // firebase 인증 객체
    private int RC_SIGN_IN=123;
    private RetrofitInstance tokenRetrofitInstance ;
    private RetrofitServiceApi retrofitServiceApi;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setList();
        mContext = getApplicationContext();
        kakao_btn = findViewById(R.id.kakao_login);
        google_btn = findViewById(R.id.google_login);
        tokenRetrofitInstance=RetrofitInstance.getRetrofitInstance(); //레트로핏 싱글톤

        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if(oAuthToken != null) {
                    //로그인이 되었을 때 처리해야할 일
                    Log.i("Kakao User", oAuthToken.getAccessToken() + " " + oAuthToken.getRefreshToken());
                    token = oAuthToken.getAccessToken();

//                    tokenRetrofitInstance = RetrofitInstance.getRetrofitInstance();

//                    Retrofit retrofit = new Retrofit.Builder()
//                            .baseUrl("http://192.168.219.101:4000/api/")
//                            .addConverterFactory(GsonConverterFactory.create())
//                            .build();
//                    retrofitServiceApi = retrofit.create(RetrofitServiceApi.class);


                        if(tokenRetrofitInstance!=null){
                            Call<TokenResponse> call = RetrofitInstance.getRetrofitService().getkakaoToken("Bearer "+token);
                            call.enqueue(new Callback<TokenResponse>() {
                                @Override
                                public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                                    if (response.isSuccessful()) {
                                        TokenResponse result = response.body();
                                        userAccessToken = result.getTokenItem().getAccessToken();
                                        userRefreshToken = result.getTokenItem().getRefreshToken();
                                        Log.d("성공", new Gson().toJson(response.body()));
                                        Log.d("userAccessToken", userAccessToken);
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
                if( throwable != null) {
                    //오류가 나왔을 떄 처리해야할 일
                    Log.w(TAG, "invoke: " + throwable.getLocalizedMessage());

                }
                updateKakaoLoginUi();
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


        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // [END config_signin]

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        google_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    private void updateKakaoLoginUi() {
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                if ( user != null) {
                    Intent intent = new Intent(LoginActivity.this, BottomNavigationActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.w(TAG, "invoke: " + throwable.getLocalizedMessage());
                }
                return  null;
            }
        });
    }



    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
    // [END on_start_check_user]

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                token = account.getIdToken();

                FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
                mUser.getIdToken(true)
                        .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                            public void onComplete(@NonNull Task<GetTokenResult> task) {
                                if (task.isSuccessful()) {
                                    String idToken = task.getResult().getToken();
                                    // Send token to your backend via HTTPS
                                    // ...
                                } else {
                                    // Handle error -> task.getException();
                                }
                            }
                        });


//                tokenRetrofitInstance = RetrofitInstance.getRetrofitInstance();
//                if(tokenRetrofitInstance!=null) {
//
//                    RetrofitInstance.getRetrofitService().getGoogleToken("Bearer "+account.getIdToken()).enqueue(new Callback<TokenItem>() {
//                        @Override
//                        public void onResponse(Call<TokenItem> call, Response<TokenItem> response) {
//                            if (response.isSuccessful()) {
//                                TokenItem result = response.body();
//                                result.toString();
//                                Log.d("인증 후 토큰 발급", result.toString());
//                            } else {
//                                Log.d("REST FAILED MESSAGE", response.message());
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<TokenItem> call, Throwable t) {
//                            Log.d("REST ERROR!", t.getMessage());
//                        }
//                    });
//                }
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(getApplicationContext(), "Google sign in Failed", Toast.LENGTH_LONG).show();
            }
        }
    }

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogleID:" + acct.getId());
        Log.d(TAG, "firebaseAuthWithGoogleIDToken:" + acct.getIdToken());
        Log.d(TAG, "firebaseAuthWithGoogleName:" + acct.getDisplayName());
        Log.d(TAG, "firebaseAuthWithGoogleName:" + acct.getEmail());
        token=acct.getIdToken();
        Log.d(TAG, "googleToken:" + token);
        // [START_EXCLUDE silent]
        //showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
//                            FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
                            //토큰 보내기.?.?
                            user.getIdToken(true)
                                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                                            if (task.isSuccessful()) {
                                                String idToken = task.getResult().getToken();

                                                Retrofit retrofit = new Retrofit.Builder()
                                                        .baseUrl("http://192.168.219.101:4000/api/")
                                                        .addConverterFactory(GsonConverterFactory.create())
                                                        .build();

                                                retrofitServiceApi = retrofit.create(RetrofitServiceApi.class);
                                                Log.d("google idToken", idToken);

                                                Call<TokenResponse> call = retrofitServiceApi.getGoogleToken("Bearer "+idToken);
                                                call.enqueue(new Callback<TokenResponse>() {
                                                    @Override
                                                    public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                                                        if (response.isSuccessful()) {
                                                            TokenResponse result = response.body();
                                                            result.toString();
                                                            Log.d("인증 후 토큰 발급", result.toString());
                                                        } else {
                                                            Log.d("REST FAILED MESSAGE", response.message());
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<TokenResponse> call, Throwable t) {
                                                        Log.d("REST ERROR!", t.getMessage());
                                                    }
                                                });
                                            } else {
                                                // Handle error -> task.getException();
                                            }
                                        }
                                    });
                            //updateUI(user);

                            Intent intent = new Intent(getApplicationContext(),BottomNavigationActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(), "Complete", Toast.LENGTH_LONG).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            // Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "Authentication Failed", Toast.LENGTH_LONG).show();

                            // updateUI(null);
                        }

                        // [START_EXCLUDE]
                        // hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_google]

    // [START signin]
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Complete", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void revokeAccess() {
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        mGoogleSignInClient.revokeAccess().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Complete", Toast.LENGTH_LONG).show();
                    }
                });
    }

    void setList(){
        mypageAdapter = new MypageAdapter(getApplicationContext(), mypageList);
        manageBlockAdapter = new ManageBlockAdapter (getApplicationContext(),blockedItemsList);
        likedStoreAdapter1 = new SunhanStoreAdapter(getApplicationContext(), storeItemArrayList1);
        likedStoreAdapter2 = new SunhanStoreAdapter(getApplicationContext(), storeItemArrayList2);
        communityAdapter = new CommunityAdapter(getApplicationContext(),communityItemArrayList);
        storeCardAdapter = new SunhanStoreAdapter(getApplicationContext(), storeList);
        storeSunhanAdapter =new SunhanStoreAdapter(getApplicationContext(),storeSunHanList);
        postLogsAdapter = new MypageMylogsAdapter(getApplicationContext(),postItemList);
        commentsLogsAdapter = new MypageMylogsAdapter(getApplicationContext(),commentItemList);
        cardCheckAdapter = new CardCheckAdapter(getApplicationContext(), cardCheckItemArrayList);
        setData();
    }

    void setData(){

        mypageAdapter.addItem(new MypageItem("아동급식카드잔액조회"));
        mypageAdapter.addItem(new MypageItem("내활동보기"));
        mypageAdapter.addItem(new MypageItem("차단관리"));
        mypageAdapter.addItem(new MypageItem("로그아웃"));
        mypageAdapter.addItem(new MypageItem("탈퇴하기"));
        mypageAdapter.addItem(new MypageItem("약관및정책"));


        manageBlockAdapter.addItem(new BlocekdItem("귤이"));
        manageBlockAdapter.addItem(new BlocekdItem("익명"));
        manageBlockAdapter.addItem(new BlocekdItem("이"));
        manageBlockAdapter.addItem(new BlocekdItem("박"));
        manageBlockAdapter.addItem(new BlocekdItem("수박"));
        manageBlockAdapter.addItem(new BlocekdItem("메론"));

        //찜한가게 카드 가맹점
        likedStoreAdapter1.addItem(new StoreItem("돈애랑장터순대국감자탕", "경기 수원시 영통구 동문3길 10",
                "0314299444","10:00-21:00"));
        likedStoreAdapter1.addItem(new StoreItem("낙원갈비집", "경기 수원시 영통구 1243 1층",
                "0314291234","11:30-22:00"));
        likedStoreAdapter1.addItem(new StoreItem("서브웨이", "경기 수원시 영통구 광교산로 22",
                "0314295687","7:00-22:00"));
        likedStoreAdapter1.addItem(new StoreItem("맘스터치", "경기 수원시 영통구 광교산로 154",
                "0314293333","9:00-21:30"));

        //찜한가게 선한가게
        likedStoreAdapter2.addItem(new StoreItem("돈애랑장터순대국감자탕", "경기 수원시 영통구 동문3길 10",
                "0314299444","10:00-21:00"));
        likedStoreAdapter2.addItem(new StoreItem("낙원갈비집", "경기 수원시 영통구 1243 1층",
                "0314291234","11:30-22:00"));
        likedStoreAdapter2.addItem(new StoreItem("서브웨이", "경기 수원시 영통구 광교산로 22",
                "0314295687","7:00-22:00"));
        likedStoreAdapter2.addItem(new StoreItem("맘스터치", "경기 수원시 영통구 광교산로 154",
                "0314293333","9:00-21:30"));

        //가게 메인화면 카드 가맹점
        storeCardAdapter.addItem(new StoreItem("돈애랑장터순대국감자탕", "경기 수원시 영통구 동문3길 10",
                "0314299444","10:00-21:00"));
        storeCardAdapter.addItem(new StoreItem("낙원갈비집", "경기 수원시 영통구 1243 1층",
                "0314291234","11:30-22:00"));
        storeCardAdapter.addItem(new StoreItem("서브웨이", "경기 수원시 영통구 광교산로 22",
                "0314295687","7:00-22:00"));
        storeCardAdapter.addItem(new StoreItem("맘스터치", "경기 수원시 영통구 광교산로 154",
                "0314293333","9:00-21:30"));

        //가게 메인화면 선한가게
        storeSunhanAdapter.addItem(new StoreItem("돈애랑장터순대국감자탕", "경기 수원시 영통구 동문3길 10",
                "0314299444","10:00-21:00"));
        storeSunhanAdapter.addItem(new StoreItem("낙원갈비집", "경기 수원시 영통구 1243 1층",
                "0314291234","11:30-22:00"));
        storeSunhanAdapter.addItem(new StoreItem("서브웨이", "경기 수원시 영통구 광교산로 22",
                "0314295687","7:00-22:00"));
        storeSunhanAdapter.addItem(new StoreItem("맘스터치", "경기 수원시 영통구 광교산로 154",
                "0314293333","9:00-21:30"));

        postLogsAdapter.addItem(new CommunityItem(R.drawable.profile,"익명", "12:10","돈애랑 장터 순대국 감자탕 먹고 왔습니다! 완전 맛있\n" +
                "고 사장님도 친절해요~","0"));
        postLogsAdapter.addItem(new CommunityItem(R.drawable.profile,"익명", "12:07","낙원갈비집 주차도 편리하고 아이랑 맛있게 먹고 왔\n" +
                "습니다","4"));
        postLogsAdapter.addItem(new CommunityItem(R.drawable.profile,"익명", "12:10","영통버거 칭구들이랑 먹었어요 사장님 감사합니다 \n" +
                "또 갈게요 ~","1"));
        postLogsAdapter.addItem(new CommunityItem(R.drawable.profile,"익명", "09:56","일리터 카페 조용하고 커피도 고소해요! 영통구 주민\n" +
                "분들께 추천드립니다 ","3"));
        postLogsAdapter.addItem(new CommunityItem(R.drawable.profile,"익명", "09:10","영통버거 사장님 친절하세요.. 감사해요..","1"));

        commentsLogsAdapter.addItem(new CommunityItem(R.drawable.profile,"익명", "12:10","돈애랑 장터 순대국 감자탕 먹고 왔습니다! 완전 맛있\n" +
                "고 사장님도 친절해요~","0"));
        commentsLogsAdapter.addItem(new CommunityItem(R.drawable.profile,"익명", "12:07","낙원갈비집 주차도 편리하고 아이랑 맛있게 먹고 왔\n" +
                "습니다","4"));
        commentsLogsAdapter.addItem(new CommunityItem(R.drawable.profile,"익명", "12:10","영통버거 칭구들이랑 먹었어요 사장님 감사합니다 \n" +
                "또 갈게요 ~","1"));
        commentsLogsAdapter.addItem(new CommunityItem(R.drawable.profile,"익명", "09:56","일리터 카페 조용하고 커피도 고소해요! 영통구 주민\n" +
                "분들께 추천드립니다 ","3"));
        commentsLogsAdapter.addItem(new CommunityItem(R.drawable.profile,"익명", "09:10","영통버거 사장님 친절하세요.. 감사해요..","1"));
    }
}
