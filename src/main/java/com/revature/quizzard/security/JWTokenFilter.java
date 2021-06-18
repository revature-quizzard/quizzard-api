package com.revature.quizzard.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;


public class JWTokenFilter extends OncePerRequestFilter {

    private final JWTokenUtil jwtTokenUtil;

    /**
     *  Constructor for the JWTokenFilter
     * @param jwtTokenUtil The JWT Utility class used to help parse the JWT
     * @author Nicholas Recino
     */
    public JWTokenFilter(JWTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }


    /**
     * Filter process to occur when a valid connection is made to the tomcat server
     * @param request HTTPServletRequest holding the JWT token within the header
     * @param response HTTPServletResponse that is used to eventually send a response back to the calling service
     * @param filterChain The Chain of filters to be executed, passes along the request and response to the next Filter in line
     * @throws ServletException if there is any issues accessing the request or response or querying them
     * @throws IOException If there is any issues parsing the jwt into usable claims
     * @author Nicholas Recino
     */
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authToken = request.getHeader("Authorization");
        if (authToken != null) {
            String token = authToken.split(" ")[1];

            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(jwtTokenUtil.getSecretKey())
                    .parseClaimsJws(token);
            String rolePrefix = "ROLE_";
            Claims body = claimsJws.getBody();
            String username = body.get("userName").toString();
            String role = (String) body.get("role");
            // If we implement roles this would be the spot to process this accordingly, or into permissions, giving GrantedAuthorities for all unique permissions
            role = rolePrefix + role;

            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);

            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, Collections.singletonList(authority));
            SecurityContextHolder.getContext().setAuthentication(authentication);


        }
        filterChain.doFilter(request, response);
    }
}
