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
import com.example.front_sunhan.View.activity.StoreDetailActivity;
import com.example.front_sunhan.View.adapter.SunhanStoreAdapter;
import com.example.front_sunhan.View.interfaceListener.OnClickStoreItemListener;

public class SunhanstSunhanFragment extends Fragment {

    RecyclerView sunhanSunhanRecyclerView;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sunhanst_sunhan,null);
        setRecyclerview(view);

        LoginActivity.storeSunhanAdapter.setOnClickStoreItemListener(new OnClickStoreItemListener() {
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
        sunhanSunhanRecyclerView.setAdapter(LoginActivity.storeSunhanAdapter);

    }
}
