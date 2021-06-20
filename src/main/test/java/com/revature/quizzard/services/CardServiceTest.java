package com.revature.quizzard.services;

import com.revature.quizzard.dtos.requestmodels.CardFavoriteDTO;
import com.revature.quizzard.exceptions.InvalidRequestException;
import com.revature.quizzard.models.composites.AccountCardEntity;
import com.revature.quizzard.models.flashcards.CardEntity;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.repositories.AccountCardRepository;
import com.revature.quizzard.repositories.AccountRepository;
import com.revature.quizzard.repositories.CardRepository;
import com.revature.quizzard.services.AccountService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.MockitoAnnotations.*;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


public class CardServiceTest {


    @InjectMocks
    private CardService sut;

    @Mock
    private AccountRepository mockAccountRepository;

    @Mock
    private CardRepository mockCardRepository;

    @Mock
    private AccountCardRepository mockAccountCardRepository;

    @Before
    public void setUpTest() {
        openMocks(this);
    }

    @After
    public void tearDownTest() {
        mockAccountCardRepository = null;
        mockCardRepository = null;
        mockAccountRepository = null;
    }

    @Test
    public void when_addFavoriteCardPresent_thenreturnTrue() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(1);
        accountEntity.setUsername("username");
        accountEntity.setPassword("password");

        CardEntity cardEntity = new CardEntity();
        cardEntity.setQuestion("question");
        cardEntity.setAnswer("answer");
        cardEntity.setId(1);

        when(mockAccountRepository.findById(1)).thenReturn(Optional.of(accountEntity));
        when(mockCardRepository.findById(1)).thenReturn(Optional.of(cardEntity));

        CardFavoriteDTO dto = new CardFavoriteDTO(1, 1, true);

        assertTrue(sut.addFavoriteCard(dto));
    }

    @Test(expected = InvalidRequestException.class)
    public void when_addFavoriteCardNotPresent_thenThrowException() {

        when(mockAccountRepository.findById(1)).thenReturn(Optional.empty());
        when(mockCardRepository.findById(1)).thenReturn(Optional.empty());

        CardFavoriteDTO dto = new CardFavoriteDTO(1, 1, true);
        sut.addFavoriteCard(dto);
    }

    @Test
    public void when_addFavoriteCardExists_thenreturnTrue() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(1);
        accountEntity.setUsername("username");
        accountEntity.setPassword("password");
        Set<AccountCardEntity> accountCardEntitySet = new HashSet<>();
        AccountCardEntity cardEntityAccount = new AccountCardEntity();
        CardEntity cardEntity = new CardEntity();
        cardEntity.setQuestion("question");
        cardEntity.setAnswer("answer");
        cardEntity.setId(1);
        cardEntityAccount.setCardEntity(cardEntity);
        cardEntityAccount.setAccountEntity(accountEntity);

        accountCardEntitySet.add(cardEntityAccount);

        accountEntity.setAccountCardEntities(accountCardEntitySet);


        when(mockAccountRepository.findById(1)).thenReturn(Optional.of(accountEntity));
        when(mockCardRepository.findById(1)).thenReturn(Optional.of(cardEntity));

        CardFavoriteDTO dto = new CardFavoriteDTO(1, 1, true);

        assertTrue(sut.addFavoriteCard(dto));
    }
}
