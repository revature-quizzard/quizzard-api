package com.revature.quizzard.models.flashcards;

import com.revature.quizzard.dtos.CardDTO;
import com.revature.quizzard.models.composites.AccountCardEntity;
import com.revature.quizzard.models.user.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cards")
public @Data class CardEntity {

    @Id
    @Column(name = "card_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.cardEntity")
    private Set<AccountCardEntity> accountCardEntities = new HashSet<>();

    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "answer", columnDefinition = "TEXT", nullable = false)
    private String answer;

    @Column(name = "reviewable")
    private boolean reviewable;

    @Column(name = "public")
    private boolean isPublic;

    @ManyToOne(targetEntity = SubjectEntity.class)
    @JoinColumn(name = "subject_id")
    private SubjectEntity subject;

    public CardEntity(CardDTO card){
        this.question = card.getQuestion();
        this.answer = card.getAnswer();
        this.reviewable = card.isReviewable();
        this.isPublic = card.isPublic();
        this.subject = new SubjectEntity();
        this.subject.setId(card.getSubjectId());
    }

    @ManyToOne(targetEntity = AccountEntity.class)
    @JoinColumn(name = "account_id")
    private AccountEntity creator;


//    @ManyToMany(mappedBy = "cards")
//    private Set<SetEntity> setEntities = new HashSet<>();
}