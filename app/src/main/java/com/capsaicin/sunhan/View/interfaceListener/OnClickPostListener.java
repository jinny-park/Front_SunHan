package com.capsaicin.sunhan.View.interfaceListener;

import android.view.View;

import com.capsaicin.sunhan.View.adapter.CommunityAdapter;

public interface OnClickPostListener {
    public void onItemClick(CommunityAdapter.ViewHolder holder, View view, int position);
}
