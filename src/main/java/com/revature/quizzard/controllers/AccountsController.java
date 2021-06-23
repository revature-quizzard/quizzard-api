package com.revature.quizzard.controllers;

import com.revature.quizzard.dtos.AccountInfoDTO;
import com.revature.quizzard.dtos.AuthenticatedDTO;
import com.revature.quizzard.dtos.UpdatedAccountDTO;
import com.revature.quizzard.exceptions.DuplicateRegistrationException;
import com.revature.quizzard.security.JWTokenUtil;
import com.revature.quizzard.services.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/accounts")
public class AccountsController {
    private AccountService accountService;
    private JWTokenUtil jwTokenUtil;

    @Autowired
    public AccountsController(AccountService accountService, JWTokenUtil jwTokenUtil){
        this.accountService = accountService;
        this.jwTokenUtil = jwTokenUtil;
    }

    @PutMapping(value = "/update", consumes = APPLICATION_JSON_VALUE)
    public Map updateAccount(@RequestBody AccountInfoDTO accountInfoDTO, HttpServletRequest req){

        int accountID = jwTokenUtil.getIdFromToken(req.getHeader("Authorization"));
        Map updatedAccountMap = accountService.updateAccountInfo(accountID, accountInfoDTO);



        return updatedAccountMap;
    }

}
