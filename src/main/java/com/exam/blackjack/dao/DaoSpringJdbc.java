package com.exam.blackjack.dao;

import com.exam.blackjack.rest.container.request.BustRequest;
import com.exam.blackjack.rest.container.request.RechargeRequest;
import com.exam.blackjack.rest.container.response.AccountResponse;
import com.exam.blackjack.rest.container.response.AvailableCashResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created on 02.08.15.
 */
@Repository
public class DaoSpringJdbc implements DAO {

    private static final Logger log = LoggerFactory.getLogger(DaoSpringJdbc.class);

    private JdbcTemplate jdbcTemplate;
    private static final int RECHARGE_BALANCE_OPERATION_NUMBER = 2;
    private static final int SUBTRACTION_OPERATION_NUMBER = 3;


    @Autowired
    public DaoSpringJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public AccountResponse getAccount(long accountId) {
        log.info("getAccount: request id:[{}]", accountId);
        String query = "SELECT * FROM account WHERE account_id = ?";
        log.info("SQL: [{}]", query);
        AccountResponse response = jdbcTemplate.queryForObject(query, new Object[]{accountId}, new AccountRowMapper());
        log.info("response name: [{}]", response.getName());
        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AvailableCashResponse recharge(RechargeRequest request) {
        Double amount = request.getRechargeCash();
        long accountId = request.getAccountId();
        this.rechargeAccount(request);
        this.recordOperation(amount, request);
        String query = "SELECT * FROM account WHERE account_id = ?";
        AccountResponse accountInfo = jdbcTemplate.queryForObject(query, new Object[]{accountId}, new AccountRowMapper());
        AvailableCashResponse response = new AvailableCashResponse();
        response.setAvailableCash(accountInfo.getCash());
        response.setAccountId(accountId);
        return response;
    }

    private void rechargeAccount(RechargeRequest request) {
        String sql = "UPDATE account SET cash = cash + ? where account_id = ?";
        Double cash = request.getRechargeCash();
        long accountId = request.getAccountId();
        int row = jdbcTemplate.update(sql, cash, accountId);
        log.info(sql + " cash = [{}], id = [{}], changes [{}] rows number", cash, accountId, row);
    }

    private void recordOperation(double amount, RechargeRequest request) {
        long accountId = request.getAccountId();
        String insert = "INSERT INTO account_operation (amount, account_id, operation_id) values (?,?,?)";
        jdbcTemplate.update(insert, amount, accountId, RECHARGE_BALANCE_OPERATION_NUMBER);
        log.info("update account_operation with amount:[{}] for accountId:[{}]", amount, accountId);
    }

    @Transactional
    public AvailableCashResponse subtraction(BustRequest request) {
        Integer moneyRate = request.getMoneyRate();
        Long fromWhomId = request.getFromWhomId();
        Long toWhomId = request.getToWhomId();

        String subtractionQuery = "UPDATE account SET cash = cash - ? where account_id = ?";
        int row = jdbcTemplate.update(subtractionQuery, moneyRate, fromWhomId);
        return null;

    }

}
