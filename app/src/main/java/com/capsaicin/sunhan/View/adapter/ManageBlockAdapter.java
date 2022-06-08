package com.capsaicin.sunhan.View.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Choreographer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.capsaicin.sunhan.Model.BlockedItem;
import com.capsaicin.sunhan.Model.ResultResponse;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.Model.TokenResponse;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.activity.LoginActivity;
import com.capsaicin.sunhan.View.fragment.MyPageFragment;
import com.capsaicin.sunhan.View.interfaceListener.OnClickBlockedItemListener;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageBlockAdapter extends RecyclerView.Adapter<ManageBlockAdapter.ViewHolder>
        implements OnClickBlockedItemListener {

    private Context context;
    private ArrayList<BlockedItem> blocekdItemsList;
    public OnClickBlockedItemListener listener;

    public ManageBlockAdapter(Context context, ArrayList<BlockedItem> list){
        this.context = context ;
        this.blocekdItemsList= list;
        notifyItemRangeInserted(blocekdItemsList.size(),blocekdItemsList.size());
    }

    @NonNull
    @Override
    public ManageBlockAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.block_item, parent, false);
        return new ManageBlockAdapter.ViewHolder(itemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull ManageBlockAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        BlockedItem item =blocekdItemsList.get(position);
        holder.textView.setText(blocekdItemsList.get(position).getNickname());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unBlockUser(position,blocekdItemsList.get(position).get_id());
            }
        });
    }

    private void unBlockUser(int position,String user_id){
        Call<ResultResponse> call = RetrofitInstance.getRetrofitService().unBlockUser("Bearer "+LoginActivity.userAccessToken, user_id);
        call.enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                if (response.isSuccessful()) {
                    ResultResponse result = response.body();
                    removeItem(position);
                    Toast toast = Toast.makeText(context, "차단해제",Toast.LENGTH_SHORT);
                    toast.show();
                    Log.d("차단풀기 성공", new Gson().toJson(response.body()));
                } else {
                    if(response.message().equals("Unauthorized")){
                        checkAuthorized();
                        unBlockUser(position,user_id);
                    }
                    Log.d("차단실패", response.message());
                }
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                Log.d("REST ERROR!", t.getMessage());
                Toast.makeText(context, "네트워크를 확인해주세요!", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void setOnClickBlockedItemListener(OnClickBlockedItemListener listener){

        this.listener = listener;
    }
    @Override
    public void onItemClick(ManageBlockAdapter.ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder,view,position);
        }
    }
    private void checkAuthorized(){
        Call<TokenResponse> call = RetrofitInstance.getRetrofitService().getRefreshToken("Bearer "+ LoginActivity.userAccessToken,LoginActivity.userRefreshToken );
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

    @Override
    public int getItemCount() {
        return blocekdItemsList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        Button button;

        public ViewHolder(@NonNull View itemView , final OnClickBlockedItemListener listener) {
            super(itemView);
            button = itemView.findViewById(R.id.isBlocked_btn);
            textView = itemView.findViewById(R.id.blocked_id);
        }
    }

    public void removeItem(int position){
        blocekdItemsList.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void addItem(BlockedItem item){
        blocekdItemsList.add(item);
    }
    public void setarrayList(ArrayList<BlockedItem> arrayList) {

        this.blocekdItemsList = arrayList;
    }

    public BlockedItem getItem(int position) {

        return blocekdItemsList.get(position);
    }

    public void setItem(int position, BlockedItem item) {

        blocekdItemsList.set(position, item);
    }


}