package com.exam.blackjack.core;

import com.exam.blackjack.dao.DAO;
import com.exam.blackjack.rest.container.request.AccountRequest;
import com.exam.blackjack.rest.container.response.AccountResponse;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by nikolay on 02.08.15.
 */
public class CoreManager {

    private DAO dao;

    @Autowired
    public void setDao(DAO dao) {
        this.dao = dao;
    }

    public AccountResponse getAccount(AccountRequest request) {
        AccountResponse account = dao.getAccount(request);
        return account;
    }
}