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

    RecyclerView sunhanSunhanRecyclerView;
    SunhanstCategoryFragment sunhanCategoryFragment;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sunhanst_sunhan,null);
        sunhanCategoryFragment = new SunhanstCategoryFragment();
//        setRecyclerview(view);

        TabLayout tabs2 = view.findViewById(R.id.food_tapLayout);
        tabs2.addTab(tabs2.newTab().setText("한식"));
        tabs2.addTab(tabs2.newTab().setText("중식"));
        tabs2.addTab(tabs2.newTab().setText("일식"));
        tabs2.addTab(tabs2.newTab().setText("양식"));
        tabs2.addTab(tabs2.newTab().setText("카페/디저트"));
        tabs2.addTab(tabs2.newTab().setText("기타"));

        tabs2.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab2) {
                int position = tab2.getPosition();

                Fragment selected = null;
                if(position == 0){
                    selected = sunhanCategoryFragment;
                }
                else if(position==1){
                    selected = sunhanCategoryFragment;
                }
                else if(position==2){
                    selected = sunhanCategoryFragment;
                }
                else if(position==3){
                    selected = sunhanCategoryFragment;
                }
                else if(position==4){
                    selected = sunhanCategoryFragment;
                }
                else {
                    selected = sunhanCategoryFragment;
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


//        LoginActivity.storeSunhanAdapter.setOnClickStoreItemListener(new OnClickStoreItemListener() {
//            @Override
//            public void onItemClick(SunhanStoreAdapter.ViewHolder holder, View view, int position) {
//                String str_position = String.valueOf(position+1);
//                if(position!=RecyclerView.NO_POSITION){
//                    for(int i=0; i<=position; i++){
//                        Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
//                        intent.putExtra("position", str_position);
//                        startActivity(intent);
//                        break;
//                    }
//                }
//            }
//        });

        return view;

    }


//    void setRecyclerview(View view){
//        sunhanSunhanRecyclerView = view.findViewById(R.id.recyclerview_sunhanstore);
//        sunhanSunhanRecyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
//        sunhanSunhanRecyclerView.setLayoutManager(recyclerViewManager);
//        sunhanSunhanRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        sunhanSunhanRecyclerView.setAdapter(LoginActivity.storeSunhanAdapter);
//
//    }
}
