package com.exam.blackjack.rest.container.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by nikolay on 03.08.15.
 */
@XmlRootElement(name = "recharge")
public class RechargeRequest {

    private Integer rechargeCash;
    private Integer accountId;

    @XmlElement(name = "cash")
    @JsonProperty("cash")
    public Integer getRechargeCash() {
        return rechargeCash;
    }

    public void setRechargeCash(Integer rechargeCash) {
        this.rechargeCash = rechargeCash;
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
