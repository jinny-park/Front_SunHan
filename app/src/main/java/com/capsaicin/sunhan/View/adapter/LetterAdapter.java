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
            Glide.with(context).load("https://sunhan.s3.ap-northeast-2.amazonaws.com/raw/"+letterItems.get(position).getImageUrl()).into(holder.letterImage);
        }

        if(letterItems.get(position).getChildrenId()!=null){ //아동급식가맹점
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    letterDelete(position,letterItems.get(position).getWriterItem().get_id(),letterItems.get(position).get_id(),"children");
                }
            });
        } else{ //선한영향력
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    letterDelete(position,letterItems.get(position).getWriterItem().get_id(),letterItems.get(position).get_id(),"sunhan");
                }
            });
        }

        holder.block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                letterBlock(letterItems.get(position).get_id());
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
        Button delete;
        TextView edit;
        Button block;

        public ViewHolder(@NonNull View itemView, final OnClickLetterListener listener) {
            super(itemView);

            userProfile = itemView.findViewById(R.id.letter_user_profile);
            letterImage = itemView.findViewById(R.id.letterImage);
            letterName = itemView.findViewById(R.id.writer);
            letterContent = itemView.findViewById(R.id.content);
            letterDate = itemView.findViewById(R.id.createAt);
            delete = itemView.findViewById(R.id.delete_letter);
//            edit = itemView.findViewById(R.id.edit_letter);
            block = itemView.findViewById(R.id.block_letter);
        }
    }

    private void letterDelete(int position, String userId,String letter_id,String type){
        if (LoginActivity.userAccessToken != null) {
            if (MyPageFragment.userId.equals(userId)) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                dlg.setMessage("삭제하시겠습니까?"); // 메시지
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Call<ResultResponse> call = RetrofitInstance.getRetrofitService().deleteLetter("Bearer " + LoginActivity.userAccessToken, letter_id, type);
                        call.enqueue(new Callback<ResultResponse>() {
                            @Override
                            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                                if (response.isSuccessful()) {
                                    ResultResponse result = response.body();
                                    removeItem(position);
                                    Log.d("삭제성공", new Gson().toJson(response.body()));
                                } else {
                                    Log.d("선한영향력편지삭제실패", response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<ResultResponse> call, Throwable t) {
                                Log.d("REST ERROR!", t.getMessage());
                            }
                        });

                    }
                });
                dlg.show();
            } else { //본인 불일치
                AlertDialog.Builder dlg = new AlertDialog.Builder(context.getApplicationContext());
                dlg.setMessage("본인 편지만 삭제가능합니다!"); // 메시지
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dlg.show();
            }
        } else {
            //로그인 안 함
            AlertDialog.Builder dlg = new AlertDialog.Builder(context.getApplicationContext());
            dlg.setMessage("로그인 후 이용해주세요."); // 메시지
            dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dlg.show();
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
                     }
                 });
        } else {
            //로그인 안함
            AlertDialog.Builder dlg = new AlertDialog.Builder(context);
            dlg.setMessage("로그인 후 이용해주세요."); // 메시지
            dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            dlg.show();
        }
    }

    @Override
    public int getItemCount() {
        return letterItems.size();
    }


    public void removeItem(int position){
        letterItems.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
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