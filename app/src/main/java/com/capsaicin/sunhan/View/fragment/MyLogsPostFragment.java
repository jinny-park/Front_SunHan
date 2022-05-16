package com.capsaicin.sunhan.View.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capsaicin.sunhan.Model.MyPostLogsResponse;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.activity.CommunityDetailActivity;
import com.capsaicin.sunhan.View.activity.LoginActivity;
import com.capsaicin.sunhan.View.adapter.MyPostLogsAdapter;
import com.capsaicin.sunhan.View.interfaceListener.OnClickPostLogsListener;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyLogsPostFragment extends Fragment {

    RecyclerView postLogsRecyclerView;
    ProgressBar progressBar;
    MyPostLogsAdapter mypageMylogsAdapter;

    private RetrofitInstance tokenRetrofitInstance ;
    int page;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mylogs_post, null);
        progressBar = view.findViewById(R.id.progress_bar_myPost);
        page = 1;
        tokenRetrofitInstance=RetrofitInstance.getRetrofitInstance(); //레트로핏 싱글톤

        initData(0);

        postLogsRecyclerView = view.findViewById(R.id.recyclerview_logs_post);
        postLogsRecyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
        postLogsRecyclerView.setLayoutManager(recyclerViewManager);
        postLogsRecyclerView.setItemAnimator(new DefaultItemAnimator());

        postLogsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = ((LinearLayoutManager) postLogsRecyclerView.getLayoutManager()).
                        findLastCompletelyVisibleItemPosition();
                int itemTotalCount = postLogsRecyclerView.getAdapter().getItemCount() - 1;
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
                Call<MyPostLogsResponse> call = RetrofitInstance.getRetrofitService().getMyPosts("Bearer "+LoginActivity.userAccessToken,page);
                call.enqueue(new Callback<MyPostLogsResponse>() {
                    @Override
                    public void onResponse(Call<MyPostLogsResponse> call, Response<MyPostLogsResponse> response) {
                        if (response.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            MyPostLogsResponse result = response.body();
                            mypageMylogsAdapter = new MyPostLogsAdapter(getActivity(),result.getMypostLosgItem().getWritePosts());
                            postLogsRecyclerView.setAdapter(mypageMylogsAdapter);
                            mypageMylogsAdapter.setOnClickCommunityLogsListener(new OnClickPostLogsListener() {
                                @Override
                                public void onItemClick(MyPostLogsAdapter.ViewHolder holder, View view, int position) {
                                    if(position!=RecyclerView.NO_POSITION){
                                        Intent intent = new Intent(getActivity(), CommunityDetailActivity.class);
                                        intent.putExtra("_id", mypageMylogsAdapter.getItem(position).get_id());
                                        Log.d("아이디", mypageMylogsAdapter.getItem(position).get_id());

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
                    public void onFailure(Call<MyPostLogsResponse> call, Throwable t) {
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
                Call<MyPostLogsResponse> call = RetrofitInstance.getRetrofitService().getMyPosts("Bearer "+LoginActivity.userAccessToken,page);
                call.enqueue(new Callback<MyPostLogsResponse>() {
                    @Override
                    public void onResponse(Call<MyPostLogsResponse> call, Response<MyPostLogsResponse> response) {
                        if (response.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            MyPostLogsResponse result = response.body();
                            mypageMylogsAdapter.addList(result.getMypostLosgItem().getWritePosts());
                            mypageMylogsAdapter.setOnClickCommunityLogsListener(new OnClickPostLogsListener() {
                                @Override
                                public void onItemClick(MyPostLogsAdapter.ViewHolder holder, View view, int position) {
                                    if(position!=RecyclerView.NO_POSITION){
                                        Intent intent = new Intent(getActivity(), CommunityDetailActivity.class);
                                        intent.putExtra("_id", mypageMylogsAdapter.getItem(position).get_id());
                                        Log.d("아이디", mypageMylogsAdapter.getItem(position).get_id());
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
                    public void onFailure(Call<MyPostLogsResponse> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("REST ERROR!", t.getMessage());
                    }
                });
            }
        }
    }

}
