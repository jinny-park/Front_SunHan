package com.capsaicin.sunhan.View.fragment;

import static com.capsaicin.sunhan.View.activity.StoreDetailActivity.whichStore;

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

import com.capsaicin.sunhan.Model.LetterResponse;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.Model.TokenResponse;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.activity.LoginActivity;
import com.capsaicin.sunhan.View.activity.StoreDetailActivity;
import com.capsaicin.sunhan.View.adapter.LetterAdapter;
import com.google.gson.Gson;

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


        //스와이프시 새로 데이터 요청
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initLetterData(0);
                page=1;
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        //리사이클러뷰 설정
        letterRecyclerView = view.findViewById(R.id.recyclerview_letter);
        letterRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
        letterRecyclerView.setLayoutManager(recyclerViewManager);
        letterRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //초기데이터 세팅
        initLetterData(0);


        letterRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                // 리사이클러뷰 페이징 네이션
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



    public void initLetterData(int page) //초기데이터 요청 메소드
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
                        if(response.message().equals("Unauthorized")){
                            checkAuthorized();
                            initLetterData(0);
                        }
                        Log.d("편지 로딩 실패", response.message());
                    }
                }

                @Override
                public void onFailure(Call<LetterResponse> call, Throwable t) {
                    Log.d("REST ERROR!", t.getMessage());
                    Toast.makeText(getContext(), "네트워크를 확인해주세요!", Toast.LENGTH_SHORT).show();
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
                        if(response.message().equals("Unauthorized")){
                            checkAuthorized();
                            initLetterData(0);
                        }
                        Log.d("ERROR", response.message());
                    }
                }
                @Override
                public void onFailure(Call<LetterResponse> call, Throwable t) {
                    Log.d("REST ERROR!", t.getMessage());
                    Toast.makeText(getContext(), "네트워크를 확인해주세요!", Toast.LENGTH_SHORT).show();
                }
            });


        }
    }


    private void getLetterData(int page) { // 페이징네이션 다음 리스트 요청 메소드
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
                        if(response.message().equals("Unauthorized")){
                            checkAuthorized();
                            getLetterData(page);
                        }
                        Log.d("편지실패", response.message());
                    }
                }

                @Override
                public void onFailure(Call<LetterResponse> call, Throwable t) {
                    Log.d("REST ERROR!", t.getMessage());
                    Toast.makeText(getContext(), "네트워크를 확인해주세요!", Toast.LENGTH_SHORT).show();
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
                        if(response.message().equals("Unauthorized")){
                            checkAuthorized();
                            getLetterData(page);
                        }
                        Log.d("편지실패", response.message());
                    }
                }

                @Override
                public void onFailure(Call<LetterResponse> call, Throwable t) {
                    Log.d("REST ERROR!", t.getMessage());
                    Toast.makeText(getContext(), "네트워크를 확인해주세요!", Toast.LENGTH_SHORT).show();
                }
            });
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