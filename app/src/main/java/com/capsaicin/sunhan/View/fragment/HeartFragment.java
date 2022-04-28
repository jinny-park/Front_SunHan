package com.capsaicin.sunhan.View.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.activity.BottomNavigationActivity;
import com.google.android.material.tabs.TabLayout;

public class HeartFragment extends Fragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    LikedStoreCardstFragment likedStoreCardstFragment;
    LikedStoreSunhanFragment likedStoreSunhanFragment;
    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_likestore, null);

        likedStoreCardstFragment = new LikedStoreCardstFragment();
        likedStoreSunhanFragment = new LikedStoreSunhanFragment();


        getChildFragmentManager().beginTransaction().replace(R.id.tabs_heart_container,likedStoreCardstFragment).commit();


        TabLayout tabs = view.findViewById(R.id.tabs_heart);
        tabs.addTab(tabs.newTab().setText("선한영향력가게"));
        tabs.addTab(tabs.newTab().setText("아동급시가드가맹점"));

       tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                Fragment selected = null;
                if(position == 0){
                    selected = likedStoreSunhanFragment;
                } else {
                    selected = likedStoreCardstFragment;
                }

                getChildFragmentManager().beginTransaction().replace(R.id.tabs_heart_container, selected).commit();
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

}
