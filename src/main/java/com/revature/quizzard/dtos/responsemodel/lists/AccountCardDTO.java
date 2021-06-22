package com.revature.quizzard.dtos.responsemodel.lists;

import com.revature.quizzard.models.composites.AccountCardEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public @Data class AccountCardDTO {

    public AccountCardDTO(AccountCardEntity accountCardEntity) {
        this.cardId = accountCardEntity.getCardEntity().getId();
        this.favorite = accountCardEntity.isFavorite();
        this.confident = accountCardEntity.isConfident();
    }

    private Integer cardId;

    private boolean favorite;

    private boolean confident;

}
