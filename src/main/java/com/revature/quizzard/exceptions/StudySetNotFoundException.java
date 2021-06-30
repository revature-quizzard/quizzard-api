package com.revature.quizzard.exceptions;

/**
 * @Author: Sean Taba
 */
public class StudySetNotFoundException extends RuntimeException {
    public StudySetNotFoundException() {
        super("StudySet not found");
    }
}
