package com.capsaicin.sunhan.View.interfaceListener;

import android.view.View;

import com.capsaicin.sunhan.View.adapter.LikedChildAdapter;
import com.capsaicin.sunhan.View.adapter.LikedSunHanAdapter;

public interface OnClickLikedSunHanListener {
    public void onItemClick(LikedSunHanAdapter.ViewHolder holder, View view, int position);
}
