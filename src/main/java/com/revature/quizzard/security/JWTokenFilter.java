package com.revature.quizzard.security;
import com.revature.quizzard.models.user.RoleEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class JWTokenFilter implements Filter {
    private JWTokenUtil jwtTokenUtil;
    /**
     *  Constructor for the JWTokenFilter
     * @author Nicholas Recino
     */
//    @Autowired
//    public JWTokenFilter(JWTokenUtil jwTokenUtil){
//        this.jwtTokenUtil = jwTokenUtil;
//    }
    public JWTokenFilter(){
    }
    @Override
    public void init(FilterConfig cfg) {
        ApplicationContext container = WebApplicationContextUtils.getRequiredWebApplicationContext(cfg.getServletContext());
        this.jwtTokenUtil = container.getBean(JWTokenUtil.class);
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
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authToken = ((HttpServletRequest)request).getHeader("Authorization");
        if (authToken != null) {
            String token = authToken.split(" ")[1];
            System.out.println(jwtTokenUtil == null);
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(jwtTokenUtil.getSecretKey())
                    .parseClaimsJws(token);
            //String rolePrefix = "ROLE_";
            Claims body = claimsJws.getBody();
            String username = body.get("userName").toString();
            System.out.println(body.get("role"));
            List<LinkedHashMap<Object, Object>> roles = (List<LinkedHashMap<Object, Object>>) body.get("role");
            // List<RoleEntity> roles = (List<RoleEntity>) body.get("role");
            // If we implement roles this would be the spot to process this accordingly, or into permissions, giving GrantedAuthorities for all unique permissions
            //role = rolePrefix + role;
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            for(Map role : roles){
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority((String) role.get("name"));
                authorities.add(authority);
            }
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
