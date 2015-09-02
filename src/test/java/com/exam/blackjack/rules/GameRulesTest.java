package com.exam.blackjack.rules;

import com.exam.blackjack.card.Card;
import com.exam.blackjack.card.Rank;
import com.exam.blackjack.card.Suit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created on 09.08.15.
 */
public class GameRulesTest {

    private GameRules rules;

    @BeforeClass
    public void init() {
        rules = new GameRules();
    }

    @Test
    public void testBlackJackWinCalculation() {
        int money = 5;
        double win = rules.blackJackWinCalculation(money);
        assertEquals(win, 7.5);
    }

    @Test
    public void testBlackJackWinCalculation2() {
        int money = 20;
        double win = rules.blackJackWinCalculation(money);
        assertEquals(win, 30.0);
    }

    @Test
    public void testBlackJackWinCalcNotEquals() {
        int money = 30;
        double win = rules.blackJackWinCalculation(money);
        assertNotEquals(win, 50);
    }

    @Test
    public void testIsBlackJackTrue() {
        Card a = new Card(Suit.CLUBS, Rank.ACE, 1);
        Card b = new Card(Suit.HEARTS, Rank.JACK, 10);
        boolean isBlackJack = rules.isBlackJack(a, b);
        assertTrue(isBlackJack);
    }
}
