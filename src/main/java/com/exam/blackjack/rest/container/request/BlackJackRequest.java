package com.exam.blackjack.rest.container.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created on 09.08.15.
 */
@XmlRootElement(name = "request")
public class BlackJackRequest {

    private long accountId;
    private int moneyRate;

    @XmlElement(name = "accountId")
    @JsonProperty("accountId")
    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    @XmlElement(name = "money")
    @JsonProperty("money")
    public int getMoneyRate() {
        return moneyRate;
    }

    public void setMoneyRate(int moneyRate) {
        this.moneyRate = moneyRate;
    }
}
