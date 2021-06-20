package com.revature.quizzard.services;

import com.revature.quizzard.dtos.AccountRegisterDTO;
import com.revature.quizzard.dtos.AuthenticatedDTO;
import com.revature.quizzard.dtos.CredentialsDTO;
import com.revature.quizzard.exceptions.InvalidCredentialsException;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.repositories.AccountRepository;
import com.revature.quizzard.repositories.UserRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class AccountServiceTest {

    @InjectMocks
    private AccountService sut;

    @Mock
    private AccountRepository mockAccountRepository;

    @Mock
    private UserRepository mockUserRepository;

    @Before
    public void setUp() {
        openMocks(this);
    }

    @After
    public void tearDown() {
        mockAccountRepository = null;
        mockUserRepository = null;
    }

    @Test
    public void test_loginWithValidCredentials() {

        //Arrange
        AuthenticatedDTO authenticatedDTO;
        AccountEntity accountEntity = new AccountEntity();

        accountEntity.setId(1);
        accountEntity.setUsername("valid");
        accountEntity.setPassword("password");
        accountEntity.setPoints(32767);

        CredentialsDTO credentialsDTO = new CredentialsDTO("valid","password");

        when(mockAccountRepository.findByUsernameAndPassword(credentialsDTO.getUsername(), credentialsDTO.getPassword()))
                .thenReturn(accountEntity);

        //Act
        authenticatedDTO = sut.login(credentialsDTO);

        //Assert
        Assert.assertEquals(new AuthenticatedDTO(1,32767, "valid"), authenticatedDTO);
    }

    @Test(expected = InvalidCredentialsException.class)
    public void test_loginWithInvalidCredentials() {
        //Arrange
        CredentialsDTO credentialsDTO = new CredentialsDTO("invalid","password");

        //Act
        sut.login(credentialsDTO);

        //Assert
        verify(mockAccountRepository, times(1)).findByUsernameAndPassword(credentialsDTO.getUsername(), credentialsDTO.getPassword());
    }

    @Test
    public void test_registerWithValidInformation() {
        //Arrange
        AccountRegisterDTO accountRegisterDTO = new AccountRegisterDTO("valid","password","valid@email.com", "Validity", "Person");

        //Act
        sut.register(accountRegisterDTO);

        //Assert

    }

    @Test
    public void test_registerWithTakenUsername() {

    }

    @Test
    public void test_registerWithTakenEmail() {

    }
}
