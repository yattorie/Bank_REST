package com.orlovandrei.bank_rest.exception;

public class TransferBetweenSameCardException extends RuntimeException{
    public TransferBetweenSameCardException(String message) {
        super(message);
    }
}

