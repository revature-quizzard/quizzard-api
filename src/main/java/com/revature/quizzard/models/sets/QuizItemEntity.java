package com.revature.quizzard.models.sets;

import com.revature.quizzard.models.flashcards.CardEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "quiz_items")
public class QuizItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_item_id", unique = true, nullable = false)
    private int id;

    @ManyToOne(targetEntity = QuizEntity.class)
    @JoinColumn(name = "quiz_id")
    private QuizEntity quiz;

    @OneToOne(targetEntity = CardEntity.class)
    @JoinColumn(name = "card_id")
    private CardEntity card;
}
