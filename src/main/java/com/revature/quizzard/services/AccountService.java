package com.revature.quizzard.services;

import com.revature.quizzard.dtos.AccountLoginDTO;
import com.revature.quizzard.dtos.AccountRegisterDTO;
import com.revature.quizzard.dtos.CredentialsDTO;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.models.user.UserEntity;
import com.revature.quizzard.repositories.AccountRepository;
import com.revature.quizzard.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.quizzard.dtos.requestmodels.CardFavoriteDTO;
import com.revature.quizzard.exceptions.InvalidRequestException;
import com.revature.quizzard.models.composites.AccountCardEntity;
import com.revature.quizzard.models.flashcards.CardEntity;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.repositories.AccountCardRepository;
import com.revature.quizzard.repositories.AccountRepository;
import com.revature.quizzard.repositories.CardRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;

/**
 * Service for Accounts
 */
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AccountService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public AccountLoginDTO register(AccountEntity accountEntity, HttpServletResponse response) {
        AccountLoginDTO accountLoginDTO = null;
        try{
            if(accountRepository.existsByUsername(accountEntity.getUsername())) {
                //return with error
                response.setStatus(409);
                response.sendError(409, "Username already taken.");
                return null;
            }
            //continue with logic
            accountRepository.save(accountEntity);
            accountLoginDTO = new AccountLoginDTO(accountEntity);
        } catch (IOException e) {
            //TODO - Log aspect and/or exception handling aspect
            e.printStackTrace();
        }
        return accountLoginDTO;

    }

    /**
     * This method is responsible for the registration/creation of a new user and their account, persisted into the database.
     * @param accountRegisterDTO The DTO required to register a user, it has fields from both <code>UserEntity</code> <code>AccountEntity</code>.
     * @return accountLoginDTO The DTO required to login to a successfully registered user's account.
     * @author Sheckeem Daley
     * @author Kyle Plummer
     */
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

    /**
     * This method is responsible for login into an account that exists in the database
     * @param credentialsDTO The DTO required for authentication.
     * @return accountLoginDTO The DTO required to login to an account.
     * @author Sheckeem Daley
     * @author Kyle Plummer
     */
    public AccountLoginDTO login(CredentialsDTO credentialsDTO) {

        AccountEntity accountEntity = new AccountEntity();
        AccountLoginDTO accountLoginDTO = new AccountLoginDTO();
        accountEntity = accountRepository.findByUsernameAndPassword(credentialsDTO.getUsername(), credentialsDTO.getPassword());

        accountLoginDTO.setId(accountEntity.getId());
        accountLoginDTO.setUsername(accountEntity.getUsername());
        accountLoginDTO.setPoints(accountEntity.getPoints());

        return accountLoginDTO;
    }
}




