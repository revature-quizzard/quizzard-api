package com.revature.quizzard.controllers;

import com.revature.quizzard.dtos.SetDTO;
import com.revature.quizzard.models.sets.SetEntity;
import com.revature.quizzard.services.SetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/set")
public class SetController {

    private SetService setService;

    @Autowired
    public SetController(SetService setService) {
        this.setService = setService;
    }

    @GetMapping(value = "public", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Set<SetDTO> getPubicSets(HttpServletRequest  req){
        SetDTO set = new SetDTO();
        Set<SetDTO> publicSets = new HashSet<>();
        Set<SetEntity> setsFromDB = setService.findIsPublicTrue(true);

        setsFromDB.stream().forEach(setEntity -> {
            set.setSetId(setEntity.getId());
            set.setUserId(setEntity.getAccount().getUser().getId());
            set.setName(setEntity.getName());
            publicSets.add(set);
        });
        return publicSets;
    }

}
