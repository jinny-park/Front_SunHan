package com.capsaicin.sunhan.View.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capsaicin.sunhan.Model.CommentItem;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.interfaceListener.OnClickCommentListener;

import java.util.ArrayList;

public class CommunityDetailCommentAdapter extends RecyclerView.Adapter<CommunityDetailCommentAdapter.ViewHolder>
        implements OnClickCommentListener{
    private Context context;
    ArrayList<CommentItem> CommunityCommentItemList;
    public OnClickCommentListener listener;

    public CommunityDetailCommentAdapter(Context context, ArrayList<CommentItem> arrayList){
        this.context = context ;
        this.CommunityCommentItemList= arrayList;
    }

    @NonNull
    @Override
    public CommunityDetailCommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.community_detail_comment_item, parent, false);
        return new CommunityDetailCommentAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityDetailCommentAdapter.ViewHolder holder, int position) {
        CommentItem item =CommunityCommentItemList.get(position);

//        holder.cuserProfile.setImageResource(CommunityCommentItemList.get(position).getUserProfile());
//        holder.cuserId.setText(CommunityCommentItemList.get(position).getCommuId());
//        holder.ccontent.setText(CommunityCommentItemList.get(position).getCommuContent());
//        holder.ccommentTime.setText(CommunityCommentItemList.get(position).getCommuIsCreateAt());
    }

    public void setOnClickCommentListner(OnClickCommentListener listner){
        this.listener = listner;
    }

    @Override
    public void onItemClick(CommunityDetailAdapter.ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView cuserProfile;
        public TextView cuserId;
        public TextView ccontent;
        public TextView ccommentTime;
        public ImageView cpop_comment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cuserProfile = itemView.findViewById(R.id.cuserProfile);
            cuserId = itemView.findViewById(R.id.cuserId);
            ccontent = itemView.findViewById(R.id.ccontent);
            ccommentTime = itemView.findViewById(R.id.cuploadTime);
            cpop_comment = itemView.findViewById(R.id.cMore);


        }

    }

    @Override
    public int getItemCount() {
        return CommunityCommentItemList.size();
    }
    public void addItem(CommentItem item){ CommunityCommentItemList.add(item); }
    public void setarrayList(ArrayList<CommentItem> arrayList) { this.CommunityCommentItemList = arrayList; }
    public CommentItem getItem(int position) { return CommunityCommentItemList.get(position); }
    public void setItem(int position, CommentItem item) { CommunityCommentItemList.set(position, item); }
}