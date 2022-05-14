package com.capsaicin.sunhan.View.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capsaicin.sunhan.Model.LikedChildItem;
import com.capsaicin.sunhan.Model.LikedSunHanItem;
import com.capsaicin.sunhan.R;

import java.util.ArrayList;

public class LikedChildAdapter  extends RecyclerView.Adapter<LikedChildAdapter.ViewHolder>{

    ArrayList<LikedChildItem> likedChildItems;
    private Context context;

    public LikedChildAdapter(Context context, ArrayList<LikedChildItem> items){
        this.context = context ;
        this.likedChildItems = items;
    }

    @NonNull
    @Override
    public LikedChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.store_item, parent, false);
        return new LikedChildAdapter.ViewHolder(itemView /*,this*/);
    }



    @Override
    public void onBindViewHolder(@NonNull LikedChildAdapter.ViewHolder holder, int position) {
        LikedChildItem item = likedChildItems.get(position);
        //     holder.imageView.setImageResource(storeItemArrayList.get(position).image);
        holder.storeName.setText(likedChildItems.get(position).getName());
        holder.storeAddrs.setText(likedChildItems.get(position).getAddress());
        String time = likedChildItems.get(position).getWeekdayStartTime()+" - "+likedChildItems.get(position).getWeekdayEndTime();
        holder.storeNum.setText(likedChildItems.get(position).getPhoneNumber());
        holder.storeTime.setText(time);
    }
//    public void setOnClickStoreItemListener(OnClickStoreItemListener listener){
//        this.listener = listener;
//    }
//
//    @Override
//    public void onItemClick(LikedSunHanAdapter.ViewHolder holder, View view, int position) {
//        if(listener != null){
//            listener.onItemClick(holder,view,position);
//        }
//    }

    @Override
    public int getItemCount() {
        return likedChildItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView storeName;
        TextView storeAddrs;
        TextView storeNum;
        TextView storeTime;
        //ImageView storeImage;

        public ViewHolder(@NonNull View itemView /*, final OnClickStoreItemListener listener*/) {
            super(itemView);
            storeName = itemView.findViewById(R.id.storeName);
            storeAddrs = itemView.findViewById(R.id.storeAddrs);
            storeNum = itemView.findViewById(R.id.storeNum);
            storeTime = itemView.findViewById(R.id.storeTime);

//            itemView.setClickable(true);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position = getAdapterPosition();
//
//                    if(listener != null){
//                        listener.onItemClick(LikedSunHanAdapter.ViewHolder.this, view, position);
//                    }
//                }
//            });


        }

    }

    public void addList(ArrayList <LikedChildItem> list){
        likedChildItems.addAll(list);
    }

    public void addItem(LikedChildItem item){
        likedChildItems.add(item);
    }

    public void setArrayList(ArrayList<LikedChildItem> arrayList) {
        this.likedChildItems = arrayList;
    }

    public LikedChildItem getItem(int position) {
        return likedChildItems.get(position);
    }

    public void setItem(int position, LikedChildItem item) {
        likedChildItems.set(position, item);
    }

}
