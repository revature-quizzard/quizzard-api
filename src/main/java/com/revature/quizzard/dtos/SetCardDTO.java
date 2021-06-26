package com.revature.quizzard.dtos;

import com.revature.quizzard.models.flashcards.SubjectEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SetCardDTO {

    private int id;
    private String question;
    private String answer;
    private boolean reviewable;
    private boolean isPublic;
    private SubjectEntity subject;
    private int studySetId;
    private CredentialsDTO creator;
}
