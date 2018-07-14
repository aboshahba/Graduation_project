package com.selema.newproject.utils;

/**
 * Created by umarfadil on 12/21/17.
 */

public class UtilsApi {

    public static final String BASE_URL_API = "https://ee-wallet.herokuapp.com/";

    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }

}
