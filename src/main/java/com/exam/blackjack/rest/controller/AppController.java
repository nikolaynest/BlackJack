package com.exam.blackjack.rest.controller;

import com.exam.blackjack.card.Card;
import com.exam.blackjack.card.CardOwner;
import com.exam.blackjack.core.CoreManager;
import com.exam.blackjack.deck.DeckOperations;
import com.exam.blackjack.rest.container.request.AccountRequest;
import com.exam.blackjack.rest.container.request.ParlayRequest;
import com.exam.blackjack.rest.container.response.AccountResponse;
import com.exam.blackjack.rest.container.response.DealCardsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created on 25.07.15.
 */
@RestController
@RequestMapping(value = "/blackjack",
consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class AppController {

    private LinkedList<Card> deck = null;
    private DeckOperations operations = new DeckOperations();
    private CoreManager manager;

    @Autowired
    public AppController(CoreManager manager) {
        this.manager = manager;
    }

    private void initDeck() {
        if (deck == null) {
            Card[] cards = DeckOperations.createDeck();
            deck = (LinkedList<Card>) operations.shuffle(cards);
        }
    }

    @RequestMapping(value = "/account", method = POST)
    public AccountResponse accountAccess(@RequestBody AccountRequest request) {
        return manager.getAccount(request);
    }

    @RequestMapping(value = "/parlay", method = POST)
    public DealCardsResponse makeRate(@RequestBody ParlayRequest request) {
        //validate request
        initDeck();

        DealCardsResponse response = new DealCardsResponse();
        List<CardOwner> playerCards = new ArrayList<>();
        List<CardOwner> dillerCards = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (i % 2 == 0) { //
                Card card = operations.pop(deck);
                CardOwner playerCard = new CardOwner(card, true);
                playerCards.add(playerCard);
            } else {
                Card card = operations.pop(deck);
                CardOwner dillerCard;
                if (i != 3){
                    dillerCard = new CardOwner(card, true);
                } else {
                    dillerCard = new CardOwner(card, false);
                }
                dillerCards.add(dillerCard);

            }
        }
        response.setDillerCards(dillerCards);
        response.setPlayerCards(playerCards);
        return response;
    }
}
