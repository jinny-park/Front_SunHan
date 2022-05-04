package com.capsaicin.sunhan.Model.Retrofit;

import com.capsaicin.sunhan.Model.StoreItem;
import com.capsaicin.sunhan.Model.TokenItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface RetrofitServiceApi {

    // @GET( EndPoint-자원위치(URI) )

//    @Headers({"authorization: Bearer "})
    @GET("auth/kakao")
//    Call<TokenItem> getkakaoToken();
    Call<TokenItem> getkakaoToken(@Header("authorization") String token);
    @GET("auth/google")
    Call<TokenItem> getGoogleToken(@Header("authorization") String token);
}
