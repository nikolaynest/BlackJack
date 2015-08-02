package com.exam.blackjack.gamer;

import com.exam.blackjack.card.Card;

import java.util.List;

/**
 * Created by nikolay on 22.07.15.
 */
public interface Shuffle {
    List<Card> shuffle(Card[] deck);
}
