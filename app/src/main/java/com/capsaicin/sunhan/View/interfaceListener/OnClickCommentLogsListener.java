package com.capsaicin.sunhan.View.interfaceListener;

import android.view.View;

import com.capsaicin.sunhan.View.adapter.MyCommentLogsAdapter;

public interface OnClickCommentLogsListener {

    public void onItemClick(MyCommentLogsAdapter.ViewHolder holder, View view, int position);
}
