package com.example.front_sunhan.View.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.front_sunhan.R;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front_sunhan.View.activity.LoginActivity;

public class MypageFragment extends Fragment {

    RecyclerView mypageRecyclerView;

//    RecyclerView mypageSettingsreRecyclerView;
//    RecyclerView mypageMylogsRecyclerView;
//    RecyclerView mypageEtcRecyclerView;

//    ArrayList<MypageItem> list = new ArrayList<>();
//    TextView res_name;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mypage,null);

        setRecyclerview(view);

//        LoginActivity.AdapterBottomFrag1.setOnCardItemClickListener(new OnCardItemClickListener() {
//            @Override public void onItemClick(AdapterBottomFrag1.ViewHolder holder, View view, int position) {
//                if (position != RecyclerView.NO_POSITION) {
//                    Intent intent = new Intent(getActivity(), MainTabActivity.class);
//                    res_name = (TextView) view.findViewById(R.id.res_name);
//                    intent.putExtra("resName",res_name.getText());
//                    startActivity(intent);
//                }
//            }
//
//            @Override
//            public void onCardClick(MyTicket_TicketList_Adapter.ViewHolder holder, View view, int position) { }
//        });

        return view;

    }


    void setRecyclerview(View view){


        mypageRecyclerView = view.findViewById(R.id.recyclerview_mypage);
        mypageRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
        mypageRecyclerView.setLayoutManager(recyclerViewManager);
        mypageRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mypageRecyclerView.setAdapter(LoginActivity.mypageSettingsAdapter);

//        mypageSettingsreRecyclerView = view.findViewById(R.id.recyclerview_settings);
//        mypageMylogsRecyclerView = view.findViewById(R.id.recyclerview_mylogs);
//        mypageEtcRecyclerView = view.findViewById(R.id.recyclerview_etc);
//
//        mypageSettingsreRecyclerView.setHasFixedSize(true);
//        mypageMylogsRecyclerView.setHasFixedSize(true);
//        mypageEtcRecyclerView.setHasFixedSize(true);
//
//        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
//        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
//        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(getActivity());
//
//        mypageSettingsreRecyclerView.setLayoutManager(layoutManager1);
//        mypageSettingsreRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mypageSettingsreRecyclerView.setAdapter(LoginActivity.mypageSettingsAdapter);
//
//        mypageMylogsRecyclerView.setLayoutManager(layoutManager2);
//        mypageMylogsRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mypageMylogsRecyclerView.setAdapter(LoginActivity.mypageMylogsAdapter);
//
//        mypageEtcRecyclerView.setLayoutManager(layoutManager3);
//        mypageEtcRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mypageEtcRecyclerView.setAdapter(LoginActivity.mypageEtcAdapter);
    }
}
