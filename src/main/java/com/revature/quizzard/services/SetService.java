package com.revature.quizzard.services;

import com.revature.quizzard.dtos.CardDTO;
import com.revature.quizzard.dtos.SetDTO;
import com.revature.quizzard.exceptions.ResourceNotFoundException;
import com.revature.quizzard.models.sets.SetEntity;
import com.revature.quizzard.repositories.SetRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

/**
 * SetService - Class to handled all validations to the data and call to the set repository
 */
@Service
@AllArgsConstructor(onConstructor_ = @Autowired )
public class SetService {

    private SetRepository setRepository;

    /**
     * findIsPublic -- Method that calls the repository to find all the public sets, and complete a conversion from
     * SetEntity to SetDTO. In the case that is unable to find any data, it will return an exception
     *
     * @param isPublic -- Must to be true to find the public values
     * @return -- A SET of SetEntity with the sets that satisfied the condition of be public
     * @throws ResourceNotFoundException -- This exception is throw when the method is unable to find set data
     */
    public Set<SetDTO> findIsPublic(Boolean isPublic) throws ResourceNotFoundException{

        Set<SetDTO> publicSetsDto = new HashSet<>();
        Set<SetEntity> publicSets = setRepository.findIsPublic(isPublic);

        if (publicSets.isEmpty()){

            throw new ResourceNotFoundException();

        }else{

            publicSets.stream().forEach(setEntity -> {
                SetDTO set = new SetDTO();
                set.setSetId(setEntity.getId());
                set.setCreatorId(setEntity.getCreator().getUser().getId());
                set.setName(setEntity.getName());
                setEntity.getCards().stream().forEach(cardEntity -> {
                    CardDTO card = new CardDTO();
                    if(cardEntity.isPublic()){
                        card.setCardId(cardEntity.getId());
                        card.setQuestion(cardEntity.getQuestion());
                        card.setAnswer(cardEntity.getAnswer());
                        card.setSubject(cardEntity.getSubject());
                        card.setCreatorId(cardEntity.getCreator().getId());
                        set.addCardToSet(card);
                    }
                });
                publicSetsDto.add(set);
            });
        }
        return publicSetsDto;
    }
}
