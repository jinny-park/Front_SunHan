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

}