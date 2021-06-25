package com.revature.quizzard.controllers;

import com.revature.quizzard.dtos.SetDTO;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.security.JWTokenUtil;
import com.revature.quizzard.services.SetService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/sets")
public class SetController {

    private SetService setService;
    private JWTokenUtil jwtTokenUtil;

    @Autowired
    public SetController(SetService setService, JWTokenUtil jwtTokenUtil) {
        this.setService = setService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    /**
     * Retrieves from the database all sets that were created by account
     * @param request HTTP request containing the authorization token with username
     * @param response HTTP response
     * @return List<SetDTO>
     * @author Vinson Chin
     * @author Austin Knauer
     */
    @GetMapping(value = "created", produces = "application/json")
    public List<SetDTO> findAllCreatedSetsByAccount(HttpServletRequest request, HttpServletResponse response) {

        String authToken = request.getHeader("Authorization");
        String token = authToken.split(" ")[1];
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(jwtTokenUtil.getSecretKey())
                .parseClaimsJws(token);
        String username = claimsJws.getBody().get("userName").toString();

        List<SetDTO> foundSets = setService.getCreatedSets(username);

        if (foundSets == null) {
            response.setStatus(404);
            return null;
        }
        response.setStatus(200);
        return foundSets;
    }
}
