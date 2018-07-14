package com.selema.newproject.MyTransactions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by selema on 5/14/18.
 */

public class model {
    @SerializedName("transactions")
    @Expose
    private List<TranactionModel> transactions = null;

    public List<TranactionModel> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TranactionModel> transactions) {
        this.transactions = transactions;
    }
}
