package com.revature.quizzard.exceptions;

/**
 * Exception to be thrown when registering a new user collides with a user already present
 */
public class DuplicateRegistrationException extends RuntimeException {
    public DuplicateRegistrationException(String message) {
        super(message);
    }
}
