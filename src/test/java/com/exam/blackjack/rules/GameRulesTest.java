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

    @Test
    public void testGetValuesSimple() {
        Card a = new Card(Suit.CLUBS, Rank.TWO, 2);
        Card b = new Card(Suit.CLUBS, Rank.NINE, 9);
        Card c = new Card(Suit.CLUBS, Rank.KING, 10);
        int values = rules.getValues(new Card[]{a, b, c});
        assertEquals(21, values);
    }

    @Test
    public void testGetValuesWithAceAsOne() {
        Card a = new Card(Suit.CLUBS, Rank.TEN, 10);
        Card b = new Card(Suit.CLUBS, Rank.KING, 10);
        Card c = new Card(Suit.CLUBS, Rank.ACE, 1);
        int values = rules.getValues(new Card[]{a, b, c});
        assertEquals(21, values);
    }

    @Test
    public void testGetValuesWithAceAsEleven() {
        Card a = new Card(Suit.CLUBS, Rank.SEVEN, 7);
        Card b = new Card(Suit.CLUBS, Rank.THREE, 3);
        Card c = new Card(Suit.CLUBS, Rank.ACE, 1);
        int values = rules.getValues(new Card[]{a, b, c});
        assertEquals(21, values);
    }

    @Test
    public void testGetValuesWithAceWhenLessTen() {
        Card a = new Card(Suit.CLUBS, Rank.SEVEN, 6);
        Card b = new Card(Suit.CLUBS, Rank.THREE, 3);
        Card c = new Card(Suit.CLUBS, Rank.ACE, 1);
        int values = rules.getValues(new Card[]{a, b, c});
        assertEquals(20, values);
    }

    @Test
    public void testGetValuesTwoAces() {
        Card a = new Card(Suit.HEARTS, Rank.ACE, 1);
        Card b = new Card(Suit.CLUBS, Rank.ACE, 1);
        int values = rules.getValues(new Card[]{a, b});
        assertEquals(12, values);
    }

    @Test
    public void testGetValuesTreeAndFourAces() {
        Card a = new Card(Suit.HEARTS, Rank.ACE, 1);
        Card b = new Card(Suit.CLUBS, Rank.ACE, 1);
        Card c = new Card(Suit.DIAMONDS, Rank.ACE, 1);
        Card d = new Card(Suit.SPADES, Rank.ACE, 1);

        int values = rules.getValues(new Card[]{a, b, c});
        assertEquals(13, values);
        values = rules.getValues(new Card[]{a,b,c,d});
        assertEquals(14, values);
    }
}
