package com.example.front_sunhan.View.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front_sunhan.Model.CardCheckItem;
import com.example.front_sunhan.R;
import com.example.front_sunhan.View.adapter.CardCheckAdapter;
import com.example.front_sunhan.View.adapter.MypageAdapter;
import com.example.front_sunhan.View.interfaceListener.OnClickCardCheckListener;
import com.example.front_sunhan.View.interfaceListener.OnClickMyPageItemListener;

import java.util.ArrayList;

public class CardCheckActivity extends AppCompatActivity {

    Button addCard;
    Toolbar toolbar;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_check);
        toolbar = findViewById(R.id.add_card_toolbar);
        setRecyclerview();

        LoginActivity.cardCheckAdapter.setOnClickCardCheckListener(new OnClickCardCheckListener() {
            @Override public void onItemClick(CardCheckAdapter.ViewHolder holder, View view, int position) {
                if (position != RecyclerView.NO_POSITION) {
                    switch (position){
                        case 0:
                            Intent intent0 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.shinhancard.com/mob/MOBFM064N/MOBFM064R01.shc?crustMenuId=ms254"));
                            startActivity(intent0);
                            finish();
                            break;
                        case 1:
                            Intent intent1 =new Intent(Intent.ACTION_VIEW, Uri.parse("https://gdream.gg.go.kr/"));
                            startActivity(intent1);
                            finish();
                            break;
                        case 2:
                            Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://ulsan.nhdream.co.kr/"));
                            startActivity(intent2);
                            finish();
                            break;
                        case 3:
                            Intent intent3 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.purmeecard.com/index2.jsp"));
                            startActivity(intent3);
                            finish();
                            break;

                        case 4: case 5: case 6: case 7: case 8: case 9: case 10: case 11:
                            Intent intent4 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.heemang.or.kr"));
                            startActivity(intent4);
                            finish();
                            break;
                        case 12:
                            Intent intent5 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.purmeekorea.co.kr"));
                            startActivity(intent5);
                            finish();
                            break;

                        default:
                            Intent intent6 = new Intent(getApplicationContext(),CardCheckActivity.class);
                            startActivity(intent6);
                            finish();
                            break;
                    }

                }
            }
        });


        setToolbar();

    }

    void setRecyclerview(){
        recyclerView = findViewById(R.id.cardRecycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclerViewManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(recyclerViewManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(LoginActivity.cardCheckAdapter);

    }

    void setToolbar(){
        setSupportActionBar (toolbar); //액티비티의 앱바(App Bar)로 지정
        ActionBar actionBar = getSupportActionBar (); //앱바 제어를 위해 툴바 액세스
        actionBar.setDisplayShowTitleEnabled(false);
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
