package com.capsaicin.sunhan.View.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capsaicin.sunhan.Model.CommunityItem;
import com.capsaicin.sunhan.Model.StoreItem;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.interfaceListener.OnClickCommunityListener;

import java.util.ArrayList;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.ViewHolder>
        implements OnClickCommunityListener {
    ArrayList<CommunityItem> items = new ArrayList<CommunityItem>();
    private Context context;
    public OnClickCommunityListener listener;

    public CommunityAdapter(Context context, ArrayList<CommunityItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public CommunityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.community_item, parent, false);

        return new CommunityAdapter.ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityAdapter.ViewHolder holder, int position) {
        CommunityItem item = items.get(position);

        //holder.userProfile.setImageResource(items.get(position).getUserProfile());
        holder.userId.setText(items.get(position).getCommuId());
        holder.uploadTime.setText(items.get(position).getCommuIsCreateAt());
        holder.content.setText(items.get(position).getCommuContent());
        holder.commentNum.setText(items.get(position).getCommuIsCommentCount());
    }

    public void setOnClickCommunityListener(OnClickCommunityListener listener) {

        this.listener = listener;
    }

    @Override
    public void onItemClick(CommunityAdapter.ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    @Override
    public int getItemCount() {
//        Log.d("size", String.valueOf(items.size()));
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //        public ImageView userProfile;
        TextView userId;
        TextView uploadTime;
        TextView content;
        TextView commentNum;

        public ViewHolder(@NonNull View itemView, final OnClickCommunityListener listener) {
            super(itemView);
//            userProfile = itemView.findViewById(R.id.userProfile);
            userId = itemView.findViewById(R.id.userId);
            uploadTime = itemView.findViewById(R.id.uploadTime);
            content = itemView.findViewById(R.id.content);
            commentNum = itemView.findViewById(R.id.commentNum);

            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null) {
                        listener.onItemClick(CommunityAdapter.ViewHolder.this, view, position);
                    }
                }
            });
        }

    }

    public void addItem(CommunityItem item){ items.add(item); }
    public void setarrayList(ArrayList<CommunityItem> arrayList) {

        this.items = arrayList;
    }

    public CommunityItem getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, CommunityItem item) {
        items.set(position, item);
    }

}