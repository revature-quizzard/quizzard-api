package com.revature.quizzard.services;



import com.revature.quizzard.dtos.AccountInfoDTO;
import com.revature.quizzard.dtos.AuthenticatedDTO;
import com.revature.quizzard.dtos.AccountRegisterDTO;
import com.revature.quizzard.dtos.CredentialsDTO;
import com.revature.quizzard.dtos.requestmodels.AddPointsDTO;
import com.revature.quizzard.dtos.responsemodel.AccountResponseDTO;
import com.revature.quizzard.exceptions.DuplicateRegistrationException;
import com.revature.quizzard.exceptions.InvalidCredentialsException;
import com.revature.quizzard.exceptions.InvalidRoleException;
import com.revature.quizzard.exceptions.ResourceNotFoundException;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.models.user.RoleEntity;
import com.revature.quizzard.models.user.UserEntity;
import com.revature.quizzard.repositories.AccountRepository;
import com.revature.quizzard.repositories.RoleRepository;
import com.revature.quizzard.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;


/**
 * Service for Accounts
 */
@Transactional
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AccountService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    /**
     * This method is responsible for the registration/creation of a new user and their account, persisted into the database.
     * @param accountRegisterDTO The DTO required to register a user, it has fields from both <code>UserEntity</code> <code>AccountEntity</code>.
     * @return accountLoginDTO The DTO required to login to a successfully registered user's account.
     * @author Sheckeem Daley
     * @author Kyle Plummer
     * @throws InvalidRoleException when the role does not exist in the database
     */
    public AuthenticatedDTO register(AccountRegisterDTO accountRegisterDTO) throws InvalidRoleException {
        AccountEntity accountEntity = new AccountEntity();
        UserEntity userEntity = new UserEntity();
        Optional<RoleEntity> optionalRoleEntity = roleRepository.findById(1);
        if(!optionalRoleEntity.isPresent()) {
            throw new InvalidRoleException("This role does not exist!");
        }

        accountEntity.setUsername(accountRegisterDTO.getUsername());
        accountEntity.setPassword(accountRegisterDTO.getPassword());
        HashSet<RoleEntity> roleSet = new HashSet<>();
        roleSet.add(optionalRoleEntity.get());
        accountEntity.setRoles(roleSet);
        accountEntity.setUser(userEntity);

        userEntity.setEmail(accountRegisterDTO.getEmail());
        userEntity.setFirstName(accountRegisterDTO.getFirstName());
        userEntity.setLastName(accountRegisterDTO.getLastName());


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
     * This method is responsible for being able to update the Account username, password and User email to be persisted into the database.
     * @param id
     * @param accountInfoDTO
     * @return updateAccountInfo
     * @author James Fallon, Juan Mendoza
     */

    public Map<String, Object> updateAccountInfo(int id, AccountInfoDTO accountInfoDTO){
        Map<String, Object> updatedAccountMap = new HashMap<>();

        if(userRepository.findByEmail(accountInfoDTO.getEmail()) == null && accountRepository.findByUsername(accountInfoDTO.getUsername()) == null){

            if(accountRepository.findById(id).isPresent()) {


                AccountEntity account = accountRepository.findById(id).get();


                if (userRepository.findById(account.getUser().getId()).isPresent()) {
                    UserEntity user = userRepository.findById(account.getUser().getId()).get();

                    if (isValid(accountInfoDTO.getPassword())) {
                        account.setPassword(accountInfoDTO.getPassword());
                        updatedAccountMap.put("password", true);
                    }

                    if (isValid(accountInfoDTO.getEmail())) {
                        user.setEmail(accountInfoDTO.getEmail());
                        updatedAccountMap.put("email", user.getEmail());
                    }

                    if (isValid(accountInfoDTO.getUsername())) {
                        account.setUsername(accountInfoDTO.getUsername());
                        updatedAccountMap.put("username", account.getUsername());
                    }


                    userRepository.save(user);
                    accountRepository.save(account);
                    return updatedAccountMap;
                }


            }
            return null;
        }else{
            updatedAccountMap.put("conflict", "email and/or username is already taken");
            return updatedAccountMap;
        }

    }

    /**
     * Takes in an AddPointsDTO (just one field with points for the time being) and adds the points to the account total.
     *
     * @param pointsToAdd AddPointsDTO
     * @param userId int
     * @return AccountResponseDTO containing updated account information
     * @throws ResourceNotFoundException if no account is found by the username.
     * @author Richard Taylor && Uros Vorkapic
     */
    public AccountResponseDTO updatePoints(AddPointsDTO pointsToAdd, int userId) throws ResourceNotFoundException {
        Optional<AccountEntity> _account = accountRepository.findById(userId);

        if(_account.isPresent()) {
            AccountEntity account = _account.get();
            account.setPoints(account.getPoints() + pointsToAdd.getPoints());
            return new AccountResponseDTO(accountRepository.save(account));
        } else {
            throw new ResourceNotFoundException();
        }
    }


    /**
     * If fields are blank, then they will not be updated
     * @param str
     * @return
     * @author James Fallon && Juan Mendoza
     */
    private boolean isValid(String str){
        if(str.trim().equals("")){
            return false;
        }

        return true;
    }



    /**
     * This method is responsible for login into an account that exists in the database
     * @param credentialsDTO The DTO required for authentication.
     * @return accountLoginDTO The DTO required to login to an account.
     * @author Sheckeem Daley
     * @author Kyle Plummer
     * @throws InvalidCredentialsException when the username/password are not found/matched in the database
     */
    public AuthenticatedDTO login(CredentialsDTO credentialsDTO) throws InvalidCredentialsException {

        AccountEntity accountEntity;
        AuthenticatedDTO authenticatedDTO;
        accountEntity = accountRepository.findByUsernameAndPassword(credentialsDTO.getUsername(), credentialsDTO.getPassword());

        try {
            authenticatedDTO = new AuthenticatedDTO(accountEntity);
        } catch (NullPointerException e) {
            throw new InvalidCredentialsException("Invalid username and/or password!");
        }

        return authenticatedDTO;
    }

}




