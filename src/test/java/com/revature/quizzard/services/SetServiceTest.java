package com.revature.quizzard.services;

import com.revature.quizzard.dtos.*;
import com.revature.quizzard.exceptions.ResourceNotFoundException;
import com.revature.quizzard.models.flashcards.CardEntity;
import com.revature.quizzard.models.sets.SetEntity;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.repositories.*;
import org.junit.*;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class SetServiceTest {

    private SetService sut;
    private SetRepository mockSetRepo;
    private AccountRepository mockAccountRepo;
    private SetEntity mockSetEntity;
    private List<SetEntity> mockSetList;
    private AccountEntity mockAccount;
    private Set<CardEntity> mockCards;

    @Before
    public void setupTest() {
        mockSetRepo = mock(SetRepository.class);
        mockAccountRepo = mock(AccountRepository.class);
        sut = new SetService(mockSetRepo, mockAccountRepo);
    }

    @After
    public void teardownTest() {
        mockSetRepo = null;
        mockAccountRepo = null;
        sut = null;
        mockSetEntity = null;
        mockAccount = null;
        mockSetList = null;
        mockCards = null;
    }

    @Test
    public void test_getCreatedSetsWithValidUsernameAndSet() {

        mockAccount = new AccountEntity();
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

        mockAccount = new AccountEntity();
        mockSetList = new ArrayList<>();
        when(mockAccountRepo.findByUsername(any())).thenReturn(mockAccount);
        when(mockSetRepo.findAllCreatedByAccount(any())).thenReturn(mockSetList);

        List<SetDTO> result = sut.getCreatedSets("test");

        assertEquals(result.size(), 0);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void test_getCreatedSetsWithInvalidUsername() {

        List<SetDTO> result = sut.getCreatedSets("test");

    }

    @Test
    public void test_createStudySet(){
        SetDTO setDTO = new SetDTO();
        setDTO.setSetName("mockName");
//        setDTO.setCreator(mockAccount);
        setDTO.setPublic(true);
        setDTO.setSetId(2);

        //Expected
        doReturn(new SetEntity()).when(mockSetRepo).save(any());
        //Act
        SetDTO result = sut.createStudySets(setDTO);

        //Assert
        verify(mockSetRepo, times(1)).save(any());

        assertEquals(setDTO, result);

    }

    @Test
    public void test_getCardsBySetId(){
        SetEntity setEntity = new SetEntity();
        Set<CardEntity> expected = new HashSet<>();
        expected.add(new CardEntity(new CardDTO(1, "question", "answer", true, true, 1)));
        setEntity.setCards(expected);

        when(mockSetRepo.findById(1)).thenReturn(Optional.of(setEntity));

        List<CardDTO> actual = sut.getCardsBySetId(1);

        verify(mockSetRepo, times(1)).findById(1);

        assertEquals(actual.size(), 1);
    }

    @Test
    public void test_getSetById(){
        SetDTO expected = new SetDTO(1, "setname", true, new ArrayList<>());
        SetEntity setEntity = new SetEntity(expected);

        when(mockSetRepo.findById(1)).thenReturn(Optional.of(setEntity));

        SetDTO actual = sut.getSetById(1);

        verify(mockSetRepo, times(1)).findById(1);

        assertEquals(actual.getSetName(), expected.getSetName());
    }
}
