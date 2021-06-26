package com.revature.quizzard.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    /**
     * This method handles an AuthenticationException should one be thrown in <code>WebSecurityConfig.configure()</code>.
     * There a number of things that can be done with this exception (such as registering a user with details provided,
     * which would be a bad idea). This application will reject the request with an error message
     *
     * @param request The client request
     * @param response The response to be returned with the error message
     * @param authException
     * @throws IOException
     * @throws ServletException
     * @author Nicholas Recino
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
    }

}