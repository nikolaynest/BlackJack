package com.exam.blackjack.dao;

import com.exam.blackjack.rest.container.request.BustRequest;
import com.exam.blackjack.rest.container.request.RechargeRequest;
import com.exam.blackjack.rest.container.response.AccountResponse;
import com.exam.blackjack.rest.container.response.AvailableCashResponse;

/**
 * Created by nikolay on 02.08.15.
 */
public interface DAO {
    AccountResponse getAccount(long accountId);

    AvailableCashResponse recharge(RechargeRequest request);

    AvailableCashResponse subtraction(BustRequest request);
}
