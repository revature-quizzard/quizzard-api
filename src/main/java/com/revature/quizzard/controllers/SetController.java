package com.revature.quizzard.controllers;

import com.revature.quizzard.dtos.*;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.services.SetService;
import org.apache.logging.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class SetController {

    private SetService setService;
    private final Logger logger = LogManager.getLogger();

    @Autowired
    public SetController(SetService setService) {
        this.setService = setService;
    }

    @GetMapping(value = "/created/{username}", produces = "application/json")
    // TODO: Spring Security implementation/add roles
//    @Secured({})
    public List<SetDTO> findAllCreatedSetsByAccountID(@PathVariable String username, HttpServletRequest request, HttpServletResponse response) {

        // TODO: change to object being sent with request header
//        Integer accountId = ((AccountEntity)request.getAttribute("account")).getId();
        List<SetDTO> foundSets = setService.getCreatedSets(username);

        if (foundSets == null) {
            response.setStatus(404);
            return null;
        }
        response.setStatus(200);
        return foundSets;
    }


    @PostMapping(value = "/sets/newset", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public SetDTO createNewSet(@RequestBody SetDTO newSet){
        logger.info(newSet);
        SetDTO newStudySet = setService.createStudySets(newSet);

        System.out.println("New set creation method invoked.");
        return newStudySet;
    }


}
