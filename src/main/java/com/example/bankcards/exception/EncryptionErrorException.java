package com.example.bankcards.exception;

public class EncryptionErrorException extends RuntimeException {
    public EncryptionErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}