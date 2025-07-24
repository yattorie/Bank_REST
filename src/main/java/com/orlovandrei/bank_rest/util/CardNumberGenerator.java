package com.orlovandrei.bank_rest.util;


import java.util.Random;

public final class CardNumberGenerator {

    public static String generate() {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            cardNumber.append(random.nextInt(10));
        }
        return cardNumber.toString();
    }
}



