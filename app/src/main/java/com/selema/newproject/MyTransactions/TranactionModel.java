package com.selema.newproject.MyTransactions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by selema on 5/14/18.
 */

public class TranactionModel {

    @SerializedName("transactionID")
    @Expose
    private Integer transactionID;
    @SerializedName("senderID")
    @Expose
    private String senderID;
    @SerializedName("reciverID")
    @Expose
    private String reciverID;
    @SerializedName("transaction_time")
    @Expose
    private String transactionTime;
    @SerializedName("transaction_message")
    @Expose
    private String transactionMessage;
    @SerializedName("transaction_amount")
    @Expose
    private Integer transactionAmount;

    public Integer getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(Integer transactionID) {
        this.transactionID = transactionID;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getReciverID() {
        return reciverID;
    }

    public void setReciverID(String reciverID) {
        this.reciverID = reciverID;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getTransactionMessage() {
        return transactionMessage;
    }

    public void setTransactionMessage(String transactionMessage) {
        this.transactionMessage = transactionMessage;
    }

    public Integer getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Integer transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

}
