package com.revature.quizzard.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import com.revature.quizzard.dtos.AuthenticatedDTO;
import com.revature.quizzard.dtos.CardDTO;
import com.revature.quizzard.dtos.SetDTO;
import com.revature.quizzard.models.sets.*;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.security.JWTokenUtil;
import com.revature.quizzard.services.SetService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;
import org.springframework.test.web.servlet.result.*;
import org.springframework.test.web.servlet.setup.*;
import org.springframework.web.context.*;

import javax.servlet.http.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ActiveProfiles("test")
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
        mockAuthDTO = new AuthenticatedDTO(1, 0, "test", new HashSet<>());
    }

    @AfterEach
    public void tearDown(){
        mockAccount = null;
        mockAuthDTO = null;
    }

//    @Test
//    public void test_getCards() throws Exception {
//        //Arrange
//
//        //Act
//        this.mockMvc.perform(get("/sets/public")
//                .header("Content-Type", "application/json"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//    }

    @Test
    public void test_createCards() throws Exception {
        SetDTO newSet = new SetDTO();
        ObjectMapper json = new ObjectMapper();
        //mockAccount needs acctController, jwtTokenUtil, and jwt.secret
//        HttpServletRequest mockReq = mock(HttpServletRequest);

        this.mockMvc.perform(post("/sets/newset")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwTokenUtil.generateToken(mockAuthDTO))
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
        List<SetDTO> results = new ArrayList<>();
        when(mockSetService.getCreatedSets(any())).thenReturn(results);

        //Act
        this.mockMvc.perform(get("/sets/created")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwTokenUtil.generateToken(mockAuthDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void test_getPublicSets() throws Exception {
        //Arrange
        List<SetEntity> mockSetEntity = new ArrayList<>();
        mockSetEntity.add(new SetEntity());

        when(mockSetService.getPublicSets()).thenReturn(mockSetEntity);

        //Act
        this.mockMvc.perform(get("/publicSets")
                .header("Content-Type", "application/json"))
                    .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void test_cardsSave() throws Exception {
        CardDTO newCard = new CardDTO(1, "", "", true, true, 1);
        ObjectMapper json = new ObjectMapper();

        this.mockMvc.perform(MockMvcRequestBuilders.post("/cards/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.writeValueAsString(newCard))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


    @Test
    public void test_getOwnedSets() throws Exception {
        List<SetEntity> results = new ArrayList<>();
        when(mockSetService.getOwnedSets(any())).thenReturn(results);

        this.mockMvc.perform(get("/ownedSets")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwTokenUtil.generateToken(mockAuthDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

}