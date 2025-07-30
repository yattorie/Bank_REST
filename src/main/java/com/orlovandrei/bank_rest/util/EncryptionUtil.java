package com.orlovandrei.bank_rest.util;

import com.orlovandrei.bank_rest.exception.EncryptionErrorException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class EncryptionUtil {
    private static final String ALGORITHM = "AES";

    private static String key;

    @Value("${encryption.key}")
    public void setKey(String key) {
        EncryptionUtil.key = key;
    }

    public static String encrypt(String value) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new EncryptionErrorException(Messages.ENCRYPTION_ERROR.getMessage(), e);
        }
    }

    public static String decrypt(String encryptedValue) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decodedValue = Base64.getDecoder().decode(encryptedValue);
            byte[] decrypted = cipher.doFinal(decodedValue);
            return new String(decrypted);
        } catch (Exception e) {
            throw new EncryptionErrorException(Messages.DECRYPTION_ERROR.getMessage(), e);
        }
    }
}