package com.exam.blackjack.rest.container.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by nikolay on 14.09.15.
 */
@XmlRootElement(name = "request")
public class StandRequest extends SessionRequest {

    private Long accountId;

    @XmlElement(name = "accountId")
    @JsonProperty("accountId")
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

}
