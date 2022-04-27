package com.capsaicin.sunhan.View.fragment;


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

import com.capsaicin.sunhan.Model.StoreItem;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.activity.LoginActivity;
import com.capsaicin.sunhan.View.activity.StoreDetailActivity;
import com.capsaicin.sunhan.View.adapter.SunhanStoreAdapter;
import com.capsaicin.sunhan.View.interfaceListener.OnClickStoreItemListener;

import java.util.ArrayList;

public class SunhanstCardFragment extends Fragment {
    ArrayList<StoreItem> storeList=new ArrayList<StoreItem>();
    RecyclerView sunhanCardRecyclerView;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sunhanst_card,null);
        setRecyclerview(view);

        LoginActivity.storeCardAdapter.setOnClickStoreItemListener(new OnClickStoreItemListener() {
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
        sunhanCardRecyclerView = view.findViewById(R.id.recyclerview_sunhancard);
        sunhanCardRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
        sunhanCardRecyclerView.setLayoutManager(recyclerViewManager);
        sunhanCardRecyclerView.setItemAnimator(new DefaultItemAnimator());
        sunhanCardRecyclerView.setAdapter(LoginActivity.storeCardAdapter);

    }
}

