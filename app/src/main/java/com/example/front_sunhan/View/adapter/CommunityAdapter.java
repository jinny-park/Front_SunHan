//package com.example.front_sunhan.View.adapter;
//
//import android.content.Context;
//import android.graphics.drawable.ShapeDrawable;
//import android.graphics.drawable.shapes.OvalShape;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.front_sunhan.Model.CommunityItem;
//import com.example.front_sunhan.Model.MypageItem;
//import com.example.front_sunhan.R;
//import com.example.front_sunhan.View.interfaceListener.OnClickMyPageItemListener;
//
//import java.util.ArrayList;
//
//public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.ViewHolder> { {
//    private Context context;
//    ArrayList<MypageItem> communityItemArrayList;
//
//    public CommunityAdapter(Context context, ArrayList<CommunityItem> arrayList){
//            this.context = context ;
//            this.communityItemArrayList= arrayList;
//        }
//
//    @NonNull
//    @Override
//    public CommunityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View itemView = layoutInflater.inflate(R.layout.community_item, parent, false);
//        return new CommunityAdapter.ViewHolder(itemView,this);
//    }
//
//        @Override
//        public void onBindViewHolder(@NonNull CommunityAdapter.ViewHolder holder, int position) {
//            MypageItem item =mypageItemArrayList.get(position);
////        holder.imageView.setImageResource(mypageItemArrayList.get(position).image);
//            holder.textView.setText(mypageItemArrayList.get(position).getItemName());
//        }
//
//        public void setOnClickCommunityItemListener(OnClickMyPageItemListener listener){
//
//            this.listener = listener;
//        }
//        @Override
//        public void onItemClick(CommunityAdapter.ViewHolder holder, View view, int position) {
//            if(listener != null){
//                listener.onItemClick(holder,view,position);
//            }
//        }
//
//
//        @Override
//        public int getItemCount() {
//            return communityItemArrayList.size();
//        }
//
//        public static class ViewHolder extends RecyclerView.ViewHolder{
//            TextView textView;
//            ImageView imageView;
//
//            public ViewHolder(@NonNull View itemView , final OnClickMyPageItemListener listener) {
//                super(itemView);
////            imageView = itemView.findViewById(R.id.view_item);
//                textView = itemView.findViewById(R.id.mypage_item);
//
//                itemView.setClickable(true);
//                itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        int position = getAdapterPosition();
//
//                        if(listener != null){
//                            listener.onItemClick(MypageAdapter.ViewHolder.this, view, position);
//                        }
//                    }
//                });
//
//
//            }
//
//        }
//
//        public void addItem(CommunityItem item){
//            communityItemArrayList.add(item);
//        }
//        public void setarrayList(ArrayList<CommunityItem> arrayList) {
//
//            this.communityItemArrayList = arrayList;
//        }
//
//        public CommunityItem getItem(int position) {
//
//            return communityItemArrayList.get(position);
//        }
//
//        public void setItem(int position, CommunityItem item) {
//
//            communityItemArrayList.set(position, item);
//        }
//    }
