package com.revature.quizzard.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.quizzard.dtos.AccountRegisterDTO;
import com.revature.quizzard.dtos.AuthenticatedDTO;
import com.revature.quizzard.dtos.CredentialsDTO;
import com.revature.quizzard.exceptions.InvalidCredentialsException;
import com.revature.quizzard.models.user.RoleEntity;
import com.revature.quizzard.security.JWTokenUtil;
import com.revature.quizzard.services.AccountService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
public class AccountControllerTest {
    private final WebApplicationContext webContext;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public AccountControllerTest(WebApplicationContext webContext, ObjectMapper objectMapper) {
        this.webContext = webContext;
        this.objectMapper = objectMapper;
    }

    @MockBean
    private AccountService mockAccountService;

    @MockBean
    private JWTokenUtil jwTokenUtil;


    @BeforeEach
    public void setUpTest() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    @AfterEach
    public void tearDown() {
        mockAccountService = null;
    }


    @Test
    public void test_registerWithValidInput() throws Exception {
        //Create Registration DTO
        AccountRegisterDTO accountRegisterDTO = new AccountRegisterDTO(
                "testuser",
                "password",
                "test@email.com",
                "test",
                "user");

        //Create Authenticated user DTO
        HashSet<RoleEntity> roles = new HashSet<>();
        roles.add(new RoleEntity(1,"BASIC_USER"));
        AuthenticatedDTO authenticatedDTO = new AuthenticatedDTO(
                1,
                10,
                "testuser",
                roles);

        when(mockAccountService.register(accountRegisterDTO)).thenReturn(authenticatedDTO);

        String jsonNewUser = objectMapper.writeValueAsString(accountRegisterDTO);

        this.mockMvc.perform(post("/register",0).content(jsonNewUser)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()) //We get back a 201
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)) //we get back a json body
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("testuser"));//we get back json that includes username: "testuser"

    }

    /*
    Currently there isn;t any checking for invalid credentials on the API side. We need to either add
    checking in the service layer or add validators on the entities.
    @Test
    public void test_failRegisterWithInvalidInput() throws Exception {
        AccountRegisterDTO accountRegisterDTO = new AccountRegisterDTO(
                "",
                "",
                "",
                "",
                "");

        //Create Authenticated user DTO
        HashSet<RoleEntity> roles = new HashSet<>();
        roles.add(new RoleEntity(1,"BASIC_USER"));
        AuthenticatedDTO authenticatedDTO = new AuthenticatedDTO(
                1,
                10,
                "testuser",
                roles);

        //when(mockAccountService.register(accountRegisterDTO)).thenThrow()


        String jsonNewUser = objectMapper.writeValueAsString(accountRegisterDTO);

        this.mockMvc.perform(post("/register",0).content(jsonNewUser)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict()) //We get back a 409
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").doesNotExist());//this field is not present

    }

     */

    @Test
    public void test_loginWithValidCredentials() throws Exception {
        CredentialsDTO credentialsDTO = new CredentialsDTO("testuser", "password");
        String jsonCredentials = objectMapper.writeValueAsString(credentialsDTO);
        AuthenticatedDTO authDto = new AuthenticatedDTO();
        authDto.setId(1);
        authDto.setUsername("testuser");

        when(mockAccountService.login(credentialsDTO)).thenReturn(authDto);
        when(jwTokenUtil.generateToken(authDto)).thenReturn("GOODTOKEN");

        this.mockMvc.perform(post("/login",0).content(jsonCredentials)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted()) //We get back a 202
                .andExpect(MockMvcResultMatchers.jsonPath("$.username")
                .value("testuser"));//this field is not present
    }

    @Test
    public void test_failLoginWithInvalidCredentials() throws Exception {
        CredentialsDTO credentialsDTO = new CredentialsDTO("baduser", "badpass");
        String jsonCredentials = objectMapper.writeValueAsString(credentialsDTO);
        AuthenticatedDTO authDto = new AuthenticatedDTO();
        authDto.setId(1);
        authDto.setUsername("testuser");

        when(mockAccountService.login(credentialsDTO)).thenThrow(InvalidCredentialsException.class);

        this.mockMvc.perform(post("/login",0).content(jsonCredentials)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized()) //We get back a 401 unauthorized
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").doesNotExist());
    }
}
