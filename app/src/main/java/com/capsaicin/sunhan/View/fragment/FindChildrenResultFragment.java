package com.capsaicin.sunhan.View.fragment;

import static com.capsaicin.sunhan.View.activity.LocationSettingActivity.lat;
import static com.capsaicin.sunhan.View.activity.LocationSettingActivity.lng;

import android.content.Intent;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capsaicin.sunhan.Model.CardStoreItem;
import com.capsaicin.sunhan.Model.CardStoreResponse;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.activity.LoginActivity;
import com.capsaicin.sunhan.View.activity.StoreDetailActivity;
import com.capsaicin.sunhan.View.adapter.CardStoreAdapter;
import com.capsaicin.sunhan.View.interfaceListener.OnClickCardStoreItemListener;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindChildrenResultFragment extends Fragment {
    ArrayList<CardStoreItem> cardStoreList=new ArrayList();
    RecyclerView sunhanCardRecyclerView;
    ProgressBar progressBar;
    private RetrofitInstance tokenRetrofitInstance ;
    CardStoreAdapter cardStoreAdapter;  /*new CardStoreAdapter(getActivity(),cardStoreList) ;*/
    int page;
    CardStoreResponse cardStoreResponse;
    public static String name;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_find_children_result,null);
        tokenRetrofitInstance=RetrofitInstance.getRetrofitInstance(); //레트로핏 싱글톤
        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        page = 1;

        //리사이클러뷰 설정
        sunhanCardRecyclerView = view.findViewById(R.id.find_children_recyclerView);
        sunhanCardRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
        sunhanCardRecyclerView.setLayoutManager(recyclerViewManager);
        sunhanCardRecyclerView.setItemAnimator(new DefaultItemAnimator());

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

        sunhanCardRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = ((LinearLayoutManager) sunhanCardRecyclerView.getLayoutManager()).
                        findLastCompletelyVisibleItemPosition();
                int itemTotalCount = sunhanCardRecyclerView.getAdapter().getItemCount() - 1;
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
                Log.d("카드프래그먼트", "토큰인스턴스이후 콜백 전");
                Call<CardStoreResponse> call = RetrofitInstance.getRetrofitService().getChildrenFindList("Bearer "+LoginActivity.userAccessToken,name, page);
                call.enqueue(new Callback<CardStoreResponse>() {
                    @Override
                    public void onResponse(Call<CardStoreResponse> call, Response<CardStoreResponse> response) {
                        if (response.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            cardStoreResponse = response.body();
                            cardStoreAdapter = new CardStoreAdapter(getActivity(),cardStoreResponse.getData());
                            sunhanCardRecyclerView.setAdapter(cardStoreAdapter);
                            cardStoreAdapter.setOnClickCardStoreItemListener(new OnClickCardStoreItemListener() {
                                @Override
                                public void onItemClick(CardStoreAdapter.ViewHolder holder, View view, int position) {
                                    String str_position = String.valueOf(position+1);
                                    if(position!=RecyclerView.NO_POSITION){
                                        Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
                                        intent.putExtra("_id", cardStoreAdapter.getItem(position).get_id());
                                        intent.putExtra("whichStore", 0);
                                        Log.d("아이디", cardStoreAdapter.getItem(position).get_id());

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
                    public void onFailure(Call<CardStoreResponse> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("REST ERROR!", t.getMessage());
                        Toast.makeText(getContext(), "네트워크를 확인해주세요!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        } else if (LoginActivity.userAccessToken==null){
            if(tokenRetrofitInstance!=null){
                Log.d("카드프래그먼트", "토큰인스턴스이후 콜백 전");
                Call<CardStoreResponse> call = RetrofitInstance.getRetrofitService().getChildrenFindListNoUser(name, page, lat, lng);
                call.enqueue(new Callback<CardStoreResponse>() {
                    @Override
                    public void onResponse(Call<CardStoreResponse> call, Response<CardStoreResponse> response) {
                        if (response.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            cardStoreResponse = response.body();
                            cardStoreAdapter = new CardStoreAdapter(getActivity(),cardStoreResponse.getData());
                            sunhanCardRecyclerView.setAdapter(cardStoreAdapter);
                            cardStoreAdapter.setOnClickCardStoreItemListener(new OnClickCardStoreItemListener() {
                                @Override
                                public void onItemClick(CardStoreAdapter.ViewHolder holder, View view, int position) {
                                    String str_position = String.valueOf(position+1);
                                    if(position!=RecyclerView.NO_POSITION){
                                        Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
                                        intent.putExtra("_id", cardStoreAdapter.getItem(position).get_id());
                                        intent.putExtra("whichStore", 0);
                                        Log.d("아이디", cardStoreAdapter.getItem(position).get_id());

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
                    public void onFailure(Call<CardStoreResponse> call, Throwable t) {
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
                Log.d("카드프래그먼트", "토큰인스턴스이후 콜백 전");
                Call<CardStoreResponse> call = RetrofitInstance.getRetrofitService().getChildrenFindList("Bearer "+LoginActivity.userAccessToken,name,page);
                call.enqueue(new Callback<CardStoreResponse>() {
                    @Override
                    public void onResponse(Call<CardStoreResponse> call, Response<CardStoreResponse> response) {
                        if (response.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            CardStoreResponse result = response.body();
                            cardStoreAdapter.addList(result.getData());
                            cardStoreAdapter.setOnClickCardStoreItemListener(new OnClickCardStoreItemListener() {
                                @Override
                                public void onItemClick(CardStoreAdapter.ViewHolder holder, View view, int position) {
                                    String str_position = String.valueOf(position+1);
                                    if(position!=RecyclerView.NO_POSITION){
                                        Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
                                        intent.putExtra("_id", cardStoreAdapter.getItem(position).get_id());
                                        intent.putExtra("whichStore", 0);
                                        Log.d("아이디", cardStoreAdapter.getItem(position).get_id());
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
                    public void onFailure(Call<CardStoreResponse> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("REST ERROR!", t.getMessage());
                        Toast.makeText(getContext(), "네트워크를 확인해주세요!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        } else if (LoginActivity.userAccessToken==null){
            if(tokenRetrofitInstance!=null){
                Log.d("카드프래그먼트", "토큰인스턴스이후 콜백 전");
                Call<CardStoreResponse> call = RetrofitInstance.getRetrofitService().getChildrenFindListNoUser(name, page, lat, lng);
                call.enqueue(new Callback<CardStoreResponse>() {
                    @Override
                    public void onResponse(Call<CardStoreResponse> call, Response<CardStoreResponse> response) {
                        if (response.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            CardStoreResponse result = response.body();
                            cardStoreAdapter.addList(result.getData());
                            cardStoreAdapter.setOnClickCardStoreItemListener(new OnClickCardStoreItemListener() {
                                @Override
                                public void onItemClick(CardStoreAdapter.ViewHolder holder, View view, int position) {
                                    String str_position = String.valueOf(position+1);
                                    if(position!=RecyclerView.NO_POSITION){
                                        Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
                                        intent.putExtra("_id", cardStoreAdapter.getItem(position).get_id());
                                        intent.putExtra("whichStore", 0);
                                        Log.d("아이디", cardStoreAdapter.getItem(position).get_id());

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
                    public void onFailure(Call<CardStoreResponse> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("REST ERROR!", t.getMessage());
                        Toast.makeText(getContext(), "네트워크를 확인해주세요!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }
}
