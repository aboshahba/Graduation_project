package com.selema.newproject.Card;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by selema on 6/24/18.
 */

public class MyCardsModel {
    @SerializedName("cards")
    @Expose
    private List<Card> cards = null;

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
