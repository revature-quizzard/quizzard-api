package com.revature.quizzard.dtos;

import com.revature.quizzard.models.user.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public @Data class AccountLoginDTO {
    String username;
    String password;

    public AccountLoginDTO(AccountEntity accountEntity) {
        this.username = accountEntity.getUsername();
        this.password = accountEntity.getPassword();
    }

}
