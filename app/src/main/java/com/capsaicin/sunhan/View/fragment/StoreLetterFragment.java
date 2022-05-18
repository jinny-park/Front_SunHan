package com.capsaicin.sunhan.View.fragment;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import static com.capsaicin.sunhan.View.activity.StoreDetailActivity.whichStore;
import static com.capsaicin.sunhan.View.fragment.SunhanstMainFragment.storeId;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.capsaicin.sunhan.Model.CardStoreDetailResponse;
import com.capsaicin.sunhan.Model.LetterItem;
import com.capsaicin.sunhan.Model.LetterResponse;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.Model.SunHanStoreDetailResponse;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.activity.StoreDetailActivity;
import com.capsaicin.sunhan.View.adapter.LetterAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreLetterFragment extends Fragment {
    public static LetterAdapter letterAdapter;
    ArrayList<LetterItem> letterList = new ArrayList<LetterItem>();
    RecyclerView letterRecyclerView;
    CardView letter;
    ImageView letter_img;
    private static final int REQUEST_CODE = 0;
    int page;
    int blockNumber;
    ProgressBar progressBar;

    LetterResponse letterResponse;
    private RetrofitInstance tokenRetrofitInstance ;


    static public TextView writer;
    static public ImageView letterImage;
    static public TextView content;
    static public TextView createAt;
    //static public ImageView avatarUrl;

    static public TextView edit_letter;
    static public TextView block_letter;



    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sunhanst_store_letter,null);
        tokenRetrofitInstance=RetrofitInstance.getRetrofitInstance(); //레트로핏 싱글톤
        page = 0;
        progressBar = view.findViewById(R.id.progress_bar_letter);

        letter = view.findViewById(R.id.add_letter_img);
        letter_img = view.findViewById(R.id.letter_img);


        writer = view.findViewById(R.id.writer);
        letterImage = view.findViewById(R.id.letterImage);
        content = view.findViewById(R.id.content);
        createAt = view.findViewById(R.id.createAt);
        //avatarUrl = view.findViewById(R.id.avatarUrl);

        edit_letter = view.findViewById(R.id.edit_letter);
        block_letter = view.findViewById(R.id.block_letter);


        letter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        letterAdapter = new LetterAdapter(getContext(), letterList);

        letterRecyclerView = view.findViewById(R.id.recyclerview_letter);
        letterRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
        letterRecyclerView.setLayoutManager(recyclerViewManager);
        letterRecyclerView.setItemAnimator(new DefaultItemAnimator());
        letterRecyclerView.setAdapter(letterAdapter);

        initLetterData(0);

        letterRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = ((LinearLayoutManager) letterRecyclerView.getLayoutManager()).
                        findLastCompletelyVisibleItemPosition();
                int itemTotalCount = letterRecyclerView.getAdapter().getItemCount() - 1;
                if(lastVisibleItemPosition == itemTotalCount) {
                    progressBar.setVisibility(View.VISIBLE);
                    getLetterData(page);
                    page++;


                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE){
            if(resultCode==RESULT_OK){
                try {
                    Uri uri = data.getData();
                    Glide.with(getActivity().getApplicationContext()).load(uri).into(letter_img);
                }catch (Exception e){
                    getActivity().finish();
                }
            }else if(resultCode==RESULT_CANCELED){
                getActivity().finish();
            }
        }
    }

/*    void setRecyclerview(View view){
        letterRecyclerView = view.findViewById(R.id.recyclerview_letter);
        letterRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
        letterRecyclerView.setLayoutManager(recyclerViewManager);
        letterRecyclerView.setItemAnimator(new DefaultItemAnimator());
        letterRecyclerView.setAdapter(letterAdapter);

    }*/

    private void initLetterData(int page)
    {
        if(tokenRetrofitInstance!=null && whichStore==0){
            Call<LetterResponse> call = RetrofitInstance.getRetrofitService().getLetter(StoreDetailActivity.id, "children", page);
            call.enqueue(new Callback<LetterResponse>() {
                @Override
                public void onResponse(Call<LetterResponse> call, Response<LetterResponse> response) {
                    if (response.isSuccessful()) {
                        LetterResponse result = response.body();
                        progressBar.setVisibility(View.GONE);
                        letterAdapter = new LetterAdapter(getActivity(), result.getData());
                        letterRecyclerView.setAdapter(letterAdapter);
                        Log.d("성공", new Gson().toJson(response.body()));
                    } else {

                        Log.d("편지 로딩 실패", response.message());
                    }
                }

                @Override
                public void onFailure(Call<LetterResponse> call, Throwable t) {
                    Log.d("REST ERROR!", t.getMessage());
                }
            });
        } else if(tokenRetrofitInstance!=null && whichStore==1){
            Call<LetterResponse> call = RetrofitInstance.getRetrofitService().getLetter(StoreDetailActivity.id, "sunhan", page);
            call.enqueue(new Callback<LetterResponse>() {
                @Override
                public void onResponse(Call<LetterResponse> call, Response<LetterResponse> response) {
                    if (response.isSuccessful()) {
                        LetterResponse result = response.body();
                        progressBar.setVisibility(View.GONE);
                        letterAdapter = new LetterAdapter(getActivity(), result.getData());
                        letterRecyclerView.setAdapter(letterAdapter);
                        Log.d("성공", new Gson().toJson(response.body()));
                    } else {

                        Log.d("ERROR", response.message());
                    }
                }

                @Override
                public void onFailure(Call<LetterResponse> call, Throwable t) {
                    Log.d("REST ERROR!", t.getMessage());
                }
            });


        }
    }


    private void getLetterData(int page) {
        if (tokenRetrofitInstance != null && whichStore == 0) {
            Call<LetterResponse> call = RetrofitInstance.getRetrofitService().getLetter(StoreDetailActivity.id, "children", page);
            call.enqueue(new Callback<LetterResponse>() {
                @Override
                public void onResponse(Call<LetterResponse> call, Response<LetterResponse> response) {
                    if (response.isSuccessful()) {
                        LetterResponse result = response.body();
                        progressBar.setVisibility(View.GONE);
                        letterRecyclerView.setAdapter(letterAdapter);
                        Log.d("성공", new Gson().toJson(response.body()));
                    } else {

                        Log.d("편지실패", response.message());
                    }
                }

                @Override
                public void onFailure(Call<LetterResponse> call, Throwable t) {
                    Log.d("REST ERROR!", t.getMessage());
                }
            });
        } else if (tokenRetrofitInstance != null && whichStore == 1) {
            Call<LetterResponse> call = RetrofitInstance.getRetrofitService().getLetter(StoreDetailActivity.id, "sunhan", page);
            call.enqueue(new Callback<LetterResponse>() {
                @Override
                public void onResponse(Call<LetterResponse> call, Response<LetterResponse> response) {
                    if (response.isSuccessful()) {
                        LetterResponse result = response.body();
                        progressBar.setVisibility(View.GONE);
                        letterRecyclerView.setAdapter(letterAdapter);
                        Log.d("성공", new Gson().toJson(response.body()));
                    } else {

                        Log.d("편지실패", response.message());
                    }
                }

                @Override
                public void onFailure(Call<LetterResponse> call, Throwable t) {
                    Log.d("REST ERROR!", t.getMessage());
                }
            });
        }
    }
}