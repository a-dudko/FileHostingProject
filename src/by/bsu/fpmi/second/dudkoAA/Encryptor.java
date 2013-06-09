package by.bsu.fpmi.second.dudkoAA;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/** Class for encrypting passwords. */
public class Encryptor {

    /** Hex-number used for encryption. */
    public static final int HEX_CODE = 0xFF;

    /** Instance of class object. */
    private static Encryptor instance;

    /**
     * Get the instance of class and create it
     * if it hasn't been created yet.
     * @return the instance of class
     */
    public static Encryptor getInstance() {
        if (instance == null) {
            instance = new Encryptor();
        }
        return instance;
    }

    /**
     * Encryptes the password using MD5 encryption algorithm.
     * @param password to be encrypted
     * @return encrypted password
     * @throws NoSuchAlgorithmException
     */
    public String getPasswordMD5(final String password) throws NoSuchAlgorithmException {
        StringBuffer hexString = new StringBuffer();
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        String salt = "hg38iu37";
        messageDigest.update((password + salt).getBytes());
        byte[] digest = messageDigest.digest();
        for (int i = 0; i < digest.length; i++) {
            hexString.append(Integer.toHexString(HEX_CODE & digest[i]));
        }
        return hexString.toString();
    }
}
