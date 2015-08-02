package com.exam.blackjack.config;

import com.exam.blackjack.dao.DAO;
import com.exam.blackjack.dao.DaoSpringJdbc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Created on 02.08.15.
 */
@Configuration
public class DaoConfiguration {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/black_jack");
        dataSource.setUsername("nikolay");
        dataSource.setPassword("nikolay");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DAO dao(JdbcTemplate jdbcTemplate) {
        return new DaoSpringJdbc(jdbcTemplate);
    }
}
