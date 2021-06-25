package com.revature.quizzard.dtos;

import com.revature.quizzard.models.composites.*;
import com.revature.quizzard.models.flashcards.*;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CardDTO {

    private int id;
    private String question;
    private String answer;
    private boolean reviewable;
    private boolean isPublic;
    private int subjectId;


    public CardDTO(CardEntity cardEntity){
        this.id = cardEntity.getId();
        this.question = cardEntity.getQuestion();
        this.answer = cardEntity.getAnswer();
        this.reviewable = cardEntity.isReviewable();
        this.isPublic = cardEntity.isPublic();
        this.subjectId = cardEntity.getSubject().getId();
    }
}
