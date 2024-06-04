package gr.aueb.cf.medicalcare.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * A utility class for password hashing and checking.
 */
public class SecUtil {
    private SecUtil() {}


    public static boolean checkPassword(String inputPasswd, String storedHashedPasswd) {
        return BCrypt.checkpw(inputPasswd, storedHashedPasswd);
    }

    public static String hashPassword(String inputPasswd) {
        int workload = 12;
        String salt = BCrypt.gensalt(workload);
        return BCrypt.hashpw(inputPasswd, salt);
    }
}
