package com.capsaicin.sunhan.View.fragment;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.capsaicin.sunhan.Model.CommunityItem;
import com.capsaicin.sunhan.Model.CommunityResponse;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitServiceApi;
import com.capsaicin.sunhan.Model.StoreResponse;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.activity.BottomNavigationActivity;
import com.capsaicin.sunhan.View.activity.CommunityDetailActivity;
import com.capsaicin.sunhan.View.activity.ToolbarActivity;
import com.capsaicin.sunhan.View.activity.WriteActivity;
import com.capsaicin.sunhan.View.adapter.CommunityAdapter;
import com.capsaicin.sunhan.View.interfaceListener.OnClickCommunityListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

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

    //    Button writeBtn;
    FloatingActionButton writeBtn;

    public static ArrayList < Object > commuWriter;
    public static String commuId;
    public static String commuContent;
    public static Boolean commuIsDeleted;
    public static int commuIsCommentCount;
    public static String commuIsCreateAt;
    public static String commuIsUpdateAt;

    private RetrofitInstance commuRetrofitInstance ;
    private RetrofitServiceApi retrofitServiceApi;//

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_community, null);
        communityAdapter = new CommunityAdapter(getContext(),  cList);

        swipeRefreshLayout = view.findViewById(R.id.swip_community);

        setRecyclerview(view);
//        setData();

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
                startActivity(new Intent(getActivity(), WriteActivity.class));
            }
        });

        commuRetrofitInstance= RetrofitInstance.getRetrofitInstance(); //싱글톤 객체
        Call<CommunityResponse> call = RetrofitInstance.getRetrofitService().getCommunity("id"); //인터페이스의 get @path 안의 변수명과 일치해야함

        call.enqueue(new Callback<CommunityResponse>() {
            @Override
            public void onResponse(Call<CommunityResponse> call, Response<CommunityResponse> response) {
                //response 체크하는거
                if (!response.isSuccessful()) {
                    CommunityResponse result = response.body();

                    Log.d(TAG, "onResponse: onResponse 실패 - " + new Gson().toJson(response.errorBody()));
                    Log.d("result", commuWriter+"\n"+commuId+"\n"+commuContent+"\n"+commuIsDeleted+"\n"+
                            commuIsCommentCount+"\n"+ commuIsCreateAt+"\n"+commuIsUpdateAt);
                } else {
                    CommunityResponse result = response.body();

                    Log.d(TAG, "onResponse: onResponse 성공 - " + new Gson().toJson(response.body()));
                    Log.d("result", commuWriter+"\n"+commuId+"\n"+commuContent+"\n"+commuIsDeleted+"\n"+
                            commuIsCommentCount+"\n"+ commuIsCreateAt+"\n"+commuIsUpdateAt);
                }
            }

            @Override
            public void onFailure(Call<CommunityResponse> call, Throwable t) {
                //check ip address
                Log.d(TAG, "onFailure: onFailure - " + t.getMessage());
            }
        });

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

    void setRecyclerview(View view){
        communityRecyclerView = view.findViewById(R.id.recyleView_community);
        communityRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
        communityRecyclerView.setLayoutManager(recyclerViewManager);
        communityRecyclerView.setItemAnimator(new DefaultItemAnimator());
        communityRecyclerView.setAdapter(communityAdapter);
    }

//    void setData(){
//        communityAdapter.addItem(new CommunityItem(R.drawable.profile,"익명", "12:10","돈애랑 장터 순대국 감자탕 먹고 왔습니다! 완전 맛있\n" +
//                "고 사장님도 친절해요~","0"));
//        communityAdapter.addItem(new CommunityItem(R.drawable.profile,"익명", "12:07","낙원갈비집 주차도 편리하고 아이랑 맛있게 먹고 왔\n" +
//                "습니다","4"));
//        communityAdapter.addItem(new CommunityItem(R.drawable.profile,"익명", "12:10","영통버거 칭구들이랑 먹었어요 사장님 감사합니다 \n" +
//                "또 갈게요 ~","1"));
//        communityAdapter.addItem(new CommunityItem(R.drawable.profile,"익명", "09:56","일리터 카페 조용하고 커피도 고소해요! 영통구 주민\n" +
//                "분들께 추천드립니다 ","3"));
//        communityAdapter.addItem(new CommunityItem(R.drawable.profile,"익명", "09:10","영통버거 사장님 친절하세요.. 감사해요..","1"));
//    }
}