package com.orlovandrei.bank_rest.util;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CardNumberConverter implements AttributeConverter<String, String> {
    @Override
    public String convertToDatabaseColumn(String attribute) {
        return EncryptionUtil.encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return EncryptionUtil.decrypt(dbData);
    }
}
