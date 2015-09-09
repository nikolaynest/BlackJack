package com.exam.blackjack.core;

import org.apache.commons.lang.RandomStringUtils;

/**
 * Created by nikolay on 05.09.15.
 */
public class SessionManagerImpl implements SessionManager {
    @Override
    public String getSession() {
        return RandomStringUtils.random(20, true, true);
    }
}
