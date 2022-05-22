package com.capsaicin.sunhan.View.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.capsaicin.sunhan.Model.CommentItem;
import com.capsaicin.sunhan.Model.CommunityDetailItem;
import com.capsaicin.sunhan.Model.DeleteResponse;
import com.capsaicin.sunhan.Model.ResultResponse;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.activity.CommunityDetailActivity;
import com.capsaicin.sunhan.View.activity.LoginActivity;
import com.capsaicin.sunhan.View.fragment.MyPageFragment;
import com.capsaicin.sunhan.View.interfaceListener.OnClickCommentListener;
import com.capsaicin.sunhan.View.interfaceListener.OnClickLetterListener;

import org.w3c.dom.Comment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityDetailAdapter extends RecyclerView.Adapter<CommunityDetailAdapter.ViewHolder>
        implements OnClickCommentListener{
    private Context context;
    ArrayList<CommunityDetailItem> CommunityDetailItemList;
    ArrayList<CommentItem> CommunityCommentList;
    public static CommunityDetailCommentAdapter communityDetailCommentAdapter ;
    public OnClickCommentListener listener;

    public static String comment_user_id ;

    ImageView pop2;
    Dialog dialog;




    public CommunityDetailAdapter(Context context, ArrayList<CommentItem> items){
        Log.d("어댑터생성자-커뮤니티comment ","들어옴" );
        this.context = context ;
        this.CommunityCommentList= items;
        notifyItemRangeInserted(CommunityCommentList.size(), items.size());
    }

    @NonNull
    @Override
    public CommunityDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("온크리에이트뷰홀더-커뮤니티comment ","들어옴" );
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.community_comment_item, parent, false);

        return new CommunityDetailAdapter.ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityDetailAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CommentItem item =CommunityCommentList.get(position);
        Log.d("온바인드홀더-커뮤니티comment ", CommunityCommentList.get(position).getC_commuId());
//        holder.userProfile.setImageResource(CommunityDetailItemList.get(position).getUserProfile());
        Glide.with(context).load("https://sunhan.s3.ap-northeast-2.amazonaws.com/raw/"+CommunityCommentList.get(position).getC_writerItem().getAvatarUrl()).error(R.drawable.profile).circleCrop().into(holder.c_userProfile);
        holder.c_userId.setText(CommunityCommentList.get(position).getC_writerItem().getNickname());
        holder.c_content.setText(CommunityCommentList.get(position).getC_commuContent());
        holder.c_commentDate.setText(CommunityCommentList.get(position).getC_commuIsCreateAt());

        dialog = new Dialog(context);       // Dialog 초기화
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dialog.setContentView(R.layout.dialog_comment);

        holder.comment_More.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog(position,CommunityCommentList.get(position).getC_writerItem().get_id(),CommunityCommentList.get(position).getC_commuId());
            }
        });



//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
//        holder.c_recyclerView.setLayoutManager(layoutManager);
//        holder.c_recyclerView.setLayoutManager(layoutManager);
//        holder.c_recyclerView.setHasFixedSize(true);

//        ArrayList<CommentItem> arrayList = new ArrayList<>(); //대댓글

//        if (CommunityDetailItemList.get(position).getUserId().equals("선한2")) {
//            arrayList.add(new CommentItem(R.drawable.profile,"익명","와우","03/17 14:12"));
//            arrayList.add(new CommentItem(R.drawable.profile,"익명","와우","03/17 14:12"));
//            arrayList.add(new CommentItem(R.drawable.profile,"익명","와우","03/17 14:12"));
//            arrayList.add(new CommentItem(R.drawable.profile,"익명","와우","03/17 14:12"));
//            arrayList.add(new CommentItem(R.drawable.profile,"익명","와우","03/17 14:12"));
//            arrayList.add(new CommentItem(R.drawable.profile,"익명","와우","03/17 14:12"));
//        }

