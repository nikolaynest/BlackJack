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

//    @AfterThrowing(pointcut = "execution(* *..DAOI.*(..))", throwing = "e")
//    public void handleExceptionThrowing(RuntimeException e) {
//        if (e instanceof DataProcessingException) {
//            throw e;
//        }
//        if (e.getCause() instanceof SQLException) {
//            SQLException sqlException = (SQLException) e.getCause();
//            throw new DataProcessingException(sqlException.getMessage(), sqlException);
//        }
//        if (e instanceof DataAccessException) {
//            throw new DataProcessingException(e);
//        }
//        throw new BasketException(e);
//    }
}
