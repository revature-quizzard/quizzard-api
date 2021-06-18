package com.revature.quizzard.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.io.IOException;
import java.util.Collections;

public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Override
    public void configure(WebSecurity webSecurity){
        webSecurity.ignoring().mvcMatchers("/swagger-ui.html/**", "/configuration/**", "/swagger-resources/**", "/v2/api-docs","/webjars/**");
    }
//
//    private void authenticationEntryPoint(HttpServletRequest request, HttpServletResponse response,
//                                          AuthenticationException authException) throws IOException {
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        // TODO fix to include Jackson, CodeWithMe is acting funny
//        response.getWriter().write((Collections.singletonMap("error", "Unauthenticated")));
//    }


    /**
     *  Creates API documentation at uri swagger-ui.html around the base package
     * @return a Docket that is used to reference the Swagger documentation
     */
    @Bean
    public Docket generateUserApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.revature.qwizzard"))
                .build();
    }
}
