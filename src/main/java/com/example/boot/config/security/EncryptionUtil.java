package com.example.boot.config.security;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class EncryptionUtil {

    private static final Logger log = LoggerFactory.getLogger(EncryptionUtil.class);
    private static Cipher cipher;
    private static Cipher decipher;
    private static final String DES_KEY = "wav8dal2an7mi8wyit4in7pyd9jan4vu6ad8yoj9ij8eeb8pha";

    static {
        try {
            final byte[] desKeyData = EncryptionUtil.DES_KEY.getBytes("UTF-8");
            final DESKeySpec desKeySpec = new DESKeySpec(desKeyData);
            final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            final SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

            EncryptionUtil.cipher = Cipher.getInstance("DES");
            EncryptionUtil.cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            EncryptionUtil.decipher = Cipher.getInstance("DES");
            EncryptionUtil.decipher.init(Cipher.DECRYPT_MODE, secretKey);

        } catch (final NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("Encryption problem - Illegal Encryption Algorithm");
        } catch (final InvalidKeyException e) {
            throw new IllegalArgumentException("Encryption problem - Invalid Key Generated");
        } catch (final InvalidKeySpecException e) {
            throw new IllegalArgumentException("Encryption problem - Invalid Key Spec");
        } catch (final NoSuchPaddingException e) {
            throw new IllegalArgumentException("Encryption problem - No Such Padding");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Encryption problem - Unsupported Encoding");
        }
    }

    public static void main(String[] args) {
        final String password = encrypt("password");
        System.out.println(password);
    }

    public static synchronized String encrypt(final String s) {
        try {
            byte[] utf8 = s.getBytes("UTF-8");

            final byte[] enc = EncryptionUtil.cipher.doFinal(utf8);

            return new String(Base64.encodeBase64(enc));
        } catch (final Exception e) {
            log.error("Error while encrypting", e);
            throw new IllegalArgumentException("Error encrypting string", e);
        }
    }

    public static synchronized String decrypt(final String s) {
        try {
            final byte[] enc = Base64.decodeBase64(s);
            return new String(EncryptionUtil.decipher.doFinal(enc), "UTF-8");
        } catch (final Exception e) {
            log.error("Error while decrypting", e);
            throw new IllegalArgumentException("Error encrypting string", e);
        }
    }

}
