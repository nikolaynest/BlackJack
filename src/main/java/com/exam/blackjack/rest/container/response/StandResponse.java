package com.exam.blackjack.rest.container.response;

import com.exam.blackjack.card.Card;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikolay on 14.09.15.
 */
@XmlRootElement(name = "response")
public class StandResponse extends SessionResponse {

    private List<Card> newDealerCards;
    private boolean isPlayerWin;
    private boolean isPush;

    @XmlElementWrapper(name = "newDealerCards")
    @XmlElement(name = "newDealerCard")
    @JsonProperty("newDealerCard")
    public List<Card> getNewDealerCards() {
        if (newDealerCards == null) {
            newDealerCards = new ArrayList<>();
        }
        return newDealerCards;
    }

    public void setNewDealerCards(List<Card> newDealerCards) {
        this.newDealerCards = newDealerCards;
    }

    @XmlElement(name = "isPlayerWin")
    @JsonProperty("isPlayerWin")
    public boolean isPlayerWin() {
        return isPlayerWin;
    }

    public void setIsPlayerWin(boolean isPlayerWin) {
        this.isPlayerWin = isPlayerWin;
    }

    @XmlElement(name = "isPush")
    @JsonProperty("isPush")
    public boolean isPush() {
        return isPush;
    }

    public void setIsPush(boolean isPush) {
        this.isPush = isPush;
    }
}
