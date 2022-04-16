package com.example.front_sunhan.View.fragment;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.front_sunhan.R;
import com.google.android.material.tabs.TabLayout;

public class LikedStoreFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.fragment_likestore);


        ViewPager pager = findViewById(R.id.viewpager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        tabLayout.setupWithViewPager(pager); //텝레이아웃과 뷰페이저를 연결
        pager.setAdapter(new PageAdapter(getSupportFragmentManager(),this)); //뷰페이저 어뎁터 설정 연결


    }
    static class PageAdapter extends FragmentStatePagerAdapter { //뷰 페이저 어뎁터

        PageAdapter(FragmentManager fm, Context context) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            if (position == 0) { //프래그먼트 사용 포지션 설정 0 이 첫탭
                return new fragment_likestore_sunhan();
            } else {
                return new fragment_likestore_cardst();
            }

        }


        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) { //텝 레이아웃의 타이틀 설정
                return "Tab_1";
            } else {
                return "Tab_2";
            }
        }
    }

}