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
import com.capsaicin.sunhan.Model.LetterItem;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.interfaceListener.OnClickCommentLogsListener;
import com.capsaicin.sunhan.View.interfaceListener.OnClickLetterListener;
//import com.example.front_sunhan.View.interfaceListener.OnClickStoreItemListener;

import java.util.ArrayList;

public class LetterAdapter extends RecyclerView.Adapter<LetterAdapter.ViewHolder>
        implements OnClickLetterListener {

    ArrayList<LetterItem> letterItems;
    private Context context;
    public OnClickLetterListener listener;

    public LetterAdapter(Context context, ArrayList<LetterItem> items) {
        this.context = context;
        this.letterItems = items;
        notifyItemRangeInserted(letterItems.size(),letterItems.size());
    }

    @NonNull
    @Override
    public LetterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.letter_item, parent, false);
        return new LetterAdapter.ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull LetterAdapter.ViewHolder holder, int position) {
        LetterItem item = letterItems.get(position);
        Glide.with(context).load("https://sunhan.s3.ap-northeast-2.amazonaws.com/raw/"+letterItems.get(position).getWriterItem().getAvatarUrl()).error(R.drawable.profile).into(holder.userProfile);
        holder.letterName.setText(letterItems.get(position).getWriterItem().getNickname());
        holder.letterContent.setText(letterItems.get(position).getContent());
        holder.letterDate.setText(letterItems.get(position).getCreateAt());
        if(letterItems.get(position).getImageUrl()!=null)
            Glide.with(context).load("https://sunhan.s3.ap-northeast-2.amazonaws.com/raw/"+letterItems.get(position).getImageUrl()).into(holder.letterImage);

//        //holder.imageView.setImageResource(storeItemArrayList.get(position).image);
//        holder.letterName.setText(letterItems.get(position).getWriterItem().getNickname());
//        holder.letterContent.setText(letterItems.get(position).getContent());
//        holder.letterDate.setText(letterItems.get(position).getCreateAt());
    }
    public void setOnClickLetterListener(OnClickLetterListener listener) {

        this.listener = listener;
    }

    @Override
    public void onItemClick(LetterAdapter.ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

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
        TextView delete;
        TextView edit;
        TextView block;


        public ViewHolder(@NonNull View itemView, final OnClickLetterListener listener) {
            super(itemView);

            userProfile = itemView.findViewById(R.id.letter_user_profile);
            letterImage = itemView.findViewById(R.id.letterImage);
            letterName = itemView.findViewById(R.id.writer);
            letterContent = itemView.findViewById(R.id.content);
            letterDate = itemView.findViewById(R.id.createAt);
            delete = itemView.findViewById(R.id.delete_letter);
            edit = itemView.findViewById(R.id.edit_letter);
            block = itemView.findViewById(R.id.block_letter);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onItemClick(LetterAdapter.ViewHolder.this, view, position);
                    }
                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onItemClick(LetterAdapter.ViewHolder.this, view, position);
                    }
                }
            });

            block.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onItemClick(LetterAdapter.ViewHolder.this, view, position);
                    }
                }
            });

//            itemView.setClickable(true);
//            itemView.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View view) {
//                    int position = getAdapterPosition();
//                    if (listener != null) {
//                        listener.onItemClick(LetterAdapter.ViewHolder.this, view, position);
//                    }
//                }
//            });

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