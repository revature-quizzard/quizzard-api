package com.revature.quizzard.web.controllers;

import com.fasterxml.jackson.databind.*;
import com.revature.quizzard.models.flashcards.SubjectEntity;
import com.revature.quizzard.web.dtos.CardDTO;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;
import org.springframework.test.web.servlet.result.*;
import org.springframework.test.web.servlet.setup.*;
import org.springframework.web.context.*;

import javax.smartcardio.Card;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CardControllerIntegrationTestSuite {

    private MockMvc mockMvc;
    private WebApplicationContext webApplicationContext;
    private ObjectMapper mapper;

    @Autowired
    public CardControllerIntegrationTestSuite(WebApplicationContext webApplicationContext, ObjectMapper mapper) {
        this.webApplicationContext = webApplicationContext;
        this.mapper = mapper;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void test_getCards() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/card/all")
                                                   .header("Content-Tye", "application/json"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
    }

    @Test
    public void test_createCard() throws Exception {
        CardDTO newCard = new CardDTO(1, "question", "answer", true, true, null);
        ObjectMapper json = new ObjectMapper();

        this.mockMvc.perform(MockMvcRequestBuilders.post("/card/newcard")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json.writeValueAsString(newCard))
        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}
