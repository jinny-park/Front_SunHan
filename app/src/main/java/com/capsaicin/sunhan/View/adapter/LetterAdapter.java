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
import com.capsaicin.sunhan.Model.LetterItem;
import com.capsaicin.sunhan.Model.ResultResponse;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.activity.LoginActivity;
import com.capsaicin.sunhan.View.fragment.MyPageFragment;
import com.capsaicin.sunhan.View.interfaceListener.OnClickLetterListener;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LetterAdapter extends RecyclerView.Adapter<LetterAdapter.ViewHolder>
        implements OnClickLetterListener {

    ArrayList<LetterItem> letterItems;
    private Context context;
    public OnClickLetterListener listener;

    public LetterAdapter(Context context, ArrayList<LetterItem> items) {
        this.context = context;
        this.letterItems = items;
        notifyItemRangeInserted(letterItems.size(),letterItems.size());
    }

    @NonNull
    @Override
    public LetterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.letter_item, parent, false);
        return new LetterAdapter.ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull LetterAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
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

        holder.block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(letterItems.get(position).getWriterItem().get_id()!=MyPageFragment.userId){
                    popUP();
                }else{
                    letterBlock(letterItems.get(position).get_id());
                }
            }
        });


    }
    public void setOnClickLetterListener(OnClickLetterListener listener) {

        this.listener = listener;
    }

    @Override
    public void onItemClick(LetterAdapter.ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView letterName;
        TextView letterContent;
        TextView letterDate;
        ImageView letterImage;
        ImageView userProfile;
        Button block;

        public ViewHolder(@NonNull View itemView, final OnClickLetterListener listener) {
            super(itemView);

            userProfile = itemView.findViewById(R.id.letter_user_profile);
            letterImage = itemView.findViewById(R.id.letterImage);
            letterName = itemView.findViewById(R.id.writer);
            letterContent = itemView.findViewById(R.id.content);
            letterDate = itemView.findViewById(R.id.createAt);
            block = itemView.findViewById(R.id.block_letter);
        }
    }


    private void letterBlock(String letter_id){
        if (LoginActivity.userAccessToken != null) {
                 Call<ResultResponse> call = RetrofitInstance.getRetrofitService().blockLetter("Bearer " + LoginActivity.userAccessToken, letter_id);
                 call.enqueue(new Callback<ResultResponse>() {
                     @Override public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                         if (response.isSuccessful()) {
                             ResultResponse result = response.body();
                             Toast toast = Toast.makeText(context, "신고되었습니다", Toast.LENGTH_SHORT);
                             toast.show();
                             Log.d("신고성공", new Gson().toJson(response.body()));
                         } else {
                             Log.d("ERROR", response.message());
                         }
                     }
                     @Override
                     public void onFailure(Call<ResultResponse> call, Throwable t) {
                         Log.d("REST ERROR!", t.getMessage());
                         Toast.makeText(context, "네트워크를 확인해주세요!", Toast.LENGTH_LONG).show();

                     }
                 });
        } else {
            loginPopUP();
        }
    }

    private void popUP(){
        AlertDialog.Builder dlg = new AlertDialog.Builder(context);
        dlg.setMessage("본인 편지는 신고할 수 없습니다."); // 메시지
        dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dlg.show();
    }

    private void loginPopUP(){
        AlertDialog.Builder dlg = new AlertDialog.Builder(context);
        dlg.setMessage("로그인을 해주세요"); // 메시지
        dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dlg.show();
    }

    @Override
    public int getItemCount() {
        return letterItems.size();
    }


    public void addList(ArrayList <LetterItem> list){
        letterItems.addAll(list);
        notifyItemRangeInserted(letterItems.size(),list.size());
    }

    public void addItem(LetterItem item,int position){
        letterItems.add(item);
        notifyItemInserted(position);
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