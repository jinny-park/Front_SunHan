package com.capsaicin.sunhan.View.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.Model.UserDeleteResponse;
import com.capsaicin.sunhan.Model.UserResponse;
import com.capsaicin.sunhan.R;
import com.google.gson.Gson;
import com.kakao.sdk.user.UserApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteAccountActivity extends AppCompatActivity {

    Button delete_btn;
    Toolbar toolbar;
    private RetrofitInstance tokenRetrofitInstance ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);
        toolbar = findViewById(R.id.delete_toolbar);
        setToolbar();
        tokenRetrofitInstance=RetrofitInstance.getRetrofitInstance(); //레트로핏 싱글톤
        delete_btn = findViewById(R.id.delete_account_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LoginActivity.userAccessToken!=null){
                    if(tokenRetrofitInstance!=null){
                        Call<UserDeleteResponse> call = RetrofitInstance.getRetrofitService().deleteUser("Bearer "+LoginActivity.userAccessToken);
                        call.enqueue(new Callback<UserDeleteResponse>() {
                            @Override
                            public void onResponse(Call<UserDeleteResponse> call, Response<UserDeleteResponse> response) {
                                if (response.isSuccessful()) {
                                    UserDeleteResponse result = response.body();
                                    showDialog();
                                    Log.d("탈퇴성공", result.getMessage());
                                } else {
                                    Log.d("REST FAILED MESSAGE", response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<UserDeleteResponse> call, Throwable t) {
                                Log.d("REST ERROR!", t.getMessage());
                            }
                        });
                    }
                }
            }
        });

    }

    void showDialog() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(getApplicationContext()).setMessage("탈퇴가 완료되었습니다.") .
                setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialogInterface, int i) {

                        UserApiClient.getInstance().logout(error -> {
                            if (error != null) {
                            }else{
                                LoginActivity.userAccessToken = null;
                                LoginActivity.userRefreshToken = null;
                                Intent intent = new Intent(getApplication(), LoginActivity.class);
                                startActivity(intent);
                            }
                            return null;
                        });
                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
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
