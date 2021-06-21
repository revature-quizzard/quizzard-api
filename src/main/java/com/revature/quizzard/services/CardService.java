package com.revature.quizzard.services;

import com.revature.quizzard.models.flashcards.*;
import com.revature.quizzard.repositories.*;
import com.revature.quizzard.web.dtos.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class CardService {

    private CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository){
        this.cardRepository = cardRepository;
    }

    /**
     * Returns a list of all cards in the database
     * @return A list of cardDTOs
     */
    public List<CardDTO> getCards(){
        List<CardEntity> cardEntities = cardRepository.findAll();
        List<CardDTO> cardDTOS = new ArrayList<>();

        //Fix magic number with actual id
        for(CardEntity card: cardEntities){

            cardDTOS.add(new CardDTO(card.getId(), card.getQuestion(), card.getAnswer(), card.isReviewable(),
                    card.isPublic(), card.getSubject().getId()));
        }
        
        return cardDTOS;
    }

    /**
     * Returns a list of cards that belong to a specified user
     * @param id The account id
     * @return A list of cardsDTOs
     * @author Giancarlo Tomasello
     * @author Kevin Chang
     */
    public List<CardDTO> getCardsByAccountId(int id){
        List<CardEntity> cardEntities = cardRepository.findAllCardsByAccountId(id);
        List<CardDTO> cardDTOS = new ArrayList<>();

        for(CardEntity card: cardEntities){
            cardDTOS.add(new CardDTO(card.getId(), card.getQuestion(), card.getAnswer(), card.isReviewable(),
                    card.isPublic(), card.getSubject().getId()));
        }

        return cardDTOS;
    }

    /**
     * Saves a new card into the database by converting the CardDTO into a CardEntity
     * @param newCard A CardDTO representing the card to be added to the database
     * @return The card (cardDTO) that was added to the database
     * @author Giancarlo Tomasello
     * @author Kevin Chang
     */
    public CardDTO createCard(CardDTO newCard){
        CardEntity newCardEntity = new CardEntity(newCard);
        CardEntity savedCard = cardRepository.save(newCardEntity);

        System.out.println("Saved ID: " + savedCard.getId());
        newCard.setId(savedCard.getId());

        return newCard;
    }
}
