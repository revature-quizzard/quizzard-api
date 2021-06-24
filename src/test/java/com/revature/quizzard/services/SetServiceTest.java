package com.revature.quizzard.services;

import com.revature.quizzard.dtos.*;
import com.revature.quizzard.exceptions.ResourceNotFoundException;
import com.revature.quizzard.models.flashcards.CardEntity;
import com.revature.quizzard.models.sets.SetEntity;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.repositories.*;
import org.junit.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
}
