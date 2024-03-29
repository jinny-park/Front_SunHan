package com.capsaicin.sunhan.View.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.Model.UserDeleteResponse;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.fragment.MyPageFragment;
import com.capsaicin.sunhan.View.fragment.SunhanstSunhanFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteAccountActivity extends AppCompatActivity {

    Button delete_btn;
    Toolbar toolbar;
    private RetrofitInstance tokenRetrofitInstance ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);
        toolbar = findViewById(R.id.delete_toolbar);
        setToolbar();
        tokenRetrofitInstance=RetrofitInstance.getRetrofitInstance(); //레트로핏 싱글톤
        delete_btn = findViewById(R.id.delete_account_btn);

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LoginActivity.userAccessToken!=null){
                    if(tokenRetrofitInstance!=null){
                        requestDeleteAccount();
                    }
                }
            }
        });

    }

    private void requestDeleteAccount(){ //탈퇴요청 메소드
        Call<UserDeleteResponse> call = RetrofitInstance.getRetrofitService().deleteUser("Bearer "+LoginActivity.userAccessToken);
        call.enqueue(new Callback<UserDeleteResponse>() {
            @Override
            public void onResponse(Call<UserDeleteResponse> call, Response<UserDeleteResponse> response) {
                if (response.isSuccessful()) {
                    UserDeleteResponse result = response.body();
                    AlertDialog.Builder dlg = new AlertDialog.Builder(DeleteAccountActivity.this);
                    dlg.setMessage("탈퇴가 완료되었습니다. "); // 메시지
                    dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which) {
                            LoginActivity.userAccessToken = null;
                            LoginActivity.userRefreshToken = null;
                            MyPageFragment.userNickName.setText("로그인을 해주세요");
                            MyPageFragment.userEmail.setText("");
                            MyPageFragment.userImage.setImageResource(R.drawable.profile);
                            SunhanstSunhanFragment.category="한식";
                            //탈퇴 후 서울 시청 위도
                            LocationSettingActivity.lat=37.5666805;
                            LocationSettingActivity.lng=126.9784147;
                            finish();
                        }
                    });
                    dlg.show();
                } else {
                    Log.d("REST FAILED MESSAGE", response.message());
                }
            }

            @Override
            public void onFailure(Call<UserDeleteResponse> call, Throwable t) {
                Log.d("REST ERROR!", t.getMessage());
                Toast.makeText(getApplicationContext(), "네트워크를 확인해주세요!", Toast.LENGTH_SHORT).show();

            }
        });

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
