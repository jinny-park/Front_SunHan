package com.example.front_sunhan.View.interfaceListener;

import android.view.View;

import com.example.front_sunhan.View.adapter.CommunityAdapter;
import com.example.front_sunhan.View.adapter.MypageAdapter;
import com.example.front_sunhan.View.adapter.MypageMylogsAdapter;

public interface OnClickCommunityLogsListener {
    public void onItemClick(MypageMylogsAdapter.ViewHolder holder, View view, int position);
}
