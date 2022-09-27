package com.capsaicin.sunhan.View.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.capsaicin.sunhan.Model.ChildrenSendLetterResponse;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.Model.SunHanSendLetterResponse;
import com.capsaicin.sunhan.Model.TokenResponse;
import com.capsaicin.sunhan.R;
import com.google.gson.Gson;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriteLetterActivity extends AppCompatActivity {
    Button sendBtn;
    EditText letterContent;
    ImageView letter_image;
    File imageFile;
    Uri uri;
    String imagePath;
    private String id;
    private int whichStore;
    RequestBody imageRequestBody;
    MultipartBody.Part filePart;
    private RetrofitInstance tokenRetrofitInstance ;
    private static final int REQUEST_CODE = 0;
    String content;
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_letter);

        sendBtn = findViewById(R.id.send_letter_btn);
        letter_image = findViewById(R.id.write_letter_image);
        letterContent = findViewById(R.id.write_letter_editText);
        tokenRetrofitInstance = RetrofitInstance.getRetrofitInstance();
        toolbar = findViewById(R.id.write_letter_toolbar);


        //가게 디테일 정보 받아오기
        Intent intent = getIntent();
        id = intent.getStringExtra("_id");
        whichStore = intent.getIntExtra("whichStore",0);

        setToolbar();

        //갤러리 접근
        letter_image.setOnClickListener(new View.OnClickListener() {
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

        sendBtn.setOnClickListener(new View.OnClickListener() { // 편지 보내기 버튼
            @Override
            public void onClick(View view) {
                content = letterContent.getText().toString();
                if(tokenRetrofitInstance!=null){
                    if(content.isEmpty()){ //편지 공백일 경우
                        letterContent.setError("내용을 입력해주세요");
                    }else{
                        if(whichStore==0){
                            sendChildrenStoreLetter(); //가맹점 편지 보내기
                        }else{
                            sendSunHanStoreLetter(); // 선한영향력 편지 보내기
                        }
                        Toast.makeText(getApplicationContext(), "편지 보내기 성공!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });
    }

    void setToolbar(){ //툴바 설정
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

    private void sendChildrenStoreLetter(){
        RequestBody Lid = RequestBody.create(MediaType.parse("text/plain"), id);
        RequestBody letter = RequestBody.create(MediaType.parse("text/plain"),content);

        if(imagePath!=null){ // 사진 넣어서 편지 쓸 때
            imageFile = new File(imagePath);
            if (!imageFile.exists()) {       // 원하는 경로에 폴더가 있는지 확인
                imageFile.mkdirs();    // 하위폴더를 포함한 폴더를 전부 생성
            }
            imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
            filePart = MultipartBody.Part.createFormData("image", imageFile.getName(), imageRequestBody);

            childrenRetrofitMethod(Lid,letter,filePart);

        }else {//사진 안 넣은 편지 보낼 때
            childrenRetrofitMethod(Lid,letter,null);
        }
    }

    //가맹점 가게 편지 보내는 레트로핏 메소드
    private void childrenRetrofitMethod(RequestBody Lid, RequestBody letter,MultipartBody.Part filePart){
        Call<ChildrenSendLetterResponse> call2 = RetrofitInstance.getRetrofitService().sendChildLetterContent("Bearer " + LoginActivity.userAccessToken, Lid, letter, filePart);
        call2.enqueue(new Callback<ChildrenSendLetterResponse>() {
            @Override
            public void onResponse(Call<ChildrenSendLetterResponse> call, Response<ChildrenSendLetterResponse> response) {
                if (response.isSuccessful()) {
                    ChildrenSendLetterResponse result = response.body();
                    Log.d("아동급식카드편지성공", new Gson().toJson(response.body()));
                } else {
                    if(response.message().equals("Unauthorized")){
                        checkAuthorized();
                        childrenRetrofitMethod(Lid,letter,filePart);
                    }
                    Log.d("REST FAILED MESSAGE", response.message());
                }
            }
            @Override
            public void onFailure(Call<ChildrenSendLetterResponse> call, Throwable t) {
                Log.d("REST ERROR!", t.getMessage());
                Toast.makeText(getApplicationContext(), "네트워크를 확인해주세요!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendSunHanStoreLetter(){
        RequestBody Lid = RequestBody.create(MediaType.parse("text/plain"), id);
        RequestBody letter = RequestBody.create(MediaType.parse("text/plain"),content);
        if(imagePath!=null){ // 사진 넣어서 편지 쓸 때

            imageFile = new File(imagePath); //이미지 파일 생성
            if (!imageFile.exists()) {       // 원하는 경로에 폴더가 있는지 확인
                imageFile.mkdirs();    // 하위폴더를 포함한 폴더를 전부 생성
            }

            //이미지를 멀티파트바디 형식으로 변경하기
            imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
            filePart = MultipartBody.Part.createFormData("image", imageFile.getName(), imageRequestBody);

            sunHanRetrofitMethod(Lid,letter,filePart);


        }else {//사진 안 넣은 편지 보낼 때
            sunHanRetrofitMethod(Lid,letter,null);
        }
    }

    //선한영향력 가게 편지 보내는 레트로핏 메소드
    private void sunHanRetrofitMethod(RequestBody Lid, RequestBody letter,MultipartBody.Part filePart){
        Call<SunHanSendLetterResponse> call2 = RetrofitInstance.getRetrofitService().sendSunHanLetterContent("Bearer "+LoginActivity.userAccessToken,Lid,letter,filePart);
        call2.enqueue(new Callback<SunHanSendLetterResponse>() {
            @Override
            public void onResponse(Call<SunHanSendLetterResponse> call, Response<SunHanSendLetterResponse> response) {
                if (response.isSuccessful()) {
                    SunHanSendLetterResponse result = response.body();
                    Log.d("선한편지성공", new Gson().toJson(response.body()));
                } else {
                    if(response.message().equals("Unauthorized")){
                        checkAuthorized();
                        sunHanRetrofitMethod(Lid,letter,filePart);
                    }
                    Log.d("REST FAILED MESSAGE", response.message());
                }
            }
            @Override
            public void onFailure(Call<SunHanSendLetterResponse> call, Throwable t) {
                Log.d("REST ERROR!", t.getMessage());
                Toast.makeText(getApplicationContext(), "네트워크를 확인해주세요!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            if(resultCode==RESULT_OK&&data!=null){
                try {
                    uri = data.getData();
                    Glide.with(getApplicationContext()).load(uri).into(letter_image);
                    imagePath = getPath(getApplicationContext(),uri);
                }catch (Exception e){
                    finish();
                }
            }else if(resultCode==RESULT_CANCELED){
                finish();
            }
        }
    }

    //사진의 절대경로 불러오기
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

    private void checkAuthorized(){
        Call<TokenResponse> call = RetrofitInstance.getRetrofitService().getRefreshToken("Bearer "+LoginActivity.userAccessToken,LoginActivity.userRefreshToken );
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

}