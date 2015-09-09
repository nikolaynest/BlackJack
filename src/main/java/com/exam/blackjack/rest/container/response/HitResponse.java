package com.exam.blackjack.rest.container.response;

import com.exam.blackjack.card.Card;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created on 10.08.15.
 */
@XmlRootElement(name = "response")
public class HitResponse extends SessionResponse {

    private Long accountId;
    private Card card;
    private boolean isBust;

    @XmlElement(name = "accountId")
    @JsonProperty("accountId")
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @XmlElement(name = "card")
    @JsonProperty("card")
    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    @XmlElement(name = "isBust")
    @JsonProperty("isBust")
    public boolean isBust() {
        return isBust;
    }

    public void setIsBust(boolean isBust) {
        this.isBust = isBust;
    }
}
