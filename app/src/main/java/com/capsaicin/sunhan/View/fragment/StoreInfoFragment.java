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

import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.Model.SunHanStoreDetailResponse;
import com.capsaicin.sunhan.Model.TokenResponse;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.activity.LoginActivity;
import com.capsaicin.sunhan.View.activity.StoreDetailActivity;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreInfoFragment extends Fragment { //선한 영향력가게 인포
    View view;
    public static TextView sunhan_Name;
    public static TextView sunhan_addr;
    public static TextView sunhan_phone;
    public static TextView sunhan_time;
    public static TextView sunhan_target;
    public static TextView sunhan_offer;
    public static TextView text_sunhan_offer;
    public static TextView text_sunhan_Name;
    public static TextView text_sunhan_addr;
    public static TextView text_sunhan_time;
    public static TextView text_sunhan_target;
    public static TextView text_sunhan_phone;

    ProgressDialog progressDialog;
    private RetrofitInstance tokenRetrofitInstance ;


    public static StoreInfoFragment newInstance() {
        StoreInfoFragment storeInfoFragment = new StoreInfoFragment();
        return storeInfoFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sunhanst_store_info, container, false);
        tokenRetrofitInstance = RetrofitInstance.getRetrofitInstance();//싱글톤

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("잠시만 기다려주세요");
        progressDialog.setCancelable(true);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);
        progressDialog.show();

        sunhan_Name = view.findViewById(R.id.sunhan_storeName);
        sunhan_addr = view.findViewById(R.id.sunhan_storeAddress);
        sunhan_phone = view.findViewById(R.id.sunhan_storeNum);
        sunhan_time = view.findViewById(R.id.sunhan_store_weekday);
        sunhan_target = view.findViewById(R.id.sunhan_target);
        sunhan_offer = view.findViewById(R.id.sunhan_offer);

        text_sunhan_offer = view.findViewById(R.id.text_sunhan_offer);
        text_sunhan_Name = view.findViewById(R.id.text_sunhan_name);
        text_sunhan_addr = view.findViewById(R.id.text_sunhan_address);
        text_sunhan_target = view.findViewById(R.id.text_sunhan_target);
        text_sunhan_time = view.findViewById(R.id.text_sunhan_weekday);
        text_sunhan_phone = view.findViewById(R.id.text_sunhan_phone);

        getData();

        return view;
    }
    private void getData(){
        if(tokenRetrofitInstance!=null && StoreDetailActivity.whichStore==1){ // 선한영향력 가게 정보
            Call<SunHanStoreDetailResponse> call = RetrofitInstance.getRetrofitService().getSunHansStoreDetail(StoreDetailActivity.id);
            call.enqueue(new Callback<SunHanStoreDetailResponse>() {
                @Override
                public void onResponse(Call<SunHanStoreDetailResponse> call, Response<SunHanStoreDetailResponse> response) {
                    if (response.isSuccessful()) {
                        SunHanStoreDetailResponse result = response.body();

                        text_sunhan_offer.setText("제공음식: ");
                        text_sunhan_Name.setText("가게이름: ");
                        text_sunhan_addr.setText("가게주소: ");
                        text_sunhan_target.setText("제공대상: ");
                        text_sunhan_time.setText("운영시간: ");
                        text_sunhan_phone.setText("가게번호: ");

                        sunhan_Name.setText(result.getSunHanDetailItem().getName());
                        sunhan_addr.setText(result.getSunHanDetailItem().getAddress());
                        sunhan_phone.setText(result.getSunHanDetailItem().getPhoneNumber());
                        sunhan_time.setText(result.getSunHanDetailItem().getOpeningHours());
                        sunhan_target.setText(result.getSunHanDetailItem().getTatget());
                        sunhan_offer.setText(result.getSunHanDetailItem().getOffer());
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
                public void onFailure(Call<SunHanStoreDetailResponse> call, Throwable t) {
                    Log.d("REST ERROR!", t.getMessage());
                    Toast.makeText(getContext(), "네트워크를 확인해주세요!", Toast.LENGTH_SHORT).show();
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