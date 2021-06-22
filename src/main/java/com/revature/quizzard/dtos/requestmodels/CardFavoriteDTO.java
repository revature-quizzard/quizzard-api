package com.revature.quizzard.dtos.requestmodels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public @Data class CardFavoriteDTO {
    private int accountId;
    private int cardId;
    private boolean favorite;
}
