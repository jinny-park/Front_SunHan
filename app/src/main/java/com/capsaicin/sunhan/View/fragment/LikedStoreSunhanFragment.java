package com.capsaicin.sunhan.View.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.capsaicin.sunhan.Model.CardStoreResponse;
import com.capsaicin.sunhan.Model.LikedChildItem;
import com.capsaicin.sunhan.Model.LikedSunHanItem;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.Model.ScrapsSunHanResponse;
import com.capsaicin.sunhan.Model.StoreResponse;
import com.capsaicin.sunhan.Model.TokenResponse;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.activity.LoginActivity;
import com.capsaicin.sunhan.View.activity.StoreDetailActivity;
import com.capsaicin.sunhan.View.adapter.CardStoreAdapter;
import com.capsaicin.sunhan.View.adapter.LikedChildAdapter;
import com.capsaicin.sunhan.View.adapter.LikedSunHanAdapter;
import com.capsaicin.sunhan.View.adapter.SunhanStoreAdapter;
import com.capsaicin.sunhan.View.interfaceListener.OnClickLikedChildListener;
import com.capsaicin.sunhan.View.interfaceListener.OnClickLikedSunHanListener;
import com.capsaicin.sunhan.View.interfaceListener.OnClickStoreItemListener;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LikedStoreSunhanFragment extends Fragment {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    private RetrofitInstance tokenRetrofitInstance ;
    LikedSunHanAdapter likedSunHanAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_likestore_sunhan,null);

        tokenRetrofitInstance=RetrofitInstance.getRetrofitInstance(); //레트로핏 싱글톤
        progressBar = view.findViewById(R.id.progress_bar_liked_card);
        progressBar.setVisibility(View.VISIBLE);

        //리싸이클러뷰 설정
        recyclerView = view.findViewById(R.id.liked_sunhan);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(recyclerViewManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        swipeRefreshLayout = view.findViewById(R.id.swipe_likeSunHan);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 새로운 데이터 스와이프로 얻어오기
                initData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        //처음 생성시 초기 데이터 요청
        initData();

        return view;
    }

    private void initData() {
        if(LoginActivity.userAccessToken!=null){
            if(tokenRetrofitInstance!=null){
                Call<ScrapsSunHanResponse> call = RetrofitInstance.getRetrofitService().getSunHanScraps("Bearer "+LoginActivity.userAccessToken,"sunhan");
                call.enqueue(new Callback<ScrapsSunHanResponse>() {
                    @Override
                    public void onResponse(Call<ScrapsSunHanResponse> call, Response<ScrapsSunHanResponse> response) {
                        if (response.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            ScrapsSunHanResponse result = response.body();
                            likedSunHanAdapter = new LikedSunHanAdapter(getActivity(),result.getScrapsItem().getScrapSunhan());
                            recyclerView.setAdapter(likedSunHanAdapter);
                            likedSunHanAdapter.setOnClickLikedSunHanListener(new OnClickLikedSunHanListener() {
                                @Override
                                public void onItemClick(LikedSunHanAdapter.ViewHolder holder, View view, int position) {
                                    if(position!=RecyclerView.NO_POSITION){
                                        Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
                                        intent.putExtra("_id", likedSunHanAdapter.getItem(position).get_id());
                                        intent.putExtra("whichStore", 1);
                                        startActivity(intent);
                                    }
                                }
                            });
                            Log.d("선한영향력스크랩리스트성공", new Gson().toJson(response.body()));
                        } else {
                            progressBar.setVisibility(View.GONE);
                            if(response.message().equals("Unauthorized")){
                                checkAuthorized();
                            }
                            Log.d("REST FAILED MESSAGE", response.message());
                        }
                    }
                    @Override
                    public void onFailure(Call<ScrapsSunHanResponse> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("REST ERROR!", t.getMessage());
                        Toast.makeText(getContext(), "네트워크를 확인해주세요!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
    private void checkAuthorized(){
        Call<TokenResponse> call = RetrofitInstance.getRetrofitService().getRefreshToken("Bearer "+LoginActivity.userAccessToken, LoginActivity.userRefreshToken );
        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful()) {
                    TokenResponse result = response.body();
                    LoginActivity.userAccessToken = result.getTokenItem().getAccessToken();
                    LoginActivity.userRefreshToken = result.getTokenItem().getRefreshToken();
                    initData();
                    Log.d("성공", new Gson().toJson(response.body()));
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
}
