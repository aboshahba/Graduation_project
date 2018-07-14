package com.selema.newproject.Messages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by selema on 5/2/18.
 */
public class Inbox {

    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("messages")
    @Expose
    private List<Messages> messages = null;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Messages> getMessages() {
        return messages;
    }

    public void setMessages(List<Messages> messages) {
        this.messages = messages;
    }

}
