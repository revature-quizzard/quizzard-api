package com.revature.quizzard.controllers;

import com.revature.quizzard.dtos.SetCardDTO;
import com.revature.quizzard.dtos.SetDTO;
import com.revature.quizzard.models.flashcards.CardEntity;
import com.revature.quizzard.models.sets.SetEntity;
import com.revature.quizzard.security.JWTokenUtil;
import com.revature.quizzard.services.SetService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/sets")
public class SetController {

    private SetService setService;
    private JWTokenUtil jwtTokenUtil;
    private final Logger logger = LogManager.getLogger();

    @Autowired
    public SetController(SetService setService, JWTokenUtil jwtTokenUtil) {
        this.setService = setService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping(value = "/newset", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public SetDTO createNewSet(@RequestBody SetDTO newSet, HttpServletRequest request){
        logger.info(newSet);

        String authToken = request.getHeader("Authorization");
        int creatorId = jwtTokenUtil.getIdFromToken(authToken);

        SetDTO newStudySet = setService.createStudySets(newSet, creatorId);

        System.out.println("New set creation method invoked.");
        return newStudySet;
    }

    //TODO Ozzy
    @GetMapping("/publicSets")
    @ResponseStatus(HttpStatus.OK)
    public List<SetEntity> getPublicSets()
    {
        return setService.getPublicSets();
    }

    //TODO Kevin
    @GetMapping("/ownedSets")
    @ResponseStatus(HttpStatus.OK)
    public List<SetEntity> getOwnedSets(HttpServletRequest request)
    {
        String token = request.getHeader("Authorization");
        return setService.getOwnedSets(token);
    }

    //TODO Giancarlo
    @PostMapping("/cards/save")
    @ResponseStatus(HttpStatus.CREATED)
    public CardEntity saveCard(@RequestBody SetCardDTO dto)
    {
        return setService.save(dto);
    }
}
