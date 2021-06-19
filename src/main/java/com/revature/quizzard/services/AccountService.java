package com.revature.quizzard.services;

import com.revature.quizzard.dtos.AccountLoginDTO;
import com.revature.quizzard.dtos.AccountRegisterDTO;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.models.user.UserEntity;
import com.revature.quizzard.repositories.AccountRepository;
import com.revature.quizzard.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;

//@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class AccountService {

    public AccountRepository accountRepository;
    public UserRepository userRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public AccountLoginDTO register(AccountEntity accountEntity, HttpServletResponse response) {
        AccountLoginDTO accountDTO = null;
        try{
            if(accountRepository.existsByUsername(accountEntity.getUsername())) {
                //return with error
                response.setStatus(409);
                response.sendError(409, "Username already taken.");
                return null;
            }
            //continue with logic
            accountRepository.save(accountEntity);
            accountDTO = new AccountLoginDTO(accountEntity);
        } catch (IOException e) {
            //TODO - Log aspect and/or exception handling aspect
            e.printStackTrace();
        }
        return accountDTO;

    }

    public AccountLoginDTO register(AccountRegisterDTO accountRegisterDTO) {
        AccountEntity accountEntity = new AccountEntity();
        UserEntity userEntity = new UserEntity();

        accountEntity.setUsername(accountRegisterDTO.getUsername());
        accountEntity.setPassword(accountRegisterDTO.getPassword());
        userEntity.setEmail(accountRegisterDTO.getEmail());
        userEntity.setFirstName(accountRegisterDTO.getFirstName());
        userEntity.setLastName(accountRegisterDTO.getLastName());
        userEntity.setAccount(accountEntity);
        accountEntity.setUser(userEntity);

        accountRepository.save(accountEntity);
        userRepository.save(userEntity);

        AccountLoginDTO accountLoginDTO = new AccountLoginDTO(accountEntity);
        return accountLoginDTO;
    }

    public AccountEntity login(AccountLoginDTO accountLoginDTO) {

        AccountEntity accountEntity = new AccountEntity();
        accountEntity = accountRepository.findByUsernameAndPassword(accountLoginDTO.getUsername(), accountLoginDTO.getPassword());

        return accountEntity;
    }
}
