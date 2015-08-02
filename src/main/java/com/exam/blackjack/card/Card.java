package com.exam.blackjack.card;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by nikolay on 20.07.15.
 */
public class Card {

    private Suit suit;
    private Rank rank;
    private int value;

    public Card(Suit suit, Rank rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    @XmlElement(name = "suit")
    public Suit getSuit(){
        return suit;
    }
    @XmlElement(name = "value")
    public int getValue() {
        return value;
    }
    @XmlElement(name = "rank")
    public Rank getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return "Card{" +
                "suit=" + suit +
                ", rank=" + rank +
                ", value=" + value +
                '}';
    }
}
