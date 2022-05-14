package com.capsaicin.sunhan.View.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.capsaicin.sunhan.R;

public class ChildrenStoreInfoFragment extends Fragment {
    View view;

   static public TextView storeName;
    static public TextView weekdayTime;
    static public TextView weekendTime;
    static public TextView address;
    static public TextView holidayTime;
    static public TextView phone;
    static public Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_children_store_info, container, false);
        bundle = getArguments();

        storeName = view.findViewById(R.id.children_storeName);
        weekdayTime =view.findViewById(R.id.children_store_weekday);
        weekendTime =view.findViewById(R.id.children_store_weekend);
        holidayTime =view.findViewById(R.id.children_store_holiday);
        address =view.findViewById(R.id.children_storeAddress);
        phone =view.findViewById(R.id.children_storeNum);

//
//        String weektime =bundle.getString("weekStart")+bundle.getString("weekEnd");
//        String weekendtime = bundle.getString("weekendStart")+bundle.getString("weekStart");
//        String holidaytime = bundle.getString("weekStart")+bundle.getString("weekendEnd");
//
//        storeName.setText(bundle.getString("name"));
//        weekdayTime.setText(weektime);
//        weekendTime.setText(weekendtime);
//        holidayTime.setText(holidaytime);
//        address.setText(bundle.getString("address"));
//        phone.setText(bundle.getString("phone"));


        return view;
    }

}
