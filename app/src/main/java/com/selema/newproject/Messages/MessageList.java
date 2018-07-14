package com.selema.newproject.Messages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by selema on 4/28/18.
 */

public class MessageList {
    @SerializedName("inbox")
    @Expose
    private List<Inbox> inbox = null;

    public List<Inbox> getInbox() {
        return inbox;
    }

    public void setInbox(List<Inbox> inbox) {
        this.inbox = inbox;
    }
}


