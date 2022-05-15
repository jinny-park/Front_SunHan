package com.capsaicin.sunhan.View.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capsaicin.sunhan.Model.CommentItem;
import com.capsaicin.sunhan.Model.CommunityDetailItem;
import com.capsaicin.sunhan.Model.CommunityDetailResponse;
import com.capsaicin.sunhan.Model.CommunityResponse;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitServiceApi;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.adapter.CommunityAdapter;
import com.capsaicin.sunhan.View.adapter.CommunityDetailAdapter;
import com.capsaicin.sunhan.View.fragment.CommunityFragment;
import com.capsaicin.sunhan.View.interfaceListener.OnClickCommunityListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityDetailActivity extends AppCompatActivity {
    public static CommunityDetailAdapter communityDetailAdapter ;
    RecyclerView commuDetailRecycleView;

    Toolbar toolbar;
    ImageView pop1;
    TextView userId;
    TextView uploadTime;
    TextView content;
    TextView commentNum;

    ImageView addImage;

    Dialog dilaog01;

    CommunityFragment communityFragment;

    public static String id ;

    private RetrofitInstance tokenRetrofitInstance ;

    int page;
    ProgressBar progressBar;

    private RetrofitInstance commuDetailRetrofitInstance ;
    private RetrofitServiceApi retrofitServiceApi;//

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        toolbar = findViewById(R.id.commu_detail_toolbar);
        setToolbar();

        tokenRetrofitInstance = RetrofitInstance.getRetrofitInstance();
        userId = findViewById(R.id.detail_userId);
        uploadTime = findViewById(R.id.detail_uploadTime);
        content = findViewById(R.id.detail_content);
        commentNum = findViewById(R.id.detail_commentNum);

        Intent intent = getIntent();
        id = intent.getStringExtra("_id");

        dilaog01 = new Dialog(CommunityDetailActivity.this);       // Dialog 초기화
        dilaog01.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dilaog01.setContentView(R.layout.dialog);



        page = 1;


//        setList();

        commuDetailRecycleView = findViewById(R.id.recyleView_community_detail);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(this);
        commuDetailRecycleView.setLayoutManager(recyclerViewManager);
        commuDetailRecycleView.setHasFixedSize(true);
        commuDetailRecycleView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView1.setAdapter(communityDetailAdapter);

//        initData(0);
        getData();

        pop1 = findViewById(R.id.popupMore);

        pop1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog();
            }
        });

        addImage = findViewById(R.id.sunhan_add);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://xn--o39akkz01az4ip7f4xzwoa.com/"));
                startActivity(intent);
            }

        });

    }

//    private void initData(int page)
//    {
////        if(LoginActivity.userAccessToken!=null){
//        if(commuDetailRetrofitInstance!=null){
//            Log.d("커뮤니티프래그먼트", "토큰인스턴스이후 콜백 전");
//            Call<CommunityDetailResponse> call = RetrofitInstance.getRetrofitService().getCommunityDetailList("Bearer "+LoginActivity.userAccessToken,page);
//
//            call.enqueue(new Callback<CommunityDetailResponse>() {
//                @Override
//                public void onResponse(Call<CommunityDetailResponse> call, Response<CommunityDetailResponse> response) {
//                    if (response.isSuccessful()) {
//                        progressBar.setVisibility(View.GONE);
//                        CommunityDetailResponse result = response.body();
////                        communityDetailAdapter = new CommunityDetailAdapter(getApplicationContext(),result.getData());
//                        commuDetailRecycleView.setAdapter(communityDetailAdapter);
//                        Log.d("성공", new Gson().toJson(response.body()));
//                    } else {
//                        progressBar.setVisibility(View.GONE);
//                        Log.d("REST FAILED MESSAGE", response.message());
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<CommunityDetailResponse> call, Throwable t) {
//                    progressBar.setVisibility(View.GONE);
//                    Log.d("REST ERROR!", t.getMessage());
//                }
//            });
//        }
////        }
//    }

    private void getData(){
        if(tokenRetrofitInstance!=null){
            Log.d("상세정보 id", id);
            Call<CommunityDetailResponse> call = RetrofitInstance.getRetrofitService().getCommunityDetail(id);
            call.enqueue(new Callback<CommunityDetailResponse>() {
                @Override
                public void onResponse(Call<CommunityDetailResponse> call, Response<CommunityDetailResponse> response) {
                    if (response.isSuccessful()) {
                        CommunityDetailResponse result = response.body();

//                        CommunityFragment.commuId.setText(result.getCommunityDetailItem().getCommuId());
                        userId.setText(result.getCommunityItem().getCommuId());
                        uploadTime.setText(result.getCommunityItem().getCommuIsCreateAt());
                        content.setText(result.getCommunityItem().getCommuContent());
                        commentNum.setText(result.getCommunityItem().getCommuIsCommentCount());
                        Log.d("성공", new Gson().toJson(response.body()));
                    } else {

                        Log.d("커뮤니티 상세정보실패", response.message());
                    }
                }

                @Override
                public void onFailure(Call<CommunityDetailResponse> call, Throwable t) {
                    Log.d("REST ERROR!", t.getMessage());
                }
            });


        }
    }

    public void dialog() {
        dilaog01.show();

        Button modify_btn = dilaog01.findViewById(R.id.modify_btn);
        modify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dilaog01.dismiss();
            }
        });

        Button delete_btn = dilaog01.findViewById(R.id.delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dilaog01.dismiss();
            }
        });

        Button report_btn = dilaog01.findViewById(R.id.report_btn);
        report_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dilaog01.dismiss();
            }
        });

        Button cancle_btn = dilaog01.findViewById(R.id.cancle_btn);
        cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dilaog01.dismiss();;
            }
        });
    }

    void setToolbar(){
        setSupportActionBar (toolbar); //액티비티의 앱바(App Bar)로 지정
        ActionBar actionBar = getSupportActionBar (); //앱바 제어를 위해 툴바 액세스
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setTitle("메인툴바");
        actionBar.setDisplayHomeAsUpEnabled (true); // 앱바에 뒤로가기 버튼 만들기
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

}
