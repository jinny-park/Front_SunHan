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
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.interfaceListener.OnClickCommunityLogsListener;

import java.util.ArrayList;

public class MypageMylogsAdapter extends RecyclerView.Adapter<MypageMylogsAdapter.ViewHolder>
        implements OnClickCommunityLogsListener {

    private Context context;
    ArrayList<CommunityItem> myLogsList;
    public OnClickCommunityLogsListener listener;

    public MypageMylogsAdapter(Context context, ArrayList<CommunityItem> myLogsList){
        this.context = context ;
        this.myLogsList= myLogsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.community_item, parent, false);
        return new ViewHolder(itemView ,this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommunityItem item = myLogsList.get(position);
        //holder.userProfile.setImageResource(myLogsList.get(position).getUserProfile());
        holder.userId.setText(myLogsList.get(position).getCommuId());
        holder.uploadTime.setText(myLogsList.get(position).getCommuIsCreateAt());
        holder.content.setText(myLogsList.get(position).getCommuContent());
        holder.commentNum.setText(myLogsList.get(position).getCommuIsCommentCount());
    }

    public void setOnClickCommunityLogsListener(OnClickCommunityLogsListener listener) {

        this.listener = listener;
    }

    @Override
    public void onItemClick(MypageMylogsAdapter.ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    @Override
    public int getItemCount() {
        return myLogsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
         ImageView userProfile;
         TextView userId;
         TextView uploadTime;
         TextView content;
         TextView commentNum;

        public ViewHolder(@NonNull View itemView , final OnClickCommunityLogsListener listener) {
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
                        listener.onItemClick(MypageMylogsAdapter.ViewHolder.this, view, position);
                    }
                }
            });


        }

    }

    public void addItem(CommunityItem item){
        myLogsList.add(item);
    }
    public void setarrayList(ArrayList<CommunityItem> arrayList) {

        this.myLogsList = arrayList;
    }

    public CommunityItem getItem(int position) {

        return myLogsList.get(position);
    }

    public void setItem(int position, CommunityItem item) {

        myLogsList.set(position, item);
    }
}
