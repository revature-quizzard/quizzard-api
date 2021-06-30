package com.revature.quizzard.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public @Data class AccountRegisterDTO {
    String username;
    String password;
    String email;
    String firstName;
    String lastName;
}
