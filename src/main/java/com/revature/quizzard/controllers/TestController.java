package com.revature.quizzard.controllers;

import com.revature.quizzard.dtos.responsemodel.AccountResponseDTO;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.repositories.AccountRepository;
import com.revature.quizzard.services.CardService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.quizzard.dtos.requestmodels.CardFavoriteDTO;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TestController {

    private final AccountRepository accountRepository;
    private final CardService cardService;

    @GetMapping("/test")
    public void securityHealthStatus(HttpServletRequest request, HttpServletResponse response){
        System.out.println("Test Succeeded");
        response.setStatus(200);
    }

    @GetMapping("/fail")
    public void securityHealthStatusFail(HttpServletRequest request, HttpServletResponse response){
        System.out.println("Test Succeeded");
        // SUPPOSED TO FAIL, this is an authenticated endpoint
        response.setStatus(200);
    }

    @GetMapping("/account")
    public AccountResponseDTO getAccount() {
        AccountEntity account = accountRepository.findById(1).get();
        return new AccountResponseDTO(account);
    }

    @PostMapping("/favorite/card")
    @ResponseStatus(HttpStatus.OK)
    public void toggleFavoriteCard(@RequestBody CardFavoriteDTO dto) {
        cardService.addFavoriteCard(dto);
    }

}
