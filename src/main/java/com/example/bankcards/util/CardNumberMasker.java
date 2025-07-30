package com.example.bankcards.util;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CardNumberMasker {

    public static String mask(String number) {
        if (number == null || number.length() < 4) return "****";
        String last4 = number.substring(number.length() - 4);
        return "**** **** **** " + last4;
    }
}