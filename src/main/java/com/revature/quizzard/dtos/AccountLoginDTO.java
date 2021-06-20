package com.revature.quizzard.dtos;

import com.revature.quizzard.models.user.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public @Data class AccountLoginDTO {
    int id;
    int points;
    String username;

    public AccountLoginDTO(AccountEntity accountEntity) {
        this.id = accountEntity.getId();
        this.username = accountEntity.getUsername();
        this.points = accountEntity.getPoints();
    }

}
