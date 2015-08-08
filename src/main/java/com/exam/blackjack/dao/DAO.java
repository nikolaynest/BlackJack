package com.exam.blackjack.dao;

import com.exam.blackjack.rest.container.request.AccountRequest;
import com.exam.blackjack.rest.container.request.RechargeRequest;
import com.exam.blackjack.rest.container.response.AccountResponse;
import com.exam.blackjack.rest.container.response.RechargeResponse;

/**
 * Created by nikolay on 02.08.15.
 */
public interface DAO {
    AccountResponse getAccount(AccountRequest request);

    RechargeResponse recharge(RechargeRequest request);
}
