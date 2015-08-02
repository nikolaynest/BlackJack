package com.exam.blackjack.rest.container.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by nikolay on 25.07.15.
 */
@XmlRootElement(name = "parlay")
public class ParlayRequest {

    private Integer moneyRate;

    @XmlElement(name = "money")
    @JsonProperty("money")
    public Integer getMoneyRate() {
        return moneyRate;
    }

    public void setMoneyRate(Integer moneyRate) {
        this.moneyRate = moneyRate;
    }
}
