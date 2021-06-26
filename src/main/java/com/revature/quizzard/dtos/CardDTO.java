package com.revature.quizzard.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CardDTO {

    private int id;
    private String question;
    private String answer;
    private boolean reviewable;
    private boolean isPublic;
    private int  subjectId;
}
