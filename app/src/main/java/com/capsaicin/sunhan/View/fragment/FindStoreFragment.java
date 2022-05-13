package com.capsaicin.sunhan.View.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.adapter.SearchAdapter;

import java.util.ArrayList;

public class FindStoreFragment extends Fragment {

   /* private List<String> list;          // 데이터를 넣은 리스트변수
    private ListView listView;          // 검색을 보여줄 리스트변수
    private EditText editSearch; */       // 검색어를 입력할 Input 창
    private SearchAdapter adapter;      // 리스트뷰에 연결할 아답터
    private ArrayList<String> arraylist;
    private SearchView searchView;
    public static FindStoreFragment findStoreFragment;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_findstore, null);
        searchView = view.findViewById(R.id.search_view);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;

    }

    public static FindStoreFragment getInstance(){

        if(findStoreFragment==null){
            findStoreFragment = new FindStoreFragment();
        }

        return findStoreFragment;
    }
}