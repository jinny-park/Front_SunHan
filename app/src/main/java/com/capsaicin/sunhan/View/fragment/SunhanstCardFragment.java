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
import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitServiceApi;
import com.capsaicin.sunhan.Model.StoreItem;
import com.capsaicin.sunhan.Model.UserResponse;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.activity.BottomNavigationActivity;
import com.capsaicin.sunhan.View.activity.LoginActivity;
import com.capsaicin.sunhan.View.activity.StoreDetailActivity;
import com.capsaicin.sunhan.View.adapter.CardStoreAdapter;
import com.capsaicin.sunhan.View.adapter.SunhanStoreAdapter;
import com.capsaicin.sunhan.View.interfaceListener.OnClickCardStoreItemListener;
import com.capsaicin.sunhan.View.interfaceListener.OnClickStoreItemListener;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SunhanstCardFragment extends Fragment {

    ArrayList<CardStoreItem> cardStoreList=new ArrayList();
    RecyclerView sunhanCardRecyclerView;
    ProgressBar progressBar;
    private RetrofitInstance tokenRetrofitInstance ;
   CardStoreAdapter cardStoreAdapter;  /*new CardStoreAdapter(getActivity(),cardStoreList) ;*/
    int page;
    SwipeRefreshLayout swipeRefreshLayout;
    public static int distinguish; // 가맹점인 선한영향력인지 구분

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
        page = 1;

        //리사이클러뷰 설정
        sunhanCardRecyclerView = view.findViewById(R.id.recyclerview_sunhancard);
        sunhanCardRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
        sunhanCardRecyclerView.setLayoutManager(recyclerViewManager);
        sunhanCardRecyclerView.setItemAnimator(new DefaultItemAnimator());



       initData(0);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData(0);
                swipeRefreshLayout.setRefreshing(false);
            }
        });



        sunhanCardRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
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


    private void initData(int page)
    {
        if(LoginActivity.userAccessToken!=null){
            if(tokenRetrofitInstance!=null){
                Log.d("카드프래그먼트", "토큰인스턴스이후 콜백 전");
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
                                        Log.d("아이디", cardStoreAdapter.getItem(position).get_id());

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
                        Log.d("REST ERROR!", t.getMessage());
                    }
                });
            }
        } else if (LoginActivity.userAccessToken==null){
            if(tokenRetrofitInstance!=null){
                Log.d("카드프래그먼트", "토큰인스턴스이후 콜백 전");
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
                                        Log.d("아이디", cardStoreAdapter.getItem(position).get_id());

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
                        Log.d("REST ERROR!", t.getMessage());
                    }
                });
            }
        }
    }


    private void getData(int page)
    {
        if(LoginActivity.userAccessToken!=null){
            if(tokenRetrofitInstance!=null){
                Log.d("카드프래그먼트", "토큰인스턴스이후 콜백 전");
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
                                    String str_position = String.valueOf(position+1);
                                    if(position!=RecyclerView.NO_POSITION){
                                        Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
                                        intent.putExtra("_id", cardStoreAdapter.getItem(position).get_id());
                                        intent.putExtra("whichStore", 0);
                                        Log.d("아이디", cardStoreAdapter.getItem(position).get_id());
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
                        Log.d("REST ERROR!", t.getMessage());
                    }
                });
            }
        } else if (LoginActivity.userAccessToken==null){
            if(tokenRetrofitInstance!=null){
                Log.d("카드프래그먼트", "토큰인스턴스이후 콜백 전");
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
                                    String str_position = String.valueOf(position+1);
                                    if(position!=RecyclerView.NO_POSITION){
                                        Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
                                        intent.putExtra("_id", cardStoreAdapter.getItem(position).get_id());
                                        intent.putExtra("whichStore", 0);
                                        Log.d("아이디", cardStoreAdapter.getItem(position).get_id());

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
                        Log.d("REST ERROR!", t.getMessage());
                    }
                });
            }
        }
    }
}

