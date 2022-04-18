package com.example.front_sunhan.View.adapter;

//fragment_sunhanst_main.xml

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front_sunhan.Model.StoreItem;
import com.example.front_sunhan.R;

import java.util.ArrayList;

public class SunhanStoreAdapter extends RecyclerView.Adapter<SunhanStoreAdapter.ViewHolder>{
    ArrayList<StoreItem> items=new ArrayList<StoreItem>();

    @NonNull
    //@Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, String viewType){
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView=inflater.inflate(R.layout.store_item, viewGroup, false);
        // 인플레이션을 통해 뷰 객체 만들었음

        return new ViewHolder(itemView); //뷰홀더 객체 생성하면서 뷰 객체 전달하고 그 뷰홀더 객체 반환
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position){
        StoreItem item = items.get(position);
        viewHolder.setItem(item);
    }

     @Override
     public int getItemCount(){

        return items.size();
     }


     public void addItem(StoreItem item){
        items.add(item);
     }

     public void setItems(ArrayList<StoreItem> items){
        this.items=items;
     }

     public StoreItem getItem(int position){
        return items.get(position);
     }

     public void setItem(int position, StoreItem item){
        items.set(position, item);
     }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        TextView textView2;
        TextView textView3;
        TextView textView4;

        public ViewHolder(View itemView){
            super(itemView);

            textView=itemView.findViewById(R.id.textView);
            textView2=itemView.findViewById(R.id.textView2);
            textView3=itemView.findViewById(R.id.textView3);
            textView4=itemView.findViewById(R.id.textView4);
        }

        public void setItem(StoreItem item){
            textView.setText(item.getStoreName());
            textView2.setText(item.getStoreAddrs());
            textView3.setText(item.getStoreNum());
            textView4.setText(item.getStoreTime());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

}