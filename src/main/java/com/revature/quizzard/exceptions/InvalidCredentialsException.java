package com.revature.quizzard.exceptions;

/**
 * Exception to be thrown upon login attempt when username or password are not matched in database.
 */
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
