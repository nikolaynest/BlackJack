package com.exam.blackjack.dao;

import com.exam.blackjack.rest.container.response.AccountResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by nikolay on 02.08.15.
 */
public class AccountRowMapper implements RowMapper<AccountResponse> {
    @Override
    public AccountResponse mapRow(ResultSet rs, int i) throws SQLException {
        AccountResponse response = new AccountResponse();
        response.setAccountId(rs.getLong("account_id"));
        response.setName(rs.getString("name"));
        response.setCash(rs.getInt("cash"));
        return response;
    }
}
