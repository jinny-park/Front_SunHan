package com.example.front_sunhan.View.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front_sunhan.Model.CommunityDetailItem;
import com.example.front_sunhan.R;
import com.example.front_sunhan.View.activity.CommunityDetailActivity;

import java.util.ArrayList;

public class CommunityDetailAdapter extends RecyclerView.Adapter<CommunityDetailAdapter.ViewHolder> {
    private Context context;
    ArrayList<CommunityDetailItem> CommunityDetailItemList;

    public CommunityDetailAdapter(Context context, ArrayList<CommunityDetailItem> arrayList){
        this.context = context ;
        this.CommunityDetailItemList= arrayList;
    }

    @NonNull
    @Override
    public CommunityDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.community_detail_item, parent, false);
        return new CommunityDetailAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityDetailAdapter.ViewHolder holder, int position) {
        CommunityDetailItem item =CommunityDetailItemList.get(position);

        holder.userProfile.setImageResource(CommunityDetailItemList.get(position).getUserProfile());
        holder.userId.setText(CommunityDetailItemList.get(position).getUserId());
        holder.content.setText(CommunityDetailItemList.get(position).getContent());
        holder.commentDate.setText(CommunityDetailItemList.get(position).getCommentDate());
        holder.commentTime.setText(CommunityDetailItemList.get(position).getCommentTime());
    }



    @Override
    public int getItemCount() {
        return CommunityDetailItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView userProfile;
        public TextView userId;
        public TextView content;
        public TextView commentDate;
        public TextView commentTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userProfile = itemView.findViewById(R.id.userProfile);
            userId = itemView.findViewById(R.id.userId);
            content = itemView.findViewById(R.id.content);
            commentDate = itemView.findViewById(R.id.commentDate);
            commentTime = itemView.findViewById(R.id.commentTime);

        }

    }

    public void addItem(CommunityDetailItem item){ CommunityDetailItemList.add(item); }
    public void setarrayList(ArrayList<CommunityDetailItem> arrayList) {

        this.CommunityDetailItemList = arrayList;
    }

    public CommunityDetailItem getItem(int position) {

        return CommunityDetailItemList.get(position);
    }

    public void setItem(int position, CommunityDetailItem item) {

        CommunityDetailItemList.set(position, item);
    }
}