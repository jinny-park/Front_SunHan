package com.capsaicin.sunhan.View.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.capsaicin.sunhan.R;
import com.google.android.material.tabs.TabLayout;

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

        TabLayout tabs2 = view.findViewById(R.id.food_tapLayout);
        tabs2.addTab(tabs2.newTab().setText("한식"));
        tabs2.addTab(tabs2.newTab().setText("중식"));
        tabs2.addTab(tabs2.newTab().setText("일식"));
        tabs2.addTab(tabs2.newTab().setText("양식"));
        tabs2.addTab(tabs2.newTab().setText("카페/디저트"));
        tabs2.addTab(tabs2.newTab().setText("기타"));

        sunhanCategoryFragment1 = new SunhanstCategoryFragment();
        getChildFragmentManager().beginTransaction().add(R.id.tabs_sunhan_store_container,  sunhanCategoryFragment1 ).commit();

        tabs2.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab2) {
                int position = tab2.getPosition();
                if(position == 0){
                    category = "한식";
                    screenChange(sunhanCategoryFragment1);
                }
                else if(position==1){
                    category = "중식";
                    if(sunhanCategoryFragment2==null){
                        sunhanCategoryFragment2 = new SunhanstCategoryFragment();
                        getChildFragmentManager().beginTransaction().add(R.id.tabs_sunhan_store_container,  sunhanCategoryFragment2 ).commit();
                    }
                   screenChange(sunhanCategoryFragment2);
                }
                else if(position==2){
                    category = "일식";
                    if(sunhanCategoryFragment3==null){
                        sunhanCategoryFragment3 = new SunhanstCategoryFragment();
                        getChildFragmentManager().beginTransaction().add(R.id.tabs_sunhan_store_container,  sunhanCategoryFragment3 ).commit();
                    }

                    screenChange(sunhanCategoryFragment3);

                }
                else if(position==3){
                    category = "양식";
                    if(sunhanCategoryFragment4==null){
                        sunhanCategoryFragment4 = new SunhanstCategoryFragment();
                        getChildFragmentManager().beginTransaction().add(R.id.tabs_sunhan_store_container,  sunhanCategoryFragment4 ).commit();
                    }

                    screenChange(sunhanCategoryFragment4);
                }
                else if(position==4){
                    category = "카페/디저트";
                    if(sunhanCategoryFragment5==null){
                        sunhanCategoryFragment5 = new SunhanstCategoryFragment();
                        getChildFragmentManager().beginTransaction().add(R.id.tabs_sunhan_store_container,  sunhanCategoryFragment5 ).commit();
                    }

                    screenChange(sunhanCategoryFragment5);

                }
                else {
                    category = "기타";
                    if(sunhanCategoryFragment6==null){
                        sunhanCategoryFragment6 = new SunhanstCategoryFragment();
                        getChildFragmentManager().beginTransaction().add(R.id.tabs_sunhan_store_container,  sunhanCategoryFragment6 ).commit();
                    }

                    screenChange(sunhanCategoryFragment6);
                }
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

    private void screenChange(Fragment fragment){
        allHideScreens();
        if(fragment!=null)
            getChildFragmentManager().beginTransaction().show(fragment).commit();
    }


    private void allHideScreens(){
        if(sunhanCategoryFragment1!=null)
            getChildFragmentManager().beginTransaction().hide(sunhanCategoryFragment1).commit();
        if(sunhanCategoryFragment2!=null)
            getChildFragmentManager().beginTransaction().hide(sunhanCategoryFragment2).commit();
        if(sunhanCategoryFragment3!=null)
            getChildFragmentManager().beginTransaction().hide(sunhanCategoryFragment3).commit();
        if(sunhanCategoryFragment4!=null)
            getChildFragmentManager().beginTransaction().hide(sunhanCategoryFragment4).commit();
        if(sunhanCategoryFragment5!=null)
            getChildFragmentManager().beginTransaction().hide(sunhanCategoryFragment5).commit();
        if(sunhanCategoryFragment6!=null)
            getChildFragmentManager().beginTransaction().hide(sunhanCategoryFragment6).commit();
    }

}
