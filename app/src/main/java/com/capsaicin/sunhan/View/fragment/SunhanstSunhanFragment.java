package com.capsaicin.sunhan.View.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.capsaicin.sunhan.Model.Retrofit.RetrofitServiceApi;
import com.capsaicin.sunhan.R;
import com.google.android.material.tabs.TabLayout;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SunhanstSunhanFragment extends Fragment {

    SunhanstCategoryFragment sunhanCategoryFragment1;
    SunhanstCategoryFragment sunhanCategoryFragment2;
    SunhanstCategoryFragment sunhanCategoryFragment3;
    SunhanstCategoryFragment sunhanCategoryFragment4;
    SunhanstCategoryFragment sunhanCategoryFragment5;
    SunhanstCategoryFragment sunhanCategoryFragment6;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    public static String category = "한식";

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sunhanst_sunhan,null);
        sunhanCategoryFragment1 = new SunhanstCategoryFragment();
        sunhanCategoryFragment2 = new SunhanstCategoryFragment();
        sunhanCategoryFragment3 = new SunhanstCategoryFragment();
        sunhanCategoryFragment4 = new SunhanstCategoryFragment();
        sunhanCategoryFragment5 = new SunhanstCategoryFragment();
        sunhanCategoryFragment6 = new SunhanstCategoryFragment();


        TabLayout tabs2 = view.findViewById(R.id.food_tapLayout);
        tabs2.addTab(tabs2.newTab().setText("한식"));
        tabs2.addTab(tabs2.newTab().setText("중식"));
        tabs2.addTab(tabs2.newTab().setText("일식"));
        tabs2.addTab(tabs2.newTab().setText("양식"));
        tabs2.addTab(tabs2.newTab().setText("카페/디저트"));
        tabs2.addTab(tabs2.newTab().setText("기타"));

        getChildFragmentManager().beginTransaction().replace(R.id.tabs_sunhan_store_container,  sunhanCategoryFragment1 ).commit();

        tabs2.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab2) {
                int position = tab2.getPosition();

                Fragment selected = null;
                if(position == 0){
                    category = "한식";
                    selected = sunhanCategoryFragment1;
                }
                else if(position==1){
                    category = "중식";
                    selected = sunhanCategoryFragment2;
                }
                else if(position==2){
                    category = "일식";
                    selected = sunhanCategoryFragment3;

                }
                else if(position==3){
                    category = "양식";
                    selected = sunhanCategoryFragment4;
                }
                else if(position==4){
                    category = "카페/디저트";
                    selected = sunhanCategoryFragment5;

                }
                else {
                    category = "기타";
                    selected = sunhanCategoryFragment6;
                }
                getChildFragmentManager().beginTransaction().replace(R.id.tabs_sunhan_store_container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab2) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab2) {

            }
        });

        return view;

    }

}
