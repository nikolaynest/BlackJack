package com.exam.blackjack.rest.container.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by nikolay on 05.09.15.
 */
public class SessionResponse {

    private String session;

    @XmlAttribute(name = "session")
    @JsonProperty("session")
    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
