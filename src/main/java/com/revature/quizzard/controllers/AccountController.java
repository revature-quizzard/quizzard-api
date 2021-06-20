package com.revature.quizzard.controllers;

import com.revature.quizzard.dtos.AuthenticatedDTO;
import com.revature.quizzard.dtos.AccountRegisterDTO;
import com.revature.quizzard.dtos.CredentialsDTO;
import com.revature.quizzard.security.JWTokenUtil;
import com.revature.quizzard.services.AccountService;
import io.swagger.annotations.ResponseHeader;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * The Controller for Accounts.
 */
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AccountController {

    private final AccountService accountService;
    private final JWTokenUtil jwTokenUtil;

    /**
     * The AccountController's POST method for the <code>/register</code> endpoint
     * This endpoint is used for registration. Assuming successful registration,
     * this method will consume and produce a JSON, and then immediately login.
     * If unsuccessful, it will throw an exception. This method leverages
     * <code>AccountRegisterDTO</code>, <code>CredentialsDTO</code>, <code>LoginDTO</code> and <code>AccountService</code>
     * @see AccountService
     * @param accountRegisterDTO The DTO required to register a user, it has fields from both <code>UserEntity</code> <code>AccountEntity</code>.
     * @return accountLoginDTO The DTO required to login to a successfully registered user's account.
     * @author Sheckeem Daley, Kyle Plummer
     */
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticatedDTO register(@RequestBody AccountRegisterDTO accountRegisterDTO, HttpServletResponse response) {
        System.out.println(accountRegisterDTO.toString());

        AuthenticatedDTO authenticatedDTO = accountService.register(accountRegisterDTO);

        CredentialsDTO credentialsDTO = new CredentialsDTO();
        credentialsDTO.setUsername(accountRegisterDTO.getUsername());
        credentialsDTO.setPassword(accountRegisterDTO.getPassword());
        login(credentialsDTO, response); // Call login after registration assuming success

        //TODO: add try-catch in case of invalid registration.
        //TODO: eventually re-factor to leverage Exception aspect.

        return authenticatedDTO;
    }

    /**
     * The AccountController's POST method for the <code>/register</code> endpoint
     * This endpoint is used for authentication. Assuming successful authentication,
     * this method will consume and produce a JSON to login. If unsuccessful, it will
     * throw an exception. This method leverages <code>CredentialsDTO</code>, <code>LoginDTO</code> and <code>AccountService</code>
     * @see AccountService
     * @param credentialsDTO The DTO required for authentication.
     * @return accountLoginDTO The DTO required to login to an account.
     * @author Sheckeem Daley, Kyle Plummer
     */
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseHeader(name = "response")
    public AuthenticatedDTO login(@RequestBody CredentialsDTO credentialsDTO, HttpServletResponse response) {
        System.out.println(credentialsDTO.toString());

        AuthenticatedDTO authenticatedDTO = accountService.login(credentialsDTO);
        response.addHeader("Authorization", jwTokenUtil.generateToken(authenticatedDTO));

        return authenticatedDTO;
    }
}
