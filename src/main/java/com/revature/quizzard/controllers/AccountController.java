package com.revature.quizzard.controllers;

import com.revature.quizzard.dtos.AccountInfoDTO;
import com.revature.quizzard.dtos.AuthenticatedDTO;
import com.revature.quizzard.dtos.AccountRegisterDTO;
import com.revature.quizzard.dtos.CredentialsDTO;
import com.revature.quizzard.dtos.requestmodels.AddPointsDTO;
import com.revature.quizzard.dtos.responsemodel.AccountResponseDTO;
import com.revature.quizzard.security.JWTokenUtil;
import com.revature.quizzard.services.AccountService;
import io.swagger.annotations.ResponseHeader;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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
        //System.out.println(credentialsDTO.toString());

        AuthenticatedDTO authenticatedDTO = accountService.login(credentialsDTO);
        response.addHeader("Authorization", "Bearer " + jwTokenUtil.generateToken(authenticatedDTO));

        return authenticatedDTO;
    }


    /**
     * This Put method will consume information to be updated on account and user by accountID which will be retrieved by the parsing of JWT.
     * @param accountInfoDTO
     * @param req
     * @returnupdatedAccountMap
     * @author James Fallon, Juan Mendoza
     */
    @PutMapping(value = "/accounts/update", consumes = APPLICATION_JSON_VALUE)
    public Map updateAccount(@RequestBody AccountInfoDTO accountInfoDTO, HttpServletRequest req){
        int accountID = jwTokenUtil.getIdFromToken(req.getHeader("Authorization"));
        Map updatedAccountMap = accountService.updateAccountInfo(accountID, accountInfoDTO);
        return updatedAccountMap;
    }

    /**
     * Takes in a JSON with the structure
     * <code>
     *     {
     *         "points" : "(amount)"
     *     }
     * </code>
     * where amount is the number of points to add (or potentially remove, if the number is negative) from the account.
     *
     * @param dto AddPointsDTO
     * @param req HttpServletRequest
     * @return AccountResponseDTO containing update user information.
     */
    @PostMapping("/accounts/points")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponseDTO addPointsToUser(@RequestBody AddPointsDTO dto, HttpServletRequest req) {
        int accountID = jwTokenUtil.getIdFromToken(req.getHeader("Authorization"));
        return accountService.updatePoints(dto, accountID);
    }
}
