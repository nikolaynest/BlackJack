package com.exam.blackjack.core;

import com.exam.blackjack.card.Card;
import com.exam.blackjack.card.CardInfo;
import com.exam.blackjack.card.Deck;
import com.exam.blackjack.dao.DAO;
import com.exam.blackjack.rest.container.request.*;
import com.exam.blackjack.rest.container.response.*;
import com.exam.blackjack.rules.GameRules;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 02.08.15.
 */
public class CoreManager {

    private DAO dao;
    private GameRules rules;
    private Validator validator;
    private Deck deck;
    private Long dealerId = null;
    private Map<String, List<Card>> playerCache = new HashMap<>();
    private Map<String, List<Card>> dealerCache = new HashMap<>();



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

    private long getDealerId() {
        if (dealerId == null) {
            dealerId = dao.getDealerId();
        }
        return dealerId;
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

    private void blaskJack(int rate, long accountId) {
        double win = rules.blackJackWinCalculation(rate);
        RechargeRequest request = new RechargeRequest();
        request.setAccountId(accountId);
        request.setRechargeCash(win);
        this.recharge(request);
    }

    public ParlayResponse makeRate(ParlayRequest request) {
        Integer moneyRate = request.getMoneyRate();
        Long accountId = request.getAccountId();
        double availableCash = dao.getAccount(accountId).getCash();
        validator.parlayValidate(moneyRate, availableCash);

        ParlayResponse response = new ParlayResponse();
        List<CardInfo> playerCards = new ArrayList<>();
        List<CardInfo> dealerCards = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (i % 2 == 0) { //1 и 3 карты - для игрока
                Card card = deck.card();
                CardInfo playerCard = new CardInfo(card, true);
                playerCards.add(playerCard);
            } else { // 2 и 4 - для дилера
                Card card = deck.card();
                CardInfo dealerCard;
                if (i != 3) {
                    dealerCard = new CardInfo(card, true);
                } else {
                    dealerCard = new CardInfo(card, false);
                }
                dealerCards.add(dealerCard);

            }
        }

        if (rules.isBlackJack(playerCards.get(0).getCard(), playerCards.get(1).getCard())) {
            if (rules.isBlackJack(dealerCards.get(0).getCard(), dealerCards.get(1).getCard())) {
                response.setIsPush(true);
            } else {
                response.setIsPush(false);
                response.setIsBlackJack(true);
                response.setWinnerId(request.getAccountId());
                blaskJack(request.getMoneyRate(), request.getAccountId());
            }
        } else {
            if (rules.isBlackJack(dealerCards.get(0).getCard(), dealerCards.get(1).getCard())){
                response.setIsBlackJack(true);
                response.setIsPush(false);
                response.setWinnerId(this.getDealerId());

                SubtractRequest subtractRequest = new SubtractRequest();
                subtractRequest.setFromWhomId(request.getAccountId());
                subtractRequest.setToWhomId(this.getDealerId());
                subtractRequest.setMoneyRate(request.getMoneyRate());
                dao.subtraction(subtractRequest);
            } else {
                response.setIsPush(false);
                response.setIsBlackJack(false);
                response.setWinnerId(null);
            }
        }
        response.setDealerCards(dealerCards);
        response.setPlayerCards(playerCards);
        return response;
    }

    public HitResponse hit(HitRequest request) {
        HitResponse response = new HitResponse();
        response.setAccountId(request.getAccountId());
        response.setCard(deck.card());
        return response;
    }



    public BustResponse bust(SubtractRequest request) {
        Integer moneyRate = request.getMoneyRate();
        Long fromWhomId = request.getFromWhomId();
        Long toWhomId = request.getToWhomId();


        return null;
    }

}
