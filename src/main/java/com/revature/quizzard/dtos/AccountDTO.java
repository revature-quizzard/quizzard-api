package com.revature.quizzard.dtos;



import com.revature.quizzard.models.user.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public @Data class AccountDTO {
    String username;
    int points;

    public AccountDTO(AccountEntity accountEntity) {
        this.username = accountEntity.getUsername();
        this.points = accountEntity.getPoints();
    }


}
