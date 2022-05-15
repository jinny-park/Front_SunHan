package com.capsaicin.sunhan.View.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
    public static String name = "치킨";

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
        /*searchView = view.findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // onQueryTextSubmit의  query에는 빈값이나 null을 받아들이지 X
                // 그래서 텍스트 입력 변경시 공백인 경우 다시 onQueryTextSubmit 를 호출하면서 인자로 빈공백을 넣어쥼
                if(newText.equals("")){
                    this.onQueryTextSubmit("");
                }
                return false;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

         */

        EditText findStore=(EditText)view.findViewById(R.id.search_view);
        String name=findStore.getText().toString();

        findChildrenResultFragment = new FindChildrenResultFragment();
        findSunhanResultFragment = new FindSunhanResultFragment();

      getChildFragmentManager().beginTransaction().replace(R.id.tabs_find_container,findChildrenResultFragment).commit();

        TabLayout tabs1 = view.findViewById(R.id.findstore_tapLayout);
        tabs1.addTab(tabs1.newTab().setText("아동급식가맹점"));
        tabs1.addTab(tabs1.newTab().setText("선한영향력가게"));

        tabs1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab1) {
                int position = tab1.getPosition();

                Fragment selected = null;
                if(position == 0 ){
                    selected = findChildrenResultFragment;
                }
                else {
                    selected = findSunhanResultFragment;
                }
                getChildFragmentManager().beginTransaction().replace(R.id.tabs_find_container, selected).commit();
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


    /*private String getResult(){
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<items.size(); i++){
            String item = items.get(i);
            if(i==items.size() -1) {
                sb.append(item + '\n');
            } else{
                sb.append(item);
                sb.append("\n");
            }
        }
        retrun sb.toString();
    }*/

}