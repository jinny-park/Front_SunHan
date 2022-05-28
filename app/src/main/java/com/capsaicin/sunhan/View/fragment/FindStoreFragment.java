package com.capsaicin.sunhan.View.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.adapter.SearchAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class FindStoreFragment extends Fragment {

   /* private List<String> list;          // 데이터를 넣은 리스트변수
    private ListView listView;          // 검색을 보여줄 리스트변수
    private EditText editSearch; */       // 검색어를 입력할 Input 창
    private SearchAdapter adapter;      // 리스트뷰에 연결할 아답터
    private ArrayList<String> arraylist;
    private SearchView searchView;

    private FindChildrenResultFragment findChildrenResultFragment;
    private FindSunhanResultFragment findSunhanResultFragment;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_findstore, null);

        findChildrenResultFragment = new FindChildrenResultFragment();
        getChildFragmentManager().beginTransaction().add(R.id.tabs_find_container,findChildrenResultFragment).commit();

        TabLayout tabs1 = view.findViewById(R.id.findstore_tapLayout);
        tabs1.addTab(tabs1.newTab().setText("아동급식가맹점"));
        tabs1.addTab(tabs1.newTab().setText("선한영향력가게"));

        tabs1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab1) {
                int position = tab1.getPosition();
                if(position == 0 ){
                    screenChange(findChildrenResultFragment);
                }
                else {
                    if(findSunhanResultFragment==null){
                        findSunhanResultFragment = new FindSunhanResultFragment();
                        getChildFragmentManager().beginTransaction().add(R.id.tabs_find_container, findSunhanResultFragment).commit();
                    }
                    screenChange(findSunhanResultFragment);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab1) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab1) {

            }
        });

        return view;
    }

    private void screenChange(Fragment fragment){
        allHideScreens();
        if(fragment!=null)
            getChildFragmentManager().beginTransaction().show(fragment).commit();
    }

    private void allHideScreens(){
        if(findChildrenResultFragment!=null)
            getChildFragmentManager().beginTransaction().hide(findChildrenResultFragment).commit();
        if(findSunhanResultFragment!=null)
            getChildFragmentManager().beginTransaction().hide(findSunhanResultFragment).commit();
    }
}