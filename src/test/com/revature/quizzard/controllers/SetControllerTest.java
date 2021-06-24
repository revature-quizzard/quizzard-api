package com.revature.quizzard.controllers;

import com.fasterxml.jackson.databind.*;

import com.revature.quizzard.services.SetService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
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

    @Mock
    private SetService mockSetService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void test_getSets() throws Exception {
        //Arrange

        //Act
        this.mockMvc.perform(MockMvcRequestBuilders.get("/set/public")
                .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


}
