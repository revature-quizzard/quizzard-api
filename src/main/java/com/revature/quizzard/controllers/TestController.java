package com.revature.quizzard.controllers;

import com.revature.quizzard.dtos.CardDTO;
import com.revature.quizzard.dtos.SetCardDTO;
import com.revature.quizzard.dtos.requestmodels.AddPointsDTO;
import com.revature.quizzard.dtos.responsemodel.AccountResponseDTO;
import com.revature.quizzard.exceptions.StudySetNotFoundException;
import com.revature.quizzard.models.flashcards.CardEntity;
import com.revature.quizzard.models.flashcards.SubjectEntity;
import com.revature.quizzard.models.sets.SetEntity;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.repositories.AccountRepository;
import com.revature.quizzard.repositories.SubjectRepository;
import com.revature.quizzard.services.AccountService;
import com.revature.quizzard.services.CardService;
import com.revature.quizzard.services.SetService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.quizzard.dtos.requestmodels.CardFavoriteDTO;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
@AllArgsConstructor(onConstructor = @__(@Autowired))
//@CrossOrigin("*")//Needed this to solve a cors issue. Obviously less than ideal
public class TestController {

    private final AccountRepository accountRepository;
    private final SubjectRepository subjectRepository;
    private final CardService cardService;
    private final SetService setService;
    private final AccountService accountService;

    @GetMapping("/test")
    public void securityHealthStatus(HttpServletRequest request, HttpServletResponse response){
        System.out.println("Test Succeeded");
        response.setStatus(200);
    }

    @GetMapping("/fail")
    public void securityHealthStatusFail(HttpServletRequest request, HttpServletResponse response){
        System.out.println("Test Succeeded");
        // SUPPOSED TO FAIL, this is an authenticated endpoint
        response.setStatus(200);
    }

    @GetMapping("/account")
    public AccountEntity getAccount() {
        Optional<AccountEntity> account = accountRepository.findById(1);
        System.out.println(account);
        if(account.isPresent()) return account.get();
        return null;
    }

    @PostMapping("/favorite/card")
    @ResponseStatus(HttpStatus.OK)

    public void toggleFavoriteCard(@RequestBody CardFavoriteDTO dto) {

        cardService.addFavoriteCard(dto);
    }

    @GetMapping("/publicSets")
    @ResponseStatus(HttpStatus.OK)
    public List<SetEntity> getPublicSets()
    {
        List<SetEntity> list = setService.getPublicSets();
        System.out.println(list);
        return list;
    }

    @GetMapping("/ownedSets")
    @ResponseStatus(HttpStatus.OK)
    public List<SetEntity> getOwnedSets(HttpServletRequest request)
    {
        //TODO: Adjust this to get sets owned by user
        String token = request.getHeader("Authorization");
        System.out.println("~ ~ ~ ~ ~ ~ ~ Inside Controller: " + token);
        List<SetEntity> list = setService.getOwnedsets(token);
        System.out.println(list);
        return list;
    }

    @PostMapping("/cards/save")
    @ResponseStatus(HttpStatus.CREATED)
    public CardEntity saveCard(@RequestBody SetCardDTO dto)
    {
        System.out.println(dto);
        Optional<SetEntity> set = setService.getSetById(dto.getStudySetId());
        Optional<SubjectEntity> subject = subjectRepository.findById(dto.getSubject().getId());
        AccountEntity account = accountRepository.findByUsername(dto.getCreator().getUsername());
        System.out.println(account);
        CardEntity card = new CardEntity(dto.getId(), null, dto.getQuestion(), dto.getAnswer(),
                                         dto.isReviewable(), dto.isPublic(), subject.orElse(null), account);
        set.orElseThrow(StudySetNotFoundException::new).getCards().add(card);
        CardEntity returnCard = cardService.savePublicCard(card);
        SetEntity returnSet = setService.updateSet(set.get());

        System.out.println(returnCard);
        return returnCard;
    }

}
