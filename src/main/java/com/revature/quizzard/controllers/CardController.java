package com.revature.quizzard.controllers;

import com.revature.quizzard.dtos.requestmodels.CardConfidentDTO;
import com.revature.quizzard.dtos.requestmodels.CardFavoriteDTO;
import com.revature.quizzard.services.CardService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CardController {

    private final CardService cardService;

    @PostMapping("/favorite")
    @ResponseStatus(HttpStatus.OK)
    public void toggleFavoriteCard(@RequestBody CardFavoriteDTO cardFavoriteDTO) {
        cardService.addFavoriteCard(cardFavoriteDTO);
    }

    @PostMapping("/confident")
    @ResponseStatus(HttpStatus.OK)
    public void toggleConfidentCard(@RequestBody CardConfidentDTO cardConfidentDTO) {
        cardService.toggleConfidentCard(cardConfidentDTO);
    }
}
