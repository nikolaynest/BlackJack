package com.exam.blackjack.gamer;

import com.exam.blackjack.card.Card;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by nikolay on 22.07.15.
 */
public class Diller implements Shuffle {


    @Override
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
