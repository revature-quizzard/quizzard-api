package com.revature.quizzard.services;

import com.revature.quizzard.dtos.SetDTO;
import com.revature.quizzard.exceptions.ResourceNotFoundException;
import com.revature.quizzard.models.flashcards.CardEntity;
import com.revature.quizzard.models.sets.SetEntity;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.models.user.RoleEntity;
import com.revature.quizzard.models.user.UserEntity;
import com.revature.quizzard.repositories.SetRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.event.annotation.AfterTestExecution;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;


public class SetServiceTest {

    @InjectMocks
    private SetService sut;

    @Mock
    private SetRepository mockSetRepository;

    @Before
    public void setUpTest(){openMocks(this) ;}

    @After
    public void tearDownTest(){
        sut=null;
        mockSetRepository=null;
    }


    @Test
    public void when_findIsPublicPublicResultIsNotEmpty(){

        //Arrange
        UserEntity mockUser = new UserEntity(1,"fN","lN","fn.ln@email.com");

        RoleEntity mockRole = new RoleEntity(1,"ADMIN");

        AccountEntity mockAccount = new AccountEntity();
        mockAccount.setId(1);
        mockAccount.setUser(mockUser);

        CardEntity mockCard = new CardEntity();
        mockCard.setId(1);
        mockCard.setCreator(mockAccount);

        SetEntity mockSet = new SetEntity();
        mockSet.setId(1);
        mockSet.setName("Public Set 1");
        mockSet.setIsPublic(true);
        mockSet.setCreator(mockAccount);
        Set<CardEntity> mockSetOfCards = new HashSet<CardEntity>();
        mockSetOfCards.add(mockCard);
        mockSet.setCards(mockSetOfCards);

        Set<SetEntity> mockPublicSets = new HashSet<SetEntity>();
        mockPublicSets.add(mockSet);
        when(mockSetRepository.findIsPublic(anyBoolean())).thenReturn(mockPublicSets);

        SetDTO mockSetDTO = new SetDTO();
        mockSetDTO.setSetId(mockSet.getId());
        mockSetDTO.setCreatorId(mockSet.getCreator().getId());
        mockSetDTO.setName(mockSet.getName());

        Set<SetDTO> expectedResult = new HashSet<SetDTO>();
        expectedResult.add(mockSetDTO);

        //Act
        Set<SetDTO> actualResult= sut.findIsPublic(true);

        //Assert

        Assert.assertEquals(expectedResult.stream().findFirst().get().getSetId(), actualResult.stream().findFirst().get().getSetId());


    }

    @Test(expected = ResourceNotFoundException.class)
    public void when_findIsPublicResultIsEmpty(){
        //Arrange

        Set<SetEntity> mockEmptySet = new HashSet<>();

        when(mockSetRepository.findIsPublic(anyBoolean())).thenReturn(mockEmptySet);

        //Act
        sut.findIsPublic(true);


    }

}
