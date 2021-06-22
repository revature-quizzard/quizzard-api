package com.revature.quizzard.services;

import com.revature.quizzard.dtos.AccountInfoDTO;
import com.revature.quizzard.dtos.AuthenticatedDTO;
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
    public Map<String, Object> updateAccountInfo(int id, AccountInfoDTO accountInfoDTO){
        if(accountRepo.findById(id).isPresent()){


            AccountEntity account = accountRepo.findById(id).get();

            Map<String, Object> updatedAccountMap = new HashMap<>();

            if(userRepo.findById(account.getUser().getId()).isPresent()) {
                UserEntity user = userRepo.findById(account.getUser().getId()).get();

                if(isValid(accountInfoDTO.getPassword())){
                    account.setPassword(accountInfoDTO.getPassword());
                    updatedAccountMap.put("password", true);
                }

                if(isValid(accountInfoDTO.getEmail())){
                    user.setEmail(accountInfoDTO.getEmail());
                    updatedAccountMap.put("email", user.getEmail());
                }

                if(isValid(accountInfoDTO.getUsername())){
                    account.setUsername(accountInfoDTO.getUsername());
                    updatedAccountMap.put("username", account.getUsername());
                }


                userRepo.save(user);
                accountRepo.save(account);

                return updatedAccountMap;
            }
        }
        
        
        return null;
    }
    
    private boolean isValid(String str){
        if(str.trim().equals("")){
            return false;
        }
        if(str == null){
            return false;
        }
        return true;
    }
}
