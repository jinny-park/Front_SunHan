package com.example.front_sunhan.View.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front_sunhan.Model.LetterItem;
import com.example.front_sunhan.Model.StoreItem;
import com.example.front_sunhan.R;
//import com.example.front_sunhan.View.interfaceListener.OnClickStoreItemListener;

import java.util.ArrayList;

public class LetterAdapter extends RecyclerView.Adapter<LetterAdapter.ViewHolder> {

    ArrayList<LetterItem> items = new ArrayList<LetterItem>();
    private Context context;
//    public OnClickStoreItemListener listener;

    public LetterAdapter(Context context, ArrayList<LetterItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public LetterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.letter_item, parent, false);
        return new LetterAdapter.ViewHolder(itemView/* ,this*/);
    }

    @Override
    public void onBindViewHolder(@NonNull LetterAdapter.ViewHolder holder, int position) {
        LetterItem item = items.get(position);
        //     holder.imageView.setImageResource(storeItemArrayList.get(position).image);
        holder.letterName.setText(items.get(position).getLetterName());
        holder.letterContent.setText(items.get(position).getLetterContent());
        holder.letterDate.setText(items.get(position).getLetterDate());
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
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView letterName;
        TextView letterContent;
        TextView letterDate;
        //ImageView letterImage;


        public ViewHolder(@NonNull View itemView/* , final OnClickStoreItemListener listener */) {
            super(itemView);
//            imageView = itemView.findViewById(R.id.storeImage);
            letterName = itemView.findViewById(R.id.letterName);
            letterContent = itemView.findViewById(R.id.letterContent);
            letterDate = itemView.findViewById(R.id.letterDate);

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
/*
    public void addItem(LetterItem item){
        items.add(item);
    }
    public void setArrayList(ArrayList<LetterItem> arrayList) {
        this.items = arrayList;
    }

    public LetterItem getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, LetterItem item) {
        items.set(position, item);
    }

 */

    }
}