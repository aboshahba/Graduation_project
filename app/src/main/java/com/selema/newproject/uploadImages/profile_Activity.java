package com.selema.newproject.uploadImages;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.selema.newproject.R;
import com.selema.newproject.uploadImages.Images;
import com.selema.newproject.utils.BaseApiService;
import com.selema.newproject.utils.PermissionListener;
import com.selema.newproject.utils.PermissionUtils;
import com.selema.newproject.utils.UtilsApi;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class profile_Activity extends AppCompatActivity implements PermissionListener {
    String name;
    String phone;
    String email;
    String balance;
    String current;
    String home;
    String bio;
    String ID;

    TextView Name;
    TextView Phone;
    TextView Email;
    TextView Current;
    TextView Home;
    TextView Bio;

    Images images;
    ImageView profileImage;
    SharedPreferences settings;
    String profile_picture;
    BaseApiService BasemApiService;
    public static final String BASE_URL_API = "https://ee-wallet.herokuapp.com/";
    Context mContext;
    private static final int PICK_IMAGE = 007;
    private ApiService mApiService;
    private ProgressDialog mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);
        mContext = this;

        mProgress = new ProgressDialog(this);
        mApiService = ApiManager.getClient().create(ApiService.class);

        BasemApiService = UtilsApi.getAPIService();
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        ID = settings.getString("phoneNumber", "01212260464");
        email = settings.getString("email", "01212260464");
        home = settings.getString("homeCity", "01212260464");
        balance = settings.getString("balance", "01212260464");
        bio = settings.getString("bio", "01212260464");
        name = settings.getString("name", "01212260464");
        profile_picture = settings.getString("profile_picture", "01212260464");

        profileImage = findViewById(R.id.Myprofile_image);
        Name = findViewById(R.id.user_name);
        Home = findViewById(R.id.user_address);
        Phone = findViewById(R.id.user_phone);
        Current = findViewById(R.id.current_address);
        Email = findViewById(R.id.user_email);
        Bio = findViewById(R.id.user_bio);
        TextView balanceTXT = findViewById(R.id.BalanceID);


        Name.setText(name);
        Home.setText(home);
        Phone.setText(ID);
        Bio.setText(bio);
        Email.setText(email);
        balanceTXT.setText(balance);


        Picasso.with(mContext).load("https://ee-wallet.herokuapp.com/images/" + profile_picture).into(profileImage);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImageFromGallery();
            }
        });
    }

    public void getImageFromGallery() {

        PermissionUtils.getInstance(this, this).requestReadPermission();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
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

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PICK_IMAGE:
                    postImageToServer(data.getData());
                    break;
            }
        }
    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
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
        Call<ResponseBody> call = mApiService.uploadPhoto(body, nameRequestBody);
        Log.i("IMAGE EE", "postImageToServer: " + call.request().url().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    showToast("Successfully Uploaded" + response.message());
                } else {
                    Log.d("tag", "onResponse: " + response.message());
                    Log.d("tag", "onResponse: " + response.code());
                    Log.d("tag", "onResponse: " + response.errorBody());
                    Log.d("tag", "onResponse: " + response.message());
                    try {
                        Log.i("kjd", "onResponse: " + response.errorBody().string());
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
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
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