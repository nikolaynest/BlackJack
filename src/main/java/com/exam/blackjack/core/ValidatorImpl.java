package com.exam.blackjack.core;

import com.exam.blackjack.exception.GameException;

/**
 * Created on 09.08.15.
 */
public class ValidatorImpl implements Validator {

    public void parlayValidate(Integer moneyRate, double availableCash) {
        if (moneyRate*1.5 > availableCash) {
            double maxRate = availableCash/1.5;
            String message = "You have no enough money for rate:["+ moneyRate + "]. Your max rate:[" + maxRate + "]";
            throw new GameException(message);
        }
    }
}
