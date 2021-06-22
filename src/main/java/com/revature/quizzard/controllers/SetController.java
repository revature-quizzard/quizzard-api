package com.revature.quizzard.controllers;

import com.revature.quizzard.dtos.CardDTO;
import com.revature.quizzard.dtos.SetDTO;
import com.revature.quizzard.models.sets.SetEntity;
import com.revature.quizzard.repositories.SetRepository;
import com.revature.quizzard.services.SetService;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor(onConstructor_ = @Autowired)
public class SetController {

    private SetService setService;

    @GetMapping(value = "public", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Set<SetDTO> getPubicSets(HttpServletRequest  req){

        Set<SetDTO> publicSets = new HashSet<>();
        Set<SetEntity> setsFromDB = setService.findIsPublic(true);

        setsFromDB.stream().forEach(setEntity -> {
            SetDTO set = new SetDTO();
            set.setSetId(setEntity.getId());
            set.setUserId(setEntity.getCreator().getUser().getId());
            set.setName(setEntity.getName());
            setEntity.getCards().stream().forEach(cardEntity -> {
                CardDTO card = new CardDTO();
                card.setCardId(cardEntity.getId());
                card.setQuestion(cardEntity.getQuestion());
                card.setAnswer(cardEntity.getAnswer());
                card.setSubject(cardEntity.getSubject());
                card.setCreator(cardEntity.getCreator());
                set.addCardToSet(card);
            });

            publicSets.add(set);
        });
        return publicSets;
    }

}
