package com.example.front_sunhan.View.interfaceListener;

import android.view.View;

import com.example.front_sunhan.View.adapter.MypageAdapter;
import com.example.front_sunhan.View.adapter.MypageEtcAdapter;

public interface OnClickMyPageItemListener {
    public void onItemClick(MypageAdapter.ViewHolder holder, View view, int position);
}
