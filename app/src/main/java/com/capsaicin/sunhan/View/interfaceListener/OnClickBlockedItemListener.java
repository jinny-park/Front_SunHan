package com.capsaicin.sunhan.View.interfaceListener;

import android.view.View;
import com.capsaicin.sunhan.View.adapter.ManageBlockAdapter;

public interface OnClickBlockedItemListener {
    public void onItemClick(ManageBlockAdapter.ViewHolder holder, View view, int position);
}

