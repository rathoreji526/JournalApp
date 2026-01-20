package net.engineeringdigest.journalApp.utilities;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;


@Service
public class CommonUtilities {
    private static final int SALT_ROUNDS = 12;
    private static final SecureRandom random = new SecureRandom();

    public static String encode(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt(SALT_ROUNDS));
    }

    public static boolean matches(String rawPassword, String hashedPassword) {
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }

    public static String OTPGenerator(){
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

}
