package com.selema.newproject;

import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.facebook.FacebookSdk;
import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.facebook.appevents.AppEventsLogger;
import com.selema.newproject.utils.BaseApiService;
import com.selema.newproject.utils.UtilsApi;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class
RegisterActivity extends AppCompatActivity {

    private static final String TAG = "PhoneAuthActivity";
    @BindView(R.id.etNama)
    EditText etNama;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.LastNama)
    EditText Lastname;
    @BindView(R.id.Adress)
    EditText Adress;
    @BindView(R.id.home)
    EditText home;
    @BindView(R.id.bio)
    EditText bio;
    @BindView(R.id.etPhone)
    EditText phone;
    @BindView(R.id.transacion_password_regist_txt)
    EditText transaction_password;
    @BindView(R.id.btnRegister)
    Button btnRegister;

    ProgressDialog loading;
    Context mContext;

    BaseApiService mApiService;
    AccessToken accessToken;
    public static int App_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService();
        accessToken = AccountKit.getCurrentAccessToken();
        if (accessToken != null) {
            AccountKit.logOut();

        }

        initComponents();


    }

    private void initComponents() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "Welcome", true, false);
              requestRegister();

            }
        });
    }


    private void requestRegister() {
        mApiService.registerRequest(
                etEmail.getText().toString(),
                etPassword.getText().toString(),
                etNama.getText().toString(),
                Lastname.getText().toString(),
                home.getText().toString(),
                phone.getText().toString(),
                Adress.getText().toString(),
                transaction_password.getText().toString(),
                bio.getText().toString()
        ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.e("CALL Back", "-----------Succeffuly----------");
                    loading.dismiss();
                    try {
                        String jsonRESULTS = response.body().string();
                        if (jsonRESULTS.equals("registered successfully")) {

                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            Toast.makeText(mContext, jsonRESULTS, Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(mContext, jsonRESULTS, Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.i("debug", "onResponse: GA BERHASIL");
                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                Toast.makeText(mContext, "Internet error", Toast.LENGTH_SHORT).show();
            }
        });
    }


}