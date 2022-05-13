package com.capsaicin.sunhan.View.fragment;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    public static SunhanstCardFragment sunhanstCardFragment;
    ArrayList<CardStoreItem> cardStoreList=new ArrayList();
    RecyclerView sunhanCardRecyclerView;
    ProgressBar progressBar;
    NestedScrollView nestedScrollView;
    private RetrofitInstance tokenRetrofitInstance ;
    int page = 0;
    CardStoreAdapter cardStoreAdapter ;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sunhanst_card,null);
        tokenRetrofitInstance=RetrofitInstance.getRetrofitInstance(); //레트로핏 싱글톤
        progressBar = view.findViewById(R.id.progress_bar);
        cardStoreAdapter = new CardStoreAdapter(getContext(),cardStoreList);
        nestedScrollView = view.findViewById(R.id.card_store_scrollView);
//        setRecyclerview(view);

        sunhanCardRecyclerView = view.findViewById(R.id.recyclerview_sunhancard);
        sunhanCardRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
        sunhanCardRecyclerView.setLayoutManager(recyclerViewManager);
        sunhanCardRecyclerView.setItemAnimator(new DefaultItemAnimator());
        sunhanCardRecyclerView.setAdapter(cardStoreAdapter);
        init();

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener()
        {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY)
            {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())
                {
                    page++;
                    progressBar.setVisibility(View.VISIBLE);
                    getData(page);
                    cardStoreAdapter.notifyDataSetChanged();
//                    sunhanCardRecyclerView.setAdapter(cardStoreAdapter);
                }
            }
        });




        cardStoreAdapter.setOnClickCardStoreItemListener(new OnClickCardStoreItemListener() {
            @Override
            public void onItemClick(CardStoreAdapter.ViewHolder holder, View view, int position) {
                String str_position = String.valueOf(position+1);
                if(position!=RecyclerView.NO_POSITION){
                    Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
                        intent.putExtra("_id", cardStoreAdapter.getItem(position).get_id());
                        startActivity(intent);


//                    for(int i=0; i<=position; i++){
//                        Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
//                        intent.putExtra("position", str_position);
//                        startActivity(intent);
//                        break;
//                    }
                }
            }
        });



        return view;

    }

    public static SunhanstCardFragment getSunhanstCardFragment(){

        if(sunhanstCardFragment==null){
            sunhanstCardFragment = new SunhanstCardFragment();
        }

        return sunhanstCardFragment;
    }
    void init(){
        getData(page);
        cardStoreAdapter.notifyDataSetChanged();
    }



    private void getData(int page)
    {
        if(LoginActivity.userAccessToken!=null){
            if(tokenRetrofitInstance!=null){
                Call<CardStoreResponse> call = RetrofitInstance.getRetrofitService().getChildrenStoreList("Bearer "+LoginActivity.userAccessToken,page,null);
                call.enqueue(new Callback<CardStoreResponse>() {
                    @Override
                    public void onResponse(Call<CardStoreResponse> call, Response<CardStoreResponse> response) {
                        if (response.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            CardStoreResponse result = response.body();
                            cardStoreAdapter.addList(result.getData());
                            Log.d("성공", new Gson().toJson(response.body()));
                        } else {
                            Log.d("REST FAILED MESSAGE", response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<CardStoreResponse> call, Throwable t) {
                        Log.d("REST ERROR!", t.getMessage());
                    }
                });
            }
        }
    }

    void setRecyclerview(View view){
//        sunhanCardRecyclerView = view.findViewById(R.id.recyclerview_sunhancard);
//        sunhanCardRecyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
//        sunhanCardRecyclerView.setLayoutManager(recyclerViewManager);
//        sunhanCardRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        sunhanCardRecyclerView.setAdapter(cardStoreAdapter);

    }
}

