package com.orlovandrei.bank_rest.exception;

public class AmountExceedsLimitException extends RuntimeException {
    public AmountExceedsLimitException(String message) {
        super(message);
    }
}

