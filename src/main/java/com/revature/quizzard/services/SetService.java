package com.revature.quizzard.services;

import com.revature.quizzard.dtos.*;
import com.revature.quizzard.exceptions.ResourceNotFoundException;
import com.revature.quizzard.models.flashcards.*;
import com.revature.quizzard.models.sets.SetEntity;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SetService {

    private SetRepository setRepo;
    private AccountRepository accountRepo;
    private CardRepository cardRepo;

    /**
     * Returns a list of sets that were created by the account. Takes in a username and finds the account associated
     * with that username. Uses that account to find the sets.
     *
     * @param username of account
     * @return List<SetDTO>
     * @author Vinson Chin
     * @author Austin Knauer
     */
    @Transactional(readOnly = true)
    public List<SetDTO> getCreatedSets(String username) throws ResourceNotFoundException{

        Optional<AccountEntity> creator = Optional.ofNullable(accountRepo.findByUsername(username));

        if (creator.isPresent()) {

            List<SetEntity> accountSets = setRepo.findAllCreatedByAccount(creator);

            List<SetDTO> createdSets = new ArrayList<SetDTO>();

            for (SetEntity set : accountSets) {
                SetDTO setDTO = new SetDTO(set);
                createdSets.add(setDTO);
            }

            return createdSets;
        } else {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Saves a new set into the database which converts the provided setDTO into a setEntity
     * @param newSet A SetDTO to be sent to the database
     * @return SetEntity
     * @author Ann Louis-Charles
     * @author Chris Levano
     */

    @Transactional
    public SetDTO createStudySets(SetDTO newSet) {
        List<CardDTO> list = newSet.getLocalFlashcards();
        Set<CardEntity> entitySet = new HashSet<>();
        for (CardDTO cardDTO: list) {
            entitySet.add(cardRepo.findCardEntityById(cardDTO.getId()));
        }
        SetEntity setEntity = new SetEntity(newSet);

        setEntity.setCards(entitySet);

        SetEntity savedEntity = setRepo.save(setEntity);
        System.out.println("Saved:" + setEntity.getName());
        return new SetDTO(savedEntity);
    }

    /**
     * Gets all the cards from a specified study set from the database
     * @param setId The study set id
     * @return List<CardDTO>
     * @author Giancarlo Tomasello
     * @author Kevin Chang
     */
    @Transactional
    public List<CardDTO> getCardsBySetId(int setId){

        SetEntity setEntity = setRepo.findById(setId).orElseThrow(ResourceNotFoundException::new);
        List<CardDTO> setCards = new ArrayList<>();

        for (CardEntity card:setEntity.getCards()) {
            setCards.add(new CardDTO(card));
        }

        return setCards;
    }

    @Transactional
    public SetDTO getSetById(int setId){
        SetEntity setEntity = setRepo.findById(setId).orElseThrow(ResourceNotFoundException::new);
        SetDTO studySet = new SetDTO(setEntity);

        return studySet;
    }
}
