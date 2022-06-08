package com.capsaicin.sunhan.View.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.capsaicin.sunhan.Model.CardStoreDetailResponse;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.Model.TokenResponse;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.activity.LoginActivity;
import com.capsaicin.sunhan.View.activity.StoreDetailActivity;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChildrenStoreInfoFragment extends Fragment { //가맹점 인포
    View view;

   static public TextView storeName;
    static public TextView weekdayTime;
    static public TextView weekendTime;
    static public TextView address;
    static public TextView holidayTime;
    static public TextView phone;
    static public Bundle bundle;
    static public TextView text_name;
    static public TextView text_address;
    static public TextView text_phone;
    static public TextView text_weekday;
    static public TextView text_weekend;
    static public TextView text_holiday;
    ProgressDialog progressDialog;

    private RetrofitInstance tokenRetrofitInstance ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_children_store_info, container, false);
        bundle = getArguments();

        tokenRetrofitInstance = RetrofitInstance.getRetrofitInstance();//싱글톤
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("잠시만 기다려주세요");
        progressDialog.setCancelable(true);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);
        progressDialog.show();

        storeName = view.findViewById(R.id.children_storeName);
        weekdayTime =view.findViewById(R.id.children_store_weekday);
        weekendTime =view.findViewById(R.id.children_store_weekend);
        holidayTime =view.findViewById(R.id.children_store_holiday);
        address =view.findViewById(R.id.children_storeAddress);
        phone =view.findViewById(R.id.children_storeNum);
        text_name = view.findViewById(R.id.text_name);
        text_address = view.findViewById(R.id.text_address);
        text_phone = view.findViewById(R.id.text_phone);
        text_weekday = view.findViewById(R.id.text_weekday);
        text_weekend = view.findViewById(R.id.text_weekend);
        text_holiday = view.findViewById(R.id.text_holiday);


        getData();

        return view;
    }


    private void getData()
    { //데이터 요청 메소드
        if(tokenRetrofitInstance!=null && StoreDetailActivity.whichStore==0){
            Call<CardStoreDetailResponse> call = RetrofitInstance.getRetrofitService().getChildrenStoreDetail(StoreDetailActivity.id);
            call.enqueue(new Callback<CardStoreDetailResponse>() {
                @Override
                public void onResponse(Call<CardStoreDetailResponse> call, Response<CardStoreDetailResponse> response) {
                    if (response.isSuccessful()) {
                        CardStoreDetailResponse result = response.body();
                        text_name.setText("가게이름: ");
                        text_address.setText("가게주소: ");
                        text_phone.setText("가게번호: ");
                        text_weekday.setText("평일운영: ");
                        text_weekend.setText("주말운영: ");
                        text_holiday.setText("공휴일운영: ");
                        String weektime =result.getCardStoreItem().getWeekdayStartTime()+"-"+result.getCardStoreItem().getWeekdayEndTime();
                        String weekendtime = result.getCardStoreItem().getWeekendStartTime()+"-"+result.getCardStoreItem().getWeekendEndTime();
                        String holidaytime = result.getCardStoreItem().getHolydayStartTime()+"-"+result.getCardStoreItem().getHolydayEndTime();
                        storeName.setText(result.getCardStoreItem().getName());
                        weekdayTime.setText(weektime);
                        weekendTime.setText(weekendtime);
                        holidayTime.setText(holidaytime);
                        address.setText(result.getCardStoreItem().getAddress());
                        phone.setText(result.getCardStoreItem().getPhoneNumber());

                        progressDialog.dismiss();
                        Log.d("성공", new Gson().toJson(response.body()));
                    } else {
                        if(response.message().equals("Unauthorized")){
                            checkAuthorized();
                            getData();
                        }
                        Log.d("가맹점상세정보실패", response.message());
                    }
                }

                @Override
                public void onFailure(Call<CardStoreDetailResponse> call, Throwable t) {
                    Log.d("REST ERROR!", t.getMessage());
                    Toast.makeText(getContext(), "네트워크를 확인해주세요!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    private void checkAuthorized(){
        Call<TokenResponse> call = RetrofitInstance.getRetrofitService().getRefreshToken("Bearer "+ LoginActivity.userAccessToken,LoginActivity.userRefreshToken );
        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful()) {
                    TokenResponse result = response.body();
                    LoginActivity.userAccessToken = result.getTokenItem().getAccessToken();
                    LoginActivity.userRefreshToken = result.getTokenItem().getRefreshToken();
                    Log.d("리프레시성공", new Gson().toJson(response.body()));
                } else {
                    Log.d("리프레시토큰 실패", response.message());
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.d("REST ERROR!", t.getMessage());
            }
        });
    }
}
