package com.example.front_sunhan.View.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.example.front_sunhan.R;
import com.example.front_sunhan.View.adapter.ManageBlockAdapter;
import com.example.front_sunhan.View.adapter.MypageAdapter;
import com.example.front_sunhan.View.interfaceListener.OnClickBlockedItemListener;
import com.example.front_sunhan.View.interfaceListener.OnClickMyPageItemListener;

public class ManageBlockActivity extends AppCompatActivity {
    Toolbar toolbar;

    RecyclerView manageBlockRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_block);
        toolbar = findViewById(R.id.manage_block_toolbar);
        setToolbar();
        setRecyclerview();

        LoginActivity.manageBlockAdapter.setOnClickBlockedItemListener(new OnClickBlockedItemListener() {
            @Override public void onItemClick(ManageBlockAdapter.ViewHolder holder, View view, int position) {
                if (position != RecyclerView.NO_POSITION) {
                    showDialog();
                }
            }
        });
    }
    void showDialog() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(ManageBlockActivity.this).
                setMessage("차단을 해제하시겠어요?") .
                setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }) .setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }

    void setRecyclerview(){
        manageBlockRecyclerView = findViewById(R.id.recyclerview_block);
        manageBlockRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getApplicationContext());
        manageBlockRecyclerView.setLayoutManager(recyclerViewManager);
        manageBlockRecyclerView.setItemAnimator(new DefaultItemAnimator());
        manageBlockRecyclerView.setAdapter(LoginActivity.manageBlockAdapter);

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