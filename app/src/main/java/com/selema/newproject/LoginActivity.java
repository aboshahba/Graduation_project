package com.selema.newproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.selema.newproject.MainActivity.MainActivity;
import com.selema.newproject.edit.editpassword;
import com.selema.newproject.utils.BaseApiService;
import com.selema.newproject.utils.Login_Response;
import com.selema.newproject.utils.UserLogin;
import com.selema.newproject.utils.UtilsApi;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyLogin";
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    Button btnforget;
    ProgressDialog loading;
    Context mContext;
    BaseApiService mApiService;
    public static int App_REQUEST_CODE = 1;


    String email;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService();
        initComponents();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
         editor = settings.edit();
    }

    private void initComponents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "WelCome TO our App", true, false);
                requestLogin();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, RegisterActivity.class));
            }
        });


    }

    String jsonRESULTS;

    private void requestLogin() {
        final Intent i = new Intent(this, MainActivity.class);
        mApiService.loginRequest(etEmail.getText().toString().trim(), etPassword.getText().toString().trim())
                .enqueue(new Callback<UserLogin>() {
                    @Override
                    public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                        jsonRESULTS = response.body().getResponseMessage().toString();

                        if (response.isSuccessful()) {
                            loading.dismiss();

                            if (jsonRESULTS.equals("logined successfully")) {
                                editor.putString("phoneNumber", response.body().getPhoneNumber().toString());
                                editor.putString("email", response.body().getEmail().toString());
                                editor.putString("password", response.body().getPassword().toString());
                                editor.putString("transaction_password", response.body().getTransactionPassword().toString());
                                editor.putString("homeCity", response.body().getHomeCity().toString());
                                editor.putString("balance", response.body().getBalance().toString());
                                editor.putString("bio", response.body().getBio().toString());
                                editor.putString("name", response.body().getName().toString());
                                editor.putString("profile_picture", response.body().getProfilePicture().toString());

                                editor.apply();

                                Toast.makeText(mContext, " LOGIN", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(mContext, jsonRESULTS, Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(mContext, jsonRESULTS, Toast.LENGTH_SHORT).show();

                            loading.dismiss();
                        }

                    }

                    @Override
                    public void onFailure(Call<UserLogin> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();

                    }
                });

    }
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }


}
