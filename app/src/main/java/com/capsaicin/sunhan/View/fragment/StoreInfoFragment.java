package com.capsaicin.sunhan.View.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.capsaicin.sunhan.R;

public class StoreInfoFragment extends Fragment {
    View view;
    TextView storeName;
    TextView storeTime;
    TextView storeHoli;
    TextView storeNum;


    public static StoreInfoFragment newInstance() {
        StoreInfoFragment storeInfoFragment = new StoreInfoFragment();
        return storeInfoFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sunhanst_store_info, container, false);
        return view;
    }
}