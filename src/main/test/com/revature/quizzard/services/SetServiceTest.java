package com.revature.quizzard.services;

import com.revature.quizzard.models.sets.SetEntity;
import com.revature.quizzard.repositories.SetRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

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
        Set<SetEntity> mockPublicSets = mockSetRepository.findIsPublic(true);


    }

}
