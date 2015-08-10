package com.exam.blackjack.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by nikolay on 09.08.15.
 */
@XmlRootElement(name = "response")
public class ErrorResponse {
    private String errorMessage;

    @XmlElement(name = "errorMessage")
    @JsonProperty("errorMessage")
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
