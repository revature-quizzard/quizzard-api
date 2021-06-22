package com.revature.quizzard.services;

import com.revature.quizzard.dtos.*;
import com.revature.quizzard.exceptions.ResourceNotFoundException;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.repositories.*;
import org.junit.*;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class SetServiceTest {

    private SetService sut;
    private SetRepository mockSetRepo;
    private AccountRepository mockAccountRepo;
    private SetDTO setDTO;
    private AccountEntity mockAccount;

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
        setDTO = null;
        mockAccount = null;
    }

    @Test
    public void test_getCreatedSetsWithValidUsernameAndSet() {
        mockAccount.setUsername("test");
        setDTO = new SetDTO(1,"test", true, mockAccount);

        List<SetDTO> result = sut.getCreatedSets("test");

        assertEquals(result.size(), 1);
    }

    @Test
    public void test_getCreatedSetsWithValidUsernameAndNoSet() {
        mockAccount.setUsername("test");

        List<SetDTO> result = sut.getCreatedSets("test");

        assertEquals(result.size(), 0);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void test_getCreatedSetsWithInvalidUsername() {

        List<SetDTO> result = sut.getCreatedSets("test");

    }
}
