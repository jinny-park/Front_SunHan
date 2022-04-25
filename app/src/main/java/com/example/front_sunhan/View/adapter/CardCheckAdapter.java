package com.example.front_sunhan.View.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front_sunhan.Model.CardCheckItem;
import com.example.front_sunhan.R;
import com.example.front_sunhan.View.interfaceListener.OnClickCardCheckListener;
import com.example.front_sunhan.View.interfaceListener.OnClickMyPageItemListener;

import java.util.ArrayList;

public class CardCheckAdapter extends RecyclerView.Adapter<CardCheckAdapter.ViewHolder>
implements OnClickCardCheckListener {

    private Context context;
    ArrayList<CardCheckItem> cardCheckItemArrayList;
    public OnClickCardCheckListener listener;
    public CardCheckAdapter(Context context, ArrayList<CardCheckItem> arrayList){
        this.context = context ;
        this.cardCheckItemArrayList = arrayList;
    }

    @NonNull
    @Override
    public CardCheckAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.card_item, parent, false);
        return new CardCheckAdapter.ViewHolder(itemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull CardCheckAdapter.ViewHolder holder, int position) {
        CardCheckItem item = cardCheckItemArrayList.get(position);
        holder.areaName.setText(cardCheckItemArrayList.get(position).getArea());
    }
    public void setOnClickCardCheckListener(OnClickCardCheckListener listener){

        this.listener = listener;
    }
    @Override
    public void onItemClick(CardCheckAdapter.ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder,view,position);
        }
    }

    @Override
    public int getItemCount() {
        return cardCheckItemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView areaName;
        ImageView cardImg;

        public ViewHolder(@NonNull View itemView , final OnClickCardCheckListener listener ) {
            super(itemView);
            cardImg = itemView.findViewById(R.id.card_img_item);
            areaName = itemView.findViewById(R.id.area_item);

            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if(listener != null){
                        listener.onItemClick(CardCheckAdapter.ViewHolder.this, view, position);
                    }
                }
            });
        }
    }

    public void addItem(CardCheckItem item){
        cardCheckItemArrayList.add(item);
    }
    public void setarrayList(ArrayList<CardCheckItem> arrayList) {

        this.cardCheckItemArrayList = arrayList;
    }

    public CardCheckItem getItem(int position) {

        return cardCheckItemArrayList.get(position);
    }

    public void setItem(int position, CardCheckItem item) {

        cardCheckItemArrayList.set(position, item);
    }
}
