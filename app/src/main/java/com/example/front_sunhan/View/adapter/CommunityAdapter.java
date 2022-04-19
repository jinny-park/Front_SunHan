package com.example.front_sunhan.View.adapter;

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

import java.util.ArrayList;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.ViewHolder> {

    private ArrayList<CommunityItem> CommunityList;

    @NonNull
    @Override
    public CommunityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityAdapter.ViewHolder holder, int position) {
        holder.onBind(CommunityList.get(position));
    }

    public void setCommunityList(ArrayList<CommunityItem> list){
        this.CommunityList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return CommunityList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profile;
        TextView name;
        TextView uptime;
        TextView content;
        TextView count;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profile = (ImageView) itemView.findViewById(R.id.userprofile);
            name = (TextView) itemView.findViewById(R.id.userid);
            uptime = (TextView) itemView.findViewById(R.id.uploadtime);
            content = (TextView) itemView.findViewById(R.id.content);
            count = (TextView) itemView.findViewById(R.id.commentnum);
        }

        void onBind(CommunityItem item){
            profile.setImageResource(item.getProfile());
            name.setText(item.getNickname());
            uptime.setText(item.getWritetime());
            content.setText(item.getContent());
            count.setText(item.getWritenum());
        }
    }


}

