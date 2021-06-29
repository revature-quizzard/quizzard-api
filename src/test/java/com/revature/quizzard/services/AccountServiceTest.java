package com.revature.quizzard.services;

import com.revature.quizzard.dtos.AccountRegisterDTO;
import com.revature.quizzard.dtos.AuthenticatedDTO;
import com.revature.quizzard.dtos.CredentialsDTO;
import com.revature.quizzard.dtos.requestmodels.AddPointsDTO;
import com.revature.quizzard.dtos.responsemodel.AccountResponseDTO;
import com.revature.quizzard.exceptions.DuplicateRegistrationException;
import com.revature.quizzard.exceptions.InvalidCredentialsException;
import com.revature.quizzard.exceptions.ResourceNotFoundException;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.models.user.RoleEntity;
import com.revature.quizzard.repositories.AccountRepository;
import com.revature.quizzard.repositories.RoleRepository;
import com.revature.quizzard.repositories.UserRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.junit.Assert.*;

@ActiveProfiles("test")
public class AccountServiceTest {

    @InjectMocks
    private AccountService sut;

    @Mock
    private AccountRepository mockAccountRepository;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private RoleRepository mockRoleRepository;

    @Before
    public void setUp() {
        openMocks(this);
    }

    @After
    public void tearDown() {
        mockAccountRepository = null;
        mockUserRepository = null;
        mockRoleRepository = null;
    }

    @Test
    public void test_loginWithValidCredentials() {

        //Arrange
        AuthenticatedDTO authenticatedDTO;
        AccountEntity accountEntity = new AccountEntity();

        accountEntity.setId(1);
        accountEntity.setUsername("valid");
        accountEntity.setPassword("password");
        accountEntity.setRoles(new HashSet<RoleEntity>());
        accountEntity.setPoints(32767);

        CredentialsDTO credentialsDTO = new CredentialsDTO("valid","password");

        when(mockAccountRepository.findByUsernameAndPassword(credentialsDTO.getUsername(), credentialsDTO.getPassword()))
                .thenReturn(accountEntity);

        //Act
        authenticatedDTO = sut.login(credentialsDTO);

        //Assert
        Assert.assertEquals(new AuthenticatedDTO(1,32767, "valid", new HashSet<RoleEntity>()), authenticatedDTO);
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
        AccountEntity accountEntity = new AccountEntity();

        accountEntity.setId(1);
        accountEntity.setUsername("valid");
        accountEntity.setPassword("password");
        accountEntity.setPoints(32767);

        AuthenticatedDTO authenticatedDTO;
        AccountRegisterDTO accountRegisterDTO = new AccountRegisterDTO("valid","password","valid@email.com", "Validity", "Person");

        RoleEntity roleEntity = new RoleEntity(1, "ROLE_USER");
        when(mockRoleRepository.findById(1)).thenReturn(Optional.of(roleEntity));

        //Act
        authenticatedDTO = sut.register(accountRegisterDTO);


        //Assert
        Assert.assertEquals(new AuthenticatedDTO(accountEntity).getUsername(),authenticatedDTO.getUsername());
        verify(mockAccountRepository, times(1)).save(any());
        verify(mockUserRepository, times(1)).save(any());

    }

    @Test(expected = DuplicateRegistrationException.class)
    public void test_registerWithTakenUsername() {
        //Arrange
        AuthenticatedDTO authenticatedDTO;
        AccountRegisterDTO accountRegisterDTO = new AccountRegisterDTO("valid","password","valid@email.com", "Validity", "Person");
        when(mockAccountRepository.save(any())).thenThrow(DuplicateRegistrationException.class);

        RoleEntity roleEntity = new RoleEntity(1, "ROLE_USER");
        when(mockRoleRepository.findById(1)).thenReturn(Optional.of(roleEntity));

        //Act
        authenticatedDTO = sut.register(accountRegisterDTO);

        //Assert
        Assert.assertNull(authenticatedDTO.getUsername());
        verify(mockAccountRepository, times(1)).save(any());
        verify(mockUserRepository, times(0)).save(any());
    }

    @Test(expected = DuplicateRegistrationException.class)
    public void test_registerWithTakenEmail() {
        //Arrange
        AuthenticatedDTO authenticatedDTO;
        AccountRegisterDTO accountRegisterDTO = new AccountRegisterDTO("valid","password","valid@email.com", "Validity", "Person");
        when(mockAccountRepository.save(any())).thenThrow(DuplicateRegistrationException.class);

        RoleEntity roleEntity = new RoleEntity(1, "ROLE_USER");
        when(mockRoleRepository.findById(1)).thenReturn(Optional.of(roleEntity));

        //Act
        authenticatedDTO = sut.register(accountRegisterDTO);

        //Assert
        Assert.assertNull(authenticatedDTO.getUsername());
        verify(mockAccountRepository, times(1)).save(any());
        verify(mockUserRepository, times(0)).save(any());
    }

    @Test
    public void test_addPointsWithValidDetails() {
        AddPointsDTO pointsDTO = new AddPointsDTO();
        pointsDTO.setPoints(5);
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUsername("username");
        accountEntity.setPassword("password");
        accountEntity.setId(1);
        accountEntity.setPoints(0);

        when(mockAccountRepository.save(any())).thenReturn(accountEntity);
        //do not use any here, it will break -Richard
        when(mockAccountRepository.findById(1)).thenReturn(Optional.of(accountEntity));

        AccountResponseDTO responseDTO = sut.updatePoints(pointsDTO, accountEntity.getId());

        assertEquals(5, responseDTO.getPoints());
    }

    @Test
    public void test_addPointsWithInvalidDetails() {
        AccountEntity accountEntity = new AccountEntity();

        when(mockAccountRepository.findById(0)).thenReturn(Optional.empty());

        try {
            sut.updatePoints(new AddPointsDTO(), accountEntity.getId());
        } catch (Exception e) {
            assertTrue(e instanceof ResourceNotFoundException);
        } finally {
            verify(mockAccountRepository, times(0)).save(any());
        }
    }
}