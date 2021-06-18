package com.revature.quizzard.models.sets;

import com.revature.quizzard.models.sets.SetEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "quizzes")
public @Data class QuizEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id", unique = true, nullable = false)
    private int id;

    @ManyToOne(targetEntity = SetEntity.class)
    @JoinColumn(name = "set_id")
    private SetEntity set;

}
