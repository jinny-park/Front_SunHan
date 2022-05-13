package com.capsaicin.sunhan.View.interfaceListener;

import android.view.View;

import com.capsaicin.sunhan.View.adapter.CardStoreAdapter;
import com.capsaicin.sunhan.View.adapter.SunhanStoreAdapter;

public interface OnClickCardStoreItemListener {
    public void onItemClick(CardStoreAdapter.ViewHolder holder, View view, int position);
}
