package com.example.front_sunhan.View.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front_sunhan.Model.BlocekdItem;
import com.example.front_sunhan.Model.CommunityItem;
import com.example.front_sunhan.Model.MypageItem;
import com.example.front_sunhan.Model.Retrofit.GetDataService;
import com.example.front_sunhan.Model.Retrofit.RetrofitInstance;
import com.example.front_sunhan.Model.StoreItem;
import com.example.front_sunhan.R;
import com.example.front_sunhan.View.adapter.CommunityAdapter;
import com.example.front_sunhan.View.adapter.ManageBlockAdapter;
import com.example.front_sunhan.View.adapter.MypageAdapter;
import com.example.front_sunhan.View.adapter.SunhanStoreAdapter;
import com.example.front_sunhan.View.fragment.SunhanstCardFragment;
import com.kakao.sdk.auth.LoginClient;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    ArrayList<MypageItem> mypageList = new ArrayList<>();
    ArrayList<BlocekdItem> blockedItemsList = new ArrayList<>();
    ArrayList<StoreItem> storeItemArrayList1= new ArrayList<>();
    ArrayList<StoreItem> storeItemArrayList2= new ArrayList<>();
    ArrayList<CommunityItem> communityItemArrayList= new ArrayList<>();
    ArrayList<StoreItem> storeList =new ArrayList<StoreItem>();
    ArrayList<StoreItem> storeSunHanList=new ArrayList<StoreItem>();

    public static Context mContext  ;
    public static MypageAdapter mypageAdapter;
    public static ManageBlockAdapter manageBlockAdapter;
    public static SunhanStoreAdapter likedStoreAdapter1;
    public static SunhanStoreAdapter likedStoreAdapter2;
    public static CommunityAdapter communityAdapter;
    public static SunhanStoreAdapter storeCardAdapter;
    public static SunhanStoreAdapter storeSunhanAdapter ;

    RecyclerView sunhanCardRecyclerView;
    // 진행바
    ProgressDialog progressDoalog;

   public static String userId;
    public static String userNickName;

    private static final String TAG = "LoginActivity";

    static String token;
    ImageButton kakao_btn;
    Button google_btn;
    Button naver_btn;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setList();
        mContext = getApplicationContext();
        kakao_btn = (ImageButton) findViewById(R.id.kakao_login);
        naver_btn = findViewById(R.id.login_naver);


        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if(oAuthToken != null) {
                    //로그인이 되었을 때 처리해야할 일
                    Log.i("user", oAuthToken.getAccessToken() + " " + oAuthToken.getRefreshToken());
                    token= oAuthToken.getAccessToken();
                    System.out.println(token);
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


//
//        progressDoalog = new ProgressDialog(getApplicationContext());
//        progressDoalog.setMessage("Loading....");
//        progressDoalog.show();
//
//        // 레트로핏 인스턴스 생성을 해줍니다.
//        // enqueue로 비동기 통신을 싱행합니다.
//        GetDataService service = RetrofitInstance.getRetrofitInstance().create(GetDataService.class);
//        Call<ArrayList<StoreItem>> call = service.getAllStores();
//        //통신완료후 이벤트 처리를 위한 콜백 리스너 등록
//        call.enqueue(new Callback<ArrayList<StoreItem>>() {
//            // 정상으로 통신 성공시
//            @Override
//            public void onResponse(Call<ArrayList<StoreItem>> call, Response<ArrayList<StoreItem>> response) {
//                progressDoalog.dismiss();
//                generateDataList(response.body());
//            }
//            // 통신 실패시(예외발생, 인터넷끊김 등의 이유)
//            @Override
//            public void onFailure(Call<ArrayList<StoreItem>> call, Throwable t) {
//                progressDoalog.dismiss();
//                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
//            }
//        });
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
//    private void generateDataList(ArrayList<StoreItem> list) {
//        storeAdapter = new SunhanStoreAdapter(getApplicationContext(), list);
//    }

    void setList(){
        mypageAdapter = new MypageAdapter(getApplicationContext(), mypageList);
        manageBlockAdapter = new ManageBlockAdapter (getApplicationContext(),blockedItemsList);
        likedStoreAdapter1 = new SunhanStoreAdapter(getApplicationContext(), storeItemArrayList1);
        likedStoreAdapter2 = new SunhanStoreAdapter(getApplicationContext(), storeItemArrayList2);
        communityAdapter = new CommunityAdapter(getApplicationContext(),communityItemArrayList);
        storeCardAdapter = new SunhanStoreAdapter(getApplicationContext(), storeList);
        storeSunhanAdapter =new SunhanStoreAdapter(getApplicationContext(),storeSunHanList);
        setData();
    }

    void setData(){

        mypageAdapter.addItem(new MypageItem("알림설정"));
        mypageAdapter.addItem(new MypageItem("아동급식카드등록"));
        mypageAdapter.addItem(new MypageItem("내 활동보기"));
        mypageAdapter.addItem(new MypageItem("차단관리"));
        mypageAdapter.addItem(new MypageItem("로그아웃"));
        mypageAdapter.addItem(new MypageItem("탈퇴하기"));
        mypageAdapter.addItem(new MypageItem("약관및정책"));
        mypageAdapter.addItem(new MypageItem("버전정보 1.1"));


        manageBlockAdapter.addItem(new BlocekdItem("귤이"));
        manageBlockAdapter.addItem(new BlocekdItem("익명"));
        manageBlockAdapter.addItem(new BlocekdItem("이"));
        manageBlockAdapter.addItem(new BlocekdItem("박"));
        manageBlockAdapter.addItem(new BlocekdItem("수박"));
        manageBlockAdapter.addItem(new BlocekdItem("메론"));

        likedStoreAdapter1.addItem(new StoreItem("돈애랑장터순대국감자탕", "경기 수원시 영통구 동문3길 10",
                "0314299444","10:00-21:00"));
        likedStoreAdapter1.addItem(new StoreItem("낙원갈비집", "경기 수원시 영통구 1243 1층",
                "0314291234","11:30-22:00"));
        likedStoreAdapter1.addItem(new StoreItem("서브웨이", "경기 수원시 영통구 광교산로 22",
                "0314295687","7:00-22:00"));
        likedStoreAdapter1.addItem(new StoreItem("맘스터치", "경기 수원시 영통구 광교산로 154",
                "0314293333","9:00-21:30"));
        likedStoreAdapter1.addItem(new StoreItem("돈애랑장터순대국감자탕", "경기 수원시 영통구 동문3길 10",
                "0314299444","10:00-21:00"));
        likedStoreAdapter1.addItem(new StoreItem("낙원갈비집", "경기 수원시 영통구 1243 1층",
                "0314291234","11:30-22:00"));
        likedStoreAdapter1.addItem(new StoreItem("서브웨이", "경기 수원시 영통구 광교산로 22",
                "0314295687","7:00-22:00"));
        likedStoreAdapter1.addItem(new StoreItem("맘스터치", "경기 수원시 영통구 광교산로 154",
                "0314293333","9:00-21:30"));

        likedStoreAdapter2.addItem(new StoreItem("돈애랑장터순대국감자탕", "경기 수원시 영통구 동문3길 10",
                "0314299444","10:00-21:00"));
        likedStoreAdapter2.addItem(new StoreItem("낙원갈비집", "경기 수원시 영통구 1243 1층",
                "0314291234","11:30-22:00"));
        likedStoreAdapter2.addItem(new StoreItem("서브웨이", "경기 수원시 영통구 광교산로 22",
                "0314295687","7:00-22:00"));
        likedStoreAdapter2.addItem(new StoreItem("맘스터치", "경기 수원시 영통구 광교산로 154",
                "0314293333","9:00-21:30"));

        storeCardAdapter.addItem(new StoreItem("돈애랑장터순대국감자탕", "경기 수원시 영통구 동문3길 10",
                "0314299444","10:00-21:00"));
        storeCardAdapter.addItem(new StoreItem("낙원갈비집", "경기 수원시 영통구 1243 1층",
                "0314291234","11:30-22:00"));
        storeCardAdapter.addItem(new StoreItem("서브웨이", "경기 수원시 영통구 광교산로 22",
                "0314295687","7:00-22:00"));
        storeCardAdapter.addItem(new StoreItem("맘스터치", "경기 수원시 영통구 광교산로 154",
                "0314293333","9:00-21:30"));

        storeSunhanAdapter.addItem(new StoreItem("돈애랑장터순대국감자탕", "경기 수원시 영통구 동문3길 10",
                "0314299444","10:00-21:00"));
        storeSunhanAdapter.addItem(new StoreItem("낙원갈비집", "경기 수원시 영통구 1243 1층",
                "0314291234","11:30-22:00"));
        storeSunhanAdapter.addItem(new StoreItem("서브웨이", "경기 수원시 영통구 광교산로 22",
                "0314295687","7:00-22:00"));
        storeSunhanAdapter.addItem(new StoreItem("맘스터치", "경기 수원시 영통구 광교산로 154",
                "0314293333","9:00-21:30"));

    }
}
