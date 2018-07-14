package com.selema.newproject.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by selema on 4/21/18.
 */

public class Login_Response {
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("user")
    @Expose
    private UserLogin user;

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public UserLogin getUser() {
        return user;
    }

    public void setUser(UserLogin user) {
        this.user = user;
    }

}
