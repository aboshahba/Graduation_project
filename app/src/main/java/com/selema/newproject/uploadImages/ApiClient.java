package com.selema.newproject.uploadImages;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String Base_Url="https://ee-wallet.herokuapp.com/";
    public static Retrofit retrofit=null;
    public static Retrofit getApiClient()
    {
        if(retrofit==null)
        {
            retrofit=new Retrofit.Builder().baseUrl(Base_Url).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

}
//https://ee-wallet.herokuapp.com/upload?profile_picture=&id=