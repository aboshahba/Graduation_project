package com.selema.newproject.uploadImages;

import com.google.gson.annotations.SerializedName;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class ApiManager {
    public static final String BASE_URL = "https://ee-wallet.herokuapp.com";
    private static Retrofit retrofit = null;

    private static OkHttpClient okHttpClient;

    public static Retrofit getClient() {
        okHttpClient = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).build();
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}