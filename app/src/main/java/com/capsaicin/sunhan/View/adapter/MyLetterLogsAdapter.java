package com.capsaicin.sunhan.View.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.capsaicin.sunhan.Model.LetterItem;
import com.capsaicin.sunhan.Model.MyPost;
import com.capsaicin.sunhan.R;

import java.util.ArrayList;

public class MyLetterLogsAdapter extends RecyclerView.Adapter<MyLetterLogsAdapter.ViewHolder> {

    ArrayList<LetterItem> letterItems;
    //ArrayList<LetterItem> letterItems = new ArrayList<LetterItem>();
    private Context context;
//    public OnClickStoreItemListener listener;

    public MyLetterLogsAdapter(Context context, ArrayList<LetterItem> items) {
        this.context = context;
        this.letterItems = items;
        notifyItemRangeInserted(letterItems.size(),letterItems.size());
    }

    @NonNull
    @Override
    public MyLetterLogsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.letter_item, parent, false);
        return new MyLetterLogsAdapter.ViewHolder(itemView/* ,this*/);
    }

    @Override
    public void onBindViewHolder(@NonNull MyLetterLogsAdapter.ViewHolder holder, int position) {
        LetterItem item = letterItems.get(position);
        Glide.with(context).load("https://sunhan.s3.ap-northeast-2.amazonaws.com/raw/"+letterItems.get(position).getWriterItem().getAvatarUrl()).error(R.drawable.profile).into(holder.userProfile);
        holder.letterName.setText(letterItems.get(position).getWriterItem().getNickname());
        holder.letterContent.setText(letterItems.get(position).getContent());
        holder.letterDate.setText(letterItems.get(position).getCreateAt());
//        Glide.with(context).load("https://sunhan.s3.ap-northeast-2.amazonaws.com/raw/"+letterItems.get(position).getWriterItem().get).error(R.drawable.profile).into(holder.letterImage);
    }

    /*
    public void setOnClickStoreItemListener(OnClickStoreItemListener listener){
        this.listener = listener;
    }


    @Override
    public void onItemClick(SunhanStoreAdapter.ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder,view,position);
        }
    }

     */
    @Override
    public int getItemCount() {
        return letterItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView letterName;
        TextView letterContent;
        TextView letterDate;
        ImageView letterImage;
        ImageView userProfile;


        public ViewHolder(@NonNull View itemView/* , final OnClickStoreItemListener listener */) {
            super(itemView);
            userProfile = itemView.findViewById(R.id.letter_profile_pic);
            letterImage = itemView.findViewById(R.id.letterImage);
            letterName = itemView.findViewById(R.id.writer);
            letterContent = itemView.findViewById(R.id.content);
            letterDate = itemView.findViewById(R.id.createAt);

            itemView.setClickable(true);
/*            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if(listener != null){
                        listener.onItemClick(SunhanStoreAdapter.ViewHolder.this, view, position);
                    }
                }
            });

 */
        }
    }

    public void addList(ArrayList <LetterItem> list){
        letterItems.addAll(list);
        notifyItemRangeInserted(letterItems.size(),list.size());
    }

    public void addItem(LetterItem item){
        letterItems.add(item);
    }
    public void setArrayList(ArrayList<LetterItem> arrayList) {
        this.letterItems = arrayList;
    }

    public LetterItem getItem(int position) {
        return letterItems.get(position);
    }

    public void setItem(int position, LetterItem item) {
        letterItems.set(position, item);
    }
}
