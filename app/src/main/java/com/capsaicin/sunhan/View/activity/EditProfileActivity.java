package com.capsaicin.sunhan.View.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.capsaicin.sunhan.Model.NickNameItem;
import com.capsaicin.sunhan.Model.ProfileChangeResponse;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitServiceApi;
import com.capsaicin.sunhan.Model.UserResponse;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.fragment.MyPageFragment;
import com.google.gson.Gson;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    Toolbar toolbar;
    CardView profile;
    ImageView profile_img;
    String imageUrl;
    Intent intent;
    TextView edit_profile_name;
    Button edit_profile_btn;
    Uri uri;
    File imageFile;
    String imagePath;
    MyPageFragment myPageFragment;
    private static final int REQUEST_CODE = 0;
    NickNameItem nickNameItem;

    private RetrofitInstance tokenRetrofitInstance ;
    private RetrofitServiceApi retrofitServiceApi;
    RequestBody imageRequestBody;
    MultipartBody.Part filePart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        toolbar = findViewById(R.id.edit_toolbar);
        profile = findViewById(R.id.edit_profile_pic);
        profile_img = findViewById(R.id.edit_user_profile);
        edit_profile_name = findViewById(R.id.edit_profile_name);
        edit_profile_btn = findViewById(R.id.edit_profile_btn);
        tokenRetrofitInstance=RetrofitInstance.getRetrofitInstance(); //레트로핏 싱글톤
        myPageFragment  = new MyPageFragment();
        nickNameItem = new NickNameItem();

        intent = getIntent();
        edit_profile_name.setText(intent.getStringExtra("nickName"));
        imageUrl = intent.getStringExtra("profilePic");
        Glide.with(getApplicationContext()).load(imageUrl).error(R.drawable.profile).into(profile_img);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            Toast.makeText(getApplicationContext(), "외부 저장소 사용을 위해 읽기/쓰기 필요", Toast.LENGTH_SHORT).show();
                        }

                        requestPermissions(new String[]
                                {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                    }
                }
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        edit_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nickNameItem.setNickname(edit_profile_name.getText().toString());

