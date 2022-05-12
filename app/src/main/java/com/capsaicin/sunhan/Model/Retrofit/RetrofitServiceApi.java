package com.capsaicin.sunhan.Model.Retrofit;

import com.capsaicin.sunhan.Model.ChildrenResponse;
import com.capsaicin.sunhan.Model.CommunityResponse;
import com.capsaicin.sunhan.Model.LetterResponse;
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
    Call<StoreResponse> getStore(@Path("id") String storeId);

    @GET("api/children/{id}")
    Call<ChildrenResponse> getChildren(@Path("id") String storeId);

    @GET("api/reviews/{id}")
    Call<LetterResponse> getLetter(@Path("id") String storeId);
    /*@GET("api/reviews")
    Call<LetterResponse> getLetter(@Header("authorization") String token);*/

    @GET("api/posts/{id}")
    Call<CommunityResponse> getCommunity(@Path("id") String commuId); //수정해야함
}
