package com.capsaicin.sunhan.View.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capsaicin.sunhan.Model.BlockListResponse;
import com.capsaicin.sunhan.Model.BlockedItem;
import com.capsaicin.sunhan.Model.ProfileChangeResponse;
import com.capsaicin.sunhan.Model.ResultResponse;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitServiceApi;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.adapter.ManageBlockAdapter;
import com.capsaicin.sunhan.View.interfaceListener.OnClickBlockedItemListener;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageBlockActivity extends AppCompatActivity {
    Toolbar toolbar;
    private RetrofitInstance tokenRetrofitInstance ;
    private RetrofitServiceApi retrofitServiceApi;
    RecyclerView manageBlockRecyclerView;
    ManageBlockAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_block);
        toolbar = findViewById(R.id.manage_block_toolbar);
        tokenRetrofitInstance=RetrofitInstance.getRetrofitInstance(); //레트로핏 싱글톤
        setToolbar();

        manageBlockRecyclerView = findViewById(R.id.recyclerview_block);
        manageBlockRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getApplicationContext());
        manageBlockRecyclerView.setLayoutManager(recyclerViewManager);
        manageBlockRecyclerView.setItemAnimator(new DefaultItemAnimator());

        getData();

    }

    private void getData(){
        if(LoginActivity.userAccessToken!=null) {
            if (tokenRetrofitInstance != null) {
                Call<BlockListResponse> call = RetrofitInstance.getRetrofitService().getBlockedList("Bearer " + LoginActivity.userAccessToken);
                call.enqueue(new Callback<BlockListResponse>() {
                    @Override
                    public void onResponse(Call<BlockListResponse> call, Response<BlockListResponse> response) {
                        if (response.isSuccessful()) {
                            BlockListResponse result = response.body();
                            adapter = new ManageBlockAdapter(getApplicationContext(),result.getBlockUsers().getBlockUsers());
                            manageBlockRecyclerView.setAdapter(adapter);
                            Log.d("차단 리스트", new Gson().toJson(response.body()));
                        } else {
                            Log.d("REST FAILED MESSAGE", response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<BlockListResponse> call, Throwable t) {
                        Log.d("REST ERROR!", t.getMessage());
                        Toast.makeText(getApplicationContext(), "네트워크를 확인해주세요!", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }
    }

    void setToolbar(){
        setSupportActionBar (toolbar); //액티비티의 앱바(App Bar)로 지정
        ActionBar actionBar = getSupportActionBar (); //앱바 제어를 위해 툴바 액세스
        actionBar.setDisplayShowTitleEnabled(false);
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