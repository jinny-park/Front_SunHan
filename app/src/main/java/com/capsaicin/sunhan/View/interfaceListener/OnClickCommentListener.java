package com.capsaicin.sunhan.View.interfaceListener;

import android.view.View;

import com.capsaicin.sunhan.View.adapter.CommunityDetailAdapter;
import com.capsaicin.sunhan.View.adapter.LetterAdapter;

public interface OnClickCommentListener {
    public void onItemClick(CommunityDetailAdapter.ViewHolder holder, View view, int position);
}
