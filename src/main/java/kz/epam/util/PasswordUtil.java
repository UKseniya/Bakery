package kz.epam.util;

import kz.epam.constant.Constant;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;


public final class PasswordUtil {

    private static final Random RANDOM = new SecureRandom();
    private static final String UTILITY_CLASS_MESSAGE = "Utility Class";
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String SECRET_KEY_FACTORY = "PBKDF2WithHmacSHA1";
    private static final String ERROR_MESSAGE = "Error while hashing a password:";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    private PasswordUtil() {
        throw new IllegalStateException(UTILITY_CLASS_MESSAGE);
    }

    public static String getSalt(int length) {
        StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }

    public static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(SECRET_KEY_FACTORY);
            return keyFactory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError(String.format(Constant.STRING_FORMAT, ERROR_MESSAGE, e.toString()));
        } finally {
            spec.clearPassword();
        }
    }

    public static String generateSecurePassword(String password, String salt) {
        String securedPassword;
        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());

        securedPassword = Base64.getEncoder().encodeToString(securePassword);

        return securedPassword;
    }

    public static boolean verifyUserPassword(String providedPassword,
                                             String securedPassword, String salt) {
        boolean passwordVerified;

        String newSecurePassword = generateSecurePassword(providedPassword, salt);
        passwordVerified = newSecurePassword.equalsIgnoreCase(securedPassword);

        return passwordVerified;
    }
}
