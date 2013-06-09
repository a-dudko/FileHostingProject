package by.bsu.fpmi.second.dudkoAA;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryptor {

    public static final int HEX_CODE = 0xFF;

    private static Encryptor instance;

    public static Encryptor getInstance() {
        if (instance == null) {
            instance = new Encryptor();
        }
        return instance;
    }

    /**
     * Scrambles the password using MD5 encryption.
     * @param password
     * @return
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
