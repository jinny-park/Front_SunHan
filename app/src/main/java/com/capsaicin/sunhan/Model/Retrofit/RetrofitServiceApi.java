package com.capsaicin.sunhan.Model.Retrofit;

import com.capsaicin.sunhan.Model.StoreItem;
import com.capsaicin.sunhan.Model.StoreResponse;
import com.capsaicin.sunhan.Model.TokenItem;
import com.capsaicin.sunhan.Model.TokenResponse;
import com.capsaicin.sunhan.Model.UserResponse;
import com.capsaicin.sunhan.Model.ErrorResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface RetrofitServiceApi {

    @GET("api/auth/kakao")
    Call<TokenResponse> getkakaoToken(@Header("authorization") String token);
    @GET("api/auth/google")
    Call<TokenResponse> getgoogleToken(@Header("authorization") String token);
    @GET("api/users")
    Call<UserResponse> getUser(@Header("authorization") String token);
//    @DELETE("users")
//    Call<DeleteRespo>

    @GET("api/sunhans/{id}")
    Call<StoreResponse> getStore(@Path("id") String id);
}
