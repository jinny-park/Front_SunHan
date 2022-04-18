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

    public void addItem(CommunityItem data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView userprofile;
        private TextView userid;
        private TextView uploadtime;
        private TextView content;
        private TextView commentnum;

        ItemViewHolder(View itemView) {
            super(itemView);

            userprofile = itemView.findViewById(R.id.userprofile);
            userid = itemView.findViewById(R.id.userid);
            uploadtime = itemView.findViewById(R.id.uploadtime);
            content = itemView.findViewById(R.id.content);
            commentnum = itemView.findViewById(R.id.commentnum);
        }

        void onBind(CommunityItem data) {
            userprofile.setImageResource(data.getProfile());
            userid.setText(data.getNickname());
            uploadtime.setText(data.getWritetime());
            content.setText(data.getContent());
            commentnum.setText(data.getWritenum());
        }
    }
}

