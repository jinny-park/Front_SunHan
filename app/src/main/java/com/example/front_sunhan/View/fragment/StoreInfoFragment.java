package com.example.front_sunhan.View.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.front_sunhan.R;

public class StoreInfoFragment extends Fragment {
    View view;
    public StoreInfoFragment() {
        // Required empty public constructor
    }


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