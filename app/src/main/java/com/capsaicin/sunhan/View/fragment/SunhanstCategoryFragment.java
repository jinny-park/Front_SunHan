package com.capsaicin.sunhan.View.fragment;

import static com.capsaicin.sunhan.View.activity.LocationSettingActivity.lat;
import static com.capsaicin.sunhan.View.activity.LocationSettingActivity.lng;

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
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.capsaicin.sunhan.Model.CardStoreResponse;
import com.capsaicin.sunhan.Model.LikedChildItem;
import com.capsaicin.sunhan.Model.LikedSunHanItem;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.Model.ScrapChildResponse;
import com.capsaicin.sunhan.Model.ScrapsSunHanResponse;
import com.capsaicin.sunhan.Model.StoreItem;
import com.capsaicin.sunhan.Model.StoreResponse;
import com.capsaicin.sunhan.Model.TokenResponse;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.activity.LoginActivity;
import com.capsaicin.sunhan.View.activity.StoreDetailActivity;
import com.capsaicin.sunhan.View.adapter.CardStoreAdapter;
import com.capsaicin.sunhan.View.adapter.LikedSunHanAdapter;
import com.capsaicin.sunhan.View.adapter.SunhanStoreAdapter;
import com.capsaicin.sunhan.View.interfaceListener.OnClickCardStoreItemListener;
import com.capsaicin.sunhan.View.interfaceListener.OnClickLikedSunHanListener;
import com.capsaicin.sunhan.View.interfaceListener.OnClickStoreItemListener;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SunhanstCategoryFragment extends Fragment{
    ArrayList<StoreItem> storeList=new ArrayList<StoreItem>();
    RecyclerView categoryRecycler;
    SunhanStoreAdapter storeAdapter;
    ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout;
    private RetrofitInstance tokenRetrofitInstance ;
    int page;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sunhanst_category,null);
        tokenRetrofitInstance=RetrofitInstance.getRetrofitInstance(); //레트로핏 싱글톤
        progressBar = view.findViewById(R.id.progress_bar_category);
        progressBar.setVisibility(View.VISIBLE);
        page = 1;
        swipeRefreshLayout = view.findViewById(R.id.swip_category);

        //리사이클러뷰 설정
        categoryRecycler = view.findViewById(R.id.recyclerview_sunhanstore_category);
        categoryRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
        categoryRecycler.setLayoutManager(recyclerViewManager);
        categoryRecycler.setItemAnimator(new DefaultItemAnimator());


        //초기 데이터 불러옴
        initData(0);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            //스와이프 시 새로 데이터 요청
            @Override
            public void onRefresh() {
                initData(0);
                page=1;
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        categoryRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                // 리사이클러뷰 페이징네이션
                // 스크롤 인식 시 다음 데이터 서버에 요청
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = ((LinearLayoutManager) categoryRecycler.getLayoutManager()).
                        findLastCompletelyVisibleItemPosition();
                int itemTotalCount = categoryRecycler.getAdapter().getItemCount() - 1;
                if(lastVisibleItemPosition == itemTotalCount) {
                    progressBar.setVisibility(View.VISIBLE);
                    getData(page);
                    page++;
                }
            }
        });

        return view;

    }

    private void initData(int page) { //초기데이터 요청
        if(tokenRetrofitInstance!=null){
           if(LoginActivity.userAccessToken!=null){
               serverInitUserRequest(page); //회원
           }else{
               serverInitNoUserRequest(page,lat,lng); //비회원
           }
        }
    }


    private void getData(int page) { //추가 데이터 요청
        if(tokenRetrofitInstance!=null){
            if(LoginActivity.userAccessToken!=null){
                serverUserNextRequest(page); //회원
            }else{
                serverNoUserNextRequest(page,lat,lng); //비회원
            }
        }
    }

    private void serverInitUserRequest(int page){
        Call<StoreResponse> call = RetrofitInstance.getRetrofitService().getSunHanStoreList("Bearer "+LoginActivity.userAccessToken,page,SunhanstSunhanFragment.category,null);
        call.enqueue(new Callback<StoreResponse>() {
            @Override
            public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    StoreResponse result = response.body();
                    storeAdapter = new SunhanStoreAdapter(getActivity(),result.getData());
                    categoryRecycler.setAdapter(storeAdapter);
                    storeAdapter.setOnClickStoreItemListener(new OnClickStoreItemListener() {
                        @Override
                        public void onItemClick(SunhanStoreAdapter.ViewHolder holder, View view, int position) {
                            String str_position = String.valueOf(position+1);
                            if(position!=RecyclerView.NO_POSITION){
                                Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
                                intent.putExtra("_id", storeAdapter.getItem(position).get_id());
                                intent.putExtra("whichStore", 1);
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
            public void onFailure(Call<StoreResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "네트워크를 확인해주세요!", Toast.LENGTH_SHORT).show();
                Log.d("REST ERROR!", t.getMessage());
            }
        });
    }
    private void serverUserNextRequest(int page){
        Call<StoreResponse> call = RetrofitInstance.getRetrofitService().getSunHanStoreList("Bearer "+LoginActivity.userAccessToken,page,SunhanstSunhanFragment.category,null);
        call.enqueue(new Callback<StoreResponse>() {
            @Override
            public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    StoreResponse result = response.body();
                    storeAdapter.addList(result.getData());
                    storeAdapter.setOnClickStoreItemListener(new OnClickStoreItemListener() {
                        @Override
                        public void onItemClick(SunhanStoreAdapter.ViewHolder holder, View view, int position) {
                            if(position!=RecyclerView.NO_POSITION){
                                Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
                                intent.putExtra("_id", storeAdapter.getItem(position).get_id());
                                intent.putExtra("whichStore", 1);
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
            public void onFailure(Call<StoreResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "네트워크를 확인해주세요!", Toast.LENGTH_SHORT).show();
                Log.d("REST ERROR!", t.getMessage());
            }
        });
    }
    private void serverInitNoUserRequest(int page, double lat, double lng){
        Call<StoreResponse> call = RetrofitInstance.getRetrofitService().getSunHanStoreListNoUser(page,SunhanstSunhanFragment.category,null, lat, lng);
        call.enqueue(new Callback<StoreResponse>() {
            @Override
            public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    StoreResponse result = response.body();
                    storeAdapter = new SunhanStoreAdapter(getActivity(),result.getData());
                    categoryRecycler.setAdapter(storeAdapter);
                    storeAdapter.setOnClickStoreItemListener(new OnClickStoreItemListener() {
                        @Override
                        public void onItemClick(SunhanStoreAdapter.ViewHolder holder, View view, int position) {
                            String str_position = String.valueOf(position+1);
                            if(position!=RecyclerView.NO_POSITION){
                                Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
                                intent.putExtra("_id", storeAdapter.getItem(position).get_id());
                                intent.putExtra("whichStore", 1);
                                startActivity(intent);
                            }
                        }
                    });
                    Log.d("선한영향력성공", new Gson().toJson(response.body()));
                } else {
                    progressBar.setVisibility(View.GONE);
                    Log.d("REST FAILED MESSAGE", response.message());
                }
            }

            @Override
            public void onFailure(Call<StoreResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "네트워크를 확인해주세요!", Toast.LENGTH_SHORT).show();
                Log.d("REST ERROR!", t.getMessage());
            }
        });
    }

    private void serverNoUserNextRequest(int page, double lat, double lng){
        Call<StoreResponse> call = RetrofitInstance.getRetrofitService().getSunHanStoreListNoUser(page,SunhanstSunhanFragment.category,null, lat, lng);
        call.enqueue(new Callback<StoreResponse>() {
            @Override
            public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    StoreResponse result = response.body();
                    storeAdapter.addList(result.getData());
                    storeAdapter.setOnClickStoreItemListener(new OnClickStoreItemListener() {
                        @Override
                        public void onItemClick(SunhanStoreAdapter.ViewHolder holder, View view, int position) {
                            String str_position = String.valueOf(position+1);
                            if(position!=RecyclerView.NO_POSITION){
                                Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
                                intent.putExtra("_id", storeAdapter.getItem(position).get_id());
                                intent.putExtra("whichStore", 1);
                                startActivity(intent);
                            }
                        }
                    });
                    Log.d("선한영향력성공", new Gson().toJson(response.body()));
                } else {
                    progressBar.setVisibility(View.GONE);
                    Log.d("REST FAILED MESSAGE", response.message());
                }
            }

            @Override
            public void onFailure(Call<StoreResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "네트워크를 확인해주세요!", Toast.LENGTH_SHORT).show();
                Log.d("REST ERROR!", t.getMessage());
            }
        });
    }

    private void checkAuthorized(){
        Call<TokenResponse> call = RetrofitInstance.getRetrofitService().getRefreshToken("Bearer "+LoginActivity.userAccessToken,LoginActivity.userRefreshToken );
        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful()) {
                    TokenResponse result = response.body();
                    LoginActivity.userAccessToken = result.getTokenItem().getAccessToken();
                    LoginActivity.userRefreshToken = result.getTokenItem().getRefreshToken();
                    Log.d("리프레시성공", new Gson().toJson(response.body()));
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
