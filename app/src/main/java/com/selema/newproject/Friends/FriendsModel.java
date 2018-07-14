package com.selema.newproject.Friends;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by selema on 6/24/18.
 */

public class FriendsModel {

    @SerializedName("friends")
    @Expose
    private List<Friend> friends = null;

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }
}
