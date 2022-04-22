package com.example.front_sunhan.View.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front_sunhan.Model.CommunityDetailItem;
import com.example.front_sunhan.R;
import com.example.front_sunhan.View.adapter.CommunityDetailAdapter;
import com.example.front_sunhan.View.adapter.CommunityDetailCommentAdapter;

import java.util.ArrayList;

public class CommunityDetailActivity extends AppCompatActivity {
    public static CommunityDetailAdapter communityDetailAdapter ;
    public static CommunityDetailCommentAdapter communityDetailCommentAdapter ;
    ArrayList<CommunityDetailItem> dList = new ArrayList<>();
    ArrayList<CommunityDetailItem> dcList = new ArrayList<>();
    Toolbar toolbar;
    ImageView pop;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        toolbar = findViewById(R.id.commu_detail_toolbar);
        setToolbar();


        setList();
        RecyclerView recyclerView1 = findViewById(R.id.recyleView_community_detail);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(layoutManager);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setAdapter(communityDetailAdapter);

//        RecyclerView recyclerView2 = findViewById(R.id.recyleView_community_comment);
//        recyclerView2.setAdapter(communityDetailCommentAdapter);

    }

    void setToolbar(){
        setSupportActionBar (toolbar); //액티비티의 앱바(App Bar)로 지정
        ActionBar actionBar = getSupportActionBar (); //앱바 제어를 위해 툴바 액세스
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setTitle("메인툴바");
        actionBar.setDisplayHomeAsUpEnabled (true); // 앱바에 뒤로가기 버튼 만들기
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //select back button
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void setList(){
        communityDetailAdapter = new CommunityDetailAdapter(getApplicationContext(),dList);
        communityDetailCommentAdapter = new CommunityDetailCommentAdapter(getApplicationContext(),dcList);
        setData();
    }

    void setData(){
        communityDetailAdapter.addItem(new CommunityDetailItem(R.drawable.profile,"익명","저도 감자탕 좋아하는데 한번 가봐야겠네요"
                ,"03/17","14:12"));
        communityDetailAdapter.addItem(new CommunityDetailItem(R.drawable.profile,"익명","0000000000000000000000000000000000000000000000000000000"
                ,"03/17","14:12"));
        communityDetailAdapter.addItem(new CommunityDetailItem(R.drawable.profile,"익명","오옹 맛있다니 가봐야겠다"
                ,"03/17","14:12"));
        communityDetailAdapter.addItem(new CommunityDetailItem(R.drawable.profile,"익명","오옹 맛있다니 가봐야겠다"
                ,"03/17","14:12"));
        communityDetailAdapter.addItem(new CommunityDetailItem(R.drawable.profile,"익명","오옹 맛있다니 가봐야겠다"
                ,"03/17","14:12"));
        communityDetailAdapter.addItem(new CommunityDetailItem(R.drawable.profile,"익명","오옹 맛있다니 가봐야겠다"
                ,"03/17","14:12"));
        communityDetailCommentAdapter.addItem(new CommunityDetailItem(R.drawable.profile,"익명","와우"
                ,"03/17","14:12"));

    }

    void showDialog() {
        CharSequence[] oItems = {"차단하기", "수정하기", "신고하기", "답댓글 달기", "삭제하기", "취소"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("")
                        .setItems(oItems, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Toast.makeText(getApplicationContext(),
                                        oItems[which], Toast.LENGTH_LONG).show();
                            }
                        })
                        .setCancelable(false)
                        .show();
    }

}
