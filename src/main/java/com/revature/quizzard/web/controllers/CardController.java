package com.revature.quizzard.web.controllers;

import com.revature.quizzard.models.flashcards.CardEntity;
import com.revature.quizzard.services.*;
import com.revature.quizzard.web.dtos.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;

import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/card")
public class CardController {

    private CardService cardService;

    @Autowired
    public CardController(CardService cardService){
        this.cardService = cardService;
    }

    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    public List<CardDTO> getCards(HttpServletRequest req){
        return cardService.getCards();
    }

    @PostMapping(value = "/newcard", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public CardDTO createCard(@RequestBody CardDTO newCard, HttpServletRequest req){
        CardDTO createdCard = cardService.createCard(newCard);

        return createdCard;
    }
}
