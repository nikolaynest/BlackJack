package com.exam.blackjack.core;

import com.exam.blackjack.card.Card;

import java.util.*;

/**
 * Created by nikolay on 20.08.15.
 */
public class CardsCache implements Cache {

    private Map<String, List<Card>> playerCards = new HashMap<>();
    private Map<String, List<Card>> dealerCards = new HashMap<>();

    @Override
    public Map<String, List<Card>> getPlayerMap() {
        return playerCards;
    }

    @Override
    public Map<String, List<Card>> getDealerMap() {
        return dealerCards;
    }

    @Override
    public void addPlayerCards(String session, Card... cardsToAdd) {
        this.add(playerCards, session, cardsToAdd);
    }

    @Override
    public void addDealerCards(String session, Card... cardsToAdd) {
        this.add(dealerCards, session, cardsToAdd);
    }

    public void deleteSessionInfo(String session) {
        this.remove(session, dealerCards);
        this.remove(session, playerCards);
    }

    private void add(Map<String, List<Card>> map, String session, Card... cardsToAdd) {
        if (map.containsKey(session)) {
            List<Card> cards = map.get(session);
            cards.addAll(Arrays.asList(cardsToAdd));
        } else {
            ArrayList<Card> cards = new ArrayList<>();
            cards.addAll(Arrays.asList(cardsToAdd));
            map.put(session, cards);
        }
    }

    private void remove(String session, Map<String, List<Card>> map) {
        if (map.containsKey(session)) {
            map.remove(session);
        }
    }

}
