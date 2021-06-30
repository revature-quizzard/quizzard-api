package com.revature.quizzard.dtos.responsemodel;

import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.models.user.RoleEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
public @Data class AccountResponseDTO {

    private Set<RoleEntity> roles;

//    private Set<AccountCardDTO> cards;

    private String username;

    private int points;

    public AccountResponseDTO(AccountEntity accountEntity) {
        this.roles = accountEntity.getRoles();
        this.username = accountEntity.getUsername();
//        this.cards = accountEntity.getAccountCards();
        this.points = accountEntity.getPoints();
    }
}
