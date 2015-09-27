package com.exam.blackjack.core;

import com.exam.blackjack.card.Card;

import java.util.*;

/**
 * Created on 20.08.15.
 */
public class CardsCache implements Cache {

    private Map<String, List<Card>> playerCardsMap = new HashMap<>();
    private Map<String, List<Card>> dealerCardsMap = new HashMap<>();
    private Map<String, Integer> rate = new HashMap<>();

    @Override
    public void setMoneyRate(String session, Integer moneyRate) {
        rate.put(session, moneyRate);
    }

    @Override
    public Integer getMoneyRate(String session) {
        return rate.get(session);
    }

    public List<Card> getPlayerCards(String session) {
        return playerCardsMap.get(session);
    }

    public List<Card> getDealerCards(String session) {
        return dealerCardsMap.get(session);
    }

    @Override
    public void addPlayerCards(String session, Card... cardsToAdd) {
        this.add(playerCardsMap, session, cardsToAdd);
    }

    @Override
    public void addDealerCards(String session, Card... cardsToAdd) {
        this.add(dealerCardsMap, session, cardsToAdd);
    }

    public void deleteSessionInfo(String session) {
        this.remove(session, dealerCardsMap);
        this.remove(session, playerCardsMap);
        this.rate.remove(session);
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
