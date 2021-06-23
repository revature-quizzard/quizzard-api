package com.revature.quizzard.controllers;

import com.revature.quizzard.dtos.SetDTO;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.services.SetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

@RestController
public class SetController {

    private SetService setService;

    @Autowired
    public SetController(SetService setService) {
        this.setService = setService;
    }

    /**
     * Retrieves from the database all sets that were created by account
     * @param username A string used to find associated account
     * @param request HTTP request
     * @param response HTTP response
     * @return List<SetDTO>
     * @author Vinson Chin
     * @author Austin Knauer
     */
    @GetMapping(value = "/sets/created/{username}", produces = "application/json")
    public List<SetDTO> findAllCreatedSetsByAccount(@PathVariable String username, HttpServletRequest request, HttpServletResponse response) {

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();

        List<SetDTO> foundSets = setService.getCreatedSets(username);

        if (foundSets == null) {
            response.setStatus(404);
            return null;
        }
        response.setStatus(200);
        return foundSets;
    }
}
