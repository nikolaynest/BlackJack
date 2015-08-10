package com.exam.blackjack.deck;

import com.exam.blackjack.card.Card;
import com.exam.blackjack.card.Rank;
import com.exam.blackjack.card.Suit;
import com.exam.blackjack.gamer.Shuffle;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created on 21.07.15.
 */
public class DeckOperations implements Shuffle {

    public static final int CARDS_NUMBERS = 52;

    public static Card[] createDeck() {
        Card[] deck = new Card[CARDS_NUMBERS];
        Suit[] suits = new Suit[]{Suit.CLUBS, Suit.DIAMONDS, Suit.HEARTS, Suit.SPADES};
        Rank[] ranks = new Rank[]
                {Rank.TWO, Rank.THREE, Rank.FOUR, Rank.FIVE,
                Rank.SIX, Rank.SEVEN, Rank.EIGHT, Rank.NINE, Rank.TEN,
                Rank.ACE, Rank.JACK, Rank.QUEEN, Rank.KING};
        int i = 0;
        for (Suit suit : suits) {
            for (Rank rank : ranks){
                Card card = new Card(suit, rank, rank.getValue());
                deck[i++] = card;
            }
        }
        return deck;
    }

    public List<Card> shuffle(Card[] deck) {
        int N = deck.length;
        for (int i = 0; i < N; i++) {
            int r = new Random().nextInt(i + 1);
            Card temp = deck[r];
            deck[r] = deck[i];
            deck[i] = temp;
        }
        return new LinkedList<>(Arrays.asList(deck));
    }

    public Card pop(LinkedList<Card> deck) {
        return deck.pop();
    }
}
