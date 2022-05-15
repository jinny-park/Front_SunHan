package com.capsaicin.sunhan.View.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.Model.SunHanStoreDetailResponse;
import com.capsaicin.sunhan.R;
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

        sunhan_Name = view.findViewById(R.id.sunhan_storeName);
        sunhan_addr = view.findViewById(R.id.sunhan_storeAddress);
        sunhan_phone = view.findViewById(R.id.sunhan_storeNum);
        sunhan_time = view.findViewById(R.id.sunhan_store_weekday);
        sunhan_target = view.findViewById(R.id.sunhan_target);
        sunhan_offer = view.findViewById(R.id.sunhan_offer);
        getData();

        return view;
    }
    private void getData(){
        if(tokenRetrofitInstance!=null && StoreDetailActivity.whichStore==1){
            Call<SunHanStoreDetailResponse> call = RetrofitInstance.getRetrofitService().getSunHansStoreDetail(StoreDetailActivity.id);
            call.enqueue(new Callback<SunHanStoreDetailResponse>() {
                @Override
                public void onResponse(Call<SunHanStoreDetailResponse> call, Response<SunHanStoreDetailResponse> response) {
                    if (response.isSuccessful()) {
                        SunHanStoreDetailResponse result = response.body();
                        sunhan_Name.setText(result.getSunHanDetailItem().getName());
                        sunhan_addr.setText(result.getSunHanDetailItem().getAddress());
                        sunhan_phone.setText(result.getSunHanDetailItem().getPhoneNumber());
                        sunhan_time.setText(result.getSunHanDetailItem().getOpeningHours());
                        sunhan_target.setText(result.getSunHanDetailItem().getTatget());
                        sunhan_offer.setText(result.getSunHanDetailItem().getOffer());
                        Log.d("성공", new Gson().toJson(response.body()));
                    } else {

                        Log.d("가맹점상세정보실패", response.message());
                    }
                }

                @Override
                public void onFailure(Call<SunHanStoreDetailResponse> call, Throwable t) {
                    Log.d("REST ERROR!", t.getMessage());
                }
            });


        }
    }

}