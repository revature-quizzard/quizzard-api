package com.revature.quizzard.web.dtos;

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


}
