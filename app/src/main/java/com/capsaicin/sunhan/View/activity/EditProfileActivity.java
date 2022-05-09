package com.capsaicin.sunhan.View.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.fragment.MyPageFragment;

public class EditProfileActivity extends AppCompatActivity {

    Toolbar toolbar;
    CardView profile;
    ImageView profile_img;
    String imageUri;
    Intent intent;
    TextView edit_profile_name;
    Button edit_profile_btn;
    MyPageFragment myPageFragment = new MyPageFragment();
    private static final int REQUEST_CODE = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        toolbar = findViewById(R.id.edit_toolbar);
        profile = findViewById(R.id.edit_profile_pic);
        profile_img = findViewById(R.id.edit_user_profile);
        edit_profile_name = findViewById(R.id.edit_profile_name);
        edit_profile_btn = findViewById(R.id.edit_profile_btn);

        intent = getIntent();
        edit_profile_name.setText(intent.getStringExtra("nickName"));
        imageUri = intent.getStringExtra("profilePic");
        Uri uri = Uri.parse(imageUri);
        Glide.with(getApplicationContext()).load(uri).into(profile_img);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        edit_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().add(R.id.main_frame,myPageFragment).addToBackStack(null).commit();
            }
        });

        setToolbar();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE){
            if(resultCode==RESULT_OK){
                try {
                    Uri uri = data.getData();
                    Glide.with(getApplicationContext()).load(uri).into(profile_img);
                }catch (Exception e){
                    finish();
                }
            }else if(resultCode==RESULT_CANCELED){
                finish();
            }
        }
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
