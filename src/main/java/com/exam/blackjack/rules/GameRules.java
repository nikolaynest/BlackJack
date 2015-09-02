package com.exam.blackjack.rules;

import com.exam.blackjack.card.Card;
import com.exam.blackjack.card.Rank;

/**
 * Created on 09.08.15.
 */
public class GameRules {

    public double blackJackWinCalculation(int moneyRate) {
        return moneyRate * 1.5;
    }

    public boolean isBlackJack(Card a, Card b) {
        if (Rank.ACE.equals(a.getRank()) & b.getValue() == 10) {
            return true;
        }
        if (Rank.ACE.equals(b.getRank()) & a.getValue() == 10) {
            return true;
        }
        return false;
    }

    public int getValues(Card[] cards) {
        int aceNumb = 0;
        int values = 0;
        for (Card card : cards) {
            if (card.getRank().equals(Rank.ACE)) {
                aceNumb++;
            } else {
                values += card.getValue();
            }
        }
        if (values > 10) {
            if (aceNumb > 0) {
                values += aceNumb;
            }
        } else if (aceNumb > 0) {
            values += aceNumb * 10 + aceNumb;
        }
        return values;
    }
}
