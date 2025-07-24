package com.orlovandrei.bank_rest.exception;

public class CardInactiveException extends RuntimeException {
    public CardInactiveException(String message) {
        super(message);
    }
}
