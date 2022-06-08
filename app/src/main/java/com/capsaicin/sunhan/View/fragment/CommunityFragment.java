package com.capsaicin.sunhan.View.fragment;

import static android.content.ContentValues.TAG;

import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.capsaicin.sunhan.Model.CommunityResponse;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.Model.TokenResponse;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.activity.CommunityDetailActivity;
import com.capsaicin.sunhan.View.activity.LoginActivity;
import com.capsaicin.sunhan.View.activity.WriteActivity;
import com.capsaicin.sunhan.View.adapter.CommunityAdapter;
import com.capsaicin.sunhan.View.interfaceListener.OnClickCommunityListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityFragment extends Fragment {
    public static CommunityAdapter communityAdapter ;
    RecyclerView communityRecyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    public static CommunityFragment communityFragment;
    int page;
    ProgressBar progressBar;

    FloatingActionButton writeBtn; //플로팅 버튼

    private RetrofitInstance commuRetrofitInstance ;

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_community, null);
        commuRetrofitInstance= RetrofitInstance.getRetrofitInstance(); //싱글톤 객체
        progressBar = view.findViewById(R.id.progress_bar);


        page = 1;


        communityRecyclerView = view.findViewById(R.id.recyleView_community); //커뮤니티 글 리사이클러뷰
        communityRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
        communityRecyclerView.setLayoutManager(recyclerViewManager);
        communityRecyclerView.setItemAnimator(new DefaultItemAnimator());

        swipeRefreshLayout = view.findViewById(R.id.swip_community); //스와이프 레이아웃 불러오기

        initData(0); //데이터 불러오기

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData(0);
                page=1;
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        writeBtn = view.findViewById(R.id.write_btn); //글쓰기 플로팅 버튼
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LoginActivity.userAccessToken==null){
                    showDialog();
                }else{
                    Intent intent = new Intent(getActivity(), WriteActivity.class);
                    startActivity(intent);
                }
            }
        });

        communityRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = ((LinearLayoutManager) communityRecyclerView.getLayoutManager()).
                        findLastCompletelyVisibleItemPosition();
                int itemTotalCount = communityRecyclerView.getAdapter().getItemCount() - 1;
                if(lastVisibleItemPosition == itemTotalCount) {
                    progressBar.setVisibility(View.VISIBLE);
                    getData(page);
                    page++;
                }
            }
        });

        return view;
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public static CommunityFragment getInstance(){

        if(communityFragment==null){
            communityFragment = new CommunityFragment();
        }

        return communityFragment;
    }

    private void initData(int page)
    {
            if(commuRetrofitInstance!=null){
                Call<CommunityResponse> call = RetrofitInstance.getRetrofitService().getCommunityList("Bearer "+LoginActivity.userAccessToken,page);
                call.enqueue(new Callback<CommunityResponse>() {
                    @Override
                    public void onResponse(Call<CommunityResponse> call, Response<CommunityResponse> response) {
                        if (response.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            CommunityResponse result = response.body();
                            communityAdapter = new CommunityAdapter(getActivity(),result.getData());
                            communityRecyclerView.setAdapter(communityAdapter);
                            communityAdapter.setOnClickCommunityListener(new OnClickCommunityListener() {
                                @Override
                                public void onItemClick(CommunityAdapter.ViewHolder holder, View view, int position) {
                                    String str_position = String.valueOf(position+1);
                                    if(position!=RecyclerView.NO_POSITION){
                                        Intent intent = new Intent(getActivity(), CommunityDetailActivity.class);
                                        intent.putExtra("_id", communityAdapter.getItem(position).getCommuId());
                                        startActivity(intent);
                                    }
                                }
                            });
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Log.d("REST FAILED MESSAGE", response.message());
                            if(response.message().equals("Unauthorized")){
                                checkAuthorized();
                                initData(0);
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<CommunityResponse> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "네트워크를 확인해주세요!", Toast.LENGTH_LONG).show();
                        Log.d("REST ERROR!", t.getMessage());
                    }
                });
            }
    }


    private void getData(int page)
    {
            if(commuRetrofitInstance!=null){
                Call<CommunityResponse> call = RetrofitInstance.getRetrofitService().getCommunityList("Bearer "+LoginActivity.userAccessToken,page);
                call.enqueue(new Callback<CommunityResponse>() {
                    @Override
                    public void onResponse(Call<CommunityResponse> call, Response<CommunityResponse> response) {
                        if (response.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            CommunityResponse result = response.body();
                            communityAdapter.addList(result.getData());
                            communityAdapter.setOnClickCommunityListener(new OnClickCommunityListener() {
                                @Override
                                public void onItemClick(CommunityAdapter.ViewHolder holder, View view, int position) {
                                    String str_position = String.valueOf(position+1);
                                    if(position!=RecyclerView.NO_POSITION){
                                        Intent intent = new Intent(getActivity(), CommunityDetailActivity.class);
                                        intent.putExtra("_id", communityAdapter.getItem(position).getCommuId());

                                        startActivity(intent);
                                    }
                                }
                            });
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
                    public void onFailure(Call<CommunityResponse> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("REST ERROR!", t.getMessage());
                        Toast.makeText(getContext(), "네트워크를 확인해주세요!", Toast.LENGTH_LONG).show();
                    }
                });
            }
    }

    void showDialog() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(getContext()).setMessage("로그인 후 이용해주세요.") .
                setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
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