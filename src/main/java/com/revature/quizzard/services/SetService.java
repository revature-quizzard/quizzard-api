package com.revature.quizzard.services;

import com.revature.quizzard.models.sets.SetEntity;
import com.revature.quizzard.repositories.SetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SetService {

    private SetRepository setRepository;

    @Autowired
    public SetService(SetRepository setRepository){this.setRepository = setRepository;}

    public Set<SetEntity> findIsPublicTrue(Boolean isPublic){
        return setRepository.findIsPublicTrue(isPublic);
    }
}
