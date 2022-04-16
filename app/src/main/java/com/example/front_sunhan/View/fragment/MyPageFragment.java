package com.example.front_sunhan.View.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front_sunhan.R;
import com.example.front_sunhan.View.activity.LoginActivity;
import com.example.front_sunhan.View.activity.ManageBlockActivity;
import com.example.front_sunhan.View.activity.MyPageAddCardActivity;
import com.example.front_sunhan.View.activity.ToolbarActivity;
import com.example.front_sunhan.View.adapter.MypageAdapter;
import com.example.front_sunhan.View.interfaceListener.OnClickMyPageItemListener;

public class MyPageFragment extends Fragment {
    RecyclerView mypageRecyclerView;

//    RecyclerView mypageSettingsreRecyclerView;
//    RecyclerView mypageMylogsRecyclerView;
//    RecyclerView mypageEtcRecyclerView;

//    ArrayList<MypageItem> list = new ArrayList<>();
//    TextView res_name;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mypage,null);

        setRecyclerview(view);


        LoginActivity.mypageAdapter.setOnClickMyPageItemListener(new OnClickMyPageItemListener() {
            @Override public void onItemClick(MypageAdapter.ViewHolder holder, View view, int position) {
                ToolbarActivity toolbarActivity = new ToolbarActivity();
                if (position != RecyclerView.NO_POSITION) {
                    System.out.println(position);
                    switch (position){
                        case 0:
                            Intent intent1 = new Intent(getActivity(), ManageBlockActivity.class);
                            startActivity(intent1);
                            break;
                        case 1:
                            Intent intent2 = new Intent(getActivity(), MyPageAddCardActivity.class);
                            startActivity(intent2);
                            break;
                        case 2:
                            Intent intent3 = new Intent(getActivity(), ManageBlockActivity.class);
                            startActivity(intent3);
                    }

                }
            }
        });

        return view;

    }


    void setRecyclerview(View view){
        mypageRecyclerView = view.findViewById(R.id.recyclerview_mypage);
        mypageRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
        mypageRecyclerView.setLayoutManager(recyclerViewManager);
        mypageRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mypageRecyclerView.setAdapter(LoginActivity.mypageAdapter);

    }
}
