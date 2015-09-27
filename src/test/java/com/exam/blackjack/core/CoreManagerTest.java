package com.exam.blackjack.core;

import com.exam.blackjack.card.Card;
import com.exam.blackjack.card.Deck;
import com.exam.blackjack.card.Rank;
import com.exam.blackjack.card.Suit;
import com.exam.blackjack.dao.DAO;
import com.exam.blackjack.rest.container.request.StandRequest;
import com.exam.blackjack.rest.container.request.SubtractRequest;
import com.exam.blackjack.rest.container.response.StandResponse;
import com.exam.blackjack.rules.GameRules;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;

/**
 * Created by nikolay on 20.09.15.
 */
public class CoreManagerTest {


    private static final String SESSION = "SESSION";

    private Long accountId = 10L;
    private StandRequest request;
    @Mock
    private Cache cache;
    @Mock
    private Deck deck;
    @Mock
    private DAO dao;

    @InjectMocks
    private CoreManagerImpl manager;

    @BeforeClass
    public void init() {
        GameRules rules = new GameRules();
        manager = new CoreManagerImpl();
        manager.setRules(rules);
        initMocks(this);
    }

    @BeforeTest
    public void setUp() {
        request = new StandRequest();
        request.setSession(SESSION);
        request.setAccountId(accountId);
    }

    @Test
    public void standDealerWinAtFirstTime() {
        List<Card> playerCards = new ArrayList<>();
        playerCards.add(new Card(Suit.CLUBS, Rank.NINE));
        playerCards.add(new Card(Suit.SPADES, Rank.SEVEN));
        playerCards.add(new Card(Suit.DIAMONDS, Rank.THREE));

        List<Card> dealerCards = new ArrayList<>();
        dealerCards.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        dealerCards.add(new Card(Suit.SPADES, Rank.TEN));

        doReturn(playerCards).when(cache).getPlayerCards(SESSION);
        doReturn(dealerCards).when(cache).getDealerCards(SESSION);
        doReturn(50).when(cache).getMoneyRate(SESSION);
        doNothing().when(dao).subtraction(any(SubtractRequest.class));

        StandResponse response = manager.stand(request);
        assertEquals(response.getNewDealerCards().size(), 0);
        assertEquals(response.getSession(), SESSION);
        assertEquals(response.isPlayerWin(), false);
        assertEquals(response.isPush(), false);
    }

    @Test
    public void standDealerWin_dealerIs17() {
            List<Card> playerCards = new ArrayList<>();
            playerCards.add(new Card(Suit.CLUBS, Rank.NINE));
            playerCards.add(new Card(Suit.SPADES, Rank.SEVEN));

            List<Card> dealerCards = new ArrayList<>();
            dealerCards.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
            dealerCards.add(new Card(Suit.SPADES, Rank.SEVEN));

            doReturn(playerCards).when(cache).getPlayerCards(SESSION);
            doReturn(dealerCards).when(cache).getDealerCards(SESSION);
            doReturn(50).when(cache).getMoneyRate(SESSION);

            StandResponse response = manager.stand(request);
            assertEquals(response.getNewDealerCards().size(), 0);
            assertEquals(response.getSession(), SESSION);
            assertEquals(response.isPlayerWin(), false);
            assertEquals(response.isPush(), false);
    }

    @Test
    public void standDealerWin_dealerLessThan17() {
        List<Card> playerCards = new ArrayList<>();
        playerCards.add(new Card(Suit.CLUBS, Rank.NINE));
        playerCards.add(new Card(Suit.SPADES, Rank.SEVEN));

        List<Card> dealerCards = new ArrayList<>();
        dealerCards.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        dealerCards.add(new Card(Suit.SPADES, Rank.FIVE));

        Card newDealerCard = new Card(Suit.HEARTS, Rank.FIVE);

        List<Card> dealerCardsAfterAdding = new ArrayList<>();
        dealerCardsAfterAdding.addAll(dealerCards);
        dealerCardsAfterAdding.add(newDealerCard);

        doReturn(50).when(cache).getMoneyRate(SESSION);

        doReturn(playerCards).when(cache).getPlayerCards(SESSION);
        when(cache.getDealerCards(SESSION))
                .thenReturn(dealerCards)
                .thenReturn(dealerCardsAfterAdding);
        doReturn(newDealerCard).when(deck).card();
        doNothing().when(cache).addDealerCards(SESSION, newDealerCard);

        StandResponse response = manager.stand(request);

        assertEquals(response.getNewDealerCards().size(), 1);
        assertEquals(response.getNewDealerCards().get(0), newDealerCard);
        assertEquals(response.getSession(), SESSION);
        assertEquals(response.isPlayerWin(), false);
        assertEquals(response.isPush(), false);
    }

