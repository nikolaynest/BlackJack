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
        return accountInfo(accountId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AvailableCashResponse recharge(RechargeRequest request) {
        Double amount = request.getRechargeCash();
        long accountId = request.getAccountId();
        this.rechargeAccount(amount, accountId);
        this.recordOperation(amount, accountId, RECHARGE_BALANCE_OPERATION_NUMBER);
        return this.availableCashResponse(accountId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void subtraction(BustRequest request) {
        Integer moneyRate = request.getMoneyRate();
        Long fromWhomId = request.getFromWhomId();
        Long toWhomId = request.getToWhomId();

        String subtractionQuery = "UPDATE account SET cash = cash - ? where account_id = ?";
        int row = jdbcTemplate.update(subtractionQuery, moneyRate, fromWhomId);
        log.info("subtraction from id:[{}] cash:[{}], change [{}] row", fromWhomId, moneyRate, row);

        String addQuery = "UPDATE account SET cash = cash + ? where account_id = ?";
        row = jdbcTemplate.update(addQuery, moneyRate, toWhomId);
        log.info("add to id:[{}] cash:[{}], change [{}] row", toWhomId, moneyRate, row);

        this.recordOperation(moneyRate, fromWhomId, SUBTRACTION_OPERATION_NUMBER);
    }

    private void rechargeAccount(double cash, long accountId) {
        String sql = "UPDATE account SET cash = cash + ? where account_id = ?";
        int row = jdbcTemplate.update(sql, cash, accountId);
        log.info(sql + " cash = [{}], id = [{}], changes [{}] rows number", cash, accountId, row);
    }

    private void recordOperation(double amount, long accountId, int operationId) {
        String insert = "INSERT INTO account_operation (amount, account_id, operation_id) values (?,?,?)";
        jdbcTemplate.update(insert, amount, accountId, operationId);
        log.info("update account_operation with amount:[{}] for accountId:[{}]", amount, accountId);
    }

    private AccountResponse accountInfo(long accountId) {
        log.info("getAccount: request id:[{}]", accountId);
        String query = "SELECT * FROM account WHERE account_id = ?";
        log.info("SQL: [{}]", query);
        AccountResponse response = jdbcTemplate.queryForObject(query, new Object[]{accountId}, new AccountRowMapper());
        log.info("response name: [{}]", response.getName());
        return response;
    }

    private AvailableCashResponse availableCashResponse(long accountId){
        AvailableCashResponse response = new AvailableCashResponse();
        response.setAvailableCash(accountInfo(accountId).getCash());
        response.setAccountId(accountId);
        return response;

    }

}
