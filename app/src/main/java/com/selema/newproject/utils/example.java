package com.selema.newproject.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class example {

    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("user")
    @Expose
    private User user;

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
