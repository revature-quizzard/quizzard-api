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

    /**
     * Returns all cards in the data base
     * @param req The HttpServletRequest
     * @return All the cards in the card table of the database
     */
    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    public List<CardDTO> getCards(HttpServletRequest req){
        return cardService.getCards();
    }

    /**
     * Creates a new flashcard in the database from infomration sent in the from of a JSON
     * @param newCard The new card object to be added
     * @param req The HttpServletRequest
     * @return The card object that was just added to the database
     */
    @PostMapping(value = "/newcard", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public CardDTO createCard(@RequestBody CardDTO newCard, HttpServletRequest req){
        System.out.println("Post Mapping Reached");

        CardDTO createdCard = cardService.createCard(newCard);

        System.out.println("Finished crate card method");
        return createdCard;
    }
}
