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

import com.example.front_sunhan.Model.StoreItem;
import com.example.front_sunhan.R;
import com.example.front_sunhan.View.activity.EditProfileActivity;
import com.example.front_sunhan.View.activity.LoginActivity;
import com.example.front_sunhan.View.activity.StoreDetailActivity;
import com.example.front_sunhan.View.adapter.SunhanStoreAdapter;
import com.example.front_sunhan.View.interfaceListener.OnClickStoreItemListener;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class SunhanstMainFragment extends Fragment {
    public static SunhanStoreAdapter storeAdapter;
    ArrayList<StoreItem> storeList=new ArrayList<StoreItem>();
    RecyclerView sunhanStoreRecyclerView;

    SunhanstCardFragment sunhanstCardFragment;
    SunhanstSunhanFragment sunhanstSunhanFragment;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sunhanst_main, null);

        sunhanstCardFragment = new SunhanstCardFragment();
        sunhanstSunhanFragment = new SunhanstSunhanFragment();

        getFragmentManager().beginTransaction().replace(R.id.tabs_store_container, sunhanstCardFragment).commit();
        TabLayout tabs = view.findViewById(R.id.store_tapLayout);
        tabs.addTab(tabs.newTab().setText("아동급식가맹점"));
        tabs.addTab(tabs.newTab().setText("선한영향력가게"));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                Fragment selected = null;
                if(position == 0){
                    selected = sunhanstCardFragment;
                }
                else {
                    selected = sunhanstSunhanFragment;
                }
                getFragmentManager().beginTransaction().replace(R.id.tabs_store_container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        return view;
    }
    /*
    void setRecyclerview(View view){
        sunhanStoreRecyclerView = view.findViewById(R.id.recyclerview_store);
        sunhanStoreRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
        sunhanStoreRecyclerView.setLayoutManager(recyclerViewManager);
        sunhanStoreRecyclerView.setItemAnimator(new DefaultItemAnimator());
        sunhanStoreRecyclerView.setAdapter(storeAdapter);

    }


    void setList(){
        storeAdapter = new SunhanStoreAdapter(getContext(), storeList);
        setData();
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

 */

}

