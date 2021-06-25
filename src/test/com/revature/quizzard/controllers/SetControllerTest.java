package com.revature.quizzard.controllers;

import com.fasterxml.jackson.databind.*;

import com.revature.quizzard.dtos.SetDTO;
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

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.*;



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

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void test_getSets() throws Exception {
        //Arrange
        Set<SetDTO> mockSetDTO = new HashSet<>();
        mockSetDTO.add(new SetDTO());

        when(mockSetService.findIsPublic(true)).thenReturn(mockSetDTO);

        //Act
        this.mockMvc.perform(get("/set/public")
                .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


}
