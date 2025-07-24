package com.orlovandrei.bank_rest.exception;

public class DuplicateBlockRequestException extends RuntimeException {
    public DuplicateBlockRequestException(String message) {
        super(message);
    }
}
