package com.capsaicin.sunhan.View.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capsaicin.sunhan.Model.LikedSunHanItem;
import com.capsaicin.sunhan.Model.StoreItem;
import com.capsaicin.sunhan.R;


import java.util.ArrayList;

public class LikedSunHanAdapter extends RecyclerView.Adapter<LikedSunHanAdapter.ViewHolder> {
    ArrayList<LikedSunHanItem> likedSunHanItems;
    private Context context;

    public LikedSunHanAdapter(Context context, ArrayList<LikedSunHanItem> items){
        this.context = context ;
        this.likedSunHanItems = items;
    }

    @NonNull
    @Override
    public LikedSunHanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.store_item, parent, false);
        return new LikedSunHanAdapter.ViewHolder(itemView /*,this*/);
    }



    @Override
    public void onBindViewHolder(@NonNull LikedSunHanAdapter.ViewHolder holder, int position) {
        LikedSunHanItem item = likedSunHanItems.get(position);
        //     holder.imageView.setImageResource(storeItemArrayList.get(position).image);
        holder.storeName.setText(likedSunHanItems.get(position).getName());
        holder.storeAddrs.setText(likedSunHanItems.get(position).getAddress());
        holder.storeNum.setText(likedSunHanItems.get(position).getTatget()); //타겟으로 씀
        holder.storeTime.setText(likedSunHanItems.get(position).getOpeningHours());
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
        return likedSunHanItems.size();
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

    public void addList(ArrayList <LikedSunHanItem> list){
        likedSunHanItems.addAll(list);
    }

    public void addItem(LikedSunHanItem item){
        likedSunHanItems.add(item);
    }

    public void setArrayList(ArrayList<LikedSunHanItem> arrayList) {
        this.likedSunHanItems = arrayList;
    }

    public LikedSunHanItem getItem(int position) {
        return likedSunHanItems.get(position);
    }

    public void setItem(int position, LikedSunHanItem item) {
        likedSunHanItems.set(position, item);
    }


}
