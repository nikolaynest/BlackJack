package com.exam.blackjack.rest.container.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created on 10.08.15.
 */
@XmlRootElement(name = "request")
public class BustRequest {
    private Integer moneyRate;
    private Long fromWhomId;
    private Long toWhomId;

    @XmlElement(name = "money")
    @JsonProperty("money")
    public Integer getMoneyRate() {
        return moneyRate;
    }

    public void setMoneyRate(Integer moneyRate) {
        this.moneyRate = moneyRate;
    }

    @XmlElement(name = "fromWhomId")
    @JsonProperty("fromWhomId")
    public Long getFromWhomId() {
        return fromWhomId;
    }

    public void setFromWhomId(Long fromWhomId) {
        this.fromWhomId = fromWhomId;
    }

    @XmlElement(name = "toWhomId")
    @JsonProperty("toWhomId")
    public Long getToWhomId() {
        return toWhomId;
    }

    public void setToWhomId(Long toWhomId) {
        this.toWhomId = toWhomId;
    }
}
