package com.capsaicin.sunhan.View.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.capsaicin.sunhan.Model.MyCommentLogsItem;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.interfaceListener.OnClickCommentLogsListener;
import com.capsaicin.sunhan.View.interfaceListener.OnClickPostLogsListener;

import java.util.ArrayList;

public class MyCommentLogsAdapter extends RecyclerView.Adapter<MyCommentLogsAdapter.ViewHolder>
        implements OnClickCommentLogsListener{

    private Context context;
    ArrayList<MyCommentLogsItem> myLogsList;
    public OnClickCommentLogsListener listener;

    public MyCommentLogsAdapter(Context context, ArrayList<MyCommentLogsItem> myLogsList){
        this.context = context ;
        this.myLogsList= myLogsList;
        notifyItemRangeInserted(myLogsList.size(),myLogsList.size());
    }

    @NonNull
    @Override
    public MyCommentLogsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.mypostitem, parent, false);
        return new MyCommentLogsAdapter.ViewHolder(itemView ,this);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCommentLogsAdapter.ViewHolder holder, int position) {
        MyCommentLogsItem item = myLogsList.get(position);
        Glide.with(context).load("https://sunhan.s3.ap-northeast-2.amazonaws.com/raw/"+myLogsList.get(position).getWriterItem().getAvatarUrl()).error(R.drawable.profile).into(holder.userProfile);
        holder.userId.setText(myLogsList.get(position).getWriterItem().getNickname());
        holder.uploadTime.setText(myLogsList.get(position).getCreateAt());
        holder.content.setText(myLogsList.get(position).getContent());
    }

    public void setOnClickCommentLogsListener(OnClickCommentLogsListener listener) {

        this.listener = listener;
    }

    @Override
    public void onItemClick(MyCommentLogsAdapter.ViewHolder holder, View view, int position) {
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

        public ViewHolder(@NonNull View itemView , final OnClickCommentLogsListener listener) {
            super(itemView);
            userProfile = itemView.findViewById(R.id.myPost_userProfile);
            userId = itemView.findViewById(R.id.myPost_userId);
            uploadTime = itemView.findViewById(R.id.myPost_uploadTime);
            content = itemView.findViewById(R.id.myPost_content);

            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onItemClick(MyCommentLogsAdapter.ViewHolder.this, view, position);
                    }
                }
            });


        }

    }

    public void addList(ArrayList <MyCommentLogsItem> list){
        myLogsList.addAll(list);
        notifyItemRangeInserted(myLogsList.size(),list.size());
    }

    public void addItem(MyCommentLogsItem item){
        myLogsList.add(item);
    }
    public void setarrayList(ArrayList<MyCommentLogsItem> arrayList) {

        this.myLogsList = arrayList;
    }

    public MyCommentLogsItem getItem(int position) {

        return myLogsList.get(position);
    }

    public void setItem(int position, MyCommentLogsItem item) {

        myLogsList.set(position, item);
    }
}
