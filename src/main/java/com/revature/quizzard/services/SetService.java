package com.revature.quizzard.services;

import com.revature.quizzard.dtos.CardDTO;
import com.revature.quizzard.dtos.SetCardDTO;
import com.revature.quizzard.dtos.SetDTO;
import com.revature.quizzard.exceptions.ResourceNotFoundException;
import com.revature.quizzard.exceptions.StudySetNotFoundException;
import com.revature.quizzard.models.flashcards.CardEntity;
import com.revature.quizzard.models.flashcards.SubjectEntity;
import com.revature.quizzard.models.sets.SetEntity;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.repositories.AccountRepository;
import com.revature.quizzard.repositories.CardRepository;
import com.revature.quizzard.repositories.SetRepository;
import com.revature.quizzard.repositories.SubjectRepository;
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
    private SubjectRepository subjectRepository;
    private CardRepository cardRepo;
    private JWTokenUtil tokenUtil;
    private CardService cardService;

    /**
     * Saves a new set into the database which converts the provided setDTO into a setEntity
     * @param newSet A SetDTO to be sent to the database
     * @return SetEntity
     * @author Ann Louis-Charles
     * @author Chris Levano
     */

    //TODO Kevin
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

    //TODO Ozzy
    public List<SetEntity> getPublicSets()
    {
        return setRepo.findAllByIsPublic(true);
    }

    //TODO Giancarlo
    public List<SetEntity> getOwnedSets(String token) {

        int id = tokenUtil.getIdFromToken(token);
        Optional<AccountEntity> optionalAccountEntity = accountRepo.findById(id);
        if(optionalAccountEntity.isPresent()) {
            return setRepo.findAllByCreator(optionalAccountEntity.get());
        } else {
            throw new ResourceNotFoundException();
        }
    }

    //TODO Ozzy
    public Optional<SetEntity> getSetById(int id)
    {
        return setRepo.findById(id);
    }


    //TODO James
    public CardEntity save(SetCardDTO setCardDTO)
    {
        Optional<SetEntity> set = getSetById(setCardDTO.getStudySetId());
        Optional<SubjectEntity> subject = subjectRepository.findById(setCardDTO.getSubject().getId());
        AccountEntity account = accountRepo.findByUsername(setCardDTO.getCreator().getUsername());
        System.out.println(account);
        CardEntity card = new CardEntity(setCardDTO.getId(), null, setCardDTO.getQuestion(), setCardDTO.getAnswer(),
                                         setCardDTO.isReviewable(), setCardDTO.isPublic(), subject.orElse(null), account);
        set.orElseThrow(StudySetNotFoundException::new).getCards().add(card);
        CardEntity returnCard = cardService.savePublicCard(card);
        setRepo.save(set.get());
        return returnCard;
    }

}
