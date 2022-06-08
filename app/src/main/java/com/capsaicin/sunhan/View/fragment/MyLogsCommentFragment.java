package com.capsaicin.sunhan.View.fragment;

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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.capsaicin.sunhan.Model.MyCommentLogsResponse;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.Model.TokenResponse;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.activity.CommunityDetailActivity;
import com.capsaicin.sunhan.View.activity.LoginActivity;
import com.capsaicin.sunhan.View.adapter.MyCommentLogsAdapter;
import com.capsaicin.sunhan.View.adapter.MyPostLogsAdapter;
import com.capsaicin.sunhan.View.interfaceListener.OnClickCommentLogsListener;
import com.capsaicin.sunhan.View.interfaceListener.OnClickPostLogsListener;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyLogsCommentFragment extends Fragment {

    RecyclerView commentsLogsRecyclerView;
    ProgressBar progressBar;
    MyCommentLogsAdapter myCommentLogsAdapter;
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

        View view = inflater.inflate(R.layout.fragment_mylogs_comment, null);

        progressBar = view.findViewById(R.id.progress_bar_myComments);
        swipeRefreshLayout = view.findViewById(R.id.swipe_myLog_comment);

        page = 1;
        tokenRetrofitInstance=RetrofitInstance.getRetrofitInstance(); //레트로핏 싱글톤

        commentsLogsRecyclerView = view.findViewById(R.id.recyclerview_logs_comments);
        commentsLogsRecyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
        commentsLogsRecyclerView.setLayoutManager(recyclerViewManager);
        commentsLogsRecyclerView.setItemAnimator(new DefaultItemAnimator());

        initData(0);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData(0);
                page=1;
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        commentsLogsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = ((LinearLayoutManager) commentsLogsRecyclerView.getLayoutManager()).
                        findLastCompletelyVisibleItemPosition();
                int itemTotalCount = commentsLogsRecyclerView.getAdapter().getItemCount() - 1;
                if(lastVisibleItemPosition == itemTotalCount) {
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
                progressBar.setVisibility(View.VISIBLE);
                Call<MyCommentLogsResponse> call = RetrofitInstance.getRetrofitService().getMyComments("Bearer "+LoginActivity.userAccessToken,page);
                call.enqueue(new Callback<MyCommentLogsResponse>() {
                    @Override
                    public void onResponse(Call<MyCommentLogsResponse> call, Response<MyCommentLogsResponse> response) {
                        if (response.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            MyCommentLogsResponse result = response.body();
                            myCommentLogsAdapter = new MyCommentLogsAdapter(getActivity(),result.getData());
                            commentsLogsRecyclerView.setAdapter(myCommentLogsAdapter);
                            myCommentLogsAdapter.setOnClickCommentLogsListener(new OnClickCommentLogsListener() {
                                @Override
                                public void onItemClick(MyCommentLogsAdapter.ViewHolder holder, View view, int position) {
                                    if(position!=RecyclerView.NO_POSITION){
                                        Intent intent = new Intent(getActivity(), CommunityDetailActivity.class);
                                        intent.putExtra("_id", myCommentLogsAdapter.getItem(position).get_id());
                                        Log.d("아이디", myCommentLogsAdapter.getItem(position).get_id());

                                        startActivity(intent);
                                    }
                                }
                            });
                            Log.d("성공", new Gson().toJson(response.body()));
                        } else {
                            progressBar.setVisibility(View.GONE);
                            if(response.message().equals("Unauthorized")){
                                checkAuthorized();
                                initData(0);
                            }
                            Log.d("REST FAILED MESSAGE", response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<MyCommentLogsResponse> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("REST ERROR!", t.getMessage());
                        Toast.makeText(getContext(), "네트워크를 확인해주세요!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }


    private void getData(int page)
    {
        if(LoginActivity.userAccessToken!=null){
            if(tokenRetrofitInstance!=null){
                Call<MyCommentLogsResponse> call = RetrofitInstance.getRetrofitService().getMyComments("Bearer "+LoginActivity.userAccessToken,page);
                call.enqueue(new Callback<MyCommentLogsResponse>() {
                    @Override
                    public void onResponse(Call<MyCommentLogsResponse> call, Response<MyCommentLogsResponse> response) {
                        if (response.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            MyCommentLogsResponse result = response.body();
                            myCommentLogsAdapter.addList(result.getData());
                            myCommentLogsAdapter.setOnClickCommentLogsListener(new OnClickCommentLogsListener() {
                                @Override
                                public void onItemClick(MyCommentLogsAdapter.ViewHolder holder, View view, int position) {
                                    if(position!=RecyclerView.NO_POSITION){
                                        Intent intent = new Intent(getActivity(), CommunityDetailActivity.class);
                                        intent.putExtra("_id", myCommentLogsAdapter.getItem(position).get_id());
                                        Log.d("아이디", myCommentLogsAdapter.getItem(position).get_id());
                                        startActivity(intent);
                                    }
                                }
                            });
                            Log.d("성공", new Gson().toJson(response.body()));
                        } else {
                            progressBar.setVisibility(View.GONE);
                            if(response.message().equals("Unauthorized")){
                                checkAuthorized();
                                getData(page);
                            }
                            Log.d("REST FAILED MESSAGE", response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<MyCommentLogsResponse> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("REST ERROR!", t.getMessage());
                        Toast.makeText(getContext(), "네트워크를 확인해주세요!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
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