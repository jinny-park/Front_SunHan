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

import com.capsaicin.sunhan.Model.MenuItem;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.adapter.MenuAdapter;

import java.util.ArrayList;


public class StoreMenuFragment extends Fragment {
    public static MenuAdapter menuAdapter ;
    ArrayList<MenuItem> menuList=new ArrayList<MenuItem>();
    RecyclerView menuRecyclerView;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sunhanst_store_menu, null);
        menuAdapter = new MenuAdapter(getContext(), menuList);
        setRecyclerview(view);
        setData();
        return view;
    }

    void setRecyclerview(View view){
        menuRecyclerView = view.findViewById(R.id.recyclerview_menu);
        menuRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
        menuRecyclerView.setLayoutManager(recyclerViewManager);
        menuRecyclerView.setItemAnimator(new DefaultItemAnimator());
        menuRecyclerView.setAdapter(menuAdapter);

    }

    void setData(){

        menuAdapter.addItem(new MenuItem("순대국 감자탕", "6000"));
        menuAdapter.addItem(new MenuItem("돼지 국밥", "7000"));
        menuAdapter.addItem(new MenuItem("뼈해장국", "8000"));
        menuAdapter.addItem(new MenuItem("수육", "9000"));
    }

}