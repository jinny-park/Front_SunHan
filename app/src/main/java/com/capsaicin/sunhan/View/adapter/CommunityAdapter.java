package com.capsaicin.sunhan.View.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.capsaicin.sunhan.Model.CardStoreItem;
import com.capsaicin.sunhan.Model.CommunityItem;
import com.capsaicin.sunhan.Model.StoreItem;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.interfaceListener.OnClickCommunityListener;

import java.util.ArrayList;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.ViewHolder>
        implements OnClickCommunityListener {
    ArrayList<CommunityItem> communityList = new ArrayList<CommunityItem>();
    private Context context;
    String save_content;
    public OnClickCommunityListener listener;

    public CommunityAdapter(Context context, ArrayList<CommunityItem> items) {
        this.context = context;
        this.communityList = items;
        notifyItemRangeInserted(communityList.size(),items.size());
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
        CommunityItem item = communityList.get(position);
        Glide.with(context).load("https://sunhan.s3.ap-northeast-2.amazonaws.com/raw/"+communityList.get(position).getWriterItem().getAvatarUrl()).error(R.drawable.profile).circleCrop().into(holder.userProfile);
        holder.userId.setText(communityList.get(position).getWriterItem().getNickname());
        holder.uploadTime.setText(communityList.get(position).getCommuIsCreateAt());
        holder.content.setText(communityList.get(position).getCommuContent());
        holder.commentNum.setText(communityList.get(position).getCommuIsCommentCount());

        if(communityList.get(position).getCommuIsCommentCount() == null) { //댓글 카운트 널일때 0으로 set
            holder.commentNum.setText("0");
        } else {
            holder.commentNum.setText(communityList.get(position).getCommuIsCommentCount());
        }

        save_content = communityList.get(position).getCommuContent(); //댓글 내용 저장
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
        return communityList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView userProfile;
        TextView userId;
        TextView uploadTime;
        TextView content;
        TextView commentNum;

        public ViewHolder(@NonNull View itemView, final OnClickCommunityListener listener) {
            super(itemView);
            userProfile = itemView.findViewById(R.id.userProfile);
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

    public void removeItem(int position){
        communityList.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void addList(ArrayList <CommunityItem> list){
        communityList.addAll(list);
        notifyItemRangeInserted(communityList.size(),list.size());
    }

    public void addItem(CommunityItem item){
        communityList.add(item);
        notifyDataSetChanged();
    }
    public void setarrayList(ArrayList<CommunityItem> arrayList) {

        this.communityList = arrayList;
    }

    public CommunityItem getItem(int position) {
        return communityList.get(position);
    }

    public void setItem(int position, CommunityItem item) {
        communityList.set(position, item);
    }

}