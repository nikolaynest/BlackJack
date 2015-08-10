package com.exam.blackjack.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Created on 25.07.15.
 */
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    private static final String CONTEXT = "/*";
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{DaoConfiguration.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{CONTEXT};
    }
}
