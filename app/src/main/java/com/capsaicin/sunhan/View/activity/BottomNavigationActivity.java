package com.capsaicin.sunhan.View.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.capsaicin.sunhan.Model.AddressItem;
import com.capsaicin.sunhan.Model.ResultResponse;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitServiceApi;
import com.capsaicin.sunhan.Model.SunHanDetailItem;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.fragment.CommunityFragment;
import com.capsaicin.sunhan.View.fragment.FindStoreFragment;
import com.capsaicin.sunhan.View.fragment.HeartFragment;
import com.capsaicin.sunhan.View.fragment.MyPageFragment;
import com.capsaicin.sunhan.View.fragment.SunhanstCardFragment;
import com.capsaicin.sunhan.View.fragment.SunhanstMainFragment;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomNavigationActivity extends AppCompatActivity {

    MyPageFragment myPageFragment;
    HeartFragment heartFragment;
    FindStoreFragment findStoreFragment;
    CommunityFragment communityFragment;
    SunhanstMainFragment sunhanstMainFragment ;

    NavigationBarView navigationBarView;
    Toolbar toolbar;
    private RetrofitInstance tokenRetrofitInstance ;

    Intent intent;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    private AddressItem addressItem;
    public static double lat;
    public static double lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi);

        tokenRetrofitInstance=RetrofitInstance.getRetrofitInstance(); //레트로핏 싱글톤

        intent = getIntent();


        navigationBarView = findViewById(R.id.bottomNavi);

        toolbar = findViewById (R.id.toolbar);
        setToolbar();

        if(sunhanstMainFragment==null){
            sunhanstMainFragment = new SunhanstMainFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.main_frame, sunhanstMainFragment).commit();
        }

        navigationBarView.setItemIconTintList(null);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_heart:
                        //찜한가게는 매번 불러와야 함
                        if(heartFragment==null){
                            heartFragment = new HeartFragment();
                            getSupportFragmentManager().beginTransaction().add(R.id.main_frame, heartFragment).commit();
                        }
                        allHideScreens();
                        getSupportFragmentManager().beginTransaction().remove(heartFragment).commit();
                        heartFragment = new HeartFragment();
                        getSupportFragmentManager().beginTransaction().add(R.id.main_frame,heartFragment).commit();
                        return true;
                    case R.id.action_community:
                        if(communityFragment==null){
                            communityFragment = new CommunityFragment();
                            getSupportFragmentManager().beginTransaction().add(R.id.main_frame, communityFragment).commit();
                        }
                        screenChange(communityFragment);
                        return true;
                    case R.id.action_mypage:
                        if(myPageFragment==null){
                            myPageFragment = new MyPageFragment();
                            getSupportFragmentManager().beginTransaction().add(R.id.main_frame, myPageFragment).commit();
                        }
                        screenChange(myPageFragment);
                        return true;
                    case R.id.action_bottomnavi:
                        screenChange(sunhanstMainFragment);
                        return true;
                    case R.id.action_findstore:
                        if(findStoreFragment==null){
                            findStoreFragment = new FindStoreFragment();
                            getSupportFragmentManager().beginTransaction().add(R.id.main_frame, findStoreFragment).commit();
                        }
                        screenChange(findStoreFragment);
                        return true;
                }
                    return false;
            }
        });
    }
    void setToolbar(){
        setSupportActionBar (toolbar); //액티비티의 앱바(App Bar)로 지정
        ActionBar actionBar = getSupportActionBar (); //앱바 제어를 위해 툴바 액세스
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled (true); // 앱바에 뒤로가기 버튼 만들기
    }

    private void screenChange(Fragment fragment){
        allHideScreens();
        if(fragment!=null)
            getSupportFragmentManager().beginTransaction().show(fragment).commit();
    }

    private void allHideScreens(){
        if(myPageFragment!=null)
            getSupportFragmentManager().beginTransaction().hide(myPageFragment).commit();
        if(communityFragment!=null)
            getSupportFragmentManager().beginTransaction().hide(communityFragment).commit();
        if(sunhanstMainFragment!=null)
            getSupportFragmentManager().beginTransaction().hide(sunhanstMainFragment).commit();
        if(findStoreFragment!=null)
            getSupportFragmentManager().beginTransaction().hide(findStoreFragment).commit();
        if(heartFragment!=null)
            getSupportFragmentManager().beginTransaction().hide(heartFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //select back button
                finish();
                return true;
            case R.id.location_search:  //사용자 위치 잡기
//                Intent intent=new Intent(getApplicationContext(), LocationSettingActivity.class);
//                startActivity(intent);
                setLocation();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        super.onRequestPermissionsResult(permsRequestCode, permissions, grandResults);
        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if (check_result) {

                //위치 값을 가져올 수 있음
                ;
            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(BottomNavigationActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_SHORT).show();
                    finish();


                } else {

                    Toast.makeText(BottomNavigationActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_SHORT).show();

                }
            }

        }
    }

    void setLocation(){ // 로케이션 버튼 누르면 화면 이동 없이 바로 잡음 (회원/비회원 둘다 가능)
        GpsTracker gpsTracker = new GpsTracker(BottomNavigationActivity.this);

        double latitude = gpsTracker.getLatitude(); //위도
        double longitude = gpsTracker.getLongitude(); //경도

        lat = latitude; // 사용자의 위도경도 스태틱으로 넣어줌 -> 위도경도를 api 요청시 필요할 때 사용
        lng = longitude; // 사용자의 위도경도
        addressItem = new AddressItem (latitude,longitude);

        String address = getCurrentAddress(latitude, longitude);

        if(!address.equals("지오코더 서비스 사용불가")) {
            if (LoginActivity.userAccessToken != null && tokenRetrofitInstance != null) { //회원일경우 서버에 전송해야함
                Call<ResultResponse> call = RetrofitInstance.getRetrofitService().postAddress("Bearer " + LoginActivity.userAccessToken, addressItem);
                call.enqueue(new Callback<ResultResponse>() {
                    @Override
                    public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                        if (response.isSuccessful()) {
                            ResultResponse result = response.body();
                            allHideScreens();
                            if(sunhanstMainFragment!=null){
                                getSupportFragmentManager().beginTransaction().remove(sunhanstMainFragment).commit();
                                sunhanstMainFragment = new SunhanstMainFragment();
                                getSupportFragmentManager().beginTransaction().add(R.id.main_frame,sunhanstMainFragment).commit();
                            }
                            Toast.makeText(BottomNavigationActivity.this, "현재위치 잡기 성공!", Toast.LENGTH_SHORT).show();
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
        }else{
            Toast.makeText(getApplicationContext(), "네트워크를 확인해주세요!", Toast.LENGTH_SHORT).show();
        }
    }


    void checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(BottomNavigationActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(BottomNavigationActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음



        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(BottomNavigationActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(BottomNavigationActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(BottomNavigationActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(BottomNavigationActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }


    public String getCurrentAddress( double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
//            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_SHORT).show();
            return "잘못된 GPS 좌표";

        }

        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_SHORT).show();
            return "주소 미발견";

        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";

    }


    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(BottomNavigationActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

}
