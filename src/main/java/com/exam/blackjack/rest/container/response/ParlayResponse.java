package com.exam.blackjack.rest.container.response;

import com.exam.blackjack.card.CardInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * Created on 25.07.15.
 */
@XmlRootElement(name = "response")
@XmlType(propOrder = {"blackJack", "push", "winnerId", "playerCards", "dealerCards"})
@JsonPropertyOrder({"isBlackJack", "isPush", "winnerId", "playerCards", "dealerCards"})
public class ParlayResponse extends SessionResponse {
    private List<CardInfo> playerCards;
    private List<CardInfo> dealerCards;
    private boolean isBlackJack;
    private Long winnerId;
    private boolean isPush;

    @XmlElement(name = "winnerId")
    @JsonProperty("winnerId")
    public Long getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(Long winnerId) {
        this.winnerId = winnerId;
    }

    @XmlElement(name = "isPush")
    @JsonProperty("isPush")
    public boolean isPush() {
        return isPush;
    }

    public void setIsPush(boolean isPush) {
        this.isPush = isPush;
    }

    @XmlElementWrapper(name = "dealerCards")
    @XmlElement(name = "dealerCard")
    @JsonProperty(value = "dealerCard")
    public List<CardInfo> getDealerCards() {
        return dealerCards;
    }

    public void setDealerCards(List<CardInfo> dealerCards) {
        this.dealerCards = dealerCards;
    }

    @XmlElementWrapper(name = "playerCards")
    @XmlElement(name = "playerCard")
    @JsonProperty(value = "playerCard")
    public List<CardInfo> getPlayerCards() {
        return playerCards;
    }

    public void setPlayerCards(List<CardInfo> playerCards) {
        this.playerCards = playerCards;
    }

    @XmlElement(name = "isBlackJack")
    @JsonProperty("isBlackJack")
    public boolean isBlackJack() {
        return isBlackJack;
    }

    public void setIsBlackJack(boolean isBlackJack) {
        this.isBlackJack = isBlackJack;
    }
}
