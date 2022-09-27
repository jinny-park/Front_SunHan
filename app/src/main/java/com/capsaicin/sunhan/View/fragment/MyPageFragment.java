package com.capsaicin.sunhan.View.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.Model.UserResponse;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.activity.BottomNavigationActivity;
import com.capsaicin.sunhan.View.activity.DeleteAccountActivity;
import com.capsaicin.sunhan.View.activity.EditProfileActivity;
import com.capsaicin.sunhan.View.activity.LoginActivity;
import com.capsaicin.sunhan.View.activity.ManageBlockActivity;
import com.capsaicin.sunhan.View.activity.MyLogsActivity;
import com.capsaicin.sunhan.View.activity.CardCheckActivity;
import com.capsaicin.sunhan.View.activity.PolicyActivity;
import com.capsaicin.sunhan.View.adapter.MypageAdapter;
import com.capsaicin.sunhan.View.interfaceListener.OnClickMyPageItemListener;
import com.google.gson.Gson;
import com.kakao.sdk.user.UserApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//import com.kakao.usermgmt.UserManagement;
//import com.kakao.usermgmt.callback.LogoutResponseCallback;

public class MyPageFragment extends Fragment {
    private static final String TAG = "Logout";
    RecyclerView mypageRecyclerView;
    Button profileEditBtn;
    public static TextView userNickName;
    public static String userId;
    public static TextView userEmail;
    public static ImageView userImage;
    public static String imageUrl;
    public static String user_id;

    static public MyPageFragment myPageFragment ;

    private RetrofitInstance tokenRetrofitInstance ;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState); }

    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mypage,null);
        tokenRetrofitInstance=RetrofitInstance.getRetrofitInstance(); //레트로핏 싱글톤

        userNickName = view.findViewById(R.id.name_mypage);
        userEmail = view.findViewById(R.id.email_mypage);
        userImage = view.findViewById(R.id.info_user_profile);
        myPageFragment = new MyPageFragment();

        requestUserData(); //마이페이지 프로필 세팅
        setRecyclerview(view);

        profileEditBtn = view.findViewById(R.id.modify_profile);
        profileEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LoginActivity.userAccessToken==null){
                    loginPopUP();

                }else{
                    Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                    intent.putExtra("nickName",userNickName.getText());
                    intent.putExtra("profilePic",imageUrl);
                    startActivity(intent);
                }
            }
        });

        userNickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LoginActivity.userAccessToken==null){
                    Intent intent = new Intent(getActivity(),LoginActivity.class);
                    ((BottomNavigationActivity)getActivity()).finish();
                    startActivity(intent);
                }
            }
        });


        LoginActivity.mypageAdapter.setOnClickMyPageItemListener(new OnClickMyPageItemListener() {
            @Override public void onItemClick(MypageAdapter.ViewHolder holder, View view, int position) {
                if (position != RecyclerView.NO_POSITION) {

                    switch (position){
                        case 0:
                            Intent intent2 = new Intent(getActivity(), CardCheckActivity.class);
                            startActivity(intent2);
                            break;
                        case 1:
                            if(LoginActivity.userAccessToken!=null){
                                Intent intent3 = new Intent(getActivity(), MyLogsActivity.class);
                                startActivity(intent3);
                            }else{
                                loginPopUP();
                            }
                            break;
                        case 2:
                            if(LoginActivity.userAccessToken!=null){
                                Intent intent4 = new Intent(getActivity(), ManageBlockActivity.class);
                                startActivity(intent4);
                            }else{
                                loginPopUP();
                            }
                            break;
                        case 3:
                            Intent intent6 = new Intent(getActivity(), PolicyActivity.class);
                            startActivity(intent6);
                            break;

                        case 4: /*로그아웃 팝업*/
                            if(LoginActivity.userAccessToken!=null){
                                showDialog();
                            }else{
                                loginPopUP();
                            }
                            break;
                        case 5:
                            if(LoginActivity.userAccessToken!=null){
                                Intent intent5 = new Intent(getActivity(), DeleteAccountActivity.class);
                                startActivity(intent5);
                            }else{
                                loginPopUP();
                            }
                            break;
                    }

                }
            }
        });

        return view;

    }

    private void loginPopUP(){
        AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
        dlg.setMessage("로그인을 해주세요"); // 메시지
        dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dlg.show();
    }

    void setRecyclerview(View view){
        mypageRecyclerView = view.findViewById(R.id.recyclerview_mypage);
        mypageRecyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager recyclerViewManager = new GridLayoutManager(getActivity(),2);
        mypageRecyclerView.setLayoutManager(recyclerViewManager);
        mypageRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mypageRecyclerView.setAdapter(LoginActivity.mypageAdapter);

    }

    void showDialog() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(getContext()).setMessage("로그아웃 하시겠습니까?") .
                setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialogInterface, int i) {

                        UserApiClient.getInstance().logout(error -> {
                            if (error != null) {
//                                Log.e(TAG, "로그아웃 실패, SDK에서 토큰 삭제됨", error);
                            }else{
                                LoginActivity.userAccessToken = null;
                                LoginActivity.userRefreshToken = null;
                                LoginActivity.token = null;
                                userNickName.setText("로그인을 해주세요");
                                userEmail.setText("");
                                userImage.setImageResource(R.drawable.profile);
                                SunhanstSunhanFragment.category="한식";
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                ((BottomNavigationActivity)getActivity()).finish();
                                startActivity(intent);
                            }
                            return null;
                        });
                    }
                }) .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialogInterface, int i) {

                            }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }

    private void requestUserData(){
        if(LoginActivity.userAccessToken!=null){
            if(tokenRetrofitInstance!=null){
                Call<UserResponse> call = RetrofitInstance.getRetrofitService().getUser("Bearer "+LoginActivity.userAccessToken);
                call.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if (response.isSuccessful()) {
                            UserResponse result = response.body();
                            userId = result.getUserItem().get_id();
                            userNickName.setText(result.getUserItem().getNickname());
                            userEmail.setText(result.getUserItem().getEmail());
                            imageUrl="https://sunhan.s3.ap-northeast-2.amazonaws.com/raw/"+result.getUserItem().getAvatarUrl();
                            user_id = result.getUserItem().get_id(); //로그인 유저 id 정보 저장
                            Glide.with(getActivity()).load(imageUrl).error(R.drawable.profile).into(userImage);
                            Log.d("성공", new Gson().toJson(response.body()));
                        } else {
                            Log.d("REST FAILED MESSAGE", response.message());
                        }
                    }
                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Log.d("REST ERROR!", t.getMessage());
                        Toast.makeText(getContext(), "네트워크를 확인해주세요!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

}
