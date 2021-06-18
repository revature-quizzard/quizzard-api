package com.revature.quizzard.services;

import com.revature.quizzard.dtos.SetDTO;
import com.revature.quizzard.exceptions.ResourceNotFoundException;
import com.revature.quizzard.models.sets.SetEntity;
import com.revature.quizzard.repositories.SetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SetService {

    private SetRepository setRepo;

    @Autowired
    public SetService(SetRepository setRepo) {
        this.setRepo = setRepo;
    }

    @Transactional(readOnly = true)
    public List<SetDTO> getCreatedSets(int accountID) {

        List<SetEntity> accountSets = setRepo.findAllByAccountID(accountID);

        List<SetDTO> createdSets = new ArrayList<SetDTO>();

        for (SetEntity set: accountSets) {
            SetDTO setDTO = new SetDTO();
            setDTO.setSetId(set.getId());
            setDTO.setSetName(set.getName());
            setDTO.setPublic(set.getIsPublic());
            setDTO.setAccountID(set.getAccountId());
            createdSets.add(setDTO);
        }

        return createdSets;
    }
}
