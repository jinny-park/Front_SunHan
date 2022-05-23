package com.capsaicin.sunhan.View.fragment;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import static com.capsaicin.sunhan.View.activity.StoreDetailActivity.whichStore;
import static com.capsaicin.sunhan.View.fragment.SunhanstMainFragment.storeId;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.capsaicin.sunhan.Model.CardStoreDetailResponse;
import com.capsaicin.sunhan.Model.LetterItem;
import com.capsaicin.sunhan.Model.LetterResponse;
import com.capsaicin.sunhan.Model.ResultResponse;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.Model.SunHanStoreDetailResponse;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.activity.CommunityDetailActivity;
import com.capsaicin.sunhan.View.activity.LoginActivity;
import com.capsaicin.sunhan.View.activity.StoreDetailActivity;
import com.capsaicin.sunhan.View.activity.WriteLetterActivity;
import com.capsaicin.sunhan.View.adapter.LetterAdapter;
import com.capsaicin.sunhan.View.adapter.MyCommentLogsAdapter;
import com.capsaicin.sunhan.View.interfaceListener.OnClickCommentLogsListener;
import com.capsaicin.sunhan.View.interfaceListener.OnClickLetterListener;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreLetterFragment extends Fragment {
    LetterAdapter letterAdapter;
    RecyclerView letterRecyclerView;

    int page;
    ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout;

    private RetrofitInstance tokenRetrofitInstance ;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sunhanst_store_letter,null);
        tokenRetrofitInstance=RetrofitInstance.getRetrofitInstance(); //레트로핏 싱글톤
        page = 1;
        progressBar = view.findViewById(R.id.progress_bar_letter);

        swipeRefreshLayout = view.findViewById(R.id.swipe_leteter);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initLetterData(0);
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        letterRecyclerView = view.findViewById(R.id.recyclerview_letter);
        letterRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
        letterRecyclerView.setLayoutManager(recyclerViewManager);
        letterRecyclerView.setItemAnimator(new DefaultItemAnimator());

        initLetterData(0);


        letterRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = ((LinearLayoutManager) letterRecyclerView.getLayoutManager()).
                        findLastCompletelyVisibleItemPosition();
                int itemTotalCount = letterRecyclerView.getAdapter().getItemCount() - 1;
                if(lastVisibleItemPosition == itemTotalCount) {
                    progressBar.setVisibility(View.VISIBLE);
                    getLetterData(page);
                    page++;


                }
            }
        });

        return view;
    }



    public void initLetterData(int page)
    {
        if(tokenRetrofitInstance!=null && whichStore==0){ // 가맹점 편지 리스트
            Call<LetterResponse> call = RetrofitInstance.getRetrofitService().getLetter(StoreDetailActivity.id, "children", page);
            call.enqueue(new Callback<LetterResponse>() {
                @Override
                public void onResponse(Call<LetterResponse> call, Response<LetterResponse> response) {
                    if (response.isSuccessful()) {
                        LetterResponse result = response.body();
                        progressBar.setVisibility(View.GONE);
                        letterAdapter = new LetterAdapter(getActivity(), result.getData());
                        letterRecyclerView.setAdapter(letterAdapter);
                        Log.d("성공", new Gson().toJson(response.body()));
                    } else {

                        Log.d("편지 로딩 실패", response.message());
                    }
                }

                @Override
                public void onFailure(Call<LetterResponse> call, Throwable t) {
                    Log.d("REST ERROR!", t.getMessage());
                    Toast.makeText(getContext(), "네트워크를 확인해주세요!", Toast.LENGTH_LONG).show();
                }
            });
        } else if(tokenRetrofitInstance!=null && whichStore==1){ // 선한영향력 가게 편지 리스트
            Call<LetterResponse> call = RetrofitInstance.getRetrofitService().getLetter(StoreDetailActivity.id, "sunhan", page);
            call.enqueue(new Callback<LetterResponse>() {
                @Override
                public void onResponse(Call<LetterResponse> call, Response<LetterResponse> response) {
                    if (response.isSuccessful()) {
                        LetterResponse result = response.body();
                        progressBar.setVisibility(View.GONE);
                        letterAdapter = new LetterAdapter(getActivity(), result.getData());
                        letterRecyclerView.setAdapter(letterAdapter);

                        Log.d("성공", new Gson().toJson(response.body()));
                    } else {

                        Log.d("ERROR", response.message());
                    }
                }
                @Override
                public void onFailure(Call<LetterResponse> call, Throwable t) {
                    Log.d("REST ERROR!", t.getMessage());
                    Toast.makeText(getContext(), "네트워크를 확인해주세요!", Toast.LENGTH_LONG).show();
                }
            });


        }
    }


    private void getLetterData(int page) {
        if (tokenRetrofitInstance != null && whichStore == 0) { // 가맹점 편지 리스트
            Call<LetterResponse> call = RetrofitInstance.getRetrofitService().getLetter(StoreDetailActivity.id, "children", page);
            call.enqueue(new Callback<LetterResponse>() {
                @Override
                public void onResponse(Call<LetterResponse> call, Response<LetterResponse> response) {
                    if (response.isSuccessful()) {
                        LetterResponse result = response.body();
                        progressBar.setVisibility(View.GONE);
                        letterAdapter.addList(result.getData());
                        Log.d("성공", new Gson().toJson(response.body()));
                    } else {

                        Log.d("편지실패", response.message());
                    }
                }

                @Override
                public void onFailure(Call<LetterResponse> call, Throwable t) {
                    Log.d("REST ERROR!", t.getMessage());
                    Toast.makeText(getContext(), "네트워크를 확인해주세요!", Toast.LENGTH_LONG).show();
                }
            });
        } else if (tokenRetrofitInstance != null && whichStore == 1) {
            Call<LetterResponse> call = RetrofitInstance.getRetrofitService().getLetter(StoreDetailActivity.id, "sunhan", page);
            call.enqueue(new Callback<LetterResponse>() {
                @Override
                public void onResponse(Call<LetterResponse> call, Response<LetterResponse> response) {
                    if (response.isSuccessful()) {
                        LetterResponse result = response.body();
                        progressBar.setVisibility(View.GONE);
                        letterAdapter.addList(result.getData());
                        Log.d("성공", new Gson().toJson(response.body()));
                    } else {

                        Log.d("편지실패", response.message());
                    }
                }

                @Override
                public void onFailure(Call<LetterResponse> call, Throwable t) {
                    Log.d("REST ERROR!", t.getMessage());
                    Toast.makeText(getContext(), "네트워크를 확인해주세요!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}