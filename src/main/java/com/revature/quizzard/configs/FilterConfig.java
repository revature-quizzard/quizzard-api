package com.revature.quizzard.configs;

import com.revature.quizzard.security.CorsFilter;
import com.revature.quizzard.security.JWTokenFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * FilterConfig
 *
 * This class holds the configuration beans for all of the filters within this application.
 * To register a new filter, refer to the code below.
 *
 */
@Configuration
public class FilterConfig {

    /**
     * Registration bean for the CorsFilter.
     *
     * @return filterRegistrationBean
     */
    @Bean
    @SuppressWarnings({"rawtypes, unchecked"})
    public FilterRegistrationBean corsRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new CorsFilter());
        filterRegistrationBean.setUrlPatterns(Collections.singletonList("/*"));
        return filterRegistrationBean;
    }

    /**
     * Registration bean for the JWTokenFilter
     *
     * @return filterRegistrationBean
     */
    @Bean
    @SuppressWarnings({"rawtypes, unchecked"})
    public FilterRegistrationBean JWTokenRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new JWTokenFilter());
        filterRegistrationBean.setUrlPatterns(Collections.singletonList("/*"));
        return filterRegistrationBean;
    }
}
