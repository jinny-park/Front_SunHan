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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capsaicin.sunhan.Model.CardStoreResponse;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.Model.StoreItem;
import com.capsaicin.sunhan.Model.StoreResponse;
import com.capsaicin.sunhan.R;
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

public class SunhanstCategoryFragment extends Fragment {
    ArrayList<StoreItem> storeList=new ArrayList<StoreItem>();
    RecyclerView categoryRecycler;
    SunhanStoreAdapter storeAdapter;
    ProgressBar progressBar;
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

        categoryRecycler = view.findViewById(R.id.recyclerview_sunhanstore_category);
        categoryRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
        categoryRecycler.setLayoutManager(recyclerViewManager);
        categoryRecycler.setItemAnimator(new DefaultItemAnimator());


        initData(0);


        categoryRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
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

    private void initData(int page)
    {
        if(LoginActivity.userAccessToken!=null){
            if(tokenRetrofitInstance!=null){
                Log.d("카드프래그먼트", "토큰인스턴스이후 콜백 전");
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
                                        Log.d("아이디", storeAdapter.getItem(position).get_id());

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
                    public void onFailure(Call<StoreResponse> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("REST ERROR!", t.getMessage());
                    }
                });
            }
        } else if(LoginActivity.userAccessToken==null){
        if(tokenRetrofitInstance!=null){
            Log.d("카드프래그먼트", "토큰인스턴스이후 콜백 전");
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
                                    Log.d("아이디", storeAdapter.getItem(position).get_id());
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
                                    String str_position = String.valueOf(position+1);
                                    if(position!=RecyclerView.NO_POSITION){
                                        Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
                                        intent.putExtra("_id", storeAdapter.getItem(position).get_id());
                                        intent.putExtra("whichStore", 1);
                                        Log.d("아이디", storeAdapter.getItem(position).get_id());

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
                    public void onFailure(Call<StoreResponse> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("REST ERROR!", t.getMessage());
                    }
                });
            }
        } else if(LoginActivity.userAccessToken==null){
            if(tokenRetrofitInstance!=null){
                Log.d("카드프래그먼트", "토큰인스턴스이후 콜백 전");
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
                                        Log.d("아이디", storeAdapter.getItem(position).get_id());
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
                        Log.d("REST ERROR!", t.getMessage());
                    }
                });
            }
        }
    }
}
