package com.capsaicin.sunhan.Model.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TokenRetrofitInstance {

    public static TokenRetrofitInstance retrofitInstance = null;
    public static RetrofitService retrofitService;
    // BaseUrl등록
    private static final String BASE_URL = "http://localhost:4000/api";

    private TokenRetrofitInstance(){
        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                // Json을 변환해줄 Gson변환기 등록
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitService = retrofit.create(RetrofitService.class);
    }

    public static TokenRetrofitInstance getRetrofitInstance() {
        if (retrofitInstance == null) {
            retrofitInstance = new TokenRetrofitInstance();
        }
        return retrofitInstance;
    }
    public static RetrofitService getRetrofitService(){
        return  retrofitService;
    }
}