//                File path = Environment.getExternalStoragePublicDirectory(
//                        Environment.DIRECTORY_PICTURES);
                if(LoginActivity.userAccessToken!=null){
                    if(tokenRetrofitInstance!=null){
                        Call<ProfileChangeResponse> call = RetrofitInstance.getRetrofitService().changeNickname("Bearer "+LoginActivity.userAccessToken, nickNameItem);
                        call.enqueue(new Callback<ProfileChangeResponse>() {
                            @Override
                            public void onResponse(Call<ProfileChangeResponse> call, Response<ProfileChangeResponse> response) {
                                if (response.isSuccessful()) {
                                    ProfileChangeResponse result = response.body();
                                    changeNickname();
                                    Log.d("닉네임변경성공", new Gson().toJson(response.body()));
                                } else {
                                    Log.d("REST FAILED MESSAGE", response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<ProfileChangeResponse> call, Throwable t) {
                                Log.d("REST ERROR!", t.getMessage());
                            }
                        });

                        if(imagePath!=null){
                            imageFile = new File(imagePath);
                            if (!imageFile.exists()) {       // 원하는 경로에 폴더가 있는지 확인
                                imageFile.mkdirs();    // 하위폴더를 포함한 폴더를 전부 생성
                            }
                            imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
                            filePart = MultipartBody.Part.createFormData("image", imageFile.getName() ,imageRequestBody);

                            Call<ProfileChangeResponse> call2 = RetrofitInstance.getRetrofitService().changePicture("Bearer "+LoginActivity.userAccessToken, filePart);
                            call2.enqueue(new Callback<ProfileChangeResponse>() {
                                @Override
                                public void onResponse(Call<ProfileChangeResponse> call, Response<ProfileChangeResponse> response) {
                                    if (response.isSuccessful()) {
                                        ProfileChangeResponse result = response.body();
                                        changePicture();
                                        Log.d("프로필사진변경성공", new Gson().toJson(response.body()));
                                    } else {
                                        Log.d("프로필 온리스폰스 실패", response.message());
                                    }
                                }

                                @Override
                                public void onFailure(Call<ProfileChangeResponse> call, Throwable t) {
                                    Log.d("프로필 실패", t.getMessage());
                                }
                            });
                        }

                    }
                }
                finish();
            }
        });

        setToolbar();
    }

    private File getRealFile(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        if(uri == null) {
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }

        Cursor cursor = getContentResolver().query(uri, projection, null, null, MediaStore.Images.Media.DATE_MODIFIED + " desc");
        if(cursor == null || cursor.getColumnCount() <1 ) {
            return null;
        }
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        String path = cursor.getString(column_index);

        if(cursor != null) {
            cursor.close();
            cursor = null;
        }

        return new File(path);
    }

    void changeNickname(){
        if(LoginActivity.userAccessToken!=null){
            if(tokenRetrofitInstance!=null){
                Call<UserResponse> call = RetrofitInstance.getRetrofitService().getUser("Bearer "+LoginActivity.userAccessToken);
                call.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if (response.isSuccessful()) {
                            UserResponse result = response.body();
                            MyPageFragment.userNickName.setText(result.getUserItem().getNickname());
                            Log.d("성공", new Gson().toJson(response.body()));
                        } else {
                            Log.d("REST FAILED MESSAGE", response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Log.d("REST ERROR!", t.getMessage());
                    }
                });
            }
        }

    }

    void changePicture(){
        if(LoginActivity.userAccessToken!=null){
            if(tokenRetrofitInstance!=null){
                Call<UserResponse> call = RetrofitInstance.getRetrofitService().getUser("Bearer "+LoginActivity.userAccessToken);
                call.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if (response.isSuccessful()) {
                            UserResponse result = response.body();
                            imageUrl="https://sunhan.s3.ap-northeast-2.amazonaws.com/raw/"+result.getUserItem().getAvatarUrl();
//                            MyPageFragment.userImage.setImageURI(uri);
                            Glide.with(getApplicationContext()).load(imageUrl).error(R.drawable.profile).into(MyPageFragment.userImage);

                            Log.d("성공", new Gson().toJson(response.body()));
                        } else {
                            Log.d("REST FAILED MESSAGE", response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Log.d("REST ERROR!", t.getMessage());
                    }
                });
            }
        }

    }

    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

//    public static String getFullPathFromUri(Context ctx, Uri fileUri) {
//        String fullPath = null;
//        final String column = "_data";
//        Cursor cursor = ctx.getContentResolver().query(fileUri, null, null, null, null);
//        if (cursor != null) {
//            cursor.moveToFirst();
//            String document_id = cursor.getString(0);
//            if (document_id == null) {
//                for (int i=0; i < cursor.getColumnCount(); i++) {
//                    if (column.equalsIgnoreCase(cursor.getColumnName(i))) {
//                        fullPath = cursor.getString(i);
//                        break;
//                    }
//                }
//            } else {
//                document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
//                cursor.close();
//
//                final String[] projection = {column};
//                try {
//                    cursor = ctx.getContentResolver().query(
//                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                            projection, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
//                    if (cursor != null) {
//                        cursor.moveToFirst();
//                        fullPath = cursor.getString(cursor.getColumnIndexOrThrow(column));
//                    }
//                } finally {
//                    if (cursor != null) cursor.close();
//                }
//            }
//        }
//        return fullPath;
//    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE){
            if(resultCode==RESULT_OK&&data!=null){
                try {
                    uri = data.getData();
                    Log.d("글라이드URI",uri.toString());
                    Glide.with(getApplicationContext()).load(uri).into(profile_img);
                    imagePath = getPath(getApplicationContext(),uri);
                    Log.d("글라이드경로",imagePath);
                }catch (Exception e){
                    finish();
                }
            }else if(resultCode==RESULT_CANCELED){
                finish();
            }
        }
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
