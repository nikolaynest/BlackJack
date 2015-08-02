package com.exam.blackjack.dao;

import com.exam.blackjack.rest.container.request.AccountRequest;
import com.exam.blackjack.rest.container.response.AccountResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created on 02.08.15.
 */
@Repository
public class DaoSpringJdbc implements DAO {

    private static final Logger log = LoggerFactory.getLogger(DaoSpringJdbc.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DaoSpringJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public AccountResponse getAccount(AccountRequest request) {
        log.info("getAccount: request id:[{}]", request.getAccountId());
        Long accountId = request.getAccountId();
        String query = "SELECT * FROM account WHERE account_id = ?";
        log.info("SQL: [{}]", query);
        AccountResponse response = jdbcTemplate.queryForObject(query, new Object[]{accountId}, new AccountRowMapper());
        log.info("response name: [{}]", response.getName());
        return response;
    }


}
