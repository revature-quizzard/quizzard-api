package com.revature.quizzard.models.flashcards;

import com.revature.quizzard.models.composites.AccountCardEntity;
import com.revature.quizzard.models.sets.SetEntity;
import com.revature.quizzard.web.dtos.CardDTO;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Table(name = "cards")
public @Data class CardEntity {

    @Id
    @Column(name = "card_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.cardEntity", cascade = CascadeType.ALL)
    private Set<AccountCardEntity> accountCardEntities = new HashSet<>();

    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "answer", nullable = false)
    private String answer;

    @Column(name = "reviewable")
    private boolean reviewable;

    @Column(name = "public")
    private boolean isPublic;

    @ManyToOne(targetEntity = SubjectEntity.class)
    @JoinColumn(name = "subject_id")
    private SubjectEntity subject;

    @ManyToMany(mappedBy = "cards")
    private Set<SetEntity> setEntities = new HashSet<>();

    public CardEntity(CardDTO card){
        this.question = card.getQuestion();
        this.answer = card.getAnswer();
        this.reviewable = card.isReviewable();
        this.isPublic = card.isPublic();
        this.subject = new SubjectEntity();
        this.subject.setId(card.getSubjectId());
    }
}