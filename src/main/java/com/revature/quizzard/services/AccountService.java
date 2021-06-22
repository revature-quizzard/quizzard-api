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
import com.revature.quizzard.dtos.AccountDTO;
import lombok.AllArgsConstructor;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Transactional
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

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

    /**
     * Method for being able to update the Account username, password and User email.
     * @param id
     * @param accountInfoDTO
     * @return updateAccountInfo
     * @author James Fallon, Juan Mendoza
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> updateAccountInfo(int id, AccountInfoDTO accountInfoDTO){
        if(accountRepository.findById(id).isPresent()){


            AccountEntity account = accountRepository.findById(id).get();

            Map<String, Object> updatedAccountMap = new HashMap<>();

            if(userRepository.findById(account.getUser().getId()).isPresent()) {
                UserEntity user = userRepository.findById(account.getUser().getId()).get();

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


                userRepository.save(user);
                accountRepository.save(account);

                return updatedAccountMap;
            }

            return null;
        }


        return null;
    }

    /**
     * If fields are blank, then they will not be updated
     * @param str
     * @return
     * @James Fallon Juan Mendoza
     */
    private boolean isValid(String str){
        if(str.trim().equals("")){
            return false;
        }

        return true;
    }

}




