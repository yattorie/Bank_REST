package com.example.bankcards.exception;

public class DuplicateBlockRequestException extends RuntimeException {
    public DuplicateBlockRequestException(String message) {
        super(message);
    }
}
