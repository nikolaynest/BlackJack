package com.exam.blackjack.card;

/**
 * Created by nikolay on 21.07.15.
 */
public enum Rank {
    TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), ACE(1), JACK(11), QUEEN(12), KING(13);

    private int value;

    Rank(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}