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
import com.example.front_sunhan.View.activity.EditProfileActivity;
import com.example.front_sunhan.View.activity.LoginActivity;
import com.example.front_sunhan.View.activity.WriteActivity;
import com.example.front_sunhan.View.adapter.CommunityAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommunityFragment extends Fragment {
    RecyclerView communityRecyclerView;
    public static CommunityAdapter communityAdapter ;
    ArrayList<CommunityItem> cList = new ArrayList<>();

    Button writeBtn;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_community, null);
        communityAdapter = new CommunityAdapter(getContext(),  cList);

        setRecyclerview(view);
        setData();

        writeBtn = view.findViewById(R.id.write_btn);
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WriteActivity.class));
            }
        });

        return view;
    }

    void setRecyclerview(View view){
        communityRecyclerView = view.findViewById(R.id.recyleView_community);
        communityRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
        communityRecyclerView.setLayoutManager(recyclerViewManager);
        communityRecyclerView.setItemAnimator(new DefaultItemAnimator());
        communityRecyclerView.setAdapter(communityAdapter);
    }

    void setData(){
        //communityAdapter.addItem(new CommunityItem(R.id.userProfile,"익명1", "12:10","맛있어요",0)); 오류남
    }
}