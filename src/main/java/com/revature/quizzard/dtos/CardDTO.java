package com.revature.quizzard.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.revature.quizzard.models.flashcards.SubjectEntity;
import com.revature.quizzard.models.user.AccountEntity;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors
@Getter @Setter
public class CardDTO {

    @NotNull
    @JsonProperty("card_id")
    private int cardId;

    @NotNull
    @JsonProperty("question")
    private String question;

    @NotNull
    @JsonProperty("answer")
    private String answer;

    @NotNull
    @JsonProperty("subject")
    private SubjectEntity subject;

    @NotNull
    @JsonProperty("creator_id")
    private int creatorId;

}
