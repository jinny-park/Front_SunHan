package com.example.front_sunhan.View.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front_sunhan.Model.MypageItem;
import com.example.front_sunhan.R;

import java.util.ArrayList;

public class MypageSettingsAdapter extends RecyclerView.Adapter<MypageSettingsAdapter.ViewHolder> {
    private Context context;
    ArrayList<MypageItem> mypageItemArrayList;
//    public OnCardItemClickListener listener;

    public MypageSettingsAdapter(Context context, ArrayList<MypageItem> arrayList){
        this.context = context ;
        this.mypageItemArrayList= arrayList;
    }

    @NonNull
    @Override
    public MypageSettingsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.mypage_item, parent, false);
        return new MypageSettingsAdapter.ViewHolder(itemView/*,this*/);
    }

    @Override
    public void onBindViewHolder(@NonNull MypageSettingsAdapter.ViewHolder holder, int position) {
        MypageItem item =mypageItemArrayList.get(position);
//        holder.imageView.setImageResource(mypageItemArrayList.get(position).image);
        holder.textView.setText(mypageItemArrayList.get(position).getItemName());
    }
//
//    public void setOnCardItemClickListener(OnCardItemClickListener listener){
//
//        this.listener = listener;
//    }
//
//    @Override
//    public void onItemClick(MypageMylogsAdapter.ViewHolder holder, View view, int position) {
//        if(listener != null){
//            listener.onItemClick(holder,view,position);
//        }
//    }
//
//    @Override
//    public void onCardClick(MyTicket_TicketList_Adapter.ViewHolder holder, View view, int position) {
//
//    }


    @Override
    public int getItemCount() {
        return mypageItemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView /* final OnCardItemClickListener listener*/) {
            super(itemView);
//            imageView = itemView.findViewById(R.id.view_item);
            textView = itemView.findViewById(R.id.mypage_item);

            itemView.setClickable(true);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position = getAdapterPosition();
//                    if(listener != null){
//                        listener.onItemClick(MypageSettingsAdapter.ViewHolder.this, view, position);
//                    }
//                }
//            });


        }

    }

    public void addItem(MypageItem item){
        mypageItemArrayList.add(item);
    }
    public void setarrayList(ArrayList<MypageItem> arrayList) {

        this.mypageItemArrayList = arrayList;
    }

    public MypageItem getItem(int position) {

        return mypageItemArrayList.get(position);
    }

    public void setItem(int position, MypageItem item) {

        mypageItemArrayList.set(position, item);
    }
}
