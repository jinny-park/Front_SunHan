//package com.example.front_sunhan.View.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.front_sunhan.Model.MypageItem;
//import com.example.front_sunhan.R;
//
//import java.util.ArrayList;
//
//public class MypageSettingsAdapter extends RecyclerView.Adapter<MypageMylogsAdapter.ViewHolder> {
//    private Context context;
//    private ArrayList<MypageItem> arrayList;
////    public OnCardItemClickListener listener;
//
//    public MypageSettingsAdapter(Context context, ArrayList<MypageItem> arrayList){
//        this.context = context ;
//        this.arrayList= arrayList;
//    }
//
//    @NonNull
//    @Override
//    public MypageMylogsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View itemView = layoutInflater.inflate(R.layout.mypage_item, parent, false);
//        return new MypageMylogsAdapter.ViewHolder(itemView,this);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MypageMylogsAdapter.ViewHolder holder, int position) {
//        ResItem item =arrayList.get(position);
//        holder.imageView.setImageResource(arrayList.get(position).image);
//        holder.textView.setText(arrayList.get(position).name);
//    }
////
////    public void setOnCardItemClickListener(OnCardItemClickListener listener){
////
////        this.listener = listener;
////    }
////
////    @Override
////    public void onItemClick(MypageMylogsAdapter.ViewHolder holder, View view, int position) {
////        if(listener != null){
////            listener.onItemClick(holder,view,position);
////        }
////    }
////
////    @Override
////    public void onCardClick(MyTicket_TicketList_Adapter.ViewHolder holder, View view, int position) {
////
////    }
//
//
//    @Override
//    public int getItemCount() {
//        return arrayList.size();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder{
//        TextView textView;
//        ImageView imageView;
//
//        public ViewHolder(@NonNull View itemView, final OnCardItemClickListener listener) {
//            super(itemView);
//            imageView = itemView.findViewById(R.id.view_item);
//            textView = itemView.findViewById(R.id.res_name);
//
//            itemView.setClickable(true);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position = getAdapterPosition();
//                    if(listener != null){
//                        listener.onItemClick(MypageMylogsAdapter.ViewHolder.this, view, position);
//                    }
//                }
//            });
//
//
//        }
//
//    }
//
//    public void addItem(MypageItem item){
//        arrayList.add(item);
//    }
//    public void setarrayList(ArrayList<MypageItem> arrayList) {
//
//        this.arrayList = arrayList;
//    }
//
//    public MypageItem getItem(int position) {
//
//        return arrayList.get(position);
//    }
//
//    public void setItem(int position, MypageItem item) {
//
//        arrayList.set(position, item);
//    }
//}
