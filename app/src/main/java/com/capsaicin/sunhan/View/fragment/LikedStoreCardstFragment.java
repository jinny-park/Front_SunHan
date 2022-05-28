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
import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.Model.ScrapChildResponse;
import com.capsaicin.sunhan.Model.ScrapsSunHanResponse;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.activity.LoginActivity;
import com.capsaicin.sunhan.View.activity.StoreDetailActivity;
import com.capsaicin.sunhan.View.adapter.CardStoreAdapter;
import com.capsaicin.sunhan.View.adapter.LikedChildAdapter;
import com.capsaicin.sunhan.View.interfaceListener.OnClickCardStoreItemListener;
import com.capsaicin.sunhan.View.interfaceListener.OnClickLikedChildListener;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LikedStoreCardstFragment extends Fragment {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    private RetrofitInstance tokenRetrofitInstance ;
    LikedChildAdapter likedChildAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_likestore_cardst,null);

        tokenRetrofitInstance=RetrofitInstance.getRetrofitInstance(); //레트로핏 싱글톤
        progressBar = view.findViewById(R.id.progress_bar_liked_card);
        progressBar.setVisibility(View.VISIBLE);

        swipeRefreshLayout = view.findViewById(R.id.swipe_likeCard);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 새로운 데이터 스와이프로 얻어오기
                initData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        //리사이클러뷰 설정
        recyclerView = view.findViewById(R.id.liked_cardStore);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(recyclerViewManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // 처음 생성 됐을 때 초기 데이터 요청
        initData();

        return view;
    }

    private void initData()
    {
        if(LoginActivity.userAccessToken!=null){
            if(tokenRetrofitInstance!=null){
                Call<ScrapChildResponse> call = RetrofitInstance.getRetrofitService().getChildrenScraps("Bearer "+LoginActivity.userAccessToken,"children");
                call.enqueue(new Callback<ScrapChildResponse>() {
                    @Override
                    public void onResponse(Call<ScrapChildResponse> call, Response<ScrapChildResponse> response) {
                        if (response.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            ScrapChildResponse result;
                            result = response.body();
                            likedChildAdapter = new LikedChildAdapter(getActivity(),result.getScrapChildItem().getLikedChildItems());
                            recyclerView.setAdapter(likedChildAdapter);
                            likedChildAdapter.setOnClickLikedChildListener(new OnClickLikedChildListener() {
                                @Override
                                public void onItemClick(LikedChildAdapter.ViewHolder holder, View view, int position) {
                                    String str_position = String.valueOf(position+1);
                                    if(position!=RecyclerView.NO_POSITION){
                                        Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
                                        intent.putExtra("_id", likedChildAdapter.getItem(position).get_id());
                                        intent.putExtra("whichStore", 0);
                                        startActivity(intent);
                                    }
                                }
                            });
                            Log.d("가맹점찜한가게리스트성공", new Gson().toJson(response.body()));
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Log.d("REST FAILED MESSAGE", response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ScrapChildResponse> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("REST ERROR!", t.getMessage());
                        Toast.makeText(getContext(), "네트워크를 확인해주세요!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}
