package kr.co.wikibook.gallery.common.util;

public class EncryptionUtils {

    private static final String ALGORITHM   = "AES";
    private static final String SECRET_KEY  = "a72ha@61@16213iu"; // 16 bytes

    public static String encrypt(String data) {
        try {
            javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(ALGORITHM);
            javax.crypto.spec.SecretKeySpec secretKey = new javax.crypto.spec.SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedData = cipher.doFinal(data.getBytes());
            return java.util.Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting data", e);
        }
    }

    public static String decrypt(String encryptedData) {
        try {
            javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(ALGORITHM);
            javax.crypto.spec.SecretKeySpec secretKey = new javax.crypto.spec.SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            cipher.init(javax.crypto.Cipher.DECRYPT_MODE, secretKey);
            byte[] decodedData = java.util.Base64.getDecoder().decode(encryptedData);
            byte[] decryptedData = cipher.doFinal(decodedData);
            return new String(decryptedData);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting data", e);
        }
    }
}
