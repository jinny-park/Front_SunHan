package com.capsaicin.sunhan.Model.Retrofit;

import com.capsaicin.sunhan.Model.StoreItem;
import com.capsaicin.sunhan.Model.TokenItem;
import com.capsaicin.sunhan.Model.TokenResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface RetrofitServiceApi {

    @GET("auth/kakao")
    Call<TokenResponse> getkakaoToken(@Header("authorization") String token);
    @GET("auth/google")
    Call<TokenResponse> getGoogleToken(@Header("authorization") String token);
}
