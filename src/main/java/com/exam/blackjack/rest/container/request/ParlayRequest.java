package com.exam.blackjack.rest.container.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by nikolay on 25.07.15.
 */
@XmlRootElement(name = "request")
public class ParlayRequest {

    private Integer moneyRate;
    private Long accountId;

    @XmlElement(name = "money")
    @JsonProperty("money")
    public Integer getMoneyRate() {
        return moneyRate;
    }

    public void setMoneyRate(Integer moneyRate) {
        this.moneyRate = moneyRate;
    }

    @XmlElement(name = "accountId")
    @JsonProperty("accountId")
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
