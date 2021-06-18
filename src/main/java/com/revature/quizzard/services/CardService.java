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

    public List<CardDTO> getCards(){
        List<CardEntity> cardEntities = cardRepository.findAll();
        List<CardDTO> cardDTOS = new ArrayList<>();

        for(CardEntity card: cardEntities){
            cardDTOS.add(new CardDTO(card.getId(), card.getQuestion(), card.getAnswer(), card.isReviewable(),
                    card.isPublic(), card.getSubject()));
        }
        
        return cardDTOS;
    }

    public CardDTO createCard(CardDTO newCard){
        CardEntity newCardEntity = new CardEntity(newCard);
        CardEntity savedCard = cardRepository.save(newCardEntity);
        newCard.setId(savedCard.getId());

        return newCard;
    }
}
