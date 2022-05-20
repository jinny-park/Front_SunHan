package com.capsaicin.sunhan.View.adapter;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.capsaicin.sunhan.Model.CommentItem;
import com.capsaicin.sunhan.Model.CommunityDetailItem;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.activity.CommunityDetailActivity;

import org.w3c.dom.Comment;

import java.util.ArrayList;

public class CommunityDetailAdapter extends RecyclerView.Adapter<CommunityDetailAdapter.ViewHolder> {
    private Context context;
    ArrayList<CommunityDetailItem> CommunityDetailItemList;
    ArrayList<CommentItem> CommunityCommentList;
    public static CommunityDetailCommentAdapter communityDetailCommentAdapter ;

    ImageView pop2;
    Dialog dilaog01;



    public CommunityDetailAdapter(Context context, ArrayList<CommentItem> items){
        Log.d("어댑터생성자-커뮤니티comment ","들어옴" );
        this.context = context ;
        this.CommunityCommentList= items;
        notifyItemRangeInserted(CommunityCommentList.size(), items.size());
    }

    @NonNull
    @Override
    public CommunityDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("온크리에이트뷰홀더-커뮤니티comment ","들어옴" );
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.community_comment_item, parent, false);

        return new CommunityDetailAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityDetailAdapter.ViewHolder holder, int position) {
        CommentItem item =CommunityCommentList.get(position);
        Log.d("온바인드홀더-커뮤니티comment ", CommunityCommentList.get(position).getC_commuId());
//        holder.userProfile.setImageResource(CommunityDetailItemList.get(position).getUserProfile());
        Glide.with(context).load("https://sunhan.s3.ap-northeast-2.amazonaws.com/raw/"+CommunityCommentList.get(position).getC_writerItem().getAvatarUrl()).error(R.drawable.profile).circleCrop().into(holder.c_userProfile);
        holder.c_userId.setText(CommunityCommentList.get(position).getC_writerItem().getNickname());
        holder.c_content.setText(CommunityCommentList.get(position).getC_commuContent());
        holder.c_commentDate.setText(CommunityCommentList.get(position).getC_commuIsCreateAt());


//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
//        holder.c_recyclerView.setLayoutManager(layoutManager);
//        holder.c_recyclerView.setLayoutManager(layoutManager);
//        holder.c_recyclerView.setHasFixedSize(true);

//        ArrayList<CommentItem> arrayList = new ArrayList<>(); //대댓글

//        if (CommunityDetailItemList.get(position).getUserId().equals("선한2")) {
//            arrayList.add(new CommentItem(R.drawable.profile,"익명","와우","03/17 14:12"));
//            arrayList.add(new CommentItem(R.drawable.profile,"익명","와우","03/17 14:12"));
//            arrayList.add(new CommentItem(R.drawable.profile,"익명","와우","03/17 14:12"));
//            arrayList.add(new CommentItem(R.drawable.profile,"익명","와우","03/17 14:12"));
//            arrayList.add(new CommentItem(R.drawable.profile,"익명","와우","03/17 14:12"));
//            arrayList.add(new CommentItem(R.drawable.profile,"익명","와우","03/17 14:12"));
//        }

//        communityDetailCommentAdapter = new CommunityDetailCommentAdapter(holder.recyclerView.getContext(), arrayList); //대댓글
//        holder.recyclerView.setAdapter(communityDetailCommentAdapter); //대댓글

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView c_userProfile;
        public TextView c_userId;
        public TextView c_content;
        public TextView c_commentDate;
        public RecyclerView c_recyclerView; //대댓글

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            c_userProfile = itemView.findViewById(R.id.comment_userProfile);
            c_userId = itemView.findViewById(R.id.comment_userId);
            c_content = itemView.findViewById(R.id.comment_content);
            c_commentDate = itemView.findViewById(R.id.comment_date);
            c_recyclerView = itemView.findViewById(R.id.recylerView_community_comment_child); //대댓글

        }

    }


    public void addList(ArrayList <CommentItem> list){
        CommunityCommentList.addAll(list);
        notifyItemRangeInserted(CommunityCommentList.size(),list.size());
        Log.d("addList ",list.toString());
    }

    public void updateData() {
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return CommunityCommentList.size();
    }
    public void addItem(CommentItem item){ CommunityCommentList.add(item); }
    public void setarrayList(ArrayList<CommentItem> arrayList) { this.CommunityCommentList = arrayList; }
    public CommentItem getItem(int position) { return CommunityCommentList.get(position); }
    public void setItem(int position, CommentItem item) { CommunityCommentList.set(position, item); }
}