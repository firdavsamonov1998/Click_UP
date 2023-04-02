package com.example.click_up.exception;

public class UserNameExistException extends RuntimeException {
    public UserNameExistException(String message) {
        super(message);
    }
}
