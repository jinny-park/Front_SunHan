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

import com.example.front_sunhan.R;
import com.example.front_sunhan.View.activity.DeleteAccountActivity;
import com.example.front_sunhan.View.activity.EditProfileActivity;
import com.example.front_sunhan.View.activity.LoginActivity;
import com.example.front_sunhan.View.activity.ManageBlockActivity;
import com.example.front_sunhan.View.activity.MyLogsActivity;
import com.example.front_sunhan.View.activity.MyPageAddCardActivity;
import com.example.front_sunhan.View.activity.PolicyActivity;
import com.example.front_sunhan.View.activity.ToolbarActivity;
import com.example.front_sunhan.View.adapter.MypageAdapter;
import com.example.front_sunhan.View.interfaceListener.OnClickMyPageItemListener;

public class MyPageFragment extends Fragment {
    RecyclerView mypageRecyclerView;
    Button profileEditBtn;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mypage,null);

        setRecyclerview(view);
        profileEditBtn = view.findViewById(R.id.modify_profile);
        profileEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), EditProfileActivity.class));
            }
        });

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
                            break;
                        case 3: /*로그아웃 팝업*/

                            break;
                        case 4:
                            Intent intent4 = new Intent(getActivity(), DeleteAccountActivity.class);
                            startActivity(intent4);
                            break;
                        case 5:
                            Intent intent5 = new Intent(getActivity(), MyLogsActivity.class);
                            startActivity(intent5);
                            break;
                        case 6:
                            Intent intent6 = new Intent(getActivity(), PolicyActivity.class);
                            startActivity(intent6);
                            break;
                        case 7:
                            break;
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
