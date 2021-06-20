package com.revature.quizzard.services;

import com.revature.quizzard.dtos.requestmodels.CardFavoriteDTO;
import com.revature.quizzard.exceptions.InvalidRequestException;
import com.revature.quizzard.models.composites.AccountCardEntity;
import com.revature.quizzard.models.flashcards.CardEntity;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.repositories.AccountCardRepository;
import com.revature.quizzard.repositories.AccountRepository;
import com.revature.quizzard.repositories.CardRepository;
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


public class AccountServiceTest {

    @InjectMocks
    private AccountService sut;

    @Mock
    private AccountRepository mockAccountRepository;

    @Before
    public void setUpTest() {
        openMocks(this);
    }

    @After
    public void tearDownTest() {
        mockAccountRepository = null;
    }

}
