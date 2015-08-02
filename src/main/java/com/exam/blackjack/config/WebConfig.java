package com.exam.blackjack.config;

import com.exam.blackjack.core.CoreManager;
import com.exam.blackjack.rest.controller.AppController;
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
    public AppController appController(CoreManager coreManager) {
        return new AppController(coreManager);
    }

    @Bean
    CoreManager coreManager() {
        return new CoreManager();
    }

}
