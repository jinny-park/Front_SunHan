package com.example.front_sunhan.View.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front_sunhan.Model.CardItem;
import com.example.front_sunhan.Model.MypageItem;
import com.example.front_sunhan.R;
import com.example.front_sunhan.View.interfaceListener.OnClickMyPageItemListener;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder>{

    private Context context;
    ArrayList<CardItem> cardItemArrayList;

    public CardAdapter(Context context,ArrayList<CardItem> arrayList){
        this.context = context ;
        this.cardItemArrayList= arrayList;
    }

    @NonNull
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.card_item, parent, false);
        return new CardAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.ViewHolder holder, int position) {
        CardItem item =cardItemArrayList.get(position);
        holder.cardName.setText(cardItemArrayList.get(position).getCardName());
        holder.cardAccount.setText(cardItemArrayList.get(position).getAccount());
        holder.money.setText(cardItemArrayList.get(position).getMoney());
    }


    @Override
    public int getItemCount() {
        return cardItemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView cardName;
        ImageView cardImg;
        TextView cardAccount;
        TextView money;

        public ViewHolder(@NonNull View itemView ) {
            super(itemView);
            cardImg = itemView.findViewById(R.id.card_img_item);
            cardName = itemView.findViewById(R.id.card_name_item);
            cardAccount = itemView.findViewById(R.id.card_account_item);
            money = itemView.findViewById(R.id.card_money_item);
        }
    }

    public void addItem(CardItem item){
        cardItemArrayList.add(item);
    }
    public void setarrayList(ArrayList<CardItem> arrayList) {

        this.cardItemArrayList = arrayList;
    }

    public CardItem getItem(int position) {

        return cardItemArrayList.get(position);
    }

    public void setItem(int position, CardItem item) {

        cardItemArrayList.set(position, item);
    }
}
