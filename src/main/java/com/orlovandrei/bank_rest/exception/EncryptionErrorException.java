package com.orlovandrei.bank_rest.exception;

public class EncryptionErrorException extends RuntimeException {
    public EncryptionErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}