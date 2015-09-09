package com.exam.blackjack.config;

import com.exam.blackjack.card.Deck;
import com.exam.blackjack.core.*;
import com.exam.blackjack.deck.DeckOperations;
import com.exam.blackjack.rest.controller.AppController;
import com.exam.blackjack.rules.GameRules;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created on 25.07.15.
 */
@EnableWebMvc
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Bean
    public Validator validator() {
        return new ValidatorImpl();
    }

    @Bean
    public AppController appController(CoreManager coreManager) {
        return new AppController(coreManager);
    }

    @Bean
    public CoreManager coreManager() {
        return new CoreManager();
    }

    @Bean
    public GameRules gameRules() {
        return new GameRules();
    }

    @Bean
    public DeckOperations deckOperations() {
        return new DeckOperations();
    }

    @Bean
    public Deck deck() {
        return new Deck();
    }

    @Bean
    public SessionManager sessionManager() {
        return new SessionManagerImpl();
    }

    @Bean
    public Cache cache() {
        return new CardsCache();
    }
}
