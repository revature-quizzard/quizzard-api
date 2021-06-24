//package com.revature.quizzard.services;
//
//import com.revature.quizzard.models.sets.SetEntity;
//import com.revature.quizzard.repositories.SetRepository;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import static org.mockito.Mockito.*;
//
//
//@Service
//@Transactional
//public class SetServiceUnitTestSuite {
//
//    private SetService sut;
//    private SetRepository mockSetRepo;
//
//    @Before
//    public void setUp(){
//        mockSetRepo = mock(SetRepository.class);
//        sut = new SetService(mockSetRepo);
//    }
//
//    @After
//    public void tearDown(){
//        sut = null;
//        mockSetRepo = null;
//    }
//
//    @Test
//    public Set<SetEntity> test_getAllPublicSetsIsEmpty(){
//        Set<SetEntity> publicSets = new HashSet<>();
//        when(mockSetRepo.findIsPublic(true));
//        sut.findIsPublic(true);
//
//    }
//
//}