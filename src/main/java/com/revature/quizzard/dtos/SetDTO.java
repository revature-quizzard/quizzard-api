package com.revature.quizzard.dtos;

import com.fasterxml.jackson.annotation.*;
import com.revature.quizzard.models.sets.SetEntity;
import com.revature.quizzard.models.user.AccountEntity;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SetDTO {

    private int setId;

    @JsonProperty(value = "setName")
    private String setName;
    @JsonProperty(value = "isPublic")
    private boolean isPublic;
    private AccountEntity creator;
    //TODO See if you can change this to account id and pull from the account by id in api
    @JsonProperty(value = "localFlashcards")
    private List<CardDTO> localFlashcards;

    public SetDTO(SetEntity setEntity) {
        this.setId = setEntity.getSetId();
        this.setName = setEntity.getName();
        this.isPublic = setEntity.getIsPublic();
        this.creator = setEntity.getCreator();
        this.localFlashcards = setEntity.getCards().stream().map(CardDTO::new).collect(Collectors.toList());
    }
}
