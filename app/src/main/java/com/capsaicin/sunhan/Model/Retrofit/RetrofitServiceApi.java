package com.capsaicin.sunhan.Model.Retrofit;

import com.capsaicin.sunhan.Model.AddressItem;
import com.capsaicin.sunhan.Model.BlockListResponse;
import com.capsaicin.sunhan.Model.CardStoreResponse;
import com.capsaicin.sunhan.Model.ChildrenResponse;
import com.capsaicin.sunhan.Model.CardStoreDetailResponse;
import com.capsaicin.sunhan.Model.ChildrenSendLetterItem;
import com.capsaicin.sunhan.Model.ChildrenSendLetterResponse;
import com.capsaicin.sunhan.Model.CommentResponse;
import com.capsaicin.sunhan.Model.CommunityDetailResponse;
import com.capsaicin.sunhan.Model.CommunityResponse;
import com.capsaicin.sunhan.Model.CommunityWritingComment;
import com.capsaicin.sunhan.Model.CommunityWritingPost;
import com.capsaicin.sunhan.Model.CommunityWritingResponse;
import com.capsaicin.sunhan.Model.LetterResponse;
import com.capsaicin.sunhan.Model.ModifypostResponse;
import com.capsaicin.sunhan.Model.MyCommentLogsResponse;
import com.capsaicin.sunhan.Model.MyLetterLogsResponse;
import com.capsaicin.sunhan.Model.MyPostLogsResponse;
import com.capsaicin.sunhan.Model.NickNameItem;
import com.capsaicin.sunhan.Model.ProfileChangeResponse;
import com.capsaicin.sunhan.Model.ResultResponse;
import com.capsaicin.sunhan.Model.ScrapChildResponse;
import com.capsaicin.sunhan.Model.ScrapOnOffResponse;
import com.capsaicin.sunhan.Model.ScrapsSunHanResponse;
import com.capsaicin.sunhan.Model.StoreResponse;
import com.capsaicin.sunhan.Model.SunHanSendLetterItem;
import com.capsaicin.sunhan.Model.SunHanSendLetterResponse;
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
    @GET("api/users") // 유저정보 받아오기
    Call<UserResponse> getUser(@Header("authorization") String token);

    @DELETE("api/users")
    Call<UserDeleteResponse> deleteUser(@Header("authorization") String token);
    @PATCH("api/users/{id}/unblock")
    Call<ResultResponse> unBlockUser(@Header("authorization") String token, @Path("id") String id);

//    @GET("api/sunhans/{id}")
//    Call<StoreResponse> getStore(@Path("id") String storeId);

    @GET("api/children/{id}")
    Call<ChildrenResponse> getChildren(@Path("id") String storeId);

    @GET("api/reviews/{id}") //감사편지 조회
    Call<LetterResponse> getLetter(@Path("id") String id, @Query("type") String type, @Query("page") int page);
