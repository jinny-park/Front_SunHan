package com.capsaicin.sunhan.View.interfaceListener;

import android.view.View;

import com.capsaicin.sunhan.View.adapter.LikedChildAdapter;

public interface OnClickLikedChildListener {
    public void onItemClick(LikedChildAdapter.ViewHolder holder, View view, int position);
}
