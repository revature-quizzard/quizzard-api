package com.revature.quizzard.controllers;

import com.revature.quizzard.dtos.SetDTO;
import com.revature.quizzard.services.SetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class SetController {

    private SetService setService;

    @Autowired
    public SetController(SetService setService) {
        this.setService = setService;
    }

    @GetMapping(value = "/all/{accountId}", produces = "application/json")
    public List<SetDTO> findAllSetsByAccountId(@PathVariable int accountId, HttpServletRequest request, HttpServletResponse response) {

        List<SetDTO> foundSets= setService.getCreatedSets(accountId);

        if (foundSets == null) {
            response.setStatus(404);
            return null;
        }
        response.setStatus(200);
        return foundSets;
    }
}
