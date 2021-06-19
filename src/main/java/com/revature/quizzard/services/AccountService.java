package com.revature.quizzard.services;

import com.revature.quizzard.dtos.AccountDTO;
import com.revature.quizzard.dtos.AccountInfoDTO;
import com.revature.quizzard.dtos.UpdatedAccountDTO;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.models.user.UserEntity;
import com.revature.quizzard.repositories.AccountRepository;
import com.revature.quizzard.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AccountService {
    
    private AccountRepository accountRepo;
    private UserRepository userRepo;

    @Autowired
    public AccountService(AccountRepository accountRepo, UserRepository userRepo){
        this.accountRepo = accountRepo;
        this.userRepo = userRepo;
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    public UpdatedAccountDTO updateAccountInfo(int id, AccountInfoDTO accountInfoDTO){
        if(accountRepo.findById(id).isPresent()){
            AccountEntity account = accountRepo.findById(id).get();
            if(userRepo.findById(account.getUser().getId()).isPresent()) {
                UserEntity user = userRepo.findById(account.getUser().getId()).get();

                account.setPassword(accountInfoDTO.getPassword());
                account.setUsername(accountInfoDTO.getUsername());
                user.setEmail(accountInfoDTO.getEmail());

                user = userRepo.save(user);
                account = accountRepo.save(account);

                return new UpdatedAccountDTO(user.getEmail(), account.getUsername());
            }
        }
        
        
        return null;
    }
}
