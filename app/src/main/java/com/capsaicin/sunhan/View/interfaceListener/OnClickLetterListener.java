package com.capsaicin.sunhan.View.interfaceListener;

import android.view.View;

import com.capsaicin.sunhan.View.adapter.LetterAdapter;

public interface OnClickLetterListener {
    public void onItemClick(LetterAdapter.ViewHolder holder, View view, int position);
}
