package com.revature.quizzard.services;

import com.revature.quizzard.dtos.AccountDTO;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class AccountService {
    
    private AccountRepository accountRepo;
    
    @Autowired
    public AccountService(AccountRepository accountRepo){
        this.accountRepo = accountRepo;
    }
    
    
    public AccountDTO updateAccountInfo(int id){
        AccountEntity account = accountRepo.findById(id);
        
        return null;
    }
}
