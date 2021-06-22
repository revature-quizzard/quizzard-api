package com.revature.quizzard.models.flashcards;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subjects")
public @Data class SubjectEntity {

    @Id
    @Column(name = "subject_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

//    @ManyToOne(targetEntity = CardEntity.class)
//    @JoinColumn(name = "card_id")
//   // private CardEntity card;
//    private Set<CardEntity> cards = new HashSet<>();

}
