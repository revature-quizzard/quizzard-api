package com.revature.quizzard.dtos;

import com.revature.quizzard.models.flashcards.CardEntity;
import com.revature.quizzard.models.sets.SetEntity;
import com.revature.quizzard.models.user.AccountEntity;
import lombok.*;

import javax.smartcardio.Card;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public @Data class SetDTO {

    private int setId;
    private String setName;
    private boolean isPublic;
    private AccountEntity creator;
    //TODO See if you can change this to account id and pull from the account by id in api
    private List<CardDTO> cardDTOList;

    public SetDTO(SetEntity setEntity) {
        this.setId = setEntity.getSetId();
        this.setName = setEntity.getName();
        this.isPublic = setEntity.getIsPublic();
        this.creator = setEntity.getCreator();
        this.cardDTOList = setEntity.getCards().stream().map(CardDTO::new).collect(Collectors.toList());
    }
}
