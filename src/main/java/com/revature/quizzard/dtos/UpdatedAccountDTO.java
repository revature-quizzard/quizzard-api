package com.revature.quizzard.dtos;

import com.revature.quizzard.models.user.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public @Data class UpdatedAccountDTO extends AuthenticatedDTO {

    private boolean updatedPassword;
    private boolean updatedUsername;
    private boolean updatedEmail;

    public UpdatedAccountDTO(AccountEntity accountEntity){
        super(accountEntity);
    }

}