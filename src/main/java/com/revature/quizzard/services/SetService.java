package com.revature.quizzard.services;

import com.revature.quizzard.models.sets.SetEntity;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.repositories.SetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SetService
{
    private SetRepository setRepository;

    public SetService(SetRepository setRepository)
    {
        this.setRepository = setRepository;
    }

    public List<SetEntity> getPublicSets()
    {
        return setRepository.findAllByIsPublic(true);
    }
    public Optional<SetEntity> getSetById(int id)
    {
        return setRepository.findById(id);
    }
    public SetEntity updateSet(SetEntity set)
    {
        return setRepository.save(set);
    }
}
