package com.capsaicin.sunhan.View.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capsaicin.sunhan.Model.StoreItem;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.adapter.SunhanStoreAdapter;

import java.util.ArrayList;

public class SunhanstCategoryFragment extends Fragment {
    ArrayList<StoreItem> storeList=new ArrayList<StoreItem>();
    RecyclerView categoryRecycler;
    SunhanStoreAdapter storeAdapter;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sunhanst_category,null);
        storeAdapter = new SunhanStoreAdapter(getContext(),storeList);
        setRecyclerview(view);

        return view;

    }

    void setRecyclerview(View view){
        categoryRecycler = view.findViewById(R.id.recyclerview_sunhanstore_category);
        categoryRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
        categoryRecycler.setLayoutManager(recyclerViewManager);
        categoryRecycler.setItemAnimator(new DefaultItemAnimator());
        categoryRecycler.setAdapter(storeAdapter);

    }
}
