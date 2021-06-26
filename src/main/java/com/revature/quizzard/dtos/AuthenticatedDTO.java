package com.revature.quizzard.dtos;

import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.models.user.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
public @Data class AuthenticatedDTO {
    private int id;
    private int points;
    private String username;
    private Set<RoleEntity> roles;

    public AuthenticatedDTO(AccountEntity accountEntity) {
        this.id = accountEntity.getId();
        this.username = accountEntity.getUsername();
        this.points = accountEntity.getPoints();
        this.roles = accountEntity.getRoles();
    }

}
