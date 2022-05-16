package com.capsaicin.sunhan.View.interfaceListener;

import android.view.View;

import com.capsaicin.sunhan.View.adapter.MyPostLogsAdapter;

public interface OnClickPostLogsListener {
    public void onItemClick(MyPostLogsAdapter.ViewHolder holder, View view, int position);
}
