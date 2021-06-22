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

@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SetService {

    private SetRepository setRepo;
    private AccountRepository accountRepo;

    @Transactional(readOnly = true)
    public List<SetDTO> getCreatedSets(String username) {

        AccountEntity creator = accountRepo.findByUsername(username);
        List<SetEntity> accountSets = setRepo.findAllCreatedByAccount(creator);

        List<SetDTO> createdSets = new ArrayList<SetDTO>();

        for (SetEntity set: accountSets) {
            SetDTO setDTO = new SetDTO(set);
            createdSets.add(setDTO);
        }

        return createdSets;
    }
}
