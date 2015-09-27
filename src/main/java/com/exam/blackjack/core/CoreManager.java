package com.exam.blackjack.core;

import com.exam.blackjack.rest.container.request.*;
import com.exam.blackjack.rest.container.response.*;

/**
 * Created by nikolay on 27.09.15.
 */
public interface CoreManager {

    AccountResponse getAccount(AccountRequest request);

    AvailableCashResponse recharge(RechargeRequest request);

    ParlayResponse makeRate(ParlayRequest request);

    HitResponse hit(HitRequest request);

    StandResponse stand(StandRequest request);
}
