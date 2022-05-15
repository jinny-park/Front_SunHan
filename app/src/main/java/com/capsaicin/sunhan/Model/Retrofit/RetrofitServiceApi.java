package com.capsaicin.sunhan.Model.Retrofit;

import com.capsaicin.sunhan.Model.AddressItem;
import com.capsaicin.sunhan.Model.BlockListResponse;
import com.capsaicin.sunhan.Model.CardStoreResponse;
import com.capsaicin.sunhan.Model.ChildrenResponse;
import com.capsaicin.sunhan.Model.CardStoreDetailResponse;
import com.capsaicin.sunhan.Model.CommunityDetailResponse;
import com.capsaicin.sunhan.Model.CommunityResponse;
import com.capsaicin.sunhan.Model.LetterResponse;
import com.capsaicin.sunhan.Model.NickNameItem;
import com.capsaicin.sunhan.Model.ProfileChangeResponse;
import com.capsaicin.sunhan.Model.ResultResponse;
import com.capsaicin.sunhan.Model.ScrapChildResponse;
import com.capsaicin.sunhan.Model.ScrapsSunHanResponse;
import com.capsaicin.sunhan.Model.StoreResponse;
import com.capsaicin.sunhan.Model.SunHanStoreDetailResponse;
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

    @DELETE("api/users")
    Call<UserDeleteResponse> deleteUser(@Header("authorization") String token);

//    @GET("api/sunhans/{id}")
//    Call<StoreResponse> getStore(@Path("id") String storeId);

    @GET("api/children/{id}")
    Call<ChildrenResponse> getChildren(@Path("id") String storeId);

    @GET("api/reviews/{id}")
    Call<LetterResponse> getLetter(@Path("id") String storeId);
    /*@GET("api/reviews")
    Call<LetterResponse> getLetter(@Header("authorization") String token);*/

    @GET("api/scraps") // 선한영향력 찜한가게
    Call<ScrapsSunHanResponse> getSunHanScraps(@Header("authorization") String token, @Query("type") String type);

    @GET("api/scraps") //가맹점 찜한가게
    Call<ScrapChildResponse> getChildrenScraps(@Header("authorization") String token, @Query("type") String type);

    @PATCH("api/users")
    Call<ProfileChangeResponse> changeNickname(@Header("authorization") String token, @Body NickNameItem nickNameItem);

    @Multipart
    @PATCH("api/users") //프로필 사진 업데이트
    Call<ProfileChangeResponse> changePicture (@Header("authorization") String token, @Part MultipartBody.Part image);

    @GET("api/users/block")//유저 차단 리스트
    Call<BlockListResponse> getBlockedList (@Header("authorization") String token);

    @GET("api/children")//가맹점 거리순 리스트(회원전용)
    Call<CardStoreResponse> getChildrenStoreList (@Header("authorization") String token, @Query("page")int page, @Query("sort") String sort);

    @GET("api/children/guest")//가맹점 거리순 리스트(비회원 전용)
    Call<CardStoreResponse> getChildrenStoreListNoUser (@Query("page")int page, @Query("sort") String sort, @Query("lat") double lat, @Query("lng") double lng);

    @GET("api/children/{id}") //가맹점 상세정보(회원 비회원 둘다)
    Call<CardStoreDetailResponse> getChildrenStoreDetail(@Path("id") String id);

    @GET("api/sunhans/{id}") //선한영향력 상세정보(회원 비회원 둘다)
    Call<SunHanStoreDetailResponse> getSunHansStoreDetail(@Path("id") String id);

    @GET("api/sunhans")//선한영향력가게 거리순 리스트(회원전용)
    Call<StoreResponse> getSunHanStoreList (@Header("authorization") String token, @Query("page")int page,@Query("category") String category ,@Query("sort") String sort);

    @GET("api/sunhans/guest")//선한영향력 거리순 리스트(비회원 전용)
    Call<StoreResponse> getSunHanStoreListNoUser (@Query("page")int page, @Query("category") String category, @Query("sort") String sort, @Query("lat") double lat, @Query("lng") double lng);

    @POST("api/users/address")
    Call<ResultResponse> postAddress(@Header("authorization") String token, @Body AddressItem addressItem);

    @GET("api/posts")
    Call<CommunityResponse> getCommunityList(@Header("authorization") String token, @Query("page")int page);

    @GET("api/comments")
    Call<CommunityDetailResponse> getCommunityDetailList(@Header("authorization") String token, @Query("page")int page);
}