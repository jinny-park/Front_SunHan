package com.capsaicin.sunhan.View.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.capsaicin.sunhan.BuildConfig;
import com.capsaicin.sunhan.Model.LetterItem;
import com.capsaicin.sunhan.Model.ResultResponse;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.Model.TokenResponse;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.activity.LoginActivity;
import com.capsaicin.sunhan.View.fragment.MyPageFragment;
import com.capsaicin.sunhan.View.interfaceListener.OnClickLetterListener;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyLetterLogsAdapter extends RecyclerView.Adapter<MyLetterLogsAdapter.ViewHolder> {

    ArrayList<LetterItem> letterItems;
    private Context context;

    public MyLetterLogsAdapter(Context context, ArrayList<LetterItem> items) {
        this.context = context;
        this.letterItems = items;
        notifyItemRangeInserted(letterItems.size(),letterItems.size());
    }

    @NonNull
    @Override
    public MyLetterLogsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.my_letter_log_item, parent, false);
        return new MyLetterLogsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyLetterLogsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        LetterItem item = letterItems.get(position);
        Glide.with(context).load("https://sunhan.s3.ap-northeast-2.amazonaws.com/raw/"+letterItems.get(position).getWriterItem().getAvatarUrl()).error(R.drawable.profile).into(holder.userProfile);
        holder.letterName.setText(letterItems.get(position).getWriterItem().getNickname());
        holder.letterContent.setText(letterItems.get(position).getContent());
        holder.letterDate.setText(letterItems.get(position).getCreateAt());

        if(letterItems.get(position).getImageUrl()!=null){
            holder.letterImage.setVisibility(View.VISIBLE);
            Glide.with(context).load("https://sunhan.s3.ap-northeast-2.amazonaws.com/raw/"+letterItems.get(position).getImageUrl()).into(holder.letterImage);
        }else{
            holder.letterImage.setVisibility(View.GONE);
        }

        if(letterItems.get(position).getChildrenId()!=null){ //?????????????????????
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("?????????","????????????");
                    letterDelete(position,letterItems.get(position).get_id(),"children");
                }
            });
        } else if(letterItems.get(position).getSunhanId()!=null){ //???????????????
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("???????????????","????????????");
                    letterDelete(position,letterItems.get(position).get_id(),"sunhan");
                }
            });
        }

    }
    private void letterDelete(int position,String letter_id,String type){ //?????? ???????????? ?????????
        AlertDialog.Builder dlg = new AlertDialog.Builder(context);
        dlg.setMessage("?????????????????????????"); // ?????????
        dlg.setPositiveButton("??????", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Call<ResultResponse> call = RetrofitInstance.getRetrofitService().deleteLetter("Bearer " + LoginActivity.userAccessToken, letter_id, type);
                call.enqueue(new Callback<ResultResponse>() {
                    @Override
                    public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                        if (response.isSuccessful()) {
                            ResultResponse result = response.body();
                            removeItem(position);
                            Toast toast = Toast.makeText(context, "????????????",Toast.LENGTH_SHORT);
                            toast.show();
                            Log.d("????????????", new Gson().toJson(response.body()));
                        } else {
                            if(response.message().equals("Unauthorized")){
                                checkAuthorized();
                                call.enqueue(new Callback<ResultResponse>() {
                                    @Override
                                    public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                                        if (response.isSuccessful()) {
                                            ResultResponse result = response.body();
                                            removeItem(position);
                                            Toast toast = Toast.makeText(context, "????????????",Toast.LENGTH_SHORT);
                                            toast.show();
                                            Log.d("????????????", new Gson().toJson(response.body()));
                                        } else {
                                            Log.d("??????????????????", response.message());
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<ResultResponse> call, Throwable t) {
                                        Log.d("REST ERROR!", t.getMessage());
                                        Toast.makeText(context, "??????????????? ??????????????????!", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                            Log.d("??????????????????", response.message());
                        }
                    }
                    @Override
                    public void onFailure(Call<ResultResponse> call, Throwable t) {
                        Log.d("REST ERROR!", t.getMessage());
                        Toast.makeText(context, "??????????????? ??????????????????!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        dlg.show();
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
                    Log.d("??????????????????", new Gson().toJson(response.body()));
                } else {
                    Log.d("?????????????????? ??????", response.message());
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.d("REST ERROR!", t.getMessage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return letterItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView letterName;
        TextView letterContent;
        TextView letterDate;
        ImageView letterImage;
        ImageView userProfile;
        Button delete;

        public ViewHolder(@NonNull View itemView ) {
            super(itemView);
            userProfile = itemView.findViewById(R.id.letter_log_user_profile);
            letterImage = itemView.findViewById(R.id.letter_log_letterImage);
            letterName = itemView.findViewById(R.id.letter_log_writer);
            letterContent = itemView.findViewById(R.id.letter_log_content);
            letterDate = itemView.findViewById(R.id.letter_log_createAt);
            delete = itemView.findViewById(R.id.delete_letter);

        }
    }
    public void removeItem(int position){ // ?????? ????????? ????????????????????? ????????? ?????? notify
        letterItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,letterItems.size());
    }

    public void addList(ArrayList <LetterItem> list){
        letterItems.addAll(list);
        notifyItemRangeInserted(letterItems.size(),list.size());
    }

    public void addItem(LetterItem item){
        letterItems.add(item);
    }
    public void setArrayList(ArrayList<LetterItem> arrayList) {
        this.letterItems = arrayList;
    }

    public LetterItem getItem(int position) {
        return letterItems.get(position);
    }

    public void setItem(int position, LetterItem item) {
        letterItems.set(position, item);
    }
}
