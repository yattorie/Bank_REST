package com.orlovandrei.bank_rest.exception;

import com.orlovandrei.bank_rest.util.LoggerUtil;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(CardNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleCardNotFoundException(CardNotFoundException e) {
        LoggerUtil.logError("Card not found: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(RequestNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleRequestNotFound(RequestNotFoundException e) {
        LoggerUtil.logError("Request not found: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionBody handleUserAlreadyExists(UserAlreadyExistsException e) {
        LoggerUtil.logError("User already exists: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionBody handleEmailAlreadyExists(EmailAlreadyExistsException e) {
        LoggerUtil.logError("Email already exists: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(DuplicateBlockRequestException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionBody handleDuplicateBlockRequest(DuplicateBlockRequestException e) {
        LoggerUtil.logError("Duplicate block request: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleUserNotFoundException(UserNotFoundException e) {
        LoggerUtil.logError("User not found: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleIllegalState(IllegalStateException e) {
        LoggerUtil.logError("Illegal state: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleResourceNotFoundException(MethodArgumentNotValidException e) {
        LoggerUtil.logError("Validation failed: " + e.getMessage(), e);
        ExceptionBody exceptionBody = new ExceptionBody("Validation failed");
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        exceptionBody.setErrors(fieldErrors.stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
        return exceptionBody;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleConstraintViolation(ConstraintViolationException e){
        LoggerUtil.logError("Constraint violation: " + e.getMessage(), e);
        ExceptionBody exceptionBody = new ExceptionBody("Validation failed");
        exceptionBody.setErrors(e.getConstraintViolations().stream().collect(Collectors.toMap(
                violation -> violation.getPropertyPath().toString(),
                violation -> violation.getMessage())));
        return exceptionBody;
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionBody handleAuthenticationException(AuthenticationException e) {
        LoggerUtil.logError("Authentication failed: " + e.getMessage(), e);
        return new ExceptionBody("Authentication failed");
    }

    @ExceptionHandler(TransferBetweenSameCardException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleTransferBetweenSameCard(TransferBetweenSameCardException e) {
        LoggerUtil.logError("Перевод на ту же карту: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionBody handleAccessDeniedException(AccessDeniedException e) {
        LoggerUtil.logError("Access denied: " + e.getMessage(), e);
        return new ExceptionBody("Access denied: You do not have permission to perform this action");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionBody handleException(Exception e){
        LoggerUtil.logError("Internal server error", e);
        return new ExceptionBody("Internal error");
    }

    @ExceptionHandler(InsufficientFundsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleInsufficientFunds(InsufficientFundsException e) {
        LoggerUtil.logError("Недостаточно средств: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(AmountExceedsLimitException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleAmountExceedsLimit(AmountExceedsLimitException e) {
        LoggerUtil.logError("Превышен лимит: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(InvalidCardOwnerException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionBody handleInvalidCardOwner(InvalidCardOwnerException e) {
        LoggerUtil.logError("Недопустимый владелец: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(CardInactiveException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleCardInactive(CardInactiveException e) {
        LoggerUtil.logError("Карта неактивна: " + e.getMessage(), e);
        return new ExceptionBody(e.getMessage());
    }

}
