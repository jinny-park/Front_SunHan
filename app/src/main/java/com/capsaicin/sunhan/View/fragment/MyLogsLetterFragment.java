package com.capsaicin.sunhan.View.fragment;

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

import com.capsaicin.sunhan.Model.MyLetterLogsResponse;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.activity.LoginActivity;
import com.capsaicin.sunhan.View.adapter.MyLetterLogsAdapter;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyLogsLetterFragment extends Fragment {

    public static MyLetterLogsAdapter letterLogsAdapter;
    RecyclerView letterRecyclerView;
    ProgressBar progressBar;
    private RetrofitInstance tokenRetrofitInstance ;
    SwipeRefreshLayout swipeRefreshLayout;


    int page;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mylogs_letter, null);

        letterRecyclerView = view.findViewById(R.id.recyclerview_logs_letter);
        letterRecyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
        letterRecyclerView.setLayoutManager(recyclerViewManager);
        letterRecyclerView.setItemAnimator(new DefaultItemAnimator());
        swipeRefreshLayout = view.findViewById(R.id.swipe_myLog_letter);
        progressBar = view.findViewById(R.id.progress_bar_myLetter);
        page = 1;
        tokenRetrofitInstance=RetrofitInstance.getRetrofitInstance(); //레트로핏 싱글톤

        initData(0);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData(0);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        letterRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = ((LinearLayoutManager) letterRecyclerView.getLayoutManager()).
                        findLastCompletelyVisibleItemPosition();
                int itemTotalCount = letterRecyclerView.getAdapter().getItemCount() - 1;
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
                Call<MyLetterLogsResponse> call = RetrofitInstance.getRetrofitService().getMyLetters("Bearer "+LoginActivity.userAccessToken,page);
                call.enqueue(new Callback<MyLetterLogsResponse>() {
                    @Override
                    public void onResponse(Call<MyLetterLogsResponse> call, Response<MyLetterLogsResponse> response) {
                        if (response.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            MyLetterLogsResponse result = response.body();
                            letterLogsAdapter = new MyLetterLogsAdapter(getActivity(),result.getMyLetterLogData().getWriteReviews());
                            letterRecyclerView.setAdapter(letterLogsAdapter);
                            Log.d("편지로그성공", new Gson().toJson(response.body()));
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Log.d("REST FAILED MESSAGE", response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<MyLetterLogsResponse> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("REST ERROR!", t.getMessage());
                        Toast.makeText(getContext(), "네트워크를 확인해주세요!", Toast.LENGTH_LONG).show();
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
                Call<MyLetterLogsResponse> call = RetrofitInstance.getRetrofitService().getMyLetters("Bearer "+LoginActivity.userAccessToken,page);
                call.enqueue(new Callback<MyLetterLogsResponse>() {
                    @Override
                    public void onResponse(Call<MyLetterLogsResponse> call, Response<MyLetterLogsResponse> response) {
                        if (response.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            MyLetterLogsResponse result = response.body();
                            letterLogsAdapter.addList(result.getMyLetterLogData().getWriteReviews());
                            Log.d("성공", new Gson().toJson(response.body()));
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Log.d("REST FAILED MESSAGE", response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<MyLetterLogsResponse> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("REST ERROR!", t.getMessage());
                        Toast.makeText(getContext(), "네트워크를 확인해주세요!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }
}
