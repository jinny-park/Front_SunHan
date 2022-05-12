package com.capsaicin.sunhan.Model.Retrofit;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    public static RetrofitInstance retrofitInstance;
    public static RetrofitServiceApi retrofitService;
    // BaseUrl등록
    private static final String BASE_URL = "http://10.0.2.15:4000/";

    private RetrofitInstance(){
        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                // Json을 변환해줄 Gson변환기 등록
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitService = retrofit.create(RetrofitServiceApi.class);
    }

    public static RetrofitInstance getRetrofitInstance() {
        if (retrofitInstance == null) {
            retrofitInstance = new RetrofitInstance();
        }
        return retrofitInstance;
    }
    public static RetrofitServiceApi getRetrofitService(){
        return  retrofitService;
    }

}
