package com.capsaicin.sunhan.View.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.capsaicin.sunhan.Model.CommunityDetailResponse;
import com.capsaicin.sunhan.Model.CommunityWritingPost;
import com.capsaicin.sunhan.Model.PostChangeResponse;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.fragment.CommunityFragment;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPostActivity extends AppCompatActivity {
    Button finishBtn;
    Toolbar toolbar;
    TextView writeContent;
    CommunityFragment communityFragment;
    CommunityWritingPost communityWritingPost;
    Intent intent;

    public static  String id;
    public static  String content;

    private RetrofitInstance tokenRetrofitInstance ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wirte_community);
        toolbar = findViewById(R.id.write_toolbar);

        writeContent = findViewById(R.id.write_content);

        communityFragment = new CommunityFragment();
        communityWritingPost = new CommunityWritingPost();
        tokenRetrofitInstance = RetrofitInstance.getRetrofitInstance();


        intent = getIntent();
        writeContent.setText(intent.getStringExtra("content")); //원래 내용 불러오기
        id = intent.getStringExtra("_id");
        content = intent.getStringExtra("content");

        setToolbar();
        finishBtn = findViewById(R.id.write_btn);
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                communityWritingPost.setContent(writeContent.getText().toString()); // 수정 입력한 내용 받기

                if(communityWritingPost.getContent().isEmpty()){
                    writeContent.setError("내용을 입력해주세요.");
                } else {
                    modify_Post(communityWritingPost);
                    Toast toast = Toast.makeText(getApplicationContext(), "수정되었습니다", Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                }
            }
        });
    }

    private void modify_Post(CommunityWritingPost content){
        Log.d("확인",LoginActivity.userAccessToken);
        if(LoginActivity.userAccessToken!=null){
            if(tokenRetrofitInstance!=null){

                Call<PostChangeResponse> call = RetrofitInstance.getRetrofitService().modifyPost("Bearer "+LoginActivity.userAccessToken, id, content); // 3rd param : 수정할 내용을 담은 request 객체
                call.enqueue(new Callback<PostChangeResponse>() {
                    @Override
                    public void onResponse(Call<PostChangeResponse> call, Response<PostChangeResponse> response) {
                        if (response.isSuccessful()) {
                            PostChangeResponse result = response.body();
                            changePost();
                            Log.d("글 수정 성공", new Gson().toJson(response.body()));
                        } else {
                            Log.d("REST FAILED MESSAGE", response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<PostChangeResponse> call, Throwable t) {
                        Log.d("REST ERROR!", t.getMessage());
                    }
                });
            }
        }
    }

    void changePost(){
        if(LoginActivity.userAccessToken!=null){
            if(tokenRetrofitInstance!=null){
                Call<CommunityDetailResponse> call = RetrofitInstance.getRetrofitService().getCommunityDetail(id);
                call.enqueue(new Callback<CommunityDetailResponse>() {
                    @Override
                    public void onResponse(Call<CommunityDetailResponse> call, Response<CommunityDetailResponse> response) {
                        if (response.isSuccessful()) {
                            CommunityDetailResponse result = response.body();
                            content = result.getCommunityItem().getCommuContent();
                            CommunityDetailActivity.content.setText(content);
                            Log.d("성공", new Gson().toJson(response.body()));
                        } else {
                            Log.d("REST FAILED MESSAGE", response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<CommunityDetailResponse> call, Throwable t) {
                        Log.d("REST ERROR!", t.getMessage());
                    }
                });
            }
        }

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
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}