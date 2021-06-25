package com.revature.quizzard.services;

import com.revature.quizzard.dtos.*;
import com.revature.quizzard.dtos.requestmodels.CardConfidentDTO;
import com.revature.quizzard.dtos.requestmodels.CardFavoriteDTO;
import com.revature.quizzard.exceptions.InvalidRequestException;
import com.revature.quizzard.models.composites.AccountCardEntity;
import com.revature.quizzard.models.flashcards.*;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.repositories.*;
import org.junit.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.openMocks;

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
    public void setupTest(){
       openMocks(this);

    }

    @After
    public void teardownTest(){
        mockAccountCardRepository = null;
        mockCardRepository = null;
        mockAccountRepository = null;
        sut = null;
    }

    @Test
    public void test_getCards(){
        //Expected
        List<CardDTO> cardDTOList = new ArrayList<>();
        CardDTO cardDTO = new CardDTO();
        cardDTO.setAnswer("bar");
        cardDTO.setQuestion("foo");
        cardDTO.setPublic(true);
        cardDTO.setReviewable(true);
        cardDTO.setSubjectId(1);
        cardDTOList.add(cardDTO);

        //Sut input
        List<CardEntity> cardEntities = new ArrayList<>();
        CardEntity cardEntity = new CardEntity(cardDTO);
        cardEntities.add(cardEntity);

        doReturn(cardEntities).when(mockCardRepository).findAll();

        //Act
        List<CardDTO> result = sut.getCards();

        //Assert
        verify(mockCardRepository, times(1)).findAll();
        assertTrue(cardDTOList.get(0).getQuestion().equals(result.get(0).getQuestion()));

    }

    @Test
    public void test_createCard(){
        CardDTO cardDTO = new CardDTO();
        cardDTO.setAnswer("bar");
        cardDTO.setQuestion("foo");
        cardDTO.setPublic(true);
        cardDTO.setReviewable(true);
        cardDTO.setSubjectId(1);

        //Expected
        doReturn(new CardEntity()).when(mockCardRepository).save(any());
        //Act
        CardDTO result = sut.createCard(cardDTO);

        //Assert
        verify(mockCardRepository, times(1)).save(any());

        assertEquals(cardDTO, result);
    }

    @Test
    public void when_ToggleConfidentCardPresent_thenreturnTrue() {
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

        CardConfidentDTO dto = new CardConfidentDTO(1, 1, true);

        assertTrue(sut.toggleConfidentCard(dto));
    }

    @Test(expected = InvalidRequestException.class)
    public void when_ToggleConfidentNotPresent_thenThrowException() {

        when(mockAccountRepository.findById(1)).thenReturn(Optional.empty());
        when(mockCardRepository.findById(1)).thenReturn(Optional.empty());

        CardConfidentDTO dto = new CardConfidentDTO(1, 1, true);
        sut.toggleConfidentCard(dto);
    }

    @Test
    public void when_ToggleConfidentCardExists_thenreturnTrue() {
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

        CardConfidentDTO dto = new CardConfidentDTO(1, 1, true);

        assertTrue(sut.toggleConfidentCard(dto));
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
