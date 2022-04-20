package com.example.front_sunhan.View.interfaceListener;

import android.view.View;

import com.example.front_sunhan.View.adapter.SunhanStoreAdapter;

public interface OnClickStoreItemListener {
    public void onItemClick(SunhanStoreAdapter.ViewHolder holder, View view, int position);
}