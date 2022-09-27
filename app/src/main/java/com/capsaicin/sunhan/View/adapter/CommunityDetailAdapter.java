package com.capsaicin.sunhan.View.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.capsaicin.sunhan.Model.CommentItem;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.interfaceListener.OnClickCommentListener;

import java.util.ArrayList;


public class CommunityDetailAdapter extends RecyclerView.Adapter<CommunityDetailAdapter.ViewHolder>
        implements OnClickCommentListener{
    Context context;
    public static ArrayList<CommentItem> CommunityCommentList;
    public OnClickCommentListener listener;

    public CommunityDetailAdapter(Context context, ArrayList<CommentItem> items){
        this.context = context ;
        this.CommunityCommentList= items;
        notifyItemRangeInserted(CommunityCommentList.size(), items.size());
    }

    @NonNull
    @Override
    public CommunityDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.community_comment_item, parent, false);

        return new CommunityDetailAdapter.ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityDetailAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CommentItem item =CommunityCommentList.get(position);
        Glide.with(context).load("https://sunhan.s3.ap-northeast-2.amazonaws.com/raw/"+CommunityCommentList.get(position).getC_writerItem().getAvatarUrl()).error(R.drawable.profile).circleCrop().into(holder.c_userProfile);
        holder.c_userId.setText(CommunityCommentList.get(position).getC_writerItem().getNickname());
        holder.c_content.setText(CommunityCommentList.get(position).getC_commuContent());
        holder.c_commentDate.setText(CommunityCommentList.get(position).getC_commuIsCreateAt());
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
        ImageView c_userProfile;
        TextView c_userId;
        TextView c_content;
        TextView c_commentDate;
        ImageView comment_More;

        public ViewHolder(@NonNull View itemView, final OnClickCommentListener listener) {
            super(itemView);
            c_userProfile = itemView.findViewById(R.id.comment_userProfile);
            c_userId = itemView.findViewById(R.id.comment_userId);
            c_content = itemView.findViewById(R.id.comment_content);
            c_commentDate = itemView.findViewById(R.id.comment_date);
            comment_More = itemView.findViewById(R.id.comment_More);

            comment_More.setOnClickListener(new View.OnClickListener() { //댓글 ...클릭시 이벤트
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(CommunityDetailAdapter.ViewHolder.this, view, position);
                    }
                }
            });

        }

    }

    public void addList(ArrayList <CommentItem> list){
        CommunityCommentList.addAll(list);
        notifyItemRangeInserted(CommunityCommentList.size(),list.size());
    }

    public void removeItem(int position){
        CommunityCommentList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return CommunityCommentList.size();
    }
    public void addItem(CommentItem item){
        CommunityCommentList.add(item);
        notifyDataSetChanged(); //댓글 업데이트 바로 해줌
    }
    public void setarrayList(ArrayList<CommentItem> arrayList) { this.CommunityCommentList = arrayList; }
    public CommentItem getItem(int position) { return CommunityCommentList.get(position); }
    public void setItem(int position, CommentItem item) { CommunityCommentList.set(position, item); }
}