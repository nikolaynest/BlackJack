package com.exam.blackjack.core;

import com.exam.blackjack.card.Card;

import java.util.List;
import java.util.Map;

/**
 * Created by nikolay on 05.09.15.
 */
public interface Cache {

    Map<String, List<Card>> getPlayerMap();
    Map<String, List<Card>> getDealerMap();
    void addPlayerCards(String session, Card... cards);
    void addDealerCards(String session, Card... cards);
    void deleteSessionInfo(String session);

}
