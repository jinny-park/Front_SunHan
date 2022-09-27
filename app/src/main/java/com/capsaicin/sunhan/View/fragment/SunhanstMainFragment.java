package com.capsaicin.sunhan.View.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.capsaicin.sunhan.R;
import com.google.android.material.tabs.TabLayout;


public class SunhanstMainFragment extends Fragment {

    SunhanstCardFragment sunhanstCardFragment;
    SunhanstSunhanFragment sunhanstSunhanFragment;
    ImageView addImage;

    public static String storeId;
    public static String storeName;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sunhanst_main, null);

        addImage = view.findViewById(R.id.img_sunhan_donate);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://xn--o39akkz01az4ip7f4xzwoa.com/"));
                startActivity(intent);
            }

        });

       TabLayout tabs1 = view.findViewById(R.id.store_tapLayout);
        tabs1.addTab(tabs1.newTab().setText("아동급식가맹점"));
        tabs1.addTab(tabs1.newTab().setText("선한영향력가게"));

        sunhanstCardFragment = new SunhanstCardFragment();
        getChildFragmentManager().beginTransaction().add(R.id.tabs_store_container,sunhanstCardFragment).commit();

        tabs1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab1) {
                int position = tab1.getPosition();
                if(position == 0){
                    screenChange(sunhanstCardFragment);
                }
                else {
                    if(sunhanstSunhanFragment==null){
                        sunhanstSunhanFragment = new SunhanstSunhanFragment();
                        getChildFragmentManager().beginTransaction().add(R.id.tabs_store_container,sunhanstSunhanFragment).commit();
                    }
                     screenChange(sunhanstSunhanFragment);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab1) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab1) {

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
        if(sunhanstCardFragment!=null)
            getChildFragmentManager().beginTransaction().hide(sunhanstCardFragment).commit();
        if(sunhanstSunhanFragment!=null)
            getChildFragmentManager().beginTransaction().hide(sunhanstSunhanFragment).commit();
    }

}

