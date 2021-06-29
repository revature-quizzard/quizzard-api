package com.revature.quizzard.services;

import com.revature.quizzard.dtos.*;
import com.revature.quizzard.exceptions.ResourceNotFoundException;
import com.revature.quizzard.models.flashcards.CardEntity;
import com.revature.quizzard.models.sets.SetEntity;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.repositories.*;
import com.revature.quizzard.security.JWTokenUtil;
import org.junit.*;
import org.springframework.test.context.ActiveProfiles;

import javax.smartcardio.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@ActiveProfiles("test")
public class SetServiceTest {

    private SetService sut;
    private SetRepository mockSetRepo;
    private AccountRepository mockAccountRepo;
    private SetEntity mockSetEntity;
    private List<SetEntity> mockSetList;
    private AccountEntity mockAccount;
    private Set<CardEntity> mockCards;
    private JWTokenUtil mockTokenUtil;
    private CardRepository mockCardRepo;

    @Before
    public void setupTest() {
        mockSetRepo = mock(SetRepository.class);
        mockAccountRepo = mock(AccountRepository.class);
        mockTokenUtil = mock(JWTokenUtil.class);
        mockAccount = new AccountEntity();
        mockCardRepo = mock(CardRepository.class);
        sut = new SetService(mockSetRepo, mockAccountRepo, mockCardRepo, mockTokenUtil);
    }

    @After
    public void teardownTest() {
        mockSetRepo = null;
        mockAccountRepo = null;
        mockCardRepo = null;
        sut = null;
        mockSetEntity = null;
        mockAccount = null;
        mockSetList = null;
        mockCards = null;
    }

    @Test
    public void test_getCreatedSetsWithValidUsernameAndSet() {

        mockSetList = new ArrayList<>();
        mockCards = new HashSet<>();
        mockSetEntity = new SetEntity(1, mockCards, mockAccount, "test", true);
        mockSetList.add(mockSetEntity);
        when(mockAccountRepo.findByUsername(any())).thenReturn(mockAccount);
        when(mockSetRepo.findAllCreatedByAccount(any())).thenReturn(mockSetList);

        List<SetDTO> result = sut.getCreatedSets("test");

        assertEquals(1, result.size());
    }

    @Test
    public void test_getCreatedSetsWithValidUsernameAndNoSet() {

        mockSetList = new ArrayList<>();
        when(mockAccountRepo.findByUsername(any())).thenReturn(mockAccount);
        when(mockSetRepo.findAllCreatedByAccount(any())).thenReturn(mockSetList);

        List<SetDTO> result = sut.getCreatedSets("test");

        assertEquals(0, result.size());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void test_getCreatedSetsWithInvalidUsername() {

        when(mockAccountRepo.findByUsername(any())).thenReturn(null);

        List<SetDTO> result = sut.getCreatedSets("test");

    }

    @Test
    public void test_createStudySets(){
        //Arrange user
        int creatorId = 100;
        mockAccount.setId(100);
        mockAccount.setUsername("testuser");
        mockAccount.setPassword("testpassword");

        //Setup cards
        List<CardDTO> cards = new ArrayList<>();
        CardDTO card = new CardDTO();
        card.setId(100);
        card.setPublic(true);
        card.setAnswer("answer");
        card.setQuestion("question");
        card.setSubjectId(1);
        card.setReviewable(true);
        cards.add(card);

        CardEntity cardEntity = new CardEntity(card);

        //Setup set
        SetDTO newSet = new SetDTO();
        newSet.setSetId(100);
        newSet.setSetName("TestSet");
        newSet.setPublic(true);
        newSet.setCreator(mockAccount);
        newSet.setLocalFlashcards(cards);

        SetEntity setEntity = new SetEntity(newSet);
        setEntity.setId(100);

        when(mockCardRepo.findCardEntityById(anyInt())).thenReturn(cardEntity);
        when(mockAccountRepo.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(mockAccount));
        when(mockSetRepo.save(any())).thenReturn(setEntity);

        //Act
        SetDTO result = sut.createStudySets(newSet, creatorId);

        //Assert
        assertEquals(newSet.getSetId(), result.getSetId());




    }

//    @Test
//    public void test_createStudySet(){
//        SetDTO setDTO = new SetDTO();
//        setDTO.setSetName("mockName");
////        setDTO.setCreator(mockAccount);
//        setDTO.setPublic(true);
//        setDTO.setSetId(2);
//
//        //Expected
//        doReturn(new SetEntity()).when(mockSetRepo).save(any());
//        //Act
//        SetDTO result = sut.createStudySets(setDTO);
//
//        //Assert
//        verify(mockSetRepo, times(1)).save(any());
//
//        assertEquals(setDTO, result);
//
//    }
}
