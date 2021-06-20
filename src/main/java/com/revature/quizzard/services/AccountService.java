package com.revature.quizzard.services;

import com.revature.quizzard.dtos.AuthenticatedDTO;
import com.revature.quizzard.dtos.AccountRegisterDTO;
import com.revature.quizzard.dtos.CredentialsDTO;
import com.revature.quizzard.exceptions.DuplicateRegistrationException;
import com.revature.quizzard.exceptions.InvalidCredentialsException;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.models.user.UserEntity;
import com.revature.quizzard.repositories.AccountRepository;
import com.revature.quizzard.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for Accounts
 */
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AccountService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    /**
     * This method is responsible for the registration/creation of a new user and their account, persisted into the database.
     * @param accountRegisterDTO The DTO required to register a user, it has fields from both <code>UserEntity</code> <code>AccountEntity</code>.
     * @return accountLoginDTO The DTO required to login to a successfully registered user's account.
     * @author Sheckeem Daley
     * @author Kyle Plummer
     */
    public AuthenticatedDTO register(AccountRegisterDTO accountRegisterDTO) {
        AccountEntity accountEntity = new AccountEntity();
        UserEntity userEntity = new UserEntity();

        accountEntity.setUsername(accountRegisterDTO.getUsername());
        accountEntity.setPassword(accountRegisterDTO.getPassword());
        userEntity.setEmail(accountRegisterDTO.getEmail());
        userEntity.setFirstName(accountRegisterDTO.getFirstName());
        userEntity.setLastName(accountRegisterDTO.getLastName());
        userEntity.setAccount(accountEntity);
        accountEntity.setUser(userEntity);

        try {
            accountRepository.save(accountEntity);
            userRepository.save(userEntity);
        } catch (Exception e) {
            throw new DuplicateRegistrationException("The username and/or email has already been taken.");
        }


        AuthenticatedDTO authenticatedDTO = new AuthenticatedDTO(accountEntity);
        return authenticatedDTO;
    }

    /**
     * This method is responsible for login into an account that exists in the database
     * @param credentialsDTO The DTO required for authentication.
     * @return accountLoginDTO The DTO required to login to an account.
     * @author Sheckeem Daley
     * @author Kyle Plummer
     */
    public AuthenticatedDTO login(CredentialsDTO credentialsDTO) throws InvalidCredentialsException {

        AccountEntity accountEntity = new AccountEntity();
        AuthenticatedDTO authenticatedDTO = new AuthenticatedDTO();
        accountEntity = accountRepository.findByUsernameAndPassword(credentialsDTO.getUsername(), credentialsDTO.getPassword());

        try {
            authenticatedDTO.setId(accountEntity.getId());
            authenticatedDTO.setUsername(accountEntity.getUsername());
            authenticatedDTO.setPoints(accountEntity.getPoints());
        } catch (NullPointerException e) {
            throw new InvalidCredentialsException("Invalid username and/or password!");
        }

        return authenticatedDTO;
    }
}




