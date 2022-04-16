package com.example.front_sunhan.View.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front_sunhan.Model.CommunityItem;
import com.example.front_sunhan.R;
import com.example.front_sunhan.View.adapter.CommunityAdapter;

import java.util.Arrays;
import java.util.List;

public class CommunityActivity extends AppCompatActivity {
    private CommunityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_community);

        init();

        getData();
    }

    private void init() {
        RecyclerView recyclerView = findViewById(R.id.communityrecyleView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new CommunityAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void getData() {
        // 임의의 데이터입니다.
        List<Integer> listProfile = Arrays.asList(
                R.drawable.profile,
                R.drawable.profile,
                R.drawable.profile
        );
        List<String> listNickname = Arrays.asList("익명", "익명", "익명");
        List<String> listWritetime = Arrays.asList("12:10", "12:11", "12:12");
        List<String> listContent = Arrays.asList(
                "맛있어요",
                "추천합니다",
                "좋은 가게입니다"
        );
        List<Integer> listWritenum = Arrays.asList(0,1,2);

        for (int i = 0; i < listContent.size(); i++) {
            // 각 List의 값들을 data 객체에 set 해줍니다.
            CommunityItem data = new CommunityItem();
            data.setProfile(listProfile.get(i));
            data.setNickname(listNickname.get(i));
            data.setWritetime(listWritetime.get(i));
            data.setContent(listContent.get(i));
            data.setWritenum(listWritenum.get(i));

            // 각 값이 들어간 data를 adapter에 추가합니다.
            adapter.addItem(data);
        }

        // adapter의 값이 변경되었다는 것을 알려줍니다.
        adapter.notifyDataSetChanged();
    }
}
