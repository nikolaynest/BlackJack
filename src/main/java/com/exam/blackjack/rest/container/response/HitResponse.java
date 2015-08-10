package com.exam.blackjack.rest.container.response;

import com.exam.blackjack.card.Card;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created on 10.08.15.
 */
@XmlRootElement(name = "response")
public class HitResponse {

    private Long accountId;
    private Card card;

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
}
