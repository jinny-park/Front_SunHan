package com.capsaicin.sunhan.View.fragment;

import static com.capsaicin.sunhan.View.activity.LocationSettingActivity.lat;
import static com.capsaicin.sunhan.View.activity.LocationSettingActivity.lng;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.Model.StoreItem;
import com.capsaicin.sunhan.Model.StoreResponse;
import com.capsaicin.sunhan.Model.TokenResponse;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.activity.LoginActivity;
import com.capsaicin.sunhan.View.activity.StoreDetailActivity;
import com.capsaicin.sunhan.View.adapter.SunhanStoreAdapter;
import com.capsaicin.sunhan.View.interfaceListener.OnClickStoreItemListener;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindSunhanResultFragment extends Fragment {
    ArrayList<StoreItem> storeList=new ArrayList<StoreItem>();
    RecyclerView sunhansSunhanRecyclerView;
    SunhanStoreAdapter storeAdapter;
    ProgressBar progressBar;
    private RetrofitInstance tokenRetrofitInstance ;
    int page;
    public static String name;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_find_sunhan_result,null);
        tokenRetrofitInstance=RetrofitInstance.getRetrofitInstance(); //레트로핏 싱글톤
        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        page = 1;

        sunhansSunhanRecyclerView = view.findViewById(R.id.find_sunhan_recyclerView);
        sunhansSunhanRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
        sunhansSunhanRecyclerView.setLayoutManager(recyclerViewManager);
        sunhansSunhanRecyclerView.setItemAnimator(new DefaultItemAnimator());


        ImageButton button=(ImageButton) view.findViewById(R.id.btn_search);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText findStore=(EditText)view.findViewById(R.id.search_view);
                name=findStore.getText().toString();
                Log.d("name","검색어는 "+name);
                initData(0);

            }
        });

        sunhansSunhanRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = ((LinearLayoutManager) sunhansSunhanRecyclerView.getLayoutManager()).
                        findLastCompletelyVisibleItemPosition();
                int itemTotalCount = sunhansSunhanRecyclerView.getAdapter().getItemCount() - 1;
                if(lastVisibleItemPosition == itemTotalCount) {
                    progressBar.setVisibility(View.VISIBLE);
                    getData(page);
                    page++;
                }
            }
        });

        return view;

    }

    private void initData(int page)
    {
        if(LoginActivity.userAccessToken!=null){
            if(tokenRetrofitInstance!=null){
                Call<StoreResponse> call = RetrofitInstance.getRetrofitService().getSunHanFindList("Bearer "+LoginActivity.userAccessToken,name, page);
                call.enqueue(new Callback<StoreResponse>() {
                    @Override
                    public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                        if (response.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            StoreResponse result = response.body();
                            storeAdapter = new SunhanStoreAdapter(getActivity(),result.getData());
                            sunhansSunhanRecyclerView.setAdapter(storeAdapter);
                            storeAdapter.setOnClickStoreItemListener(new OnClickStoreItemListener() {
                                @Override
                                public void onItemClick(SunhanStoreAdapter.ViewHolder holder, View view, int position) {
                                    String str_position = String.valueOf(position+1);
                                    if(position!=RecyclerView.NO_POSITION){
                                        Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
                                        intent.putExtra("_id", storeAdapter.getItem(position).get_id());
                                        intent.putExtra("whichStore", 1);
                                        Log.d("아이디", storeAdapter.getItem(position).get_id());

                                        startActivity(intent);
                                    }
                                }
                            });
                            Log.d("성공", new Gson().toJson(response.body()));
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Log.d("REST FAILED MESSAGE", response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<StoreResponse> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("REST ERROR!", t.getMessage());
                        Toast.makeText(getContext(), "네트워크를 확인해주세요!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        } else if(LoginActivity.userAccessToken==null){
            if(tokenRetrofitInstance!=null){
                Log.d("선한프래그먼트", "토큰인스턴스이후 콜백 전");
                Call<StoreResponse> call = RetrofitInstance.getRetrofitService().getSunHanFindListNoUser(name, page, lat, lng);
                call.enqueue(new Callback<StoreResponse>() {
                    @Override
                    public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                        if (response.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            StoreResponse result = response.body();
                            storeAdapter = new SunhanStoreAdapter(getActivity(),result.getData());
                            sunhansSunhanRecyclerView.setAdapter(storeAdapter);
                            storeAdapter.setOnClickStoreItemListener(new OnClickStoreItemListener() {
                                @Override
                                public void onItemClick(SunhanStoreAdapter.ViewHolder holder, View view, int position) {
                                    String str_position = String.valueOf(position+1);
                                    if(position!=RecyclerView.NO_POSITION){
                                        Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
                                        intent.putExtra("_id", storeAdapter.getItem(position).get_id());
                                        intent.putExtra("whichStore", 1);
                                        Log.d("아이디", storeAdapter.getItem(position).get_id());
                                        startActivity(intent);
                                    }
                                }
                            });
                            Log.d("성공", new Gson().toJson(response.body()));
                        } else {
                            progressBar.setVisibility(View.GONE);
                            if(response.message().equals("Unauthorized")){
                                checkAuthorized();
                                initData(0);
                            }
                            Log.d("REST FAILED MESSAGE", response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<StoreResponse> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("REST ERROR!", t.getMessage());
                        Toast.makeText(getContext(), "네트워크를 확인해주세요!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }


    private void getData(int page)
    {
        if(LoginActivity.userAccessToken!=null){
            if(tokenRetrofitInstance!=null){
                Log.d("선한프래그먼트", "토큰인스턴스이후 콜백 전");
                Call<StoreResponse> call = RetrofitInstance.getRetrofitService().getSunHanFindList("Bearer "+LoginActivity.userAccessToken,name, page);
                call.enqueue(new Callback<StoreResponse>() {
                    @Override
                    public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                        if (response.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            StoreResponse result = response.body();
                            storeAdapter.addList(result.getData());
                            storeAdapter.setOnClickStoreItemListener(new OnClickStoreItemListener() {
                                @Override
                                public void onItemClick(SunhanStoreAdapter.ViewHolder holder, View view, int position) {
                                    String str_position = String.valueOf(position+1);
                                    if(position!=RecyclerView.NO_POSITION){
                                        Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
                                        intent.putExtra("_id", storeAdapter.getItem(position).get_id());
                                        intent.putExtra("whichStore", 1);
                                        Log.d("아이디", storeAdapter.getItem(position).get_id());

                                        startActivity(intent);
                                    }
                                }
                            });
                            Log.d("성공", new Gson().toJson(response.body()));
                        } else {
                            progressBar.setVisibility(View.GONE);
                            if(response.message().equals("Unauthorized")){
                                checkAuthorized();
                                getData(page);
                            }
                            Log.d("REST FAILED MESSAGE", response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<StoreResponse> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("REST ERROR!", t.getMessage());
                        Toast.makeText(getContext(), "네트워크를 확인해주세요!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        } else if(LoginActivity.userAccessToken==null){
            if(tokenRetrofitInstance!=null){
                Log.d("선한프래그먼트", "토큰인스턴스이후 콜백 전");
                Call<StoreResponse> call = RetrofitInstance.getRetrofitService().getSunHanFindListNoUser(name, page, lat, lng);
                call.enqueue(new Callback<StoreResponse>() {
                    @Override
                    public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                        if (response.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            StoreResponse result = response.body();
                            storeAdapter.addList(result.getData());
                            storeAdapter.setOnClickStoreItemListener(new OnClickStoreItemListener() {
                                @Override
                                public void onItemClick(SunhanStoreAdapter.ViewHolder holder, View view, int position) {
                                    String str_position = String.valueOf(position+1);
                                    if(position!=RecyclerView.NO_POSITION){
                                        Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
                                        intent.putExtra("_id", storeAdapter.getItem(position).get_id());
                                        intent.putExtra("whichStore", 1);
                                        Log.d("아이디", storeAdapter.getItem(position).get_id());
                                        startActivity(intent);
                                    }
                                }
                            });
                            Log.d("성공", new Gson().toJson(response.body()));
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Log.d("REST FAILED MESSAGE", response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<StoreResponse> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("REST ERROR!", t.getMessage());
                        Toast.makeText(getContext(), "네트워크를 확인해주세요!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

    private void checkAuthorized(){
        Call<TokenResponse> call = RetrofitInstance.getRetrofitService().getRefreshToken("Bearer "+LoginActivity.userAccessToken,LoginActivity.userRefreshToken );
        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful()) {
                    TokenResponse result = response.body();
                    LoginActivity.userAccessToken = result.getTokenItem().getAccessToken();
                    LoginActivity.userRefreshToken = result.getTokenItem().getRefreshToken();
                    Log.d("리프레시성공", new Gson().toJson(response.body()));
                } else {
                    Log.d("리프레시토큰 실패", response.message());
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.d("REST ERROR!", t.getMessage());
            }
        });
    }
}