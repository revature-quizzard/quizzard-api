package com.revature.quizzard.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.quizzard.dtos.AccountRegisterDTO;
import com.revature.quizzard.dtos.AuthenticatedDTO;
import com.revature.quizzard.dtos.CredentialsDTO;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.models.user.RoleEntity;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.mockito.Mock;

import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import static org.mockito.MockitoAnnotations.openMocks;


@SpringBootTest
public class AccountControllerTest {
    private final WebApplicationContext webContext;
    private MockMvc mockMvc;

    @Autowired
    public AccountControllerTest(WebApplicationContext webContext) {
        this.webContext = webContext;
    }

    @Mock
    private AccountRepository mockAccountRepository;

    @Mock
    private AccountService mockAccountService;

    @Mock
    private JWTokenUtil jwTokenUtil;


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
        //openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }


    /*
    This is actually putting the new user in the h2 inmem database. This is not what we want, we need
    to be able to injects mocked objects and mock method call returns.
     */
    @Test
    public void test_registerWithValidInput() throws Exception {
        AccountRegisterDTO accountRegisterDTO = new AccountRegisterDTO(
                "testuser",
                "password",
                "test@email.com",
                "test",
                "user");

        ObjectMapper mapper = new ObjectMapper();
        String jsonNewUser = mapper.writeValueAsString(accountRegisterDTO);

        this.mockMvc.perform(post("/register",0).content(jsonNewUser)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()) //We get back a 201
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)) //we get back a json body
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("testuser"));//we get back json that includes username: "testuser"

    }

    @Test
    public void test_failRegisterWithInvalidInput() throws Exception {
        AccountRegisterDTO accountRegisterDTO = new AccountRegisterDTO(
                null,
                null,
                null,
                "imBad",
                null);

        ObjectMapper mapper = new ObjectMapper();
        String jsonNewUser = mapper.writeValueAsString(accountRegisterDTO);

        this.mockMvc.perform(post("/register",0).content(jsonNewUser)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict()) //We get back a 409
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").doesNotExist());//this field is not present

    }


    /*
    This is actually checking the user in the h2 inmem database. This is not what we want, we need
    to be able to injects mocked objects and mock method call returns.
     */
    @Test
    public void test_loginWithValidCredentials() throws Exception {
        CredentialsDTO credentialsDTO = new CredentialsDTO("testuser", "password");
        AuthenticatedDTO authDto = new AuthenticatedDTO();
        authDto.setId(1);
        authDto.setUsername("testuser");
        HashSet<RoleEntity> set = new HashSet<>();
        RoleEntity role = new RoleEntity(1,"BASIC_USER");
        set.add(role);
        ObjectMapper mapper = new ObjectMapper();
        String jsonCredentials = mapper.writeValueAsString(credentialsDTO);
        when(mockAccountService.login(credentialsDTO)).thenReturn(authDto);
        when(jwTokenUtil.generateToken(authDto)).thenReturn("GOODTOKEN");
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(1);
        accountEntity.setUsername("testuser");
        accountEntity.setPoints(10);
        accountEntity.setRoles(set);
        when(mockAccountRepository.findByUsernameAndPassword("testuser", "password")).thenReturn(accountEntity);



        this.mockMvc.perform(post("/login",0).content(jsonCredentials)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted()) //We get back a 202
                .andExpect(MockMvcResultMatchers.jsonPath("$.username")
                .value("testuser"));//this field is not present
    }

    @Test
    public void test_failLoginWithInvalidCredentials() throws Exception {

    }
}
