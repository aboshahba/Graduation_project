package com.selema.newproject.uploadImages;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.selema.newproject.R;
import com.selema.newproject.utils.PermissionListener;
import com.selema.newproject.utils.PermissionUtils;

import java.io.File;
import java.io.IOException;

import gun0912.tedbottompicker.TedBottomPicker;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Images extends AppCompatActivity implements PermissionListener {

    private static final int PICK_IMAGE = 007;
    private ApiService mApiService;
    private ProgressDialog mProgress;

    SharedPreferences settings;
    String ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        settings = PreferenceManager.getDefaultSharedPreferences(this);
        ID = settings.getString("phoneNumber", "01212260464");


        mProgress = new ProgressDialog(this);
        Button button = (Button) findViewById(R.id.button_pick);
        mApiService = ApiManager.getClient().create(ApiService.class);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.button_pick:
                        getImageFromGallery();
                        break;
                }

            }
        });
    }


    public void getImageFromGallery() {

        PermissionUtils.getInstance(this, this).requestReadPermission();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PermissionUtils.WRITE_EXTERNAL_STORAGE_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_PICK);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK){
            switch (requestCode){
                case PICK_IMAGE:
                    postImageToServer(data.getData());
                    break;
            }
        }
    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    private void postImageToServer(Uri uri) {

        mProgress.setMessage("Uploading Please wait...");
        mProgress.show();

        File file = new File(getRealPathFromUri(this, uri));

        Log.d("tag", "postImageToServer: " + uri.getPath());

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);


        RequestBody nameRequestBody = RequestBody.create(MediaType.parse("text/plain"), ID);
        Call<ResponseBody> call = mApiService.uploadPhoto(body,nameRequestBody);
        Log.i("IMAGE EE", "postImageToServer: " + call.request().url().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    showToast("Successfully Uploaded" + response.message());
                }else{
                    Log.d("tag", "onResponse: " + response.message());
                    Log.d("tag", "onResponse: " + response.code());
                    Log.d("tag", "onResponse: " + response.errorBody());
                    Log.d("tag", "onResponse: " + response.message());
                    try {
                        Log.i("kjd", "onResponse: "+ response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                mProgress.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showToast("Failed Uploading image" + " " + t.getMessage());
                t.printStackTrace();
                mProgress.dismiss();

            }
        });

    }

    private void showToast(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionGranted() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    public void onPermissionDenied() {

    }
}