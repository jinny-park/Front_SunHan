package com.capsaicin.sunhan.View.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capsaicin.sunhan.Model.BlockedItem;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.interfaceListener.OnClickBlockedItemListener;

import java.util.ArrayList;

public class ManageBlockAdapter extends RecyclerView.Adapter<ManageBlockAdapter.ViewHolder>
        implements OnClickBlockedItemListener {

    private Context context;
    private ArrayList<BlockedItem> blocekdItemsList;
    public OnClickBlockedItemListener listener;

    public ManageBlockAdapter(Context context, ArrayList<BlockedItem> list){
        this.context = context ;
        this.blocekdItemsList= list;
    }

    @NonNull
    @Override
    public ManageBlockAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.block_item, parent, false);
        return new ManageBlockAdapter.ViewHolder(itemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull ManageBlockAdapter.ViewHolder holder, int position) {
        BlockedItem item =blocekdItemsList.get(position);
//     holder.imageView.setImageResource(mypageItemArrayList.get(position).image);
        holder.textView.setText(blocekdItemsList.get(position).getNickname());
    }

    public void setOnClickBlockedItemListener(OnClickBlockedItemListener listener){

        this.listener = listener;
    }
    @Override
    public void onItemClick(ManageBlockAdapter.ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder,view,position);
        }
    }


    @Override
    public int getItemCount() {
        return blocekdItemsList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        Button button;

        public ViewHolder(@NonNull View itemView , final OnClickBlockedItemListener listener) {
            super(itemView);
            button = itemView.findViewById(R.id.isBlocked_btn);
            textView = itemView.findViewById(R.id.blocked_id);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if(listener != null){
                        listener.onItemClick(ManageBlockAdapter.ViewHolder.this, view, position);
                    }
                }
            });

            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if(listener != null){
                        listener.onItemClick(ManageBlockAdapter.ViewHolder.this, view, position);
                    }
                }
            });


        }

    }

    public void addItem(BlockedItem item){
        blocekdItemsList.add(item);
    }
    public void setarrayList(ArrayList<BlockedItem> arrayList) {

        this.blocekdItemsList = arrayList;
    }

    public BlockedItem getItem(int position) {

        return blocekdItemsList.get(position);
    }

    public void setItem(int position, BlockedItem item) {

        blocekdItemsList.set(position, item);
    }


}