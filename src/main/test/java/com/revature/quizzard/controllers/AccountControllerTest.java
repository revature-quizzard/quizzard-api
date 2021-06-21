package com.revature.quizzard.controllers;

import com.revature.quizzard.dtos.AccountRegisterDTO;
import com.revature.quizzard.repositories.AccountRepository;
import com.revature.quizzard.repositories.UserRepository;
import com.revature.quizzard.security.JWTokenUtil;
import com.revature.quizzard.services.AccountService;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest
public class AccountControllerTest {
    private final WebApplicationContext webContext;
    private final JWTokenUtil jwTokenUtil;
    private MockMvc mockMvc;

    @Autowired
    public AccountControllerTest(WebApplicationContext webContext, JWTokenUtil jwTokenUtil) {
        this.webContext = webContext;
        this.jwTokenUtil = jwTokenUtil;
    }


    @Mock
    private AccountService mockAccountService;

    @Before
    public void setUp() {
        openMocks(this);
    }

    @After
    public void tearDown() {
        mockAccountService = null;
    }

    @BeforeEach
    public void setUpTest() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    @Test
    public void test_registerWithValidInput() {
        AccountRegisterDTO accountRegisterDTO = new AccountRegisterDTO();

    }


}
