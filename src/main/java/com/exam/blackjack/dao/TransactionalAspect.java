package com.exam.blackjack.dao;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

/**
 * Created on 08.08.15.
 */
@Aspect
public class TransactionalAspect {

    private static final Logger log = LoggerFactory.getLogger(TransactionalAspect.class);

    @AfterThrowing(pointcut = "execution(* com.exam.blackjack.core.CoreManager.*(..))", throwing = "e")
    public void rollback(SQLException e) {
        log.debug("Exception during db operation: [{}]", e.toString());
        throw new RuntimeException(e);
    }
}
