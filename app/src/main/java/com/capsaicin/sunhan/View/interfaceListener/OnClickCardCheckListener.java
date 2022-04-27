package com.capsaicin.sunhan.View.interfaceListener;

import android.view.View;

import com.capsaicin.sunhan.View.adapter.CardCheckAdapter;

public interface OnClickCardCheckListener {
    public void onItemClick(CardCheckAdapter.ViewHolder holder, View view, int position);
}
