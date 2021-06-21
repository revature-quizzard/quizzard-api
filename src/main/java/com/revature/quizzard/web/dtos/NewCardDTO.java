package com.revature.quizzard.web.dtos;

import lombok.*;

@Getter
@Setter @NoArgsConstructor @AllArgsConstructor
public class NewCardDTO {
        private int id;
        private String question;
        private String answer;
        private boolean reviewable;
        private boolean isPublic;
        private String subjectName;
    }