package com.capsaicin.sunhan.View.fragment;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.capsaicin.sunhan.Model.LetterItem;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.adapter.LetterAdapter;

import java.util.ArrayList;

public class StoreLetterFragment extends Fragment {
    public static LetterAdapter letterAdapter;
    ArrayList<LetterItem> letterList = new ArrayList<LetterItem>();
    RecyclerView letterRecyclerView;
    CardView letter;
    ImageView letter_img;
    private static final int REQUEST_CODE = 0;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sunhanst_store_letter,null);

        letter = view.findViewById(R.id.add_letter_img);
        letter_img = view.findViewById(R.id.letter_img);

        letter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        letterAdapter = new LetterAdapter(getContext(), letterList);

        setRecyclerview(view);
        setData();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE){
            if(resultCode==RESULT_OK){
                try {
                    Uri uri = data.getData();
                    Glide.with(getActivity().getApplicationContext()).load(uri).into(letter_img);
                }catch (Exception e){
                    getActivity().finish();
                }
            }else if(resultCode==RESULT_CANCELED){
                getActivity().finish();
            }
        }
    }

    void setRecyclerview(View view){
        letterRecyclerView = view.findViewById(R.id.recyclerview_letter);
        letterRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
        letterRecyclerView.setLayoutManager(recyclerViewManager);
        letterRecyclerView.setItemAnimator(new DefaultItemAnimator());
        letterRecyclerView.setAdapter(letterAdapter);

    }


    void setList(){
        letterAdapter = new LetterAdapter(getContext(), letterList);
        setData();
    }

    void setData(){

        letterAdapter.addItem(new LetterItem("김순대", "따뜻한 밥 한끼 정말 잘 먹었습니다 다음에 꼭 또 올래요",
                "2022년 1월 22일"));
        letterAdapter.addItem(new LetterItem("이떡볶이", "완전 맛있어요 사장님 감사합니다",
                "2022년 2월 3일"));
        letterAdapter.addItem(new LetterItem("박튀김", "집이랑 가까워서 자주 올 것 같아요. 최고~~",
                "2022년 3월 4일"));
        letterAdapter.addItem(new LetterItem("최우동", "아이들이 너무 좋아하네요! ! 사장님 번창하세요",
                "2022년 4월 5일"));
        letterAdapter.addItem(new LetterItem("안국수", "주차공간도 너무 넓고 찾아오는 길도 편해요",
                "2022년 5월 6일"));
        letterAdapter.addItem(new LetterItem("조카레", "가게가 시끄럽고 어수선하지 않아서 편하게 잘 먹고 갑니다 감사합니다",
                "2022년 6월 7일"));
    }


}