package com.example.front_sunhan.View.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front_sunhan.Model.CommunityItem;
import com.example.front_sunhan.R;
import com.example.front_sunhan.View.activity.CommunityDetailActivity;
import com.example.front_sunhan.View.activity.ToolbarActivity;
import com.example.front_sunhan.View.activity.WriteActivity;
import com.example.front_sunhan.View.adapter.CommunityAdapter;
import com.example.front_sunhan.View.interfaceListener.OnClickCommunityListener;

import java.util.ArrayList;

public class CommunityDetailFragment extends Fragment {
    public static CommunityAdapter communityAdapter ;
    ArrayList<CommunityItem> cList = new ArrayList<CommunityItem>();
    RecyclerView communityRecyclerView;

//    Button writeBtn;

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_community_detail, null);
//        communityAdapter = new CommunityAdapter(getContext(),  cList);

//        setRecyclerview(view);
//        setData();

        return view;
    }

//    public void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//    }
//
//
//
//    void setRecyclerview(View view){
//        communityRecyclerView = view.findViewById(R.id.recyleView_community);
//        communityRecyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
//        communityRecyclerView.setLayoutManager(recyclerViewManager);
//        communityRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        communityRecyclerView.setAdapter(communityAdapter);
//    }
//
//    void setData(){
//        communityAdapter.addItem(new CommunityItem(R.drawable.profile,"익명6", "12:10","맛있어요","0"));
//        communityAdapter.addItem(new CommunityItem(R.drawable.profile,"익명5", "12:10","맛있어요","0"));
//        communityAdapter.addItem(new CommunityItem(R.drawable.profile,"익명4", "12:10","맛있어요","0"));
//        communityAdapter.addItem(new CommunityItem(R.drawable.profile,"익명3", "12:10","맛있어요","0"));
//        communityAdapter.addItem(new CommunityItem(R.drawable.profile,"익명2", "12:10","맛있어요","0"));
//        communityAdapter.addItem(new CommunityItem(R.drawable.profile,"익명1", "12:10","맛있어요","0"));
//    }
}