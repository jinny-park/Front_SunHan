package com.example.front_sunhan.View.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front_sunhan.Model.StoreItem;
import com.example.front_sunhan.R;
import com.example.front_sunhan.View.activity.DeleteAccountActivity;
import com.example.front_sunhan.View.activity.EditProfileActivity;
import com.example.front_sunhan.View.activity.LoginActivity;
import com.example.front_sunhan.View.activity.ManageBlockActivity;
import com.example.front_sunhan.View.activity.MyLogsActivity;
import com.example.front_sunhan.View.activity.MyPageAddCardActivity;
import com.example.front_sunhan.View.activity.PolicyActivity;
import com.example.front_sunhan.View.activity.StoreDetailActivity;
import com.example.front_sunhan.View.activity.ToolbarActivity;
import com.example.front_sunhan.View.adapter.MypageAdapter;
import com.example.front_sunhan.View.adapter.SunhanStoreAdapter;
import com.example.front_sunhan.View.interfaceListener.OnClickMyPageItemListener;
import com.example.front_sunhan.View.interfaceListener.OnClickStoreItemListener;

import java.util.ArrayList;

public class SunhanstSunhanFragment extends Fragment {
    public static SunhanStoreAdapter storeAdapter ;
    ArrayList<StoreItem> storeList=new ArrayList<StoreItem>();
    RecyclerView sunhanSunhanRecyclerView;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sunhanst_sunhan,null);
        storeAdapter = new SunhanStoreAdapter(getContext(), storeList);
        setRecyclerview(view);
        setData();

        storeAdapter.setOnClickStoreItemListener(new OnClickStoreItemListener() {
            @Override
            public void onItemClick(SunhanStoreAdapter.ViewHolder holder, View view, int position) {
                String str_position = String.valueOf(position+1);
                if(position!=RecyclerView.NO_POSITION){
                    for(int i=0; i<=position; i++){
                        Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
                        intent.putExtra("position", str_position);
                        startActivity(intent);
                        break;
                    }
                }
            }
        });

        return view;

    }

    void setRecyclerview(View view){
        sunhanSunhanRecyclerView = view.findViewById(R.id.recyclerview_sunhanstore);
        sunhanSunhanRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
        sunhanSunhanRecyclerView.setLayoutManager(recyclerViewManager);
        sunhanSunhanRecyclerView.setItemAnimator(new DefaultItemAnimator());
        sunhanSunhanRecyclerView.setAdapter(storeAdapter);

    }

    void setData(){

        storeAdapter.addItem(new StoreItem("돈애랑장터순대국감자탕", "경기 수원시 영통구 동문3길 10",
                "0314299444","10:00-21:00"));
        storeAdapter.addItem(new StoreItem("낙원갈비집", "경기 수원시 영통구 1243 1층",
                "0314291234","11:30-22:00"));
        storeAdapter.addItem(new StoreItem("서브웨이", "경기 수원시 영통구 광교산로 22",
                "0314295687","7:00-22:00"));
        storeAdapter.addItem(new StoreItem("맘스터치", "경기 수원시 영통구 광교산로 154",
                "0314293333","9:00-21:30"));
    }

}
