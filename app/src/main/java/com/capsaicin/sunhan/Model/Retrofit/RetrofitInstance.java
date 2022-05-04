package com.capsaicin.sunhan.Model.Retrofit;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    public static RetrofitInstance retrofitInstance;
    public static RetrofitServiceApi retrofitService;
    // BaseUrl등록
    private static final String BASE_URL = "http://192.168.219.101:4000/api/";

    private RetrofitInstance(){
        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                // Json을 변환해줄 Gson변환기 등록
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitService = retrofit.create(RetrofitServiceApi.class);
    }

    public static RetrofitInstance getRetrofitInstance() {
        if (retrofitInstance == null) {
            retrofitInstance = new RetrofitInstance();
        }
        return retrofitInstance;
    }
    public static RetrofitServiceApi getRetrofitService(){
        return  retrofitService;
    }

//        private String authToken;
//        public RetrofitInstance(String authToken) {
//            this.authToken = authToken;
//        }
//
//
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        Request newRequest  = chain.request().newBuilder()
//                                .addHeader("authorization", "Bearer "+authToken)
//                                .build();
//                        return chain.proceed(newRequest);
//                    }
//                }).build();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .client(client)
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        RetrofitServiceApi retrofitServiceApi = retrofit.create(RetrofitServiceApi.class);
//
//    }


//    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//
//    private static Retrofit.Builder builder =
//            new Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create());
//
//    private static Retrofit retrofit = builder.build();
//
//    public static <S> S createService(Class<S> serviceClass) {
//        return createService(serviceClass, null);
//    }
//
//    @SuppressLint("LongLogTag")
//    public static <S> S createService(
//            Class<S> serviceClass, final String authToken) {
//        if (!TextUtils.isEmpty(authToken)) {
//
//            Log.d("AuthenticationInterceptor", "들어감");
//            AuthenticationInterceptor interceptor =
//                    new AuthenticationInterceptor("Bearer "+authToken);
//
//            if (!httpClient.interceptors().contains(interceptor)) {
//                Log.d("intercept", "들어감");
//
//                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//                logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
//                httpClient.addInterceptor(interceptor);
//                httpClient.addInterceptor(logging);
//                builder.client(httpClient.build());
//                retrofit = builder.build();
//
//                Log.d("interceptToken", authToken);
//                //이건 httpClient 밑에
//            }
//        }
//        return retrofit.create(serviceClass);
//    }
//


}
