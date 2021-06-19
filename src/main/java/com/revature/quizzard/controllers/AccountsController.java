package com.revature.quizzard.controllers;

import com.revature.quizzard.dtos.AccountInfoDTO;
import com.revature.quizzard.dtos.UpdatedAccountDTO;
import com.revature.quizzard.services.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/accounts")
public class AccountsController {
    private AccountService accountService;

    @Autowired
    public AccountsController(AccountService accountService){
        this.accountService = accountService;
    }

    @PutMapping(value = "/update", consumes = APPLICATION_JSON_VALUE)
    public Map<String, String> updateAccount(@RequestBody AccountInfoDTO accountInfoDTO){
        System.out.println(accountInfoDTO);
        Map<String, String> updatedAccountMap = accountService.updateAccountInfo(1, accountInfoDTO);
        return updatedAccountMap;
    }

}
