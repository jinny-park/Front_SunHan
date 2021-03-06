package com.capsaicin.sunhan.View.activity;

import static com.capsaicin.sunhan.View.activity.StoreDetailActivity.whichStore;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.capsaicin.sunhan.Model.ChildrenSendLetterItem;
import com.capsaicin.sunhan.Model.ChildrenSendLetterResponse;
import com.capsaicin.sunhan.Model.CommunityWritingPost;
import com.capsaicin.sunhan.Model.LetterItem;
import com.capsaicin.sunhan.Model.LetterResponse;
import com.capsaicin.sunhan.Model.ProfileChangeResponse;
import com.capsaicin.sunhan.Model.ResultResponse;
import com.capsaicin.sunhan.Model.Retrofit.RetrofitInstance;
import com.capsaicin.sunhan.Model.SunHanSendLetterItem;
import com.capsaicin.sunhan.Model.SunHanSendLetterResponse;
import com.capsaicin.sunhan.Model.TokenResponse;
import com.capsaicin.sunhan.Model.WriterItem;
import com.capsaicin.sunhan.R;
import com.capsaicin.sunhan.View.adapter.LetterAdapter;
import com.capsaicin.sunhan.View.fragment.CommunityFragment;
import com.capsaicin.sunhan.View.fragment.MyPageFragment;
import com.capsaicin.sunhan.View.fragment.StoreLetterFragment;
import com.capsaicin.sunhan.View.interfaceListener.OnClickLetterListener;
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


        //?????? ????????? ?????? ????????????
        Intent intent = getIntent();
        id = intent.getStringExtra("_id");
        whichStore = intent.getIntExtra("whichStore",0);

        setToolbar();

        //????????? ??????
        letter_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            Toast.makeText(getApplicationContext(), "?????? ????????? ????????? ?????? ??????/?????? ??????", Toast.LENGTH_SHORT).show();
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

        sendBtn.setOnClickListener(new View.OnClickListener() { // ?????? ????????? ??????
            @Override
            public void onClick(View view) {
                content = letterContent.getText().toString();
                if(tokenRetrofitInstance!=null){
                    if(content.isEmpty()){ //?????? ????????? ??????
                        letterContent.setError("????????? ??????????????????");
                    }else{
                        if(whichStore==0){
                            sendChildrenStoreLetter(); //????????? ?????? ?????????
                        }else{
                            sendSunHanStoreLetter(); // ??????????????? ?????? ?????????
                        }
                        Toast.makeText(getApplicationContext(), "?????? ????????? ??????!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });
    }

    void setToolbar(){ //?????? ??????
        setSupportActionBar (toolbar); //??????????????? ??????(App Bar)??? ??????
        ActionBar actionBar = getSupportActionBar (); //?????? ????????? ?????? ?????? ?????????
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled (true); // ????????? ???????????? ?????? ?????????
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

        if(imagePath!=null){ // ?????? ????????? ?????? ??? ???
            imageFile = new File(imagePath);
            if (!imageFile.exists()) {       // ????????? ????????? ????????? ????????? ??????
                imageFile.mkdirs();    // ??????????????? ????????? ????????? ?????? ??????
            }
            imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
            filePart = MultipartBody.Part.createFormData("image", imageFile.getName(), imageRequestBody);

            childrenRetrofitMethod(Lid,letter,filePart);

        }else {//?????? ??? ?????? ?????? ?????? ???
            childrenRetrofitMethod(Lid,letter,null);
        }
    }

    //????????? ?????? ?????? ????????? ???????????? ?????????
    private void childrenRetrofitMethod(RequestBody Lid, RequestBody letter,MultipartBody.Part filePart){
        Call<ChildrenSendLetterResponse> call2 = RetrofitInstance.getRetrofitService().sendChildLetterContent("Bearer " + LoginActivity.userAccessToken, Lid, letter, filePart);
        call2.enqueue(new Callback<ChildrenSendLetterResponse>() {
            @Override
            public void onResponse(Call<ChildrenSendLetterResponse> call, Response<ChildrenSendLetterResponse> response) {
                if (response.isSuccessful()) {
                    ChildrenSendLetterResponse result = response.body();
                    Log.d("??????????????????????????????", new Gson().toJson(response.body()));
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
                Toast.makeText(getApplicationContext(), "??????????????? ??????????????????!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendSunHanStoreLetter(){
        RequestBody Lid = RequestBody.create(MediaType.parse("text/plain"), id);
        RequestBody letter = RequestBody.create(MediaType.parse("text/plain"),content);
        if(imagePath!=null){ // ?????? ????????? ?????? ??? ???

            imageFile = new File(imagePath); //????????? ?????? ??????
            if (!imageFile.exists()) {       // ????????? ????????? ????????? ????????? ??????
                imageFile.mkdirs();    // ??????????????? ????????? ????????? ?????? ??????
            }

            //???????????? ?????????????????? ???????????? ????????????
            imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
            filePart = MultipartBody.Part.createFormData("image", imageFile.getName(), imageRequestBody);

            sunHanRetrofitMethod(Lid,letter,filePart);


        }else {//?????? ??? ?????? ?????? ?????? ???
            sunHanRetrofitMethod(Lid,letter,null);
        }
    }

    //??????????????? ?????? ?????? ????????? ???????????? ?????????
    private void sunHanRetrofitMethod(RequestBody Lid, RequestBody letter,MultipartBody.Part filePart){
        Call<SunHanSendLetterResponse> call2 = RetrofitInstance.getRetrofitService().sendSunHanLetterContent("Bearer "+LoginActivity.userAccessToken,Lid,letter,filePart);
        call2.enqueue(new Callback<SunHanSendLetterResponse>() {
            @Override
            public void onResponse(Call<SunHanSendLetterResponse> call, Response<SunHanSendLetterResponse> response) {
                if (response.isSuccessful()) {
                    SunHanSendLetterResponse result = response.body();
                    Log.d("??????????????????", new Gson().toJson(response.body()));
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
                Toast.makeText(getApplicationContext(), "??????????????? ??????????????????!", Toast.LENGTH_SHORT).show();
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

    //????????? ???????????? ????????????
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
                    Log.d("??????????????????", new Gson().toJson(response.body()));
                } else {
                    Log.d("?????????????????? ??????", response.message());
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.d("REST ERROR!", t.getMessage());
            }
        });
    }

}