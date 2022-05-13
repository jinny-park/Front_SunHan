package com.capsaicin.sunhan.Model.Retrofit;

import com.capsaicin.sunhan.Model.AddressItem;
import com.capsaicin.sunhan.Model.BlockListResponse;
import com.capsaicin.sunhan.Model.CardStoreResponse;
import com.capsaicin.sunhan.Model.ChildrenResponse;
import com.capsaicin.sunhan.Model.CommunityResponse;
import com.capsaicin.sunhan.Model.LetterResponse;
import com.capsaicin.sunhan.Model.NickNameItem;
import com.capsaicin.sunhan.Model.ProfileChangeResponse;
import com.capsaicin.sunhan.Model.ResultResponse;
import com.capsaicin.sunhan.Model.StoreResponse;
import com.capsaicin.sunhan.Model.TokenResponse;
import com.capsaicin.sunhan.Model.UserDeleteResponse;
import com.capsaicin.sunhan.Model.UserResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitServiceApi {

    @GET("api/auth/kakao")
    Call<TokenResponse> getkakaoToken(@Header("authorization") String token);
    @GET("api/auth/google")
    Call<TokenResponse> getgoogleToken(@Header("authorization") String token);
    @GET("api/users")
    Call<UserResponse> getUser(@Header("authorization") String token);

    @DELETE("users")
    Call<UserDeleteResponse> deleteUser(@Header("authorization") String token);

    @GET("api/sunhans/{id}")
    Call<StoreResponse> getStore(@Path("id") String storeId);

    @GET("api/children/{id}")
    Call<ChildrenResponse> getChildren(@Path("id") String storeId);

    @GET("api/reviews/{id}")
    Call<LetterResponse> getLetter(@Path("id") String storeId);
    /*@GET("api/reviews")
    Call<LetterResponse> getLetter(@Header("authorization") String token);*/


    @PATCH("api/users")
    Call<ProfileChangeResponse> changeNickname(@Header("authorization") String token, @Body NickNameItem nickNameItem);

    @Multipart
    @PATCH("api/users") //프로필 사진 업데이트
    Call<ProfileChangeResponse> changePicture (@Header("authorization") String token, @Part MultipartBody.Part image);

    @GET("api/users/block")//유저 차단 리스트
    Call<BlockListResponse> getBlockedList (@Header("authorization") String token);

    @GET("api/children")//가맹점 거리순 리스트(회원전용)
    Call<CardStoreResponse> getChildrenStoreList (@Header("authorization") String token, @Query("page")int page, @Query("sort") String sort);

    @POST("api/users/address")
    Call<ResultResponse> postAddress(@Header("authorization") String token, @Body AddressItem addressItem);

    @GET("api/posts/{id}")
    Call<CommunityResponse> getCommunity(@Path("id") String commuId); //수정해야함
}