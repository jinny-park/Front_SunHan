package com.capsaicin.sunhan.View.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
    public OnClickCommunityListener listener;

    public CommunityAdapter(Context context, ArrayList<CommunityItem> items) {
        Log.d("어댑터생성자-커뮤니티post ","들어옴" );
        this.context = context;
        this.communityList = items;
        notifyItemRangeInserted(communityList.size(),items.size());
    }

    @NonNull
    @Override
    public CommunityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("온크리에이트뷰홀더-커뮤니티post ","들어옴" );
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.community_item, parent, false);

        return new CommunityAdapter.ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityAdapter.ViewHolder holder, int position) {
        CommunityItem item = communityList.get(position);
        Log.d("온바인드홀더-커뮤니티post ", communityList.get(position).getCommuId());
        //holder.userProfile.setImageResource(items.get(position).getUserProfile());
        Glide.with(context).load("https://sunhan.s3.ap-northeast-2.amazonaws.com/raw/"+communityList.get(position).getWriterItem().getAvatarUrl()).error(R.drawable.profile).circleCrop().into(holder.userProfile);
        holder.userId.setText(communityList.get(position).getWriterItem().getNickname());
        holder.uploadTime.setText(communityList.get(position).getCommuIsCreateAt());
        holder.content.setText(communityList.get(position).getCommuContent());
        holder.commentNum.setText(communityList.get(position).getCommuIsCommentCount());
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

    public void addList(ArrayList <CommunityItem> list){
        communityList.addAll(list);
        notifyItemRangeInserted(communityList.size(),list.size());
        Log.d("addList ",list.toString());
    }

    public void addItem(CommunityItem item){ communityList.add(item); }
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