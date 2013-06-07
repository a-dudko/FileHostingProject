package by.bsu.fpmi.second.dudkoAA;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Scrambler {

    private static Scrambler instance;

    public static Scrambler getInstance() {
        if (instance == null) {
            instance = new Scrambler();
        }
        return instance;
    }

    public String getPasswordMD5(String password) throws NoSuchAlgorithmException {
        StringBuffer hexString = new StringBuffer();
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        String salt = "hg38iu37";
        messageDigest.update((password + salt).getBytes());
        byte digest[] = messageDigest.digest();
        for (int i = 0; i < digest.length; i++) {
            hexString.append(Integer.toHexString(0xFF & digest[i]));
        }
        return hexString.toString();
    }
}
