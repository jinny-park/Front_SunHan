package com.capsaicin.sunhan.View.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capsaicin.sunhan.Model.LetterItem;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.activity.LoginActivity;
import com.capsaicin.sunhan.View.adapter.MyLogsLetterAdapter;

import java.util.ArrayList;

public class MyLogsLetterFragment extends Fragment {

    ArrayList<LetterItem> list = new ArrayList<>();
    public static MyLogsLetterAdapter letterLogsAdapter;
    RecyclerView letterRecyclerView;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mylogs_letter, null);
        setData();
        letterLogsAdapter = new MyLogsLetterAdapter(getContext(),list);


        setRecyclerview(view);
        return view;
    }
    void setData(){

        list.add(new LetterItem("김순대", "따뜻한 밥 한끼 정말 잘 먹었습니다 다음에 꼭 또 올래요",
                "2022년 1월 22일"));
        list.add(new LetterItem("이떡볶이", "완전 맛있어요 사장님 감사합니다",
                "2022년 2월 3일"));
        list.add(new LetterItem("박튀김", "집이랑 가까워서 자주 올 것 같아요. 최고~~",
                "2022년 3월 4일"));
        list.add(new LetterItem("최우동", "아이들이 너무 좋아하네요! ! 사장님 번창하세요",
                "2022년 4월 5일"));
        list.add(new LetterItem("안국수", "주차공간도 너무 넓고 찾아오는 길도 편해요",
                "2022년 5월 6일"));
        list.add(new LetterItem("조카레", "가게가 시끄럽고 어수선하지 않아서 편하게 잘 먹고 갑니다 감사합니다",
                "2022년 6월 7일"));
    }

    void setRecyclerview(View view){
        letterRecyclerView = view.findViewById(R.id.recyclerview_logs_letter);
        letterRecyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
        letterRecyclerView.setLayoutManager(recyclerViewManager);
        letterRecyclerView.setItemAnimator(new DefaultItemAnimator());
        letterRecyclerView.setAdapter(letterLogsAdapter);

    }
}