    @Test
    public void standDealerFail_dealerEqual17() {
        List<Card> playerCards = new ArrayList<>();
        playerCards.add(new Card(Suit.CLUBS, Rank.TEN));
        playerCards.add(new Card(Suit.SPADES, Rank.FIVE));
        playerCards.add(new Card(Suit.SPADES, Rank.SIX));

        List<Card> dealerCards = new ArrayList<>();
        dealerCards.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        dealerCards.add(new Card(Suit.SPADES, Rank.SEVEN));

        Card newDealerCard = new Card(Suit.HEARTS, Rank.TWO);

        List<Card> dealerCardsAfterAdding = new ArrayList<>();
        dealerCardsAfterAdding.addAll(dealerCards);
        dealerCardsAfterAdding.add(newDealerCard);

        doReturn(50).when(cache).getMoneyRate(SESSION);

        doReturn(playerCards).when(cache).getPlayerCards(SESSION);
        when(cache.getDealerCards(SESSION))
                .thenReturn(dealerCards)
                .thenReturn(dealerCardsAfterAdding);
        doReturn(newDealerCard).when(deck).card();
        doNothing().when(cache).addDealerCards(SESSION, newDealerCard);

        StandResponse response = manager.stand(request);

        assertEquals(response.getNewDealerCards().size(), 1);
        assertEquals(response.getNewDealerCards().get(0), newDealerCard);
        assertEquals(response.getSession(), SESSION);
        assertEquals(response.isPlayerWin(), true);
        assertEquals(response.isPush(), false);
    }

    @Test
    public void standDealerFail_dealerMoreThan17() {
        List<Card> playerCards = new ArrayList<>();
        playerCards.add(new Card(Suit.CLUBS, Rank.NINE));
        playerCards.add(new Card(Suit.SPADES, Rank.TEN));

        List<Card> dealerCards = new ArrayList<>();
        dealerCards.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        dealerCards.add(new Card(Suit.SPADES, Rank.EIGHT));

        List<Card> dealerCardsAfterAdding = new ArrayList<>();
        dealerCardsAfterAdding.addAll(dealerCards);

        doReturn(50).when(cache).getMoneyRate(SESSION);

        doReturn(playerCards).when(cache).getPlayerCards(SESSION);
        when(cache.getDealerCards(SESSION))
                .thenReturn(dealerCards)
                .thenReturn(dealerCardsAfterAdding);

        StandResponse response = manager.stand(request);

        assertEquals(response.getNewDealerCards().size(), 0);
        assertEquals(response.getSession(), SESSION);
        assertEquals(response.isPlayerWin(), true);
        assertEquals(response.isPush(), false);
    }

    @Test
    public void standDealerMoreThan17_EqualsPlayer() {
        List<Card> playerCards = new ArrayList<>();
        playerCards.add(new Card(Suit.CLUBS, Rank.FIVE));
        playerCards.add(new Card(Suit.CLUBS, Rank.FOUR));
        playerCards.add(new Card(Suit.SPADES, Rank.TEN));

        List<Card> dealerCards = new ArrayList<>();
        dealerCards.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        dealerCards.add(new Card(Suit.SPADES, Rank.NINE));

        doReturn(50).when(cache).getMoneyRate(SESSION);

        doReturn(playerCards).when(cache).getPlayerCards(SESSION);
        when(cache.getDealerCards(SESSION)).thenReturn(dealerCards);

        StandResponse response = manager.stand(request);

        assertEquals(response.getNewDealerCards().size(), 0);
        assertEquals(response.getSession(), SESSION);
        assertEquals(response.isPlayerWin(), false);
        assertEquals(response.isPush(), true);
    }
}
