package com.example.bankcards.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Messages {
    ACCESS_DENIED_BALANCE("You don't have permission to view this card's balance"),
    ACCESS_DENIED_DEPOSIT("You don't have permission to deposit to this card"),
    AMOUNT_EXCEEDS_LIMIT("Transfer amount exceeds the limit of 10,000"),
    BALANCE_RETRIEVED_SUCCESS("Balance retrieved successfully"),
    CARD_BLOCK_REQUEST_EXISTS("You already have a request to block this card."),
    CARD_INACTIVE("One of the cards is inactive"),
    CARD_NOT_FOUND("Card not found with id:"),
    CARD_NOT_FOUND_TRANSFER("Card not found or does not belong to the user"),
    DECRYPTION_ERROR("Decryption error"),
    DEPOSIT_SUCCESS("Deposit successful"),
    EMAIL("Email "),
    ENCRYPTION_ERROR("Encryption error"),
    INSUFFICIENT_FUNDS("Insufficient funds on the source card"),
    INVALID_CARD_OWNER("You can only request to block your own cards"),
    INVALID_REFRESH_TOKEN("Invalid refresh token"),
    INVALID_STATUS_VALUE("Invalid status value: "),
    IS_ALREADY_TAKEN(" is already taken."),
    NO_USER_FOUND("No user found"),
    REQUEST_NOT_FOUND("Request not found"),
    SAME_CARD_TRANSFER("Transfer to the same card is not possible"),
    TRANSFER_SUCCESS("Transfer successful"),
    USERNAME("Username "),
    USER_NOT_FOUND("User not found"),
    USER_NOT_FOUND_EMAIL("User not found with email: "),
    USER_NOT_FOUND_ID("User not found with id:"),
    USER_NOT_FOUND_USERNAME("User not found with username: ");

    private final String message;
}
