package com.revature.quizzard.services;

import com.revature.quizzard.dtos.AccountInfoDTO;
import com.revature.quizzard.dtos.requestmodels.CardFavoriteDTO;
import com.revature.quizzard.exceptions.InvalidRequestException;
import com.revature.quizzard.models.composites.AccountCardEntity;
import com.revature.quizzard.models.flashcards.CardEntity;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.models.user.UserEntity;
import com.revature.quizzard.repositories.AccountCardRepository;
import com.revature.quizzard.repositories.AccountRepository;
import com.revature.quizzard.repositories.CardRepository;
import com.revature.quizzard.repositories.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import javax.jws.soap.SOAPBinding;
import java.util.*;

import static org.mockito.MockitoAnnotations.*;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


public class UpdateAccountServiceTest {

    @InjectMocks
    private AccountService sut;

    @Mock
    private AccountRepository mockAccountRepository;
    @Mock
    private UserRepository mockUserRepository;

    @Before
    public void setUpTest() {
        openMocks(this);

    }

    @After
    public void tearDownTest() {
        mockAccountRepository = null;
        mockUserRepository = null;
    }

    @Test
    public void updateUserEmail(){
        UserEntity user = new UserEntity(1, "mock", "mocker", "mockman@mail.com");
        AccountEntity account = new AccountEntity(1, user, null, null, "mocker", "password", 1);
        AccountInfoDTO accountInfoDTO = new AccountInfoDTO("", "fake@email.com", "");
        Map<String, Object> updatedMap = new HashMap<>();
        updatedMap.put("email", accountInfoDTO.getEmail());

        when(mockAccountRepository.findById (anyInt())).thenReturn(Optional.of(account));
        when(mockUserRepository.findById (anyInt())).thenReturn(Optional.of(user));
        Map testMap = sut.updateAccountInfo(1, accountInfoDTO);

        assertEquals(testMap, updatedMap);


    }

    @Test
    public void updateUsername(){
        UserEntity user = new UserEntity(1, "mock", "mocker", "mockman@mail.com");
        AccountEntity account = new AccountEntity(1, user, null, null, "mocker", "password", 1);
        AccountInfoDTO accountInfoDTO = new AccountInfoDTO("faker", "", "");
        Map<String, Object> updatedMap = new HashMap<>();
        updatedMap.put("username", accountInfoDTO.getUsername());

        when(mockAccountRepository.findById (anyInt())).thenReturn(Optional.of(account));
        when(mockUserRepository.findById (anyInt())).thenReturn(Optional.of(user));

        Map testMap = sut.updateAccountInfo(1, accountInfoDTO);

        assertEquals(testMap, updatedMap);
    }

    @Test
    public void updatePassword(){
        UserEntity user = new UserEntity(1, "mock", "mocker", "mockman@mail.com");
        AccountEntity account = new AccountEntity(1, user, null, null, "mocker", "password", 1);
        AccountInfoDTO accountInfoDTO = new AccountInfoDTO("", "", "fakePassword");
        Map<String, Object> updatedMap = new HashMap<>();
        updatedMap.put("password", "Password Updated");

        when(mockAccountRepository.findById (anyInt())).thenReturn(Optional.of(account));
        when(mockUserRepository.findById (anyInt())).thenReturn(Optional.of(user));

        Map testMap = sut.updateAccountInfo(1, accountInfoDTO);

        assertEquals(testMap, updatedMap);
    }

    @Test
    public void updateWithNoAccount(){
        UserEntity user = new UserEntity(1, "mock", "mocker", "mockman@mail.com");
        AccountEntity account = new AccountEntity(1, user, null, null, "mocker", "password", 1);
        AccountInfoDTO accountInfoDTO = new AccountInfoDTO("", "fake@email.com", "");

        when(mockAccountRepository.findById (anyInt())).thenReturn(Optional.ofNullable(null));
        when(mockUserRepository.findById (anyInt())).thenReturn(Optional.of(user));

        Map testMap = sut.updateAccountInfo(1, accountInfoDTO);
        assertNull(testMap);

    }

    @Test
    public void updateWithNoUser(){
        UserEntity user = new UserEntity(1, "mock", "mocker", "mockman@mail.com");
        AccountEntity account = new AccountEntity(1, user, null, null, "mocker", "password", 1);
        AccountInfoDTO accountInfoDTO = new AccountInfoDTO("", "fake@email.com", "");

        when(mockAccountRepository.findById (anyInt())).thenReturn(Optional.of(account));
        when(mockUserRepository.findById (anyInt())).thenReturn(Optional.ofNullable(null));

        Map testMap = sut.updateAccountInfo(1, accountInfoDTO);
        assertNull(testMap);


    }

    @Test
    public void updatewithtakenInfo(){
        UserEntity user = new UserEntity(1, "mock", "mocker", "mockman@mail.com");
        AccountEntity account = new AccountEntity(1, user, null, null, "mocker", "password", 1);
        AccountInfoDTO accountInfoDTO = new AccountInfoDTO("", "fake@email.com", "");
        Map<String, Object> updatedMap = new HashMap<>();
        updatedMap.put("conflict", "email and/or username is already taken");

        when(mockAccountRepository.findByUsername(anyString())).thenReturn(account);
        when(mockAccountRepository.findById (anyInt())).thenReturn(Optional.of(account));
        when(mockUserRepository.findById (anyInt())).thenReturn(Optional.ofNullable(null));

        Map testMap = sut.updateAccountInfo(1, accountInfoDTO);
        assertEquals(testMap, updatedMap);
    }




}
