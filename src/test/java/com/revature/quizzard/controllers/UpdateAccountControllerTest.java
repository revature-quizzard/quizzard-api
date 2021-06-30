package com.revature.quizzard.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.quizzard.dtos.AccountInfoDTO;
import com.revature.quizzard.dtos.AuthenticatedDTO;
import com.revature.quizzard.dtos.requestmodels.AddPointsDTO;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.models.user.RoleEntity;
import com.revature.quizzard.models.user.UserEntity;
import com.revature.quizzard.repositories.AccountRepository;
import com.revature.quizzard.repositories.RoleRepository;
import com.revature.quizzard.repositories.SetRepository;
import com.revature.quizzard.repositories.UserRepository;
import com.revature.quizzard.security.JWTokenUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UpdateAccountControllerTest {
    private WebApplicationContext webContext;
    private AccountRepository accountRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private MockMvc mockMvc;
    private JWTokenUtil jwTokenUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public UpdateAccountControllerTest(WebApplicationContext webContext, AccountRepository accountRepository, UserRepository userRepository, RoleRepository roleRepository, JWTokenUtil jwTokenUtil) {
        this.webContext = webContext;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwTokenUtil = jwTokenUtil;
    }

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();


    }


    @Test
    public void updateAccountInfo() throws Exception {
        UserEntity user = new UserEntity(50, "mock", "mocker", "mockman@mail.com");
        RoleEntity role = new RoleEntity(50, "Tester");
        Set<RoleEntity> roles = new HashSet<>();
        roles.add(role);
        AccountEntity account = new AccountEntity(50, user, null, roles, "oldUser", "password", 1);


        userRepository.save(user);
        roleRepository.save(role);
        accountRepository.save(account);

        AuthenticatedDTO authenticatedDTO = new AuthenticatedDTO(account);
        String jwt = jwTokenUtil.generateToken(authenticatedDTO);

        AccountInfoDTO accountInfoDTO = new AccountInfoDTO();
        accountInfoDTO.setUsername("newUser");

        this.mockMvc.perform(MockMvcRequestBuilders.put("/accounts/update").header("Authorization","Bearer " + jwt)
                .content(asJsonString(accountInfoDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test_addPointsWithValidToken() throws Exception {
        UserEntity user = new UserEntity(1, "Alice", "Anderson", "aanderson@mail.com");
        RoleEntity role = new RoleEntity(1, "ROLE_USER");
        Set<RoleEntity> roles = new HashSet<>();
        roles.add(role);
        AccountEntity account = new AccountEntity(1, user, null, roles, "aanderson", "password", 0);
        AddPointsDTO dto = new AddPointsDTO();
        dto.setPoints(5);

        String token = jwTokenUtil.generateToken(new AuthenticatedDTO(account));
        String requestBody = objectMapper.writeValueAsString(dto);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/accounts/points")
                .header("Authorization", "Bearer " + token).content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

    }

    @Test
    public void test_addPointsWithInvalidToken() throws Exception {
        UserEntity user = new UserEntity(69, "worong", "person", "worong@mail.com");
        RoleEntity role = new RoleEntity(69, "ROLE_USER");
        Set<RoleEntity> roles = new HashSet<>();
        roles.add(role);
        AccountEntity account = new AccountEntity(69, user, null, roles, "worongperson", "password", 0);
        AddPointsDTO dto = new AddPointsDTO();
        dto.setPoints(5);

        String token = jwTokenUtil.generateToken(new AuthenticatedDTO(account));
        String requestBody = objectMapper.writeValueAsString(dto);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/accounts/points")
                .header("Authorization", "Bearer " + token).content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();

    }
}
