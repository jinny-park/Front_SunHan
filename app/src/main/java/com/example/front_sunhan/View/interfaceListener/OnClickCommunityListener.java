package com.example.front_sunhan.View.interfaceListener;

import android.view.View;

import com.example.front_sunhan.View.adapter.CommunityAdapter;

public interface OnClickCommunityListener {
    public void onItemClick(CommunityAdapter.ViewHolder holder, View view, int position);
}
