package com.exam.blackjack.rest.container.response;

import com.exam.blackjack.card.CardOwner;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by nikolay on 25.07.15.
 */
@XmlRootElement(name = "dealResponse")
public class DealCardsResponse {
    private List<CardOwner> playerCards;
    private List<CardOwner> dillerCards;

    @XmlElementWrapper(name = "dillerCards")
    @XmlElement(name = "dillerCard")
    @JsonProperty(value = "dillerCard")
    public List<CardOwner> getDillerCards() {
        return dillerCards;
    }

    public void setDillerCards(List<CardOwner> dillerCards) {
        this.dillerCards = dillerCards;
    }

    @XmlElementWrapper(name = "playerCards")
    @XmlElement(name = "playerCard")
    @JsonProperty(value = "playerCard")
    public List<CardOwner> getPlayerCards() {
        return playerCards;
    }

    public void setPlayerCards(List<CardOwner> playerCards) {
        this.playerCards = playerCards;
    }
}
