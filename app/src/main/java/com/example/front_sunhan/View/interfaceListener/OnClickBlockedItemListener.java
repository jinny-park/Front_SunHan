package com.example.front_sunhan.View.interfaceListener;

import android.view.View;

import com.example.front_sunhan.View.adapter.ManageBlockAdapter;
import com.example.front_sunhan.View.adapter.MypageAdapter;

public interface OnClickBlockedItemListener {
    public void onItemClick(ManageBlockAdapter.ViewHolder holder, View view, int position);
}
