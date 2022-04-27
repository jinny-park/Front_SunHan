package com.capsaicin.sunhan.View.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.activity.LoginActivity;

public class MyLogsPostFragment extends Fragment {

    RecyclerView postLogsRecyclerView;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mylogs_post, null);
        setRecyclerview(view);
        return view;
    }

    void setRecyclerview(View view){
        postLogsRecyclerView = view.findViewById(R.id.recyclerview_logs_post);
        postLogsRecyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
        postLogsRecyclerView.setLayoutManager(recyclerViewManager);
        postLogsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        postLogsRecyclerView.setAdapter(LoginActivity.postLogsAdapter);

    }
}
