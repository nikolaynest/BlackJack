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
import java.util.List;

/**
 * Created on 02.08.15.
 */
public class CoreManagerImpl implements CoreManager {

    private static final int MAX_VALUE = 21;
    private DAO dao;
    private GameRules rules;
    private Validator validator;
    private Deck deck;
    private Long dealerId = null;
    private SessionManager sessionManager;
    private Cache cache;


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

    @Autowired
    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Autowired
    public void setCache(Cache cache) {
        this.cache = cache;
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

    private void blackJackMoneyReward(int rate, long accountId) {
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
        String session = sessionManager.getSession();


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

        Card dealerCard_1 = dealerCards.get(0).getCard();
        Card dealerCard_2 = dealerCards.get(1).getCard();
        Card playerCard_1 = playerCards.get(0).getCard();
        Card playerCard_2 = playerCards.get(1).getCard();

        if (rules.isBlackJack(playerCard_1, playerCard_2)) {
            if (rules.isBlackJack(dealerCard_1, dealerCard_2)) {
                response.setIsPush(true);
            } else {
                response.setIsPush(false);
                response.setIsBlackJack(true);
                response.setWinnerId(request.getAccountId());
                this.blackJackMoneyReward(request.getMoneyRate(), request.getAccountId());
            }
        } else {
            if (rules.isBlackJack(dealerCard_1, dealerCard_2)) {
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

                cache.setMoneyRate(session, moneyRate);
                cache.addDealerCards(session, (Card[]) dealerCards.toArray());
                cache.addPlayerCards(session, (Card[]) playerCards.toArray());
            }
        }
        response.setDealerCards(dealerCards);
        response.setPlayerCards(playerCards);
        response.setSession(session);
        return response;
    }


    public HitResponse hit(HitRequest request) {
        String session = request.getSession();
        Long accountId = request.getAccountId();
        Card newCard = deck.card();

        HitResponse response = new HitResponse();
        response.setCard(newCard);
        response.setSession(session);
        response.setAccountId(accountId);

        if (accountId != this.getDealerId()) {
            cache.addPlayerCards(session, newCard);
            int playerValues = rules.getValues((Card[]) cache.getPlayerCards(session).toArray());
            if (playerValues > MAX_VALUE) {
                response.setIsBust(true);
                SubtractRequest subtractRequest = this.createSubtractRequest(session, request.getAccountId(), this.getDealerId());
                dao.subtraction(subtractRequest);
            } else {
                response.setIsBust(false);
            }
        } else {
            cache.addDealerCards(session, newCard);
            int dealerValues = rules.getValues((Card[]) cache.getDealerCards(session).toArray());
            if (dealerValues > MAX_VALUE) {
                response.setIsBust(true);
                SubtractRequest subtractRequest = this.createSubtractRequest(session, this.getDealerId(), request.getAccountId());
                dao.subtraction(subtractRequest);
            } else {
                response.setIsBust(false);
            }

        }

        return response;
    }


    public StandResponse stand(StandRequest request) {
        String session = request.getSession();
        List<Card> playerCards = cache.getPlayerCards(session);
        List<Card> dealerCards = cache.getDealerCards(session);
        int playerValues = rules.getValues(playerCards.toArray(new Card[playerCards.size()]));
        int dealerValues = rules.getValues(dealerCards.toArray(new Card[dealerCards.size()]));


        StandResponse response = new StandResponse();
        response.setSession(session);
        List<Card> newDealerCards = new ArrayList<>();

        if (dealerValues > playerValues) {
            response.setIsPlayerWin(false);
            response.setIsPush(false);
            SubtractRequest subtract = this.createSubtractRequest(session, request.getAccountId(), this.dealerId);
            dao.subtraction(subtract);
            return response;
        } else /*d <= p*/{
            if (dealerValues > 17) {
                if (dealerValues < playerValues) {
                    response.setIsPush(false);
                    response.setIsPlayerWin(true);
                    SubtractRequest subtract = this.createSubtractRequest(session, this.dealerId, request.getAccountId());
                    dao.subtraction(subtract);
                    return response;
                }
                if (dealerValues == playerValues) {
                    response.setIsPlayerWin(false);
                    response.setIsPush(true);
                    return response;
                }
            } else /* d <= 17 */ {
                while (dealerValues <=17) {
                    Card dealerNewCard = deck.card();
                    cache.addDealerCards(session, dealerNewCard);
                    List<Card> allDealerCardsFromCache = cache.getDealerCards(session);
                    dealerValues = rules.getValues(allDealerCardsFromCache.toArray(new Card[allDealerCardsFromCache.size()]));
                    newDealerCards.add(dealerNewCard);

                    if (dealerValues <= playerValues && dealerValues <= 17) {
                        continue;
                    }
                    if (dealerValues < playerValues && dealerValues > 17) {
                        response.setIsPlayerWin(true);
                        response.setIsPush(false);
                        response.setNewDealerCards(newDealerCards);
                        SubtractRequest subtract = this.createSubtractRequest(session, this.dealerId, request.getAccountId());
                        dao.subtraction(subtract);
                        return response;
                    }
                    if (dealerValues > playerValues && dealerValues <= MAX_VALUE) {
                        response.setIsPush(false);
                        response.setIsPlayerWin(false);
                        response.setNewDealerCards(newDealerCards);
                        SubtractRequest subtract = this.createSubtractRequest(session, request.getAccountId(), this.dealerId);
                        dao.subtraction(subtract);
                        return response;
                    }
                    if (dealerValues > playerValues && dealerValues > MAX_VALUE) {
                        response.setIsPlayerWin(true);
                        response.setIsPush(false);
                        response.setNewDealerCards(newDealerCards);
                        SubtractRequest subtract = this.createSubtractRequest(session, this.dealerId, request.getAccountId());
                        dao.subtraction(subtract);
                        return response;
                    }
                    if (dealerValues == playerValues && dealerValues > 17) {
                        response.setIsPush(true);
                        response.setIsPlayerWin(false);
                        response.setNewDealerCards(newDealerCards);
                        SubtractRequest subtract = this.createSubtractRequest(session, request.getAccountId(), this.dealerId);
                        dao.subtraction(subtract);
                        return response;
                    }
                }
            }

            return response;
        }
    }

    private SubtractRequest createSubtractRequest(String session, Long fromId, Long toId) {
        SubtractRequest subtractRequest = new SubtractRequest();
        subtractRequest.setMoneyRate(cache.getMoneyRate(session));
        subtractRequest.setFromWhomId(fromId);
        subtractRequest.setToWhomId(toId);
        return subtractRequest;
    }
}
