package com.revature.quizzard.dtos;

import com.revature.quizzard.models.user.AccountEntity;
import lombok.Data;


public @Data
class AccountDTO {
    String username;
    int points;

    public AccountDTO(AccountEntity accountEntity) {
        this.username = accountEntity.getUsername();
        this.points = accountEntity.getPoints();
    }

}
