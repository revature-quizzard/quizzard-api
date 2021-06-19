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

import java.util.HashMap;
import java.util.Map;
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
    
    
    /**
     * Method for being able to update the Account username, password and User email.
     * @param id
     * @param accountInfoDTO
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, String> updateAccountInfo(int id, AccountInfoDTO accountInfoDTO){
        if(accountRepo.findById(id).isPresent()){
            Map<String, String> updatedAccountInfo = new HashMap<>();
            AccountEntity account = accountRepo.findById(id).get();
            if(userRepo.findById(account.getUser().getId()).isPresent()) {
                UserEntity user = userRepo.findById(account.getUser().getId()).get();

                System.out.println(accountInfoDTO.getEmail());
                if(isValid(accountInfoDTO.getPassword())){
                    account.setPassword(accountInfoDTO.getPassword());
                    updatedAccountInfo.put("Password", "Updated");
                }

                if(isValid(accountInfoDTO.getEmail())){
                    user.setEmail(accountInfoDTO.getEmail());
                    updatedAccountInfo.put("Email", "Updated to " + user.getEmail());
                }

                if(isValid(accountInfoDTO.getUsername())){
                    account.setUsername(accountInfoDTO.getUsername());
                    updatedAccountInfo.put("Username", "Updated to " + account.getUsername());
                }


                userRepo.save(user);
                accountRepo.save(account);

                return updatedAccountInfo;
            }
        }
        
        
        return null;
    }
    
    private boolean isValid(String str){
        if(str.trim().equals("")){
            return false;
        }
        return true;
    }
}
