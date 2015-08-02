package com.exam.blackjack.card;

/**
 * масть
 * Created by nikolay on 20.07.15.
 */
public enum Suit {
    SPADES("spades"), HEARTS("hearts"), DIAMONDS("diamonds"), CLUBS("clubs");

    Suit(String suitValue) {
        this.suitValue = suitValue;
    }

    private String suitValue;

    public String getSuitValue() {
        return this.suitValue;
    }
}
