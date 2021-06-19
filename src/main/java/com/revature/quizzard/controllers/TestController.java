package com.revature.quizzard.controllers;

import com.revature.quizzard.dtos.requestmodels.CardFavoriteDTO;
import com.revature.quizzard.exceptions.InvalidRequestException;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.repositories.AccountRepository;
import com.revature.quizzard.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TestController {

    private AccountRepository accountRepository;
    private AccountService accountService;

    @GetMapping("/test")
    public void securityHealthStatus(HttpServletRequest request, HttpServletResponse response){
        System.out.println("Test Succeeded");
        response.setStatus(200);
    }

    @PostMapping("/favorite/card")
    @ResponseStatus(HttpStatus.OK)
    public void addFavoriteCard(@RequestBody CardFavoriteDTO dto) {
        accountService.addFavoriteCard(dto);
    }



}
