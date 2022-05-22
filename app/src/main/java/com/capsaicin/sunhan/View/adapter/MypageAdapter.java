package com.capsaicin.sunhan.View.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capsaicin.sunhan.Model.MypageItem;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.interfaceListener.OnClickMyPageItemListener;

import java.util.ArrayList;

public class MypageAdapter extends RecyclerView.Adapter<MypageAdapter.ViewHolder>
    implements OnClickMyPageItemListener {
    private Context context;
    ArrayList<MypageItem> mypageItemArrayList;
    public OnClickMyPageItemListener listener;

    public MypageAdapter(Context context, ArrayList<MypageItem> arrayList){
        this.context = context ;
        this.mypageItemArrayList= arrayList;
    }

    @NonNull
    @Override
    public MypageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.mypage_item, parent, false);
        return new MypageAdapter.ViewHolder(itemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull MypageAdapter.ViewHolder holder, int position) {
        MypageItem item =mypageItemArrayList.get(position);
        holder.myPageName.setText(mypageItemArrayList.get(position).getItemName());
        holder.myPageImage.setImageResource(mypageItemArrayList.get(position).getMyPageIcon());
    }

    public void setOnClickMyPageItemListener(OnClickMyPageItemListener listener){

        this.listener = listener;
    }
    @Override
    public void onItemClick(MypageAdapter.ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder,view,position);
        }
    }


    @Override
    public int getItemCount() {
        return mypageItemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView myPageName;
        ImageView myPageImage;

        public ViewHolder(@NonNull View itemView , final OnClickMyPageItemListener listener) {
            super(itemView);

            myPageName = itemView.findViewById(R.id.mypage_item_name);
            myPageImage = itemView.findViewById(R.id.mypage_icon);

            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if(listener != null){
                        listener.onItemClick(MypageAdapter.ViewHolder.this, view, position);
                    }
                }
            });
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
