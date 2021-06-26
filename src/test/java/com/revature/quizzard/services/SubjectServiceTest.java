package com.revature.quizzard.services;

import com.revature.quizzard.models.flashcards.SubjectEntity;
import com.revature.quizzard.repositories.SubjectRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@ActiveProfiles("test")
public class SubjectServiceTest {

    private SubjectService sut;
    private SubjectRepository mockSubjectRepository;
    private SubjectEntity mockSubjectEntity;

    @Before
    public void setupTest(){
        mockSubjectRepository = mock(SubjectRepository.class);
        sut = new SubjectService(mockSubjectRepository);
        mockSubjectEntity = mock(SubjectEntity.class);
        mockSubjectEntity.setId(1);
        mockSubjectEntity.setName("Java");
    }

    @After
    public void tearDown(){
        mockSubjectRepository = null;
        sut = null;
    }

    @Test
    public void test_getAllSubjects(){
        //Expected
        List<SubjectEntity> expected = new ArrayList<>();
        expected.add(mockSubjectEntity);

        List<SubjectEntity> subjectEntities = new ArrayList<>();
        subjectEntities.add(mockSubjectEntity);

        doReturn(expected).when(mockSubjectRepository).findAll();

        //Act
        List<SubjectEntity> result = sut.getAllSubjects();

        //Assert
        verify(mockSubjectRepository, times(1)).findAll();
        assertTrue(expected.equals(result));
    }
}
