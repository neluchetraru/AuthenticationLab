package Server.Auth;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Encryption {

    public Encryption() {

    }
    static String hashPassword(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] encodedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
        return Arrays.toString(encodedPassword);
    }

}
