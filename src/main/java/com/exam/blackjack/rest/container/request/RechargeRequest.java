package com.exam.blackjack.rest.container.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created on 03.08.15.
 */
@XmlRootElement(name = "request")
public class RechargeRequest {

    private Double rechargeCash;
    private Long accountId;

    @XmlElement(name = "cash")
    @JsonProperty("cash")
    public Double getRechargeCash() {
        return rechargeCash;
    }

    public void setRechargeCash(Double rechargeCash) {
        this.rechargeCash = rechargeCash;
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
