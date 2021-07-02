package com.revature.quizzard.exceptions;

import lombok.NoArgsConstructor;

/**
 * General-purpose exception for when a bad request comes in
 */
@NoArgsConstructor
public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String message) {
        super(message);
    }
}
