package com.capsaicin.sunhan.View.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.adapter.CardCheckSpinnerAdpater;

import java.util.ArrayList;
import java.util.List;

public class CardCheckActivity extends AppCompatActivity {

    Toolbar toolbar;
    private final String TAG = this.getClass().getSimpleName();

    private List<String> list = new ArrayList<>();
    private Spinner spinner;
    private CardCheckSpinnerAdpater adapter ;
    private String selectedItem;
    private int cardPosition;
    Button cardCheckBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_check);
        toolbar = findViewById(R.id.add_card_toolbar);
        cardCheckBtn = findViewById(R.id.card_check_btn);
        spinner = findViewById(R.id.spinner);

        list.add("서울");
        list.add("경기");
        list.add("울산");
        list.add("인천");
        list.add("부산");
        list.add("양산");
        list.add("공주");
        list.add("보령");
        list.add("예산");
        list.add("서천");
        list.add("부여");
        list.add("태안");
        list.add("전국푸르미카드");

        // 스피너에 붙일 어댑터 초기화
        adapter = new CardCheckSpinnerAdpater(this, list);
        spinner.setAdapter(adapter);

        // 스피너 클릭 리스너
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 어댑터에서 정의한 메서드를 통해 스피너에서 선택한 아이템의 이름을 받아온다
                selectedItem = adapter.getItem();
                switch (position){
                        case 0:
                            cardPosition = 0;
                            break;
                        case 1:
                            cardPosition = 1;
                            break;
                        case 2:
                            cardPosition = 2;
                            break;
                        case 3:
                            cardPosition = 3;
                            break;
                        case 4: case 5: case 6: case 7: case 8: case 9: case 10: case 11:
                            cardPosition = 4;
                            break;
                        case 12:
                            cardPosition = 5;
                            break;
                    }
                 // 어댑터에서 정의하는 게 귀찮다면 아래처럼 구할 수도 있다
                // getItemAtPosition()의 리턴형은 Object이므로 String 캐스팅이 필요하다
                String otherItem = (String) spinner.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });

        cardCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (cardPosition){
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

                    case 4:
                        Intent intent4 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.heemang.or.kr"));
                        startActivity(intent4);
                        finish();
                        break;
                    case 5:
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
        });

        setToolbar();

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
