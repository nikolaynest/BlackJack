package com.exam.blackjack.core;

import com.exam.blackjack.card.Card;
import com.exam.blackjack.card.CardOwner;
import com.exam.blackjack.card.Deck;
import com.exam.blackjack.dao.DAO;
import com.exam.blackjack.rest.container.request.*;
import com.exam.blackjack.rest.container.response.*;
import com.exam.blackjack.rules.GameRules;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 02.08.15.
 */
public class CoreManager {

    private DAO dao;
    private GameRules rules;
    private Validator validator;
    private Deck deck;


    @Autowired
    public void setDao(DAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    @Autowired
    public void setRules(GameRules rules) {
        this.rules = rules;
    }

    @Autowired
    public void setDeck(Deck deck) {
        this.deck = deck;
    }


    public AccountResponse getAccount(AccountRequest request) {
        AccountResponse account = dao.getAccount(request.getAccountId());
        return account;
    }

    public AvailableCashResponse recharge(RechargeRequest request) {
        return dao.recharge(request);
    }

    public AvailableCashResponse blackJack(BlackJackRequest request) {
        int rate = request.getMoneyRate();
        long accountId = request.getAccountId();
        double win = rules.blackJackWinCalculation(rate);

        RechargeRequest rechargeRequest = new RechargeRequest();
        rechargeRequest.setAccountId(accountId);
        rechargeRequest.setRechargeCash(win);
        return this.recharge(rechargeRequest);
    }

    public ParlayResponse makeRate(ParlayRequest request) {
        Integer moneyRate = request.getMoneyRate();
        Long accountId = request.getAccountId();
        double availableCash = dao.getAccount(accountId).getCash();
        validator.parlayValidate(moneyRate, availableCash);

        ParlayResponse response = new ParlayResponse();
        List<CardOwner> playerCards = new ArrayList<>();
        List<CardOwner> dillerCards = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (i % 2 == 0) { //1 и 3 карты - для игрока
                Card card = deck.card();
                CardOwner playerCard = new CardOwner(card, true);
                playerCards.add(playerCard);
            } else { // 2 и 4 - для дилера
                Card card = deck.card();
                CardOwner dillerCard;
                if (i != 3) {
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

    public HitResponse hit(HitRequest request) {
        HitResponse response = new HitResponse();
        response.setAccountId(request.getAccountId());
        response.setCard(deck.card());
        return response;
    }

    public BustResponse bust(BustRequest request) {
        Integer moneyRate = request.getMoneyRate();
        Long fromWhomId = request.getFromWhomId();
        Long toWhomId = request.getToWhomId();


        return null;
    }

}
