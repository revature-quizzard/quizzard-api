package com.revature.quizzard.controllers;

import com.revature.quizzard.dtos.AccountInfoDTO;
import com.revature.quizzard.dtos.UpdatedAccountDTO;
import com.revature.quizzard.services.AccountService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/accounts")
public class AccountsController {
    private AccountService accountService;

    @Autowired
    public AccountsController(AccountService accountService){
        this.accountService = accountService;
    }

    @PutMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE, value = "/update/{accountId}")
    public UpdatedAccountDTO updateAccount(@PathVariable(value = "accountId") int accountId,
                                           @RequestBody AccountInfoDTO accountInfoDTO){
        UpdatedAccountDTO updatedAccountDTO = accountService.updateAccountInfo(accountId, accountInfoDTO);
        return updatedAccountDTO;
    }

}
