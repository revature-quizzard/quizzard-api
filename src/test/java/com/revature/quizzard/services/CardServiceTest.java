package com.revature.quizzard.services;

import com.revature.quizzard.dtos.*;
import com.revature.quizzard.models.flashcards.*;
import com.revature.quizzard.repositories.*;
import org.junit.*;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class CardServiceTest {

    private CardService sut;
    private CardRepository mockCardRepository;
    private CardDTO cardDTO;


    @Before
    public void setupTest(){
        mockCardRepository = mock(CardRepository.class);
        sut = new CardService(mockCardRepository);
        cardDTO = new CardDTO();
        cardDTO.setAnswer("bar");
        cardDTO.setQuestion("foo");
        cardDTO.setPublic(true);
        cardDTO.setReviewable(true);
        cardDTO.setSubjectId(1);

    }

    @After
    public void teardownTest(){
        mockCardRepository = null;
        sut = null;
        cardDTO = null;
    }

    @Test
    public void test_getCards(){
        //Expected
        List<CardDTO> cardDTOList = new ArrayList<>();
        cardDTOList.add(cardDTO);

        //Sut input
        List<CardEntity> cardEntities = new ArrayList<>();
        CardEntity cardEntity = new CardEntity(cardDTO);
        cardEntities.add(cardEntity);

        doReturn(cardEntities).when(mockCardRepository).findAll();

        //Act
        List<CardDTO> result = sut.getCards();

        //Assert
        verify(mockCardRepository, times(1)).findAll();
        assertTrue(cardDTOList.get(0).getQuestion().equals(result.get(0).getQuestion()));

    }

    @Test
    public void test_createCard(){

        //Expected
        doReturn(new CardEntity()).when(mockCardRepository).save(any());
        //Act
        CardDTO result = sut.createCard(cardDTO);

        //Assert
        verify(mockCardRepository, times(1)).save(any());

        assertEquals(cardDTO, result);
    }


}
