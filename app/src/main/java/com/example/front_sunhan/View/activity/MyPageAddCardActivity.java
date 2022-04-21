package com.example.front_sunhan.View.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front_sunhan.Model.CardItem;
import com.example.front_sunhan.R;
import com.example.front_sunhan.View.adapter.CardAdapter;

import java.util.ArrayList;

public class MyPageAddCardActivity extends AppCompatActivity {

    Button addCard;
    Toolbar toolbar;
    RecyclerView recyclerView;

    ArrayList<CardItem> cardItemArrayList = new ArrayList<>();
    static CardAdapter cardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_add_card);
        toolbar = findViewById(R.id.add_card_toolbar);
        cardAdapter = new CardAdapter(getApplicationContext(),cardItemArrayList);

        setRecyclerview();
        setData();

        addCard = findViewById(R.id.add_card_btn);
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyPageAddCardDetailActivity.class);
                startActivity(intent);
            }
        });

        setToolbar();

    }

    void setRecyclerview(){
        recyclerView = findViewById(R.id.cardRecycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(recyclerViewManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cardAdapter);

    }

   void setData(){

       cardAdapter.addItem(new CardItem("박","1264567891234","카드1","150000","19991234"));
       cardAdapter.addItem(new CardItem("박","1264567891234","카드1","150000","19991234"));
       cardAdapter.addItem(new CardItem("박","1264567891234","카드1","150000","19991234"));
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
}
