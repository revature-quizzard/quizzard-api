package com.revature.quizzard.services;

import com.revature.quizzard.dtos.*;
import com.revature.quizzard.exceptions.ResourceNotFoundException;
import com.revature.quizzard.models.flashcards.CardEntity;
import com.revature.quizzard.models.flashcards.SubjectEntity;
import com.revature.quizzard.models.sets.SetEntity;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.models.user.RoleEntity;
import com.revature.quizzard.models.user.UserEntity;
import com.revature.quizzard.repositories.*;
import com.revature.quizzard.security.JWTokenUtil;
import org.junit.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

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
    private CardService mockCardService;

    @Before
    public void setupTest() {
        mockSetRepo = mock(SetRepository.class);
        mockAccountRepo = mock(AccountRepository.class);
        mockTokenUtil = mock(JWTokenUtil.class);
        mockCardRepo = mock(CardRepository.class);
        mockSubjectRepo = mock(SubjectRepository.class);
        mockCardService = mock(CardService.class);
        sut = new SetService(mockSetRepo, mockAccountRepo, mockSubjectRepo, mockCardRepo, mockTokenUtil, mockCardService);

        mockAccount = new AccountEntity();


    }

    @After
    public void teardownTest() {
        mockSetRepo = null;
        mockAccountRepo = null;

        mockSubjectRepo = null;

        mockCardRepo = null;
        mockCardService = null;

        sut = null;
        mockSetEntity = null;
        mockAccount = null;
        mockSetList = null;
        mockCard = null;
        mockCards = null;
        mockSetEntity = null;
    }

    @Test
    public void test_getPublicSetsResultIsNotEmpty(){

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

        List<SetEntity> mockPublicSets = new ArrayList<SetEntity>();
        mockPublicSets.add(mockSet);
        when(mockSetRepo.findAllByIsPublic(anyBoolean())).thenReturn(mockPublicSets);

        List<SetEntity> expectedResult = new ArrayList<>();
        expectedResult.add(mockSet);

        //Act
        List<SetEntity> actualResult= sut.getPublicSets();

        //Assert
        Assert.assertEquals(expectedResult.stream().findFirst().get().getId(), actualResult.stream().findFirst().get().getId());


    }

    @Test
    public void test_getSetById() {
        //Arrange
        UserEntity mockUser = new UserEntity(1, "fN", "lN", "fn.ln@email.com");

        RoleEntity mockRole = new RoleEntity(1, "ADMIN");

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

        when(mockSetRepo.findById(anyInt())).thenReturn(java.util.Optional.of(mockSet));

        SetEntity expectedResult = new SetEntity();
        expectedResult.setId(mockSet.getId());
        //Act

        Optional<SetEntity> actualResult = sut.getSetById(1);

        //Assert
        Assert.assertEquals(expectedResult.getId(), actualResult.get().getId());
    }

    @Test
    public void test_save(){
        CredentialsDTO credentialsDTO = new CredentialsDTO("mocker", "mockpass");

        mockSetEntity = new SetEntity();
        mockSetEntity.setId(100);
        mockAccount = new AccountEntity(100, null, null, null, "mocker", "mockpass", 100);
        mockSubjectEntity = new SubjectEntity(100, "mock");


        SetCardDTO setCardDTO = new SetCardDTO(100, "What is mock", "mock", true, true, mockSubjectEntity, 100, credentialsDTO);
        mockCard = new CardEntity(setCardDTO.getId(), null, setCardDTO.getQuestion(), setCardDTO.getAnswer(),
                setCardDTO.isReviewable(), setCardDTO.isPublic(), mockSubjectEntity, mockAccount);
        when(sut.getSetById(anyInt())).thenReturn(Optional.ofNullable(mockSetEntity));
        when(mockSubjectRepo.findById(anyInt())).thenReturn(java.util.Optional.of(mockSubjectEntity));
        when(mockAccountRepo.findByUsername(anyString())).thenReturn(mockAccount);
        when(mockCardService.savePublicCard(any())).thenReturn(mockCard);
        CardEntity cardEntity = sut.save(setCardDTO);
        assertEquals(mockCard.getAnswer(), cardEntity.getAnswer());
        assertEquals(mockCard.getQuestion(), cardEntity.getQuestion());

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

    @Test
    public void test_getOwnedSets() {

        mockAccount = new AccountEntity();
        mockSetList = new ArrayList<>();
        mockSetList.add(new SetEntity());

        when(mockTokenUtil.getIdFromToken(anyString())).thenReturn(1);
        when(mockAccountRepo.findById(1)).thenReturn(java.util.Optional.ofNullable(mockAccount));
        when(mockSetRepo.findAllByCreator(mockAccount)).thenReturn(mockSetList);

        List<SetEntity> result = sut.getOwnedSets(anyString());

        assertEquals(mockSetList.size(), result.size());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void test_getOwnedSets_NoAccount() {

        when(mockTokenUtil.getIdFromToken(anyString())).thenReturn(1);
        when(mockAccountRepo.findById(anyInt())).thenReturn(Optional.empty());

        sut.getOwnedSets(anyString());
    }

    @Test
    public void test_createStudySets(){
        //Arrange user
        int creatorId = 100;
        mockAccount.setId(100);
        mockAccount.setUsername("testuser");
        mockAccount.setPassword("testpassword");

        //Setup cards
        List<CardDTO> cards = new ArrayList<>();
        CardDTO card = new CardDTO();
        card.setId(100);
        card.setPublic(true);
        card.setAnswer("answer");
        card.setQuestion("question");
        card.setSubjectId(1);
        card.setReviewable(true);
        cards.add(card);

        CardEntity cardEntity = new CardEntity(card);

        //Setup set
        SetDTO newSet = new SetDTO();
        newSet.setSetId(100);
        newSet.setSetName("TestSet");
        newSet.setPublic(true);
        newSet.setCreator(mockAccount);
        newSet.setLocalFlashcards(cards);

        SetEntity setEntity = new SetEntity(newSet);
        setEntity.setId(100);

        when(mockCardRepo.findCardEntityById(anyInt())).thenReturn(cardEntity);
        when(mockAccountRepo.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(mockAccount));
        when(mockSetRepo.save(any())).thenReturn(setEntity);

        //Act
        SetDTO result = sut.createStudySets(newSet, creatorId);

        //Assert
        assertEquals(newSet.getSetId(), result.getSetId());




    }


}
