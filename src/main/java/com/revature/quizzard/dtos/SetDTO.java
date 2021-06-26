package com.revature.quizzard.dtos;

import com.revature.quizzard.models.sets.SetEntity;
import com.revature.quizzard.models.user.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public @Data class SetDTO {

    private int setId;
    private String setName;
    private boolean isPublic;
    private AccountEntity creator;

    public SetDTO(SetEntity setEntity) {
        this.setId = setEntity.getId();
        this.setName = setEntity.getName();
        this.isPublic = setEntity.getIsPublic();
        this.creator = setEntity.getCreator();
    }
}
