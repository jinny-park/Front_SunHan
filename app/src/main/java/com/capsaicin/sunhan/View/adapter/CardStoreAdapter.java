package com.capsaicin.sunhan.View.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capsaicin.sunhan.Model.CardStoreItem;
import com.capsaicin.sunhan.Model.StoreItem;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.fragment.SunhanstCardFragment;
import com.capsaicin.sunhan.View.interfaceListener.OnClickCardStoreItemListener;
import com.capsaicin.sunhan.View.interfaceListener.OnClickStoreItemListener;

import java.util.ArrayList;

public class CardStoreAdapter extends RecyclerView.Adapter<CardStoreAdapter.ViewHolder>
        implements OnClickCardStoreItemListener {

    ArrayList<CardStoreItem> cardStoreList;
    private Context context;
    public OnClickCardStoreItemListener listener;

    public CardStoreAdapter(Context context, ArrayList<CardStoreItem> items){
        Log.d("어댑터생성자 ","들어옴" );
        this.context = context ;
        this.cardStoreList= items;
        notifyItemRangeInserted(cardStoreList.size(),items.size());
    }

    @NonNull
    @Override
    public CardStoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("온크리에이트뷰홀더 ","들어옴" );
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.store_item, parent, false);
        return new CardStoreAdapter.ViewHolder(itemView ,this);
    }

    @Override
    public void onBindViewHolder(@NonNull CardStoreAdapter.ViewHolder holder, int position) {
        CardStoreItem item =cardStoreList.get(position);
        Log.d("온바인드홀더 ", cardStoreList.get(position).getName());
        holder.storeName.setText(cardStoreList.get(position).getName());
        holder.storeAddrs.setText(cardStoreList.get(position).getAddress());
        holder.storeNum.setText(cardStoreList.get(position).getPhoneNumber());
        String time = cardStoreList.get(position).getWeekdayStartTime()+" - "+cardStoreList.get(position).getWeekdayEndTime();
        holder.storeTime.setText(time);
    }
    public void setOnClickCardStoreItemListener(OnClickCardStoreItemListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(CardStoreAdapter.ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder,view,position);
        }
    }

    @Override
    public int getItemCount() {
        return cardStoreList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView storeName;
        TextView storeAddrs;
        TextView storeNum;
        TextView storeTime;

        public ViewHolder(@NonNull View itemView , final OnClickCardStoreItemListener listener) {
            super(itemView);
            Log.d("뷰홀더 ","들어옴" );
            storeName = itemView.findViewById(R.id.storeName);
            storeAddrs = itemView.findViewById(R.id.storeAddrs);
            storeNum = itemView.findViewById(R.id.storeNum);
            storeTime = itemView.findViewById(R.id.storeTime);

            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(CardStoreAdapter.ViewHolder.this, view, position);
                    }
                }
            });


        }

    }

    public void addList(ArrayList <CardStoreItem> list){
        cardStoreList.addAll(list);
        notifyItemRangeInserted(cardStoreList.size(),list.size());
        Log.d("addList ",list.toString());
    }

    public void addItem(CardStoreItem item){
        cardStoreList.add(item);
    }
    public void setArrayList(ArrayList<CardStoreItem> arrayList) {
        this.cardStoreList = arrayList;
    }

    public CardStoreItem getItem(int position) {
        return cardStoreList.get(position);
    }

    public void setItem(int position, CardStoreItem item) {
        cardStoreList.set(position, item);
    }
}
