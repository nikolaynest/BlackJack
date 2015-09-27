package com.exam.blackjack.core;

import com.exam.blackjack.card.Card;

import java.util.List;

/**
 * Created by nikolay on 05.09.15.
 */
public interface Cache {

    void setMoneyRate(String session, Integer moneyRate);
    Integer getMoneyRate(String session);
    List<Card> getPlayerCards(String session);
    List<Card> getDealerCards(String session);
    void addPlayerCards(String session, Card... cards);
    void addDealerCards(String session, Card... cards);
    void deleteSessionInfo(String session);

}
