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
        
        return
    }
}
