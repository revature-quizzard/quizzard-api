package com.revature.quizzard.configs;

import com.revature.quizzard.security.AuthEntryPointJwt;
import com.revature.quizzard.security.CorsFilter;
import com.revature.quizzard.security.JWTokenFilter;
import com.revature.quizzard.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@EnableWebSecurity
@Configuration
@EnableSwagger2
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private AuthEntryPointJwt unauthorizedHandler;
    private UserDetailsServiceImpl userDetailService;
    private JWTokenFilter jwTokenFilter;
    private CorsFilter corsFilter;

    /**
     *  Spring Security Configuration Constructor
     * @param unauthorizedHandler Entry point used to handle any unauthorized requests
     * @param userDetailService Implementation used to load a "User object" into an Authentication object
     * @param jwTokenFilter The Filter responsible for intercepting communications and parsing the jwt token on the header in said communications
     * @param corsFilter The filter responsible for allowing for other origins to have access to desired headers in a response
     * @author Nichols Recino
     */
    public WebSecurityConfig(AuthEntryPointJwt unauthorizedHandler, UserDetailsServiceImpl userDetailService,JWTokenFilter jwTokenFilter,CorsFilter corsFilter) {
        this.unauthorizedHandler = unauthorizedHandler;
        this.userDetailService = userDetailService;
        this.jwTokenFilter = jwTokenFilter;
        this.corsFilter = corsFilter;
    }


    /**
     *  Processes and configures behavior of security requests, sets the authentication entry point, authorizes pertinent uri's, and then authenticates all other uri's
     *  Also adds in the filters necessary for the control flow to allow for proper processing of all communications from an origin and the api
     * @param http The Security manager for all http requests
     * @throws Exception Throws an exception if there is any issue with Filters being applied to the Filter Chain
     * @author Nicholas Recino
     * @author Richard Taylor
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.headers().frameOptions().sameOrigin();
        http.csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .authorizeRequests().antMatchers(authenticatePointsForTesting()).permitAll()
                .anyRequest().authenticated();
        http.addFilterBefore(jwTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(corsFilter, ChannelProcessingFilter.class);
    }

    /**
     *  This is utilized for setting points to not be authenticated via JWT's used for Register/Login
     *  and then for all testing of endpoints where you do not intend to utilize JWT state to process information
     *  and are expecting multiple changes, anything after webjars will be deleted upon production.
     * @return an array of unprotected end points
     * @author Nicholas Recino
     */
    public String[] authenticatePointsForTesting(){
        List<String> pointsToAuthenticate = new ArrayList<>();
        pointsToAuthenticate.add("/h2-console/**");
        pointsToAuthenticate.add("/test/**");
        pointsToAuthenticate.add("/swagger-ui.html/**");
        pointsToAuthenticate.add("/configuration/**");
        pointsToAuthenticate.add("/swagger-resources/**");
        pointsToAuthenticate.add("/v2/api-docs");
        pointsToAuthenticate.add("/webjars/**");
        pointsToAuthenticate.add("/set/**");
        return pointsToAuthenticate.toArray(new String[0]);
    }

    /**
     *  Bean that builds an authentication manager based upon a specific implementation of a UserDetailsService
     * @param authenticationManagerBuilder A builder used to build Authentication manager's provided by spring
     * @throws Exception if there is an issue with creating a Authentication manager
     * @author Nicholas Recino
     */
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//      authenticationManagerBuilder.userDetailsService(userDetailService).passwordEncoder(passwordEncoder()); // Use this if we decided to encode passwords
        authenticationManagerBuilder.userDetailsService(userDetailService);
    }

    /**
     * Sets the entry point of the configuration class to the bean of AuthEntryPointJwt
     * @param unauthorizedHandler The entry point of the JWT security point
     * @author Nicholas Recino
     */
    @Autowired
    public void setUnauthorizedHandler(AuthEntryPointJwt unauthorizedHandler) {
        this.unauthorizedHandler = unauthorizedHandler;
    }

    /**
     * Sets the UserDetailsService that is used to build an authentication manager
     * @param userDetailsService A specific implementation of a UserDetailsService defined from a bean
     * @author Nicholas Recino
     */
    @Autowired
    public void setUserDetailsService(UserDetailsServiceImpl userDetailsService) {
        this.userDetailService = userDetailsService;
    }

    /**
     *  Allows all methods, origins and headers to be accessed by specific cors sources, then registers the configuration into a url based configuration
     * @return a configured Cors source
     * @author Nicholas Recino
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowedOrigins(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /**
     * Creates an authentication manager bean to be utilized and leveraged by spring security for authentication tasks
     * @return A Authentication Manager built according to specified params such as the UserDetailsService, and any password encryption enabled
     * @throws Exception if there is an issue generating the Authentication manager
     * @author Nicholas Recino
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * A password encoder that can be leveraged, constructor takes an int for strength, higher the stronger the encoding is, but takes longer
     * 10 is default strength, should be tailored to take about 1 second to process any password through this, all servers are slightly different
     * @return a password encoder
     * @author Nicholas Recino
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Generates our Swagger API documentation
     * @return A docket containing all of our handlers to be implemented into a swagger page
     * @author Nicholas Recino
     */
    @Bean
    public Docket generateUserApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                    .select().apis(RequestHandlerSelectors.basePackage("com.revature.quizzard")).build();
    }
}