/*    @GET("api/reviews/{id}") //감사편지 조회
    Call<CardStoreLetterResponse> getLetter(@Path("id") String storeId, @Query("type") String type, @Query("page") int page);*/


    @GET("api/scraps") // 선한영향력 찜한가게
    Call<ScrapsSunHanResponse> getSunHanScraps(@Header("authorization") String token, @Query("type") String type);
    @PATCH("api/scraps/{id}")//선한영향력 찜한가게 등록하기
    Call<ScrapOnOffResponse> getSunHanScrapsOn(@Header("authorization") String token, @Path("id") String id, @Query("type") String type);
    @DELETE("api/scraps/{id}")//선한영향력 찜한가게 삭제하기
    Call<ScrapOnOffResponse> getSunHanScrapsOff(@Header("authorization") String token, @Path("id") String id, @Query("type") String type);

    @GET("api/scraps") //아동급식가맹점 찜한가게
    Call<ScrapChildResponse> getChildrenScraps(@Header("authorization") String token, @Query("type") String type);
    @PATCH("api/scraps/{id}")//아동급식가맹점 찜한가게 등록하기
    Call<ScrapOnOffResponse> getChildrenScrapsOn(@Header("authorization") String token, @Path("id") String id, @Query("type") String type);
    @DELETE("api/scraps/{id}")//아동급식가맹점 찜한가게 삭제하기
    Call<ScrapOnOffResponse> getChildrenScrapsOff(@Header("authorization") String token, @Path("id") String id, @Query("type") String type);



    @PATCH("api/users") // 닉네임 업데이트
    Call<ProfileChangeResponse> changeNickname(@Header("authorization") String token, @Body NickNameItem nickNameItem);

    @Multipart
    @PATCH("api/users") //프로필 사진 업데이트
    Call<ProfileChangeResponse> changePicture (@Header("authorization") String token, @Part MultipartBody.Part image);

    @GET("api/users/block")//유저 차단 리스트
    Call<BlockListResponse> getBlockedList (@Header("authorization") String token);

    @GET("api/users/posts") //내가 쓴 게시글
    Call<MyPostLogsResponse> getMyPosts(@Header("authorization") String token, @Query("page")int page);

    @GET("api/users/comments") //내가 쓴 댓글글
     Call<MyCommentLogsResponse> getMyComments(@Header("authorization") String token, @Query("page")int page);

    @GET("api/users/reviews") //내가 쓴 감사편지
    Call<MyLetterLogsResponse> getMyLetters(@Header("authorization") String token, @Query("page")int page);

    @POST("api/reviews") //아동급식 감사편지 쓰기
    Call<ChildrenSendLetterResponse> sendChildLetterContent(@Header("authorization") String toke, @Body ChildrenSendLetterItem letter);

    @POST("api/reviews") //선한영향력가게 감사편지 쓰기
    Call<SunHanSendLetterResponse> sendSunHanLetterContent(@Header("authorization") String toke, @Body SunHanSendLetterItem letter);

    @Multipart
    @POST("api/reviews") //감사편지 쓰기(사진 보내기)
    Call<SunHanSendLetterResponse> sendLetterImage(@Header("authorization") String token, @Part MultipartBody.Part image);

    @DELETE("api/reviews/{id}") //감사편지 삭제
    Call<ResultResponse> deleteLetter(@Header("authorization") String token, @Path("id") String id, @Query("type") String type);

    @PATCH("api/reviews/{id}/block") //감사편지 차단블록
    Call<ResultResponse> blockLetter(@Header("authorization") String token, @Path("id") String id);

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


    @GET("api/children/search")//아동급식카드 가맹점 찾기 (회원)
    Call<CardStoreResponse> getChildrenFindList (@Header("authorization") String token, @Query("name")String name, @Query("page")int page);
    @GET("api/children/search/guest")//아동급식카드 가맹점 찾기 (비회원)
    Call<CardStoreResponse> getChildrenFindListNoUser (@Query("name")String name, @Query("page")int page, @Query("lat") double lat, @Query("lng") double lng);

    @GET("api/sunhans/search")//선한가게 찾기 (회원)
    Call<StoreResponse> getSunHanFindList (@Header("authorization") String token, @Query("name")String name, @Query("page")int page);
    @GET("api/sunhans/search/guest")//선한가게 찾기 (비회원)
    Call<StoreResponse> getSunHanFindListNoUser (@Query("name")String name, @Query("page")int page, @Query("lat") double lat, @Query("lng") double lng);


    @POST("api/users/address") // 위도 경도 보내기
    Call<ResultResponse> postAddress(@Header("authorization") String token, @Body AddressItem addressItem);

    @GET("api/posts") //커뮤니티 글
    Call<CommunityResponse> getCommunityList(@Header("authorization") String token, @Query("page")int page);

    @GET("api/posts/{id}") //커뮤니티 상세화면
    Call<CommunityDetailResponse> getCommunityDetail(@Path("id") String id);

    @POST("api/posts") //커뮤니티 글쓰기
    Call<CommunityWritingResponse> writePost(@Header("authorization") String token, @Body CommunityWritingPost post);

//    @PATCH("api/posts/{id}") //커뮤니티 글 수정
//    Call<Post> modifyPost(@Path("id") String id);

//    @DELETE("api/posts/{id}") //커뮤니티 글 삭제
//    Call<PostDeleteResponse> deletePost(@Header("authorization") String token, @Path("id") String id);

    @GET("api/comments/{id}/post") //커뮤니티 댓글
    Call<CommentResponse> getCommunityCommentList(@Header("authorization") String token, @Path("id") String id, @Query("page")int page);

    @POST("api/comments/post/parent") //커뮤니티 댓글 작성
    Call<CommunityWritingResponse> writeComment(@Header("authorization") String token, @Body CommunityWritingComment post);
}