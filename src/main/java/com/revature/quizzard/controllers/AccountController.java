package com.revature.quizzard.controllers;

import com.revature.quizzard.dtos.AccountLoginDTO;
import com.revature.quizzard.dtos.AccountRegisterDTO;
import com.revature.quizzard.dtos.CredentialsDTO;
import com.revature.quizzard.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AccountController {

    private AccountService accountService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody AccountRegisterDTO accountRegisterDTO) {
        System.out.println(accountRegisterDTO.toString());

        AccountLoginDTO accountLoginDTO = accountService.register(accountRegisterDTO);

        CredentialsDTO credentialsDTO = new CredentialsDTO();
        credentialsDTO.setUsername(accountRegisterDTO.getUsername());
        credentialsDTO.setPassword(accountRegisterDTO.getPassword());
        login(credentialsDTO); // Call login after registration assuming success

        //TODO: add try-catch in case of invalid registration.
        //TODO: eventually re-factor to leverage Exception aspect.
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public AccountLoginDTO login(@RequestBody CredentialsDTO credentialsDTO) {
        System.out.println(credentialsDTO.toString());

        AccountLoginDTO accountLoginDTO = accountService.login(credentialsDTO);

        return accountLoginDTO;
        //TODO: add try-catch in case of invalid login.
        //TODO: eventually re-factor to leverage Exception aspect.
    }
}
