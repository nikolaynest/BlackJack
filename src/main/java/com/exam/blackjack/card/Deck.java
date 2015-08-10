package com.exam.blackjack.card;

import com.exam.blackjack.deck.DeckOperations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;

/**
 * Created on 10.08.15.
 */
public class Deck {

    private LinkedList<Card> deck = new LinkedList<>();

    private DeckOperations operations;

    @Autowired
    public void setOperations(DeckOperations operations) {
        this.operations = operations;
    }

    private void initDeck() {
        Card[] cards = DeckOperations.createDeck();
        deck = (LinkedList<Card>) operations.shuffle(cards);
    }

    public Card card() {
        if (deck.isEmpty()){
            initDeck();
        }
        return operations.pop(deck);
    }

}
