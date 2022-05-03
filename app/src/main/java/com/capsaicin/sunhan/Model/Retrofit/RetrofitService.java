package com.capsaicin.sunhan.Model.Retrofit;

import com.capsaicin.sunhan.Model.StoreItem;
import com.capsaicin.sunhan.Model.TokenItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitService {

    // @GET( EndPoint-자원위치(URI) )
    @GET("posts/{token}")
    Call<TokenItem> getToken(@Path("token") String token);

}
