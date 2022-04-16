package com.example.front_sunhan.View.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.front_sunhan.R;


public class SunhanStoreLetterFragment extends Fragment {
    View view;
    public SunhanStoreLetterFragment() {
        // Required empty public constructor
    }


    public static SunhanStoreLetterFragment newInstance() {
        SunhanStoreLetterFragment sunhanStoreLetterFragment = new SunhanStoreLetterFragment();
        return sunhanStoreLetterFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sunhanst_store_letter, container, false);
        return view;
    }

}