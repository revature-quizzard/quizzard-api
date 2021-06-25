package com.revature.quizzard.controllers;

import com.fasterxml.jackson.databind.*;

import com.revature.quizzard.dtos.AuthenticatedDTO;
import com.revature.quizzard.dtos.SetDTO;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.security.JWTokenUtil;
import com.revature.quizzard.services.SetService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;
import org.springframework.test.web.servlet.result.*;
import org.springframework.test.web.servlet.setup.*;
import org.springframework.web.context.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class SetControllerTest {

    private MockMvc mockMvc;
    private WebApplicationContext webApplicationContext;
    private ObjectMapper mapper;
    private JWTokenUtil jwTokenUtil;

    @Autowired
    public SetControllerTest(WebApplicationContext webApplicationContext, ObjectMapper mapper, JWTokenUtil jwTokenUtil) {
        this.webApplicationContext = webApplicationContext;
        this.mapper = mapper;
        this.jwTokenUtil = jwTokenUtil;
    }

    @MockBean
    private SetService mockSetService;
    private AccountEntity mockAccount;
    private AuthenticatedDTO mockAuthDTO;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    public void tearDown(){
        mockAccount = null;
        mockAuthDTO = null;
    }

    @Test
    public void test_getCards() throws Exception {
        //Arrange

        //Act
        this.mockMvc.perform(get("/sets/public")
                .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_createCards() throws Exception {
        SetDTO newSet = new SetDTO();
        ObjectMapper json = new ObjectMapper();
        //mockAccount needs acctController, jwtTokenUtil, and jwt.secret

        this.mockMvc.perform(post("/sets/newset")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.writeValueAsString(newSet))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_getCreatedSets() throws Exception {
        //Arrange
        mockAuthDTO = new AuthenticatedDTO(1, 0, "test", new HashSet<>());
        List<SetDTO> results = new ArrayList<>();
        when(mockSetService.getCreatedSets(any())).thenReturn(results);

        //Act
        this.mockMvc.perform(get("/sets/created")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwTokenUtil.generateToken(mockAuthDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}