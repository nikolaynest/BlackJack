package com.exam.blackjack.rest.container.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by nikolay on 03.08.15.
 */
@XmlRootElement(name = "available")
public class RechargeResponse {

    private Integer availableCash;
    private Integer accountId;

    @XmlElement(name = "cash")
    @JsonProperty("cash")
    public Integer getAvailableCash() {
        return availableCash;
    }

    public void setAvailableCash(Integer availableCash) {
        this.availableCash = availableCash;
    }

    @XmlElement(name = "accountId")
    @JsonProperty("accountId")
    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
}
