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
    public UpdatedAccountDTO updateAccountInfo(int id, AccountInfoDTO accountInfoDTO){
        if(accountRepo.findById(id).isPresent()){


            AccountEntity account = accountRepo.findById(id).get();
            UpdatedAccountDTO updatedAccountDTO = new UpdatedAccountDTO();
            updatedAccountDTO.setUsername(account.getUsername());
            updatedAccountDTO.setId(account.getId());
            updatedAccountDTO.setPoints(account.getPoints());
            updatedAccountDTO.setRoles(account.getRoles());
            if(userRepo.findById(account.getUser().getId()).isPresent()) {
                UserEntity user = userRepo.findById(account.getUser().getId()).get();

                if(isValid(accountInfoDTO.getPassword())){
                    account.setPassword(accountInfoDTO.getPassword());
                    updatedAccountDTO.setUpdatedPassword(true);
                }

                if(isValid(accountInfoDTO.getEmail())){
                    user.setEmail(accountInfoDTO.getEmail());
                    updatedAccountDTO.setUpdatedEmail(true);
                }

                if(isValid(accountInfoDTO.getUsername())){
                    updatedAccountDTO.setUsername(accountInfoDTO.getUsername());
                    account.setUsername(accountInfoDTO.getUsername());
                    updatedAccountDTO.setUpdatedUsername(true);
                }


                userRepo.save(user);
                accountRepo.save(account);

                return updatedAccountDTO;
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
