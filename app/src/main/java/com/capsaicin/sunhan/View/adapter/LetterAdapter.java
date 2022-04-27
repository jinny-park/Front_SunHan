package com.capsaicin.sunhan.View.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capsaicin.sunhan.Model.LetterItem;
import com.capsaicin.sunhan.R;
//import com.example.front_sunhan.View.interfaceListener.OnClickStoreItemListener;

import java.util.ArrayList;

public class LetterAdapter extends RecyclerView.Adapter<LetterAdapter.ViewHolder> {

    ArrayList<LetterItem> letterItems;
    //ArrayList<LetterItem> letterItems = new ArrayList<LetterItem>();
    private Context context;
//    public OnClickStoreItemListener listener;

    public LetterAdapter(Context context, ArrayList<LetterItem> items) {
        this.context = context;
        this.letterItems = items;
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
        LetterItem item = letterItems.get(position);
        //holder.imageView.setImageResource(storeItemArrayList.get(position).image);
        holder.letterName.setText(letterItems.get(position).getLetterName());
        holder.letterContent.setText(letterItems.get(position).getLetterContent());
        holder.letterDate.setText(letterItems.get(position).getLetterDate());
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
        return letterItems.size();
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
    }

    public void addItem(LetterItem item){
        letterItems.add(item);
    }
    public void setArrayList(ArrayList<LetterItem> arrayList) {
        this.letterItems = arrayList;
    }

    public LetterItem getItem(int position) {
        return letterItems.get(position);
    }

    public void setItem(int position, LetterItem item) {
        letterItems.set(position, item);
    }
}