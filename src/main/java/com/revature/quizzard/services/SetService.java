package com.revature.quizzard.services;

import com.revature.quizzard.exceptions.ResourceNotFoundException;
import com.revature.quizzard.models.sets.SetEntity;
import com.revature.quizzard.repositories.SetRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * SetService - Class to handled all validations to the data and call to the set repository
 */
@Service
@AllArgsConstructor(onConstructor_ = @Autowired )
public class SetService {

    private SetRepository setRepository;

    /**
     * findIsPublic -- Method that calls the repository to find all the public sets.
     * In the case that is unable to find any data, it will return an exception
     * @param isPublic -- Must to be true to find the public values
     * @return -- A SET of SetEntity with the sets that satisfied the condition of be public
     * @throws ResourceNotFoundException -- This exception is throw when the method is unable to find set data
     */
    public Set<SetEntity> findIsPublic(Boolean isPublic) throws ResourceNotFoundException{
        Set<SetEntity> publicSets = setRepository.findIsPublic(isPublic);
        if (publicSets.isEmpty()){
            throw new ResourceNotFoundException();
        }
        return publicSets;
    }
}
