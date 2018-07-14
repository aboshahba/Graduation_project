package com.selema.newproject.Messages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by selema on 4/28/18.
 */
public class Messages {

    @SerializedName("messageId")
    @Expose
    private Integer messageId;
    @SerializedName("senderID")
    @Expose
    private String senderID;
    @SerializedName("receiverID")
    @Expose
    private String receiverID;
    @SerializedName("message_content")
    @Expose
    private String messageContent;
    @SerializedName("message_time")
    @Expose
    private String messageTime;
    @SerializedName("requested_Money")
    @Expose
    private String requestedMoney;

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getRequestedMoney() {
        return requestedMoney;
    }

    public void setRequestedMoney(String requestedMoney) {
        this.requestedMoney = requestedMoney;
    }

}