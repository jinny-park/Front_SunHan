package com.capsaicin.sunhan.View.fragment;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.kakao.sdk.user.UserApiClient;
//import com.kakao.usermgmt.UserManagement;
//import com.kakao.usermgmt.callback.LogoutResponseCallback;

public class MyPageFragment extends Fragment {
    private static final String TAG = "Logout";
    RecyclerView mypageRecyclerView;
    Button profileEditBtn;
    TextView userNickName;
    TextView userId;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mypage,null);


        setRecyclerview(view);
        profileEditBtn = view.findViewById(R.id.modify_profile);
        profileEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), EditProfileActivity.class));
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
                            Intent intent3 = new Intent(getActivity(), MyLogsActivity.class);
                            startActivity(intent3);
                            break;
                        case 2:
                            Intent intent4 = new Intent(getActivity(), ManageBlockActivity.class);
                            startActivity(intent4);
                            break;
                        case 3: /*로그아웃 팝업*/
                            showDialog();
                            break;

                        case 4:
                            Intent intent5 = new Intent(getActivity(), DeleteAccountActivity.class);
                            startActivity(intent5);

                            break;
                        case 5:
                            Intent intent6 = new Intent(getActivity(), PolicyActivity.class);
                            startActivity(intent6);
                            break;
                    }

                }
            }
        });

        return view;

    }
//    @Override
//    public void onResume() {
//        super.onResume();
//        FragmentActivity activity = getActivity();
//        if (activity != null) {
//            ((BottomNavigationActivity) activity).setActionBarTitle("마이페이지");
//        }
//    }

    void setRecyclerview(View view){
        mypageRecyclerView = view.findViewById(R.id.recyclerview_mypage);
        mypageRecyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
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
                                LoginActivity.token = null;
                                Intent intent = new Intent(getActivity(), BottomNavigationActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                Log.w(TAG, "invoke: " + LoginActivity.token);
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
}
