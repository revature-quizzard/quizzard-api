package com.revature.quizzard.controllers;

import com.fasterxml.jackson.databind.*;

import com.revature.quizzard.dtos.CardDTO;
import com.revature.quizzard.dtos.SetDTO;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.services.SetService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.Mock;
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



@SpringBootTest
@ExtendWith(SpringExtension.class)
public class SetControllerTest {

    private MockMvc mockMvc;
    private WebApplicationContext webApplicationContext;
    private ObjectMapper mapper;

    @Autowired
    public SetControllerTest(WebApplicationContext webApplicationContext, ObjectMapper mapper) {
        this.webApplicationContext = webApplicationContext;
        this.mapper = mapper;
    }

    @MockBean
    private SetService mockSetService;
    private AccountEntity mockAccount;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    public void tearDown(){
        mockAccount = null;
    }

    @Test
    public void test_getCards() throws Exception {
        //Arrange

        //Act
        this.mockMvc.perform(MockMvcRequestBuilders.get("/set/public")
                .header("Content-Tye", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_createCards() throws Exception {
        SetDTO newSet = new SetDTO(1,"mockName",false, mockAccount);
        ObjectMapper json = new ObjectMapper();
        //mockAccount needs acctController, jwtTokenUtil, and jwt.secret

        this.mockMvc.perform(MockMvcRequestBuilders.post("/sets/newset")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.writeValueAsString(newSet))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}