package com.capsaicin.sunhan.View.fragment;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitServiceApi;
import com.capsaicin.sunhan.Model.StoreItem;
import com.capsaicin.sunhan.Model.StoreResponse;
import com.capsaicin.sunhan.Model.TokenResponse;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.adapter.SunhanStoreAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.kakao.sdk.auth.model.OAuthToken;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SunhanstMainFragment extends Fragment {

    private SunhanstCardFragment sunhanstCardFragment;
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

        sunhanstCardFragment = new SunhanstCardFragment();
        sunhanstSunhanFragment = new SunhanstSunhanFragment();

        getChildFragmentManager().beginTransaction().add(R.id.tabs_store_container,sunhanstCardFragment).commit();
        getChildFragmentManager().beginTransaction().replace(R.id.tabs_store_container,sunhanstCardFragment).commit();

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

        tabs1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab1) {
                int position = tab1.getPosition();

                Fragment selected = null;
                if(position == 0){
                    selected = sunhanstCardFragment;
                }
                else {
                    selected = sunhanstSunhanFragment;
                }
                getChildFragmentManager().beginTransaction().replace(R.id.tabs_store_container, selected).commit();
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

}

