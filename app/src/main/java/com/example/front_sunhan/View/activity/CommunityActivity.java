package com.example.front_sunhan.View.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front_sunhan.Model.CommunityItem;
import com.example.front_sunhan.R;
import com.example.front_sunhan.View.adapter.CommunityAdapter;

import java.util.ArrayList;
import java.util.List;

public class CommunityActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private CommunityAdapter mCommunityAdapter;
    private ArrayList<CommunityItem> mCommunityItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_community);

        mRecyclerView = (RecyclerView) findViewById(R.id.communityrecyleView);

        /* initiate adapter */
        mCommunityAdapter = new CommunityAdapter();

        /* initiate recyclerview */
        mRecyclerView.setAdapter(mCommunityAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));

        for (int i=0; i<profile.length; i++) {
            mCommunityItems.add(new CommunityItem(profile[i], Nickname[i], Writetime[i], Content[i], Writenum[i]));
        }

        mRecyclerView.setAdapter(mCommunityAdapter);

    }


    int[] profile = {R.drawable.profile, R.drawable.profile, R.drawable.profile};
    String[] Nickname = {"익명", "익명", "익명"};
    String[] Writetime = {"12:10", "12:10", "12:10"};
    String[] Content = {"맛있어요", "추천합니다", "좋은 가게입니다"};
    int[] Writenum = {0, 1, 2};
}