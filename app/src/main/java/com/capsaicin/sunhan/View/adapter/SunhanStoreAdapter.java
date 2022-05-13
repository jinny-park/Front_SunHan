package com.capsaicin.sunhan.View.adapter;

//fragment_sunhanst_main.xml

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capsaicin.sunhan.Model.StoreItem;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.interfaceListener.OnClickStoreItemListener;

import java.util.ArrayList;

/*
public interface AdapterItemListener<T> {
    void onItemClicked(T item);
}
 */

public class SunhanStoreAdapter extends RecyclerView.Adapter<SunhanStoreAdapter.ViewHolder>
        implements OnClickStoreItemListener{

    ArrayList<StoreItem> cardStoreList=new ArrayList<StoreItem>();
    private Context context;
    public OnClickStoreItemListener listener;

    public SunhanStoreAdapter(Context context, ArrayList<StoreItem> items){
        this.context = context ;
        this.cardStoreList= items;
    }

    @NonNull
    @Override
    public SunhanStoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.store_item, parent, false);
        return new SunhanStoreAdapter.ViewHolder(itemView ,this);
    }

    @Override
    public void onBindViewHolder(@NonNull SunhanStoreAdapter.ViewHolder holder, int position) {
        StoreItem item =cardStoreList.get(position);
   //     holder.imageView.setImageResource(storeItemArrayList.get(position).image);
        holder.storeName.setText(cardStoreList.get(position).getStoreName());
        holder.storeAddrs.setText(cardStoreList.get(position).getStoreAddrs());
        holder.storeNum.setText(cardStoreList.get(position).getStoreNum());
        holder.storeTime.setText(cardStoreList.get(position).getStoreTime());
    }
    public void setOnClickStoreItemListener(OnClickStoreItemListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(SunhanStoreAdapter.ViewHolder holder, View view, int position) {
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
        //ImageView storeImage;

        public ViewHolder(@NonNull View itemView , final OnClickStoreItemListener listener) {
            super(itemView);
//            imageView = itemView.findViewById(R.id.storeImage);
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
                        listener.onItemClick(SunhanStoreAdapter.ViewHolder.this, view, position);
                    }
                }
            });


        }

    }

    public void addList(ArrayList <StoreItem> list){
        cardStoreList.addAll(list);
    }

    public void addItem(StoreItem item){
        cardStoreList.add(item);
    }
    public void setArrayList(ArrayList<StoreItem> arrayList) {
        this.cardStoreList = arrayList;
    }

    public StoreItem getItem(int position) {
        return cardStoreList.get(position);
    }

    public void setItem(int position, StoreItem item) {
        cardStoreList.set(position, item);
    }

}