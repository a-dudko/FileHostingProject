package by.bsu.fpmi.second.dudkoAA;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/** Class for encrypting strings. */
public class Encryptor {

    /** Hex-number used for encryption. */
    public static final int HEX_CODE = 0xFF;

    /**
     * Encryptes the string using MD5 encryption algorithm.
     * @param string to be encrypted
     * @return encrypted string
     * @throws NoSuchAlgorithmException
     */
    public static String getStringMD5(final String string) throws NoSuchAlgorithmException {
        StringBuffer hexString = new StringBuffer();
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        String salt = "hg38iu37";
        messageDigest.update((string + salt).getBytes());
        byte[] digest = messageDigest.digest();
        for (int i = 0; i < digest.length; i++) {
            hexString.append(Integer.toHexString(HEX_CODE & digest[i]));
        }
        return hexString.toString();
    }
}
