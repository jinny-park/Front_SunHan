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

import com.capsaicin.sunhan.Model.LetterItem;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.adapter.MyLetterLogsAdapter;

import java.util.ArrayList;

public class MyLogsLetterFragment extends Fragment {

    ArrayList<LetterItem> list = new ArrayList<>();
    public static MyLetterLogsAdapter letterLogsAdapter;
    RecyclerView letterRecyclerView;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mylogs_letter, null);

        setRecyclerview(view);
        return view;
    }

    void setRecyclerview(View view){
        letterRecyclerView = view.findViewById(R.id.recyclerview_logs_letter);
        letterRecyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
        letterRecyclerView.setLayoutManager(recyclerViewManager);
        letterRecyclerView.setItemAnimator(new DefaultItemAnimator());
        letterRecyclerView.setAdapter(letterLogsAdapter);

    }
}
