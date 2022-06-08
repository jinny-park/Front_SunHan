package com.capsaicin.sunhan.View.fragment;


import static com.capsaicin.sunhan.View.activity.LocationSettingActivity.lat;
import static com.capsaicin.sunhan.View.activity.LocationSettingActivity.lng;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.capsaicin.sunhan.Model.CardStoreItem;
import com.capsaicin.sunhan.Model.CardStoreResponse;
import com.capsaicin.sunhan.Model.LikedChildItem;
import com.capsaicin.sunhan.Model.LikedSunHanItem;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitServiceApi;
import com.capsaicin.sunhan.Model.ScrapChildResponse;
import com.capsaicin.sunhan.Model.StoreItem;
import com.capsaicin.sunhan.Model.TokenResponse;
import com.capsaicin.sunhan.Model.UserResponse;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.activity.BottomNavigationActivity;
import com.capsaicin.sunhan.View.activity.LoginActivity;
import com.capsaicin.sunhan.View.activity.StoreDetailActivity;
import com.capsaicin.sunhan.View.adapter.CardStoreAdapter;
import com.capsaicin.sunhan.View.adapter.LikedChildAdapter;
import com.capsaicin.sunhan.View.adapter.SunhanStoreAdapter;
import com.capsaicin.sunhan.View.interfaceListener.OnClickCardStoreItemListener;
import com.capsaicin.sunhan.View.interfaceListener.OnClickLikedChildListener;
import com.capsaicin.sunhan.View.interfaceListener.OnClickStoreItemListener;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SunhanstCardFragment extends Fragment{

    RecyclerView sunhanCardRecyclerView;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    private RetrofitInstance tokenRetrofitInstance ;
    CardStoreAdapter cardStoreAdapter;  /*new CardStoreAdapter(getActivity(),cardStoreList) ;*/
    int page;
    SwipeRefreshLayout swipeRefreshLayout;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sunhanst_card,null);
        tokenRetrofitInstance=RetrofitInstance.getRetrofitInstance(); //레트로핏 싱글톤
        progressBar = view.findViewById(R.id.progress_bar);
        swipeRefreshLayout = view.findViewById(R.id.swip_children);


        //리사이클러뷰 페이징네이션
        page = 1;

        //리사이클러뷰 설정
        sunhanCardRecyclerView = view.findViewById(R.id.recyclerview_sunhancard);
        sunhanCardRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
        sunhanCardRecyclerView.setLayoutManager(recyclerViewManager);
        sunhanCardRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //초기데이터 세팅
        initData(0);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override // 스와이프 실행 시 새 데이터
            public void onRefresh() {
                initData(0);
                swipeRefreshLayout.setRefreshing(false);
            }
        });



        sunhanCardRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //리사이클러뷰 페이징네이션
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                // 리사이클러뷰 스크롤 인지
                // 스크롤 인지 시 추가데이터 서버에 요청 해서 10개씩 데이터 받아옴
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = ((LinearLayoutManager) sunhanCardRecyclerView.getLayoutManager()).
                        findLastCompletelyVisibleItemPosition();
                int itemTotalCount = sunhanCardRecyclerView.getAdapter().getItemCount() - 1;
                if(lastVisibleItemPosition == itemTotalCount) {
                    progressBar.setVisibility(View.VISIBLE);
                    getData(page);
                    page++;
                }
            }
        });

        return view;

    }

    private void initData(int page) { // 초기 데이터 세팅
        if(LoginActivity.userAccessToken!=null){
            if(tokenRetrofitInstance!=null){
                serverInitUserRequest(page);
            }
        } else{
            if(tokenRetrofitInstance!=null){
                serverInitNoUserRequest(page,lat,lng);
            }
        }
    }


    private void getData(int page) { // 추가데이터 받아옴
        if(LoginActivity.userAccessToken!=null){
            if(tokenRetrofitInstance!=null){
                serverUserNextRequest(page);
            }
        } else{
            if(tokenRetrofitInstance!=null){
                serverNoUserNextRequest(page,lat,lng);
            }
        }
    }


    private void serverInitUserRequest(int page){ // 회원 서버 요청 레트로핏 함수
        Call<CardStoreResponse> call = RetrofitInstance.getRetrofitService().getChildrenStoreList("Bearer "+LoginActivity.userAccessToken,page,null);
        call.enqueue(new Callback<CardStoreResponse>() {
            @Override
            public void onResponse(Call<CardStoreResponse> call, Response<CardStoreResponse> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    CardStoreResponse result = response.body();
                    cardStoreAdapter = new CardStoreAdapter(getActivity(),result.getData());
                    sunhanCardRecyclerView.setAdapter(cardStoreAdapter);
                    cardStoreAdapter.setOnClickCardStoreItemListener(new OnClickCardStoreItemListener() {
                        @Override
                        public void onItemClick(CardStoreAdapter.ViewHolder holder, View view, int position) {
                            if(position!=RecyclerView.NO_POSITION){
                                Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
                                intent.putExtra("_id", cardStoreAdapter.getItem(position).get_id());
                                intent.putExtra("whichStore", 0);
                                startActivity(intent);
                            }
                        }
                    });
                    Log.d("성공", new Gson().toJson(response.body()));
                } else {
                    progressBar.setVisibility(View.GONE);
                    if(response.message().equals("Unauthorized")){
                        checkAuthorized();
                    }
                    Log.d("REST FAILED MESSAGE", response.message());
                }
            }

            @Override
            public void onFailure(Call<CardStoreResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "네트워크를 확인해주세요!", Toast.LENGTH_SHORT).show();
                Log.d("REST ERROR!", t.getMessage());
            }
        });
    }

    private void serverUserNextRequest(int page){ //유저의 추가데이터 요청 함수
        Call<CardStoreResponse> call = RetrofitInstance.getRetrofitService().getChildrenStoreList("Bearer "+LoginActivity.userAccessToken,page,null);
        call.enqueue(new Callback<CardStoreResponse>() {
            @Override
            public void onResponse(Call<CardStoreResponse> call, Response<CardStoreResponse> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    CardStoreResponse result = response.body();
                    cardStoreAdapter.addList(result.getData());
                    cardStoreAdapter.setOnClickCardStoreItemListener(new OnClickCardStoreItemListener() {
                        @Override
                        public void onItemClick(CardStoreAdapter.ViewHolder holder, View view, int position) {
                            if(position!=RecyclerView.NO_POSITION){
                                int imageIndex=0;
                                Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
                                intent.putExtra("_id", cardStoreAdapter.getItem(position).get_id());
                                intent.putExtra("whichStore", 0);
                                startActivity(intent);
                            }
                        }
                    });
                    Log.d("성공", new Gson().toJson(response.body()));
                } else {
                    progressBar.setVisibility(View.GONE);
                    if(response.message().equals("Unauthorized")){
                        checkAuthorized();
                    }
                    Log.d("REST FAILED MESSAGE", response.message());
                }
            }

            @Override
            public void onFailure(Call<CardStoreResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "네트워크를 확인해주세요!", Toast.LENGTH_SHORT).show();
                Log.d("REST ERROR!", t.getMessage());
            }
        });
    }

    private void serverNoUserNextRequest(int page, double lat, double lng){ // 비회원의 추가데이터 요청 메소드
        Call<CardStoreResponse> call = RetrofitInstance.getRetrofitService().getChildrenStoreListNoUser(page, null, lat, lng);
        call.enqueue(new Callback<CardStoreResponse>() {
            @Override
            public void onResponse(Call<CardStoreResponse> call, Response<CardStoreResponse> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    CardStoreResponse result = response.body();
                    cardStoreAdapter.addList(result.getData());
                    cardStoreAdapter.setOnClickCardStoreItemListener(new OnClickCardStoreItemListener() {
                        @Override
                        public void onItemClick(CardStoreAdapter.ViewHolder holder, View view, int position) {
                            if(position!=RecyclerView.NO_POSITION){
                                Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
                                intent.putExtra("_id", cardStoreAdapter.getItem(position).get_id());
                                intent.putExtra("whichStore", 0);

                                startActivity(intent);
                            }
                        }
                    });
                    Log.d("성공", new Gson().toJson(response.body()));
                } else {
                    progressBar.setVisibility(View.GONE);
                    Log.d("REST FAILED MESSAGE", response.message());
                }
            }

            @Override
            public void onFailure(Call<CardStoreResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "네트워크를 확인해주세요!", Toast.LENGTH_SHORT).show();
                Log.d("REST ERROR!", t.getMessage());
            }
        });
    }

    private void serverInitNoUserRequest(int page,double lat, double lng){ // 비회원 초기 서버 요청 레트로핏 함수
        Call<CardStoreResponse> call = RetrofitInstance.getRetrofitService().getChildrenStoreListNoUser(page, null, lat, lng);
        call.enqueue(new Callback<CardStoreResponse>() {
            @Override
            public void onResponse(Call<CardStoreResponse> call, Response<CardStoreResponse> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    CardStoreResponse result = response.body();
                    cardStoreAdapter = new CardStoreAdapter(getActivity(),result.getData());
                    sunhanCardRecyclerView.setAdapter(cardStoreAdapter);
                    cardStoreAdapter.setOnClickCardStoreItemListener(new OnClickCardStoreItemListener() {
                        @Override
                        public void onItemClick(CardStoreAdapter.ViewHolder holder, View view, int position) {
                            if(position!=RecyclerView.NO_POSITION){
                                Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
                                intent.putExtra("_id", cardStoreAdapter.getItem(position).get_id());
                                intent.putExtra("whichStore", 0);
                                startActivity(intent);
                            }
                        }
                    });
                    Log.d("성공", new Gson().toJson(response.body()));
                } else {
                    progressBar.setVisibility(View.GONE);
                    Log.d("REST FAILED MESSAGE", response.message());
                }
            }

            @Override
            public void onFailure(Call<CardStoreResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "네트워크를 확인해주세요!", Toast.LENGTH_SHORT).show();
                Log.d("REST ERROR!", t.getMessage());
            }
        });
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
                    serverInitUserRequest(0);
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

