package com.revature.quizzard.dtos.requestmodels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public @Data class CardConfidentDTO {
    private int accountId;
    private int cardId;
    private boolean confident;
}
