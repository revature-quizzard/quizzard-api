package com.revature.quizzard.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.quizzard.dtos.UserDTO;
import com.revature.quizzard.dtos.requestmodels.CardConfidentDTO;
import com.revature.quizzard.dtos.requestmodels.CardFavoriteDTO;
import com.revature.quizzard.security.JWTokenUtil;
import lombok.extern.java.Log;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Log
public class CardControllerTest {

    private MockMvc mockMvc;
    private WebApplicationContext webApplicationContext;
    private JWTokenUtil jwTokenUtil;
    private ObjectMapper objectMapper;

    @Autowired
    public CardControllerTest(WebApplicationContext webApplicationContext, JWTokenUtil jwTokenUtil, ObjectMapper objectMapper) {
        this.webApplicationContext = webApplicationContext;
        this.jwTokenUtil = jwTokenUtil;
        this.objectMapper = objectMapper;
    }


    @BeforeEach
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    public void breakdown(){
        this.mockMvc = null;
        log.info("Cri inside erry time");
    }

    @Test
    public void test_toggleFavoriteCard() throws Exception {
        CardFavoriteDTO favoriteDTO = new CardFavoriteDTO();
        favoriteDTO.setFavorite(true);
        favoriteDTO.setAccountId(1);
        favoriteDTO.setCardId(1);
        // TODO need kyle and sheeckems refactor for AuthenticatedDTO to finish in the meantime this broky
        String jwtToken = jwTokenUtil.generateToken(new UserDTO());

        this.mockMvc.perform(post("/card/favorite").content(objectMapper.writeValueAsString(favoriteDTO)).contentType(MediaType.APPLICATION_JSON)
                .header("Authorization",jwtToken))
                    .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void test_toggleConfidentCard() throws Exception {
        CardConfidentDTO favoriteDTO = new CardConfidentDTO();
        favoriteDTO.setConfident(true);
        favoriteDTO.setAccountId(1);
        favoriteDTO.setCardId(1);
        // TODO need kyle and sheeckems refactor for AuthenticatedDTO to finish in the meantime this broky
        String jwtToken = jwTokenUtil.generateToken(new UserDTO());

        this.mockMvc.perform(post("/card/confident").content(objectMapper.writeValueAsString(favoriteDTO)).contentType(MediaType.APPLICATION_JSON)
                .header("Authorization",jwtToken))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void test_toggleInvalidFavoriteCard() throws Exception {
        CardConfidentDTO favoriteDTO = new CardConfidentDTO();
        favoriteDTO.setConfident(true);
        favoriteDTO.setAccountId(1);
        favoriteDTO.setCardId(1);
        // TODO need kyle and sheeckems refactor for AuthenticatedDTO to finish in the meantime this broky
        String jwtToken = jwTokenUtil.generateToken(new UserDTO());

        this.mockMvc.perform(post("/card/favorite").content(objectMapper.writeValueAsString(favoriteDTO))
                .header("Authorization",jwtToken))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void test_toggleInvalidConfidentCard() throws Exception {
        CardFavoriteDTO favoriteDTO = new CardFavoriteDTO();
        favoriteDTO.setFavorite(true);
        favoriteDTO.setAccountId(1);
        favoriteDTO.setCardId(1);
        // TODO need kyle and sheeckems refactor for AuthenticatedDTO to finish in the meantime this broky
        String jwtToken = jwTokenUtil.generateToken(new UserDTO());

        this.mockMvc.perform(post("/card/confident").content(objectMapper.writeValueAsString(favoriteDTO))
                .header("Authorization",jwtToken))
                .andExpect(status().is4xxClientError());
    }


}
