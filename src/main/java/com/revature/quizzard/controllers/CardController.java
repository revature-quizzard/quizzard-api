package com.revature.quizzard.controllers;


import com.revature.quizzard.dtos.CardDTO;
import com.revature.quizzard.services.CardService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/card")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CardController {

    private CardService cardService;
  
    /**
     * Returns all cards in the database
     * @param req The HttpServletRequest
     * @return List<CardDTO>
     * @author Giancarlo Tomasello
     * @author Kevin Chang
     */
    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    public List<CardDTO> getCards(HttpServletRequest req){
        return cardService.getCards();
    }


    /**
     * Returns all cards that belong to a specific account
     * @param id The account id
     * @param req The HttpServletRequest
     * @return List<CardDTO>
     * @author Giancarlo Tomasello
     * @author Kevin Chang
     */
//    @GetMapping(value = "/account/{id}", produces = APPLICATION_JSON_VALUE)
//    public List<CardDTO> getUsersCards(@PathVariable String id, HttpServletRequest req){
//        return cardService.getCardsByAccountId(Integer.parseInt(id));
//    }


    /**
     * Creates a new flashcard in the database from a passed in CardDTO in the Request Body
     * @param newCard The new card object to be added
     * @param req The HttpServletRequest
     * @return CardDTO
     * @auhtor Giancarlo Tomasello
     * @author Kevin Chang
     */
    @PostMapping(value = "/newcard", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public CardDTO createCard(@RequestBody CardDTO newCard, HttpServletRequest req) {
        System.out.println("Post Mapping Reached");

        CardDTO createdCard = cardService.createCard(newCard);

        System.out.println("Finished crate card method");
        return createdCard;
    }
      
//    @PostMapping("/favorite")
//    @ResponseStatus(HttpStatus.OK)
//    public void toggleFavoriteCard(@RequestBody CardFavoriteDTO cardFavoriteDTO) {
//        cardService.addFavoriteCard(cardFavoriteDTO);
//    }
//
//    @PostMapping("/confident")
//    @ResponseStatus(HttpStatus.OK)
//    public void toggleConfidentCard(@RequestBody CardConfidentDTO cardConfidentDTO) {
//        cardService.toggleConfidentCard(cardConfidentDTO);
//
//    }
}
