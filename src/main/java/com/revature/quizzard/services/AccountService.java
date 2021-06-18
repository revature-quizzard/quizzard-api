package com.revature.quizzard.services;

import com.revature.quizzard.dtos.AccountDTO;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;

//@Component
@Service
public class AccountService {

    public AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public AccountDTO register(AccountEntity accountEntity, HttpServletResponse response) {
        AccountDTO accountDTO = null;
        try{
            if(accountRepository.existsByUsername(accountEntity.getUsername())) {
                //return with error
                response.setStatus(409);
                response.sendError(409, "Username already taken.");
                return null;
            }
            //continue with logic
            accountRepository.save(accountEntity);
            accountDTO = new AccountDTO(accountEntity);
        } catch (IOException e) {
            //TODO - Log aspect and/or exception handling aspect
            e.printStackTrace();
        }
        return accountDTO;

    }

}
