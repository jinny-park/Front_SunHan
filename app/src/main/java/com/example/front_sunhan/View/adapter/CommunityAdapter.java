package com.example.front_sunhan.View.adapter;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front_sunhan.Model.CommunityItem;
import com.example.front_sunhan.R;
import com.example.front_sunhan.View.interfaceListener.OnClickMyPageItemListener;

import java.util.ArrayList;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.ViewHolder> {
    ArrayList<CommunityItem> items=new ArrayList<CommunityItem>();
    private Context context;

    public OnClickMyPageItemListener listener;

    public CommunityAdapter(Context context, ArrayList<CommunityItem> items){
        this.context = context ;
        this.items= items;
    }

    @NonNull
    @Override
    public CommunityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.community_item, parent, false);
        return new CommunityAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityAdapter.ViewHolder holder, int position) {
        CommunityItem item =items.get(position);

        //holder.userProfile.setImageResource(items.get(position).getUserProfile());
        holder.userId.setText(items.get(position).getUserId());
        holder.uploadTime.setText(items.get(position).getContent());
        holder.content.setText(items.get(position).getUploadTime());
        holder.commentNum.setText(items.get(position).getCommentNum());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView userProfile;
        public TextView userId;
        public TextView uploadTime;
        public TextView content;
        public TextView commentNum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userProfile = itemView.findViewById(R.id.userProfile);
            userId = itemView.findViewById(R.id.userId);
            uploadTime = itemView.findViewById(R.id.uploadTime);
            content = itemView.findViewById(R.id.content);
            commentNum = itemView.findViewById(R.id.commentNum);

            itemView.setClickable(true);
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