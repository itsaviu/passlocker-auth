package com.ua.passlocker.auth.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

@Slf4j
public class SecurityUtils {


    private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static String encryptDate(String rawData) {
        return bCryptPasswordEncoder.encode(rawData);
    }

    public static String generateMasterToken(String _rawSecret, String clientSecret) {
        return blowFishEncrypt(_rawSecret, clientSecret);
    }


    public static String encodeWithBase64(String _rawData) {
        return Base64.getEncoder().encodeToString(_rawData.getBytes());
    }
    public static String uniqueHashCode() {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(UUID.randomUUID().toString().getBytes());
            messageDigest.digest();
            return new BigInteger(1, messageDigest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
            throw new RuntimeException("Error occured whule generating hash");
        }
    }

    public static String blowFishEncrypt(String data, String clientSecret) {
        byte[] pArray = getByteByMode(data.getBytes(StandardCharsets.UTF_8), Cipher.ENCRYPT_MODE, clientSecret);
        return Base64.getEncoder().encodeToString(pArray);
    }

    public  static String blowFishDecrypt(String data, String clientSecret) {
        byte[] encryptedData = Base64.getDecoder().decode(data);
        byte[] decrypted = getByteByMode(encryptedData, Cipher.DECRYPT_MODE, clientSecret);
        return new String(decrypted);

    }

    private  static byte[] getByteByMode(byte[] data, int mode, String clientSecret) {
        try {
            SecretKeySpec key = new SecretKeySpec(clientSecret.getBytes(StandardCharsets.UTF_8), "Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(mode, key);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Exception while initializing Cipher");
        }
    }
}
