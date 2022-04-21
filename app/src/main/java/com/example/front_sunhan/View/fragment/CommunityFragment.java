package com.example.front_sunhan.View.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front_sunhan.Model.CommunityItem;
import com.example.front_sunhan.R;
import com.example.front_sunhan.View.activity.CommunityDetailActivity;
import com.example.front_sunhan.View.activity.DeleteAccountActivity;
import com.example.front_sunhan.View.activity.EditProfileActivity;
import com.example.front_sunhan.View.activity.LoginActivity;
import com.example.front_sunhan.View.activity.ManageBlockActivity;
import com.example.front_sunhan.View.activity.MyLogsActivity;
import com.example.front_sunhan.View.activity.MyPageAddCardActivity;
import com.example.front_sunhan.View.activity.PolicyActivity;
import com.example.front_sunhan.View.activity.ToolbarActivity;
import com.example.front_sunhan.View.activity.WriteActivity;
import com.example.front_sunhan.View.adapter.CommunityAdapter;
import com.example.front_sunhan.View.interfaceListener.OnClickCommunityListener;

import java.util.ArrayList;

public class CommunityFragment extends Fragment {
    public static CommunityAdapter communityAdapter ;
    ArrayList<CommunityItem> cList = new ArrayList<CommunityItem>();
    RecyclerView communityRecyclerView;

    Button writeBtn;

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_community, null);
        communityAdapter = new CommunityAdapter(getContext(),  cList);

        setRecyclerview(view);
        setData();

        writeBtn = view.findViewById(R.id.write_btn);
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WriteActivity.class));
            }
        });

        communityAdapter.setOnClickCommunityListener(new OnClickCommunityListener() {
            @Override
            public void onItemClick(CommunityAdapter.ViewHolder holder, View view, int position) {
                ToolbarActivity toolbarActivity = new ToolbarActivity();
                String str_position = String.valueOf(position + 1); //
                if (position != RecyclerView.NO_POSITION) {
                    for (int i = 0; i <= position; i++) {
                        Intent intent = new Intent(getActivity(), CommunityDetailActivity.class);
                        intent.putExtra("position", str_position); //
                        startActivity(intent);
                        break;
                    }
                }
            }
            });

        return view;
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }



    void setRecyclerview(View view){
        communityRecyclerView = view.findViewById(R.id.recyleView_community);
        communityRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
        communityRecyclerView.setLayoutManager(recyclerViewManager);
        communityRecyclerView.setItemAnimator(new DefaultItemAnimator());
        communityRecyclerView.setAdapter(communityAdapter);
    }

    void setData(){
        communityAdapter.addItem(new CommunityItem(R.drawable.profile,"익명", "12:10","돈애랑 장터 순대국 감자탕 먹고 왔습니다! 완전 맛있\n" +
                "고 사장님도 친절해요~","0"));
        communityAdapter.addItem(new CommunityItem(R.drawable.profile,"익명", "12:07","낙원갈비집 주차도 편리하고 아이랑 맛있게 먹고 왔\n" +
                "습니다","4"));
        communityAdapter.addItem(new CommunityItem(R.drawable.profile,"익명", "12:10","영통버거 칭구들이랑 먹었어요 사장님 감사합니다 \n" +
                "또 갈게요 ~","1"));
        communityAdapter.addItem(new CommunityItem(R.drawable.profile,"익명", "09:56","일리터 카페 조용하고 커피도 고소해요! 영통구 주민\n" +
                "분들께 추천드립니다 ","3"));
        communityAdapter.addItem(new CommunityItem(R.drawable.profile,"익명", "09:10","영통버거 사장님 친절하세요.. 감사해요..","1"));
    }
}
