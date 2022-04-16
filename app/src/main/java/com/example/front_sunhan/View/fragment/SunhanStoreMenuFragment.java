package com.example.front_sunhan.View.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.front_sunhan.R;


public class SunhanStoreMenuFragment extends Fragment {
    View view;
    public SunhanStoreMenuFragment() {
        // Required empty public constructor
    }


    public static SunhanStoreMenuFragment newInstance() {
        SunhanStoreMenuFragment sunhanStoreMenuFragment = new SunhanStoreMenuFragment();
        return sunhanStoreMenuFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sunhanst_store_menu, container, false);
        return view;
    }

}