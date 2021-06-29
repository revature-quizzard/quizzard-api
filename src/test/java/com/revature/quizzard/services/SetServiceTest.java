package com.revature.quizzard.services;

import com.revature.quizzard.dtos.*;
import com.revature.quizzard.exceptions.ResourceNotFoundException;
import com.revature.quizzard.models.flashcards.CardEntity;
import com.revature.quizzard.models.flashcards.SubjectEntity;
import com.revature.quizzard.models.sets.SetEntity;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.repositories.*;
import com.revature.quizzard.security.JWTokenUtil;
import org.junit.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@ActiveProfiles("test")
public class SetServiceTest {

    private SetService sut;
    private SetRepository mockSetRepo;
    private AccountRepository mockAccountRepo;
    private SetEntity mockSetEntity;
    private List<SetEntity> mockSetList;
    private AccountEntity mockAccount;
    private CardEntity mockCard;
    private Set<CardEntity> mockCards;
    private JWTokenUtil mockTokenUtil;
    private CardRepository mockCardRepo;
    private SubjectRepository mockSubjectRepo;
    private SubjectEntity mockSubjectEntity;

    @Before
    public void setupTest() {
        mockSetRepo = mock(SetRepository.class);
        mockAccountRepo = mock(AccountRepository.class);
        mockTokenUtil = mock(JWTokenUtil.class);
        mockSubjectRepo = mock(SubjectRepository.class);
        sut = new SetService(mockSetRepo, mockAccountRepo, mockSubjectRepo, mockCardRepo, mockTokenUtil);
    }

    @After
    public void teardownTest() {
        mockSetRepo = null;
        mockAccountRepo = null;
        mockSubjectRepo = null;
        sut = null;
        mockSetEntity = null;
        mockAccount = null;
        mockSetList = null;
        mockCard = null;
        mockCards = null;
        mockSetEntity = null;
    }

    @Test
    public void test_getCreatedSetsWithValidUsernameAndSet() {

        mockAccount = new AccountEntity();
        mockSetList = new ArrayList<>();
        mockCards = new HashSet<>();
        mockSetEntity = new SetEntity(1, mockCards, mockAccount, "test", true);
        mockSetList.add(mockSetEntity);
        when(mockAccountRepo.findByUsername(any())).thenReturn(mockAccount);
        when(mockSetRepo.findAllCreatedByAccount(any())).thenReturn(mockSetList);

        List<SetDTO> result = sut.getCreatedSets("test");

        assertEquals(1, result.size());
    }

    @Test
    public void test_getCreatedSetsWithValidUsernameAndNoSet() {

        mockAccount = new AccountEntity();
        mockSetList = new ArrayList<>();
        when(mockAccountRepo.findByUsername(any())).thenReturn(mockAccount);
        when(mockSetRepo.findAllCreatedByAccount(any())).thenReturn(mockSetList);

        List<SetDTO> result = sut.getCreatedSets("test");

        assertEquals(0, result.size());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void test_getCreatedSetsWithInvalidUsername() {

        when(mockAccountRepo.findByUsername(any())).thenReturn(null);

        List<SetDTO> result = sut.getCreatedSets("test");

    }

    @Test
    public void test_save(){
        CredentialsDTO credentialsDTO = new CredentialsDTO("mocker", "mockpass");
        mockSubjectEntity = new SubjectEntity(100, "mock");
        mockSetEntity = new SetEntity();
        mockSetEntity.setId(100);

        SetCardDTO setCardDTO = new SetCardDTO(100, "What is mock", "mock", true, true, mockSubjectEntity, 100, credentialsDTO);

        when(mockSubjectRepo.findById(anyInt())).thenReturn(java.util.Optional.of(mockSubjectEntity));
        when(mockAccountRepo.findByUsername(anyString())).thenReturn(mockAccount);

        mockCard = new CardEntity(setCardDTO.getId(), null, setCardDTO.getQuestion(), setCardDTO.getAnswer(),
                setCardDTO.isReviewable(), setCardDTO.isPublic(), mockSubjectEntity, mockAccount);

        sut.save(setCardDTO);
    }

//    @Test
//    public void test_createStudySet(){
//        SetDTO setDTO = new SetDTO();
//        setDTO.setSetName("mockName");
////        setDTO.setCreator(mockAccount);
//        setDTO.setPublic(true);
//        setDTO.setSetId(2);
//
//        //Expected
//        doReturn(new SetEntity()).when(mockSetRepo).save(any());
//        //Act
//        SetDTO result = sut.createStudySets(setDTO);
//
//        //Assert
//        verify(mockSetRepo, times(1)).save(any());
//
//        assertEquals(setDTO, result);
//
//    }
}
