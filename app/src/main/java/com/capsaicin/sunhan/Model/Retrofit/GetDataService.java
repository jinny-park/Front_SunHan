package com.capsaicin.sunhan.Model.Retrofit;

import com.capsaicin.sunhan.Model.StoreItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    // @GET/POST/PUT/DELETE 중에서 어떤작업인지 설정할수 있습니다.
    @GET("/stores")
    Call<ArrayList<StoreItem>> getAllStores();

}
