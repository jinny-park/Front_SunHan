package com.capsaicin.sunhan.View.activity;

import static com.capsaicin.sunhan.View.activity.LocationSettingActivity.lat;
import static com.capsaicin.sunhan.View.activity.LocationSettingActivity.lng;
import static com.kakao.util.maps.helper.Utility.getKeyHash;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.MenuItem;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.R;
import com.kakao.util.maps.helper.Utility;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FindRoadActivity extends AppCompatActivity {
    Toolbar toolbar;
    public String KakaoMapAPI = "카카오 API Key";
    public MapView mMapView;
    public MapPoint mapPoint;
    public LocationManager lm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findroad);
        toolbar = findViewById(R.id.findroad_toolbar);
        setToolbar();
/*

        mMapView = new MapView( FindRoadActivity.this );
        ViewGroup mapViewContainer = findViewById( R.id.map_view );
        mMapView.setDaumMapApiKey( KakaoMapAPI );
        mapViewContainer.addView( mMapView );
        mMapView.removeAllPOIItems();
        MapMarker("마커에 찍을 내용", lat, lng);

        Handler mHandler = new Handler();
        mHandler.postDelayed( new Runnable() {
            public void run() {
                // 3초 후에 현재위치를 받아오도록 설정 , 바로 시작 시 에러납니다.
                mMapView.setCurrentLocationTrackingMode( MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading );
            }
        }, 4000 ); // 1000 = 1초
        lm = (LocationManager) getApplicationContext().getSystemService( Context.LOCATION_SERVICE );
*/

    }


    public void Marker(String MakerName, double startX, double startY) {
        mapPoint = MapPoint.mapPointWithGeoCoord( startY, startX );
        mMapView.setMapCenterPoint( mapPoint, true );
        //true면 앱 실행 시 애니메이션 효과가 나오고 false면 애니메이션이 나오지않음.
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(MakerName); // 마커 클릭 시 컨테이너에 담길 내용

        marker.setMapPoint( mapPoint );
        // 기본으로 제공하는 BluePin 마커 모양.
        marker.setMarkerType( MapPOIItem.MarkerType.RedPin );
        // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        marker.setSelectedMarkerType( MapPOIItem.MarkerType.BluePin );
        mMapView.addPOIItem( marker );
    }

    public void MapMarker(String MakerName,/* String detail,*/ double startX, double startY) {
        mapPoint = MapPoint.mapPointWithGeoCoord( startY, startX );
        mMapView.setMapCenterPoint( mapPoint, true );
        //true면 앱 실행 시 애니메이션 효과가 나오고 false면 애니메이션이 나오지않음.
        MapPOIItem marker = new MapPOIItem();
        //marker.setItemName(MakerName+"("+detail+")"); // 마커 클릭 시 컨테이너에 담길 내용
        marker.setMapPoint( mapPoint );
        // 기본으로 제공하는 BluePin 마커 모양.
        marker.setMarkerType( MapPOIItem.MarkerType.RedPin );
        // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        marker.setSelectedMarkerType( MapPOIItem.MarkerType.BluePin );
        mMapView.addPOIItem( marker );
    }


    void setToolbar(){
        setSupportActionBar (toolbar); //액티비티의 앱바(App Bar)로 지정
        ActionBar actionBar = getSupportActionBar (); //앱바 제어를 위해 툴바 액세스
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled (true); // 앱바에 뒤로가기 버튼 만들기
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //select back button
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
