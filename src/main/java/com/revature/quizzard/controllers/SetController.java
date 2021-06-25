package com.revature.quizzard.controllers;

import com.revature.quizzard.dtos.CardDTO;
import com.revature.quizzard.dtos.SetDTO;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.services.SetService;
import org.apache.logging.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.ArrayList;
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

    /**
     * Returns all the cards for a specified study set
     * @param setId The studySet id
     * @param request The HttpServlet Request
     * @return List<CardDTO>
     * @author Giancarlo Tomasello
     * @author Kevin Chang
     */
    @GetMapping(value = "/sets/{setId}/cards", produces = APPLICATION_JSON_VALUE)
    public List<CardDTO> findAllCardsBySetID(@PathVariable int setId, HttpServletRequest request){
        List<CardDTO> studyCards = setService.getCardsBySetId(setId);
        return studyCards;
    }

    @GetMapping(value = "/sets/{setId}", produces = APPLICATION_JSON_VALUE)
    public SetDTO findSetByID(@PathVariable int setId, HttpServletRequest request){
        SetDTO studySet = setService.getSetById(setId);
        return studySet;
    }


}
