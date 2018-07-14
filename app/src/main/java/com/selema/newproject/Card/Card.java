package com.selema.newproject.Card;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by selema on 6/24/18.
 */

public class Card {


    @SerializedName("cardNumber")
    @Expose
    private String cardNumber;
    @SerializedName("expiredDate")
    @Expose
    private String expiredDate;
    @SerializedName("cvv")
    @Expose
    private String cvv;
    @SerializedName("balance")
    @Expose
    private Integer balance;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }


}
