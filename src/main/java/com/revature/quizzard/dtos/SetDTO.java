package com.revature.quizzard.dtos;

import com.revature.quizzard.models.flashcards.CardEntity;
import com.revature.quizzard.models.sets.SetEntity;
import com.revature.quizzard.models.user.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
public @Data class SetDTO {

    private int setId;
    private String setName;
    private boolean isPublic;
    private AccountEntity creator;
    private Set<CardEntity> cards;

    public SetDTO(SetEntity setEntity) {
        this.setId = setEntity.getSetId();
        this.setName = setEntity.getName();
        this.isPublic = setEntity.getIsPublic();
        this.creator = setEntity.getCreator();
        this.cards = setEntity.getCards();
    }
}
