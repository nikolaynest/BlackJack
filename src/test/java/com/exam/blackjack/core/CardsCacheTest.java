package com.exam.blackjack.core;

import com.exam.blackjack.card.Card;
import com.exam.blackjack.card.Rank;
import com.exam.blackjack.card.Suit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * Created on 21.09.15.
 */
public class CardsCacheTest {

    private CardsCache cache;
    private static final String SESSION = "SESSION_12345";


    @BeforeMethod
    public void init() {
        cache = new CardsCache();
    }



    @Test
    public void testSetMoneyRate() {
        String session = "13456";
        Integer moneyRate = 100;
        cache.setMoneyRate(session, moneyRate);
        Integer actual = cache.getMoneyRate(session);
        assertEquals(actual, moneyRate);
    }

    @Test
    public void testSetMoneyRateMoreThan128() {
        String session = "13456";
        Integer moneyRate = 200;
        cache.setMoneyRate(session, moneyRate);
        Integer actual = cache.getMoneyRate(session);
        assertEquals(actual, moneyRate);
    }

    @Test
    public void testSetMoneyRateMoreThan128ChangeValue() {
        String session = "13456";
        Integer moneyRate = 200;
        Integer secondRate = 300;
        cache.setMoneyRate(session, moneyRate);
        cache.setMoneyRate(session, secondRate);
        Integer actual = cache.getMoneyRate(session);
        assertEquals(actual, secondRate);
    }

    @Test
    public void testAddDealerCardOne() {
        Card first = new Card(Suit.CLUBS, Rank.ACE);
        cache.addDealerCards(SESSION, first);
        assertEquals(cache.getDealerCards(SESSION).size(), 1);
        assertEquals(cache.getDealerCards(SESSION).get(0), first);
    }

    @Test
    public void testAddTwoDealerCards() {
        Card first = new Card(Suit.CLUBS, Rank.ACE);
        Card second = new Card(Suit.HEARTS, Rank.FIVE);

        cache.addDealerCards(SESSION, first);
        assertEquals(cache.getDealerCards(SESSION).size(), 1);
        assertEquals(cache.getDealerCards(SESSION).get(0), first);

        cache.addDealerCards(SESSION, second);
        assertEquals(cache.getDealerCards(SESSION).size(), 2);
        assertEquals(cache.getDealerCards(SESSION).get(1), second);

    }

    @Test
    public void testAddTwoDealerCardsWithAnotherSession() {
        Card first = new Card(Suit.CLUBS, Rank.ACE);
        Card second = new Card(Suit.HEARTS, Rank.FIVE);

        String session2 = "session2";
        cache.addDealerCards(SESSION, first);
        assertEquals(cache.getDealerCards(SESSION).size(), 1);
        assertEquals(cache.getDealerCards(SESSION).get(0), first);

        cache.addDealerCards(session2, second);
        assertEquals(cache.getDealerCards(SESSION).size(), 1);
        assertEquals(cache.getDealerCards(SESSION).get(0), first);
        assertEquals(cache.getDealerCards(session2).size(), 1);
        assertEquals(cache.getDealerCards(session2).get(0), second);
    }

    @Test
    public void testRemove() {
        Card toBeRemoved = new Card(Suit.HEARTS, Rank.FIVE);
        cache.addDealerCards(SESSION, toBeRemoved);

        assertEquals(cache.getDealerCards(SESSION).size(), 1);
        assertEquals(cache.getDealerCards(SESSION).get(0), toBeRemoved);

        cache.deleteSessionInfo(SESSION);
        assertNull(cache.getDealerCards(SESSION));
    }

}
