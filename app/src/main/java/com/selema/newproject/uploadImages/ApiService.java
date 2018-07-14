package com.selema.newproject.uploadImages;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {


    @Multipart
    @POST("/storage/old_upload")
    Call<ResponseBody> uploadPhoto(@Part MultipartBody.Part profile_picture,
                                   @Part("id") RequestBody name);
//https://ee-wallet.herokuapp.com/upload?profile_picture=&id=
}