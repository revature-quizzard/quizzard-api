package com.revature.quizzard.services;

import com.revature.quizzard.dtos.SetDTO;
import com.revature.quizzard.exceptions.ResourceNotFoundException;
import com.revature.quizzard.models.sets.SetEntity;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.repositories.AccountRepository;
import com.revature.quizzard.repositories.SetRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SetService {

    private SetRepository setRepo;
    private AccountRepository accountRepo;

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
    public List<SetEntity> getPublicSets()
    {
        return setRepo.findAllByIsPublic(true);
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
