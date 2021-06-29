package com.revature.quizzard.services;

import com.revature.quizzard.dtos.*;
import com.revature.quizzard.exceptions.ResourceNotFoundException;
import com.revature.quizzard.models.flashcards.*;
import com.revature.quizzard.models.sets.SetEntity;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.repositories.*;
import com.revature.quizzard.security.JWTokenUtil;
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
    private JWTokenUtil tokenUtil;

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
    public SetDTO createStudySets(SetDTO newSet, int creatorId) {
        List<CardDTO> list = newSet.getLocalFlashcards();
        Set<CardEntity> entitySet = new HashSet<>();
        for (CardDTO cardDTO : list) {
            entitySet.add(cardRepo.findCardEntityById(cardDTO.getId()));
        }

        //Get the account of the creator of the set
        Optional<AccountEntity> optionalCreatorAccount = accountRepo.findById(creatorId);
        if(optionalCreatorAccount.isPresent()) {
            newSet.setCreator(optionalCreatorAccount.get());
        }


        SetEntity setEntity = new SetEntity(newSet);
        setEntity.setCards(entitySet);

        SetEntity savedEntity = setRepo.save(setEntity);
        System.out.println(" ~ ~ ~ ~ ~ Saved:" + setEntity.getName());
        return new SetDTO(savedEntity);
    }

    public List<SetEntity> getPublicSets()
    {
        return setRepo.findAllByIsPublic(true);
    }

    public List<SetEntity> getOwnedsets(String token) {
        System.out.println("~ ~ ~ ~ ~ ~ ~ Inside Service layer: " + token);
        int id = tokenUtil.getIdFromToken(token);
        Optional<AccountEntity> optionalAccountEntity = accountRepo.findById(id);
        if(optionalAccountEntity.isPresent()) {
            return setRepo.findAllByCreator(optionalAccountEntity.get());
        } else {
            throw new ResourceNotFoundException();
        }
    }
    public Optional<SetEntity> getSetById(int id)
    {
        return setRepo.findById(id);
    }
    public SetEntity updateSet(SetEntity set)
    {
        return setRepo.save(set);
    }
}
