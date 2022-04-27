package com.capsaicin.sunhan.View.interfaceListener;

import android.view.View;

import com.capsaicin.sunhan.View.adapter.SunhanStoreAdapter;

public interface OnClickStoreItemListener {
    public void onItemClick(SunhanStoreAdapter.ViewHolder holder, View view, int position);
}