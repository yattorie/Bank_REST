package com.orlovandrei.bank_rest.exception;

public class InvalidCardOwnerException extends RuntimeException {
    public InvalidCardOwnerException(String message) {
        super(message);
    }
}