//        communityDetailCommentAdapter = new CommunityDetailCommentAdapter(holder.recyclerView.getContext(), arrayList); //대댓글
//        holder.recyclerView.setAdapter(communityDetailCommentAdapter); //대댓글

    }

    public void setOnClickCommentListner(OnClickCommentListener listner){
        this.listener = listner;
    }

    @Override
    public void onItemClick(CommunityDetailAdapter.ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView c_userProfile;
        TextView c_userId;
        TextView c_content;
        TextView c_commentDate;
        ImageView comment_More;
        RecyclerView c_recyclerView; //대댓글

        public ViewHolder(@NonNull View itemView, final OnClickCommentListener listener) {
            super(itemView);
            c_userProfile = itemView.findViewById(R.id.comment_userProfile);
            c_userId = itemView.findViewById(R.id.comment_userId);
            c_content = itemView.findViewById(R.id.comment_content);
            c_commentDate = itemView.findViewById(R.id.comment_date);
            comment_More = itemView.findViewById(R.id.comment_More);
            c_recyclerView = itemView.findViewById(R.id.recylerView_community_comment_child); //대댓글

            comment_More.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(CommunityDetailAdapter.ViewHolder.this, view,position);
                    }
                }
            });

        }

    }

    public void dialog(int position, String userId,String comment_id) {
        Button delete_btn = dialog.findViewById(R.id.delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LoginActivity.userAccessToken != null) {
                    if (MyPageFragment.userId.equals(userId)) {
                        AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                        dlg.setMessage("삭제하시겠습니까?"); // 메시지
                        dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Call<DeleteResponse> call = RetrofitInstance.getRetrofitService().deleteComment("Bearer "+LoginActivity.userAccessToken, comment_id);
                                call.enqueue(new Callback<DeleteResponse>() {
                                    @Override
                                    public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                                        if (response.isSuccessful()) {
                                            DeleteResponse result = response.body();
                                            removeItem(position);
                                            Log.d("삭제성공", result.getMessage());
                                        } else {
                                            Log.d("REST FAILED MESSAGE", response.message());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<DeleteResponse> call, Throwable t) {
                                        Log.d("REST ERROR!", t.getMessage());
                                    }
                                });
                            }
                        });
                        dlg.show();
                    } else { //본인 아님
                        AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                        dlg.setMessage("본인 댓글만 삭제가능합니다."); // 메시지
                        dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        dlg.show();
                    }
                } else { //로그인 안함
                    AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                    dlg.setMessage("로그인 후 이용해주세요."); // 메시지
                    dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dlg.show();
                }
            }
        });

        Button report_btn = dialog.findViewById(R.id.report_btn);
        report_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LoginActivity.userAccessToken != null) {
                    if (!MyPageFragment.user_id.equals(userId)) {
                        AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                        dlg.setMessage("신고하시겠습니까?"); // 메시지
                        dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Call<ResultResponse> call = RetrofitInstance.getRetrofitService().blockComment("Bearer "+LoginActivity.userAccessToken, comment_id);
                                call.enqueue(new Callback<ResultResponse>() {
                                    @Override
                                    public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                                        if (response.isSuccessful()) {
                                            ResultResponse result = response.body();
                                            Log.d("신고성공", result.getMessage());
                                        } else {
                                            Log.d("REST FAILED MESSAGE", response.message());
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
                    } else { //본인일경우
                        AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                        dlg.setMessage("본인은 신고 불가능합니다."); // 메시지
                        dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        dlg.show();
                    }
                } else { //로그인 안함
                    AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                    dlg.setMessage("로그인 후 이용해주세요."); // 메시지
                    dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dlg.show();
                }
            }
        });
    }

    private void commentDelete(int position, String userId,String comment_id){
        if (LoginActivity.userAccessToken != null) {
            if (MyPageFragment.userId.equals(userId)) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                dlg.setMessage("삭제하시겠습니까?"); // 메시지
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Call<DeleteResponse> call = RetrofitInstance.getRetrofitService().deleteComment("Bearer "+LoginActivity.userAccessToken, comment_id);
                        call.enqueue(new Callback<DeleteResponse>() {
                            @Override
                            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                                if (response.isSuccessful()) {
                                    DeleteResponse result = response.body();
                                    removeItem(position);
                                    Log.d("삭제성공", result.getMessage());
                                } else {
                                    Log.d("REST FAILED MESSAGE", response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                                Log.d("REST ERROR!", t.getMessage());
                            }
                        });
                    }
                });
                dlg.show();
            } else { //본인 아님
                AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                dlg.setMessage("본인 댓글만 삭제가능합니다."); // 메시지
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dlg.show();
            }
        } else { //로그인 안함
            AlertDialog.Builder dlg = new AlertDialog.Builder(context);
            dlg.setMessage("로그인 후 이용해주세요."); // 메시지
            dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dlg.show();
        }
    }

    private void commentReport(int position, String userId,String comment_id){
        if (LoginActivity.userAccessToken != null) {
            if (!MyPageFragment.user_id.equals(userId)) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                dlg.setMessage("신고하시겠습니까?"); // 메시지
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Call<ResultResponse> call = RetrofitInstance.getRetrofitService().blockComment("Bearer "+LoginActivity.userAccessToken, comment_id);
                        call.enqueue(new Callback<ResultResponse>() {
                            @Override
                            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                                if (response.isSuccessful()) {
                                    ResultResponse result = response.body();
                                    Log.d("신고성공", result.getMessage());
                                } else {
                                    Log.d("REST FAILED MESSAGE", response.message());
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
            } else { //본인일경우
                AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                dlg.setMessage("본인은 신고 불가능합니다."); // 메시지
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dlg.show();
            }
        } else { //로그인 안함
            AlertDialog.Builder dlg = new AlertDialog.Builder(context);
            dlg.setMessage("로그인 후 이용해주세요."); // 메시지
            dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dlg.show();
        }

    }


    public void addList(ArrayList <CommentItem> list){
        CommunityCommentList.addAll(list);
        notifyItemRangeInserted(CommunityCommentList.size(),list.size());
        Log.d("addList ",list.toString());
    }

    public void removeItem(int position){
        CommunityCommentList.remove(position);
        notifyItemRemoved(position);
//        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return CommunityCommentList.size();
    }
    public void addItem(CommentItem item){ CommunityCommentList.add(item); }
    public void setarrayList(ArrayList<CommentItem> arrayList) { this.CommunityCommentList = arrayList; }
    public CommentItem getItem(int position) { return CommunityCommentList.get(position); }
    public void setItem(int position, CommentItem item) { CommunityCommentList.set(position, item); }
}