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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.capsaicin.sunhan.Model.CardStoreResponse;
import com.capsaicin.sunhan.Model.CommunityItem;
import com.capsaicin.sunhan.Model.CommunityResponse;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitServiceApi;
import com.capsaicin.sunhan.Model.StoreResponse;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.activity.BottomNavigationActivity;
import com.capsaicin.sunhan.View.activity.CommunityDetailActivity;
import com.capsaicin.sunhan.View.activity.EditProfileActivity;
import com.capsaicin.sunhan.View.activity.LoginActivity;
import com.capsaicin.sunhan.View.activity.ToolbarActivity;
import com.capsaicin.sunhan.View.activity.WriteActivity;
import com.capsaicin.sunhan.View.adapter.CardStoreAdapter;
import com.capsaicin.sunhan.View.adapter.CommunityAdapter;
import com.capsaicin.sunhan.View.interfaceListener.OnClickCommunityListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.kakao.sdk.user.UserApiClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityFragment extends Fragment {
    public static CommunityAdapter communityAdapter ;
    ArrayList<CommunityItem> cList = new ArrayList<CommunityItem>();
    RecyclerView communityRecyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    public static CommunityFragment communityFragment;
    int page;
    ProgressBar progressBar;

    //    Button writeBtn;
    FloatingActionButton writeBtn;

//    public static ArrayList < Object > commuWriter;
//    public static String commuId;
//    public static String commuContent;
//    public static Boolean commuIsDeleted;
//    public static int commuIsCommentCount;
//    public static String commuIsCreateAt;
//    public static String commuIsUpdateAt;

    private RetrofitInstance commuRetrofitInstance ;
    private RetrofitServiceApi retrofitServiceApi;//

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_community, null);
        commuRetrofitInstance= RetrofitInstance.getRetrofitInstance(); //싱글톤 객체
        progressBar = view.findViewById(R.id.progress_bar);

        page = 1;


        communityRecyclerView = view.findViewById(R.id.recyleView_community);
        communityRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
        communityRecyclerView.setLayoutManager(recyclerViewManager);
        communityRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        communityRecyclerView.setAdapter(communityAdapter);

        swipeRefreshLayout = view.findViewById(R.id.swip_community);

//        setRecyclerview(view);
//        setData();
        initData(0);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        writeBtn = view.findViewById(R.id.write_btn);
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LoginActivity.userAccessToken==null){
                    showDialog();
                }else{
                    Intent intent = new Intent(getActivity(), WriteActivity.class);
//                    intent.putExtra("nickName",userNickName.getText());
//                    intent.putExtra("profilePic",imageUrl);
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

//        communityAdapter.setOnClickCommunityListener(new OnClickCommunityListener() {
//            @Override
//            public void onItemClick(CommunityAdapter.ViewHolder holder, View view, int position) {
//                ToolbarActivity toolbarActivity = new ToolbarActivity();
//                String str_position = String.valueOf(position + 1); //
//                if (position != RecyclerView.NO_POSITION) {
//                    for (int i = 0; i <= position; i++) {
//                        Intent intent = new Intent(getActivity(), CommunityDetailActivity.class);
//                        intent.putExtra("position", str_position); //
//                        startActivity(intent);
//                        break;
//                    }
//                }
//            }
//        });

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
//        if(LoginActivity.userAccessToken!=null){
            if(commuRetrofitInstance!=null){
                Log.d("커뮤니티프래그먼트", "토큰인스턴스이후 콜백 전");
                Call<CommunityResponse> call = RetrofitInstance.getRetrofitService().getCommunityList("Bearer "+LoginActivity.userAccessToken,page,null);
//                Call<CardStoreResponse> call = RetrofitInstance.getRetrofitService().getChildrenStoreList("Bearer "+LoginActivity.userAccessToken,page,null);
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
                                    ToolbarActivity toolbarActivity = new ToolbarActivity();
                                    String str_position = String.valueOf(position + 1); //
                                    if (position != RecyclerView.NO_POSITION) {
                                        for (int i = 0; i <= position; i++) {
                                            Intent intent = new Intent(getActivity(), CommunityDetailActivity.class);
                                            intent.putExtra("position", str_position); //
                                            startActivity(intent);
                                            break;
                                        }
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
                    public void onFailure(Call<CommunityResponse> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("REST ERROR!", t.getMessage());
                    }
                });
            }
//        }
    }


    private void getData(int page)
    {
//        if(LoginActivity.userAccessToken!=null){
            if(commuRetrofitInstance!=null){
                Log.d("커뮤니티프래그먼트", "토큰인스턴스이후 콜백 전");
                Call<CommunityResponse> call = RetrofitInstance.getRetrofitService().getCommunityList("Bearer "+LoginActivity.userAccessToken,page,null);
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
                                    ToolbarActivity toolbarActivity = new ToolbarActivity();
                                    String str_position = String.valueOf(position + 1); //
                                    if (position != RecyclerView.NO_POSITION) {
                                        for (int i = 0; i <= position; i++) {
                                            Intent intent = new Intent(getActivity(), CommunityDetailActivity.class);
                                            intent.putExtra("position", str_position); //
                                            startActivity(intent);
                                            break;
                                        }
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
                    public void onFailure(Call<CommunityResponse> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("REST ERROR!", t.getMessage());
                    }
                });
            }
//        }
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
}