package com.capsaicin.sunhan.View.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.capsaicin.sunhan.Model.CardStoreDetailResponse;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.activity.StoreDetailActivity;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChildrenStoreInfoFragment extends Fragment {
    View view;

   static public TextView storeName;
    static public TextView weekdayTime;
    static public TextView weekendTime;
    static public TextView address;
    static public TextView holidayTime;
    static public TextView phone;
    static public Bundle bundle;
    private RetrofitInstance tokenRetrofitInstance ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_children_store_info, container, false);
        bundle = getArguments();

        tokenRetrofitInstance = RetrofitInstance.getRetrofitInstance();//싱글톤

        storeName = view.findViewById(R.id.children_storeName);
        weekdayTime =view.findViewById(R.id.children_store_weekday);
        weekendTime =view.findViewById(R.id.children_store_weekend);
        holidayTime =view.findViewById(R.id.children_store_holiday);
        address =view.findViewById(R.id.children_storeAddress);
        phone =view.findViewById(R.id.children_storeNum);

        getData();

        return view;
    }


    private void getData()
    {
        if(tokenRetrofitInstance!=null && StoreDetailActivity.whichStore==0){
            Call<CardStoreDetailResponse> call = RetrofitInstance.getRetrofitService().getChildrenStoreDetail(StoreDetailActivity.id);
            call.enqueue(new Callback<CardStoreDetailResponse>() {
                @Override
                public void onResponse(Call<CardStoreDetailResponse> call, Response<CardStoreDetailResponse> response) {
                    if (response.isSuccessful()) {
                        CardStoreDetailResponse result = response.body();
                        String weektime =result.getCardStoreItem().getWeekdayStartTime()+"-"+result.getCardStoreItem().getWeekdayEndTime();
                        String weekendtime = result.getCardStoreItem().getWeekendStartTime()+"-"+result.getCardStoreItem().getWeekendEndTime();
                        String holidaytime = result.getCardStoreItem().getHolydayStartTime()+"-"+result.getCardStoreItem().getHolydayEndTime();
                        storeName.setText(result.getCardStoreItem().getName());
                        weekdayTime.setText(weektime);
                        weekendTime.setText(weekendtime);
                        holidayTime.setText(holidaytime);
                        address.setText(result.getCardStoreItem().getAddress());
                        phone.setText(result.getCardStoreItem().getPhoneNumber());
                        Log.d("성공", new Gson().toJson(response.body()));
                    } else {

                        Log.d("가맹점상세정보실패", response.message());
                    }
                }

                @Override
                public void onFailure(Call<CardStoreDetailResponse> call, Throwable t) {
                    Log.d("REST ERROR!", t.getMessage());
                }
            });
        }
    }

}
