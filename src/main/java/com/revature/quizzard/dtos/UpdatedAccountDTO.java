package com.revature.quizzard.dtos;

import javax.validation.constraints.Email;

public class UpdatedAccountDTO {

    @Email
    private String email;

    private String username;

    public UpdatedAccountDTO(){
        super();
    }

    public UpdatedAccountDTO(String email, String username) {
        this.email = email;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
