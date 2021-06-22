package com.revature.quizzard.services;

import com.revature.quizzard.exceptions.ResourceNotFoundException;
import com.revature.quizzard.models.sets.SetEntity;
import com.revature.quizzard.repositories.SetRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired )
public class SetService {

    private SetRepository setRepository;

    public Set<SetEntity> findIsPublic(Boolean isPublic) throws ResourceNotFoundException{
        Set<SetEntity> publicSets = setRepository.findIsPublic(isPublic);
        if (publicSets.isEmpty()){
            throw new ResourceNotFoundException();
        }
        return publicSets;
    }
}
