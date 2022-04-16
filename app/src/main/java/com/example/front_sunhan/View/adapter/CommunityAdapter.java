package com.example.front_sunhan.View.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front_sunhan.Model.CommunityItem;
import com.example.front_sunhan.R;

import java.util.ArrayList;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.ItemViewHolder> {

    // adapter에 들어갈 list 입니다.
    private ArrayList<CommunityItem> listData = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }

    void addItem(CommunityItem data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView1;
        private TextView textView2;
        private TextView textView3;
        private TextView textView4;

        ItemViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.userprofile);
            textView1 = itemView.findViewById(R.id.userid);
            textView2 = itemView.findViewById(R.id.uploadtime);
            textView3 = itemView.findViewById(R.id.content);
            textView4 = itemView.findViewById(R.id.commentnum);
        }

        void onBind(CommunityItem data) {
            imageView.setImageResource(data.getProfile());
            textView1.setText(data.getNickname());
            textView2.setText(data.getWritetime());
            textView3.setText(data.getContent());
            textView4.setText(data.getWritenum());
        }
    }
}

