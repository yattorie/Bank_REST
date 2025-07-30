package com.example.bankcards.exception;

public class InvalidCardOwnerException extends RuntimeException {
    public InvalidCardOwnerException(String message) {
        super(message);
    }
}
