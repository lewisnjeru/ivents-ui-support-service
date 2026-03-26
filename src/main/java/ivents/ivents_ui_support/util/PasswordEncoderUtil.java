package ivents.ivents_ui_support.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderUtil {

    // Singleton instance of PasswordEncoder
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder(12); // strength 12

    private PasswordEncoderUtil() {
        // private constructor to prevent instantiation
    }

    /**
     * Encode a plain text password using BCrypt
     */
    public static String encode(String rawPassword) {
        return ENCODER.encode(rawPassword);
    }

    /**
     * Verify a raw password against the encoded hash
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        return ENCODER.matches(rawPassword, encodedPassword);
    }

    /**
     * Get the underlying PasswordEncoder (optional)
     */
    public static PasswordEncoder getEncoder() {
        return ENCODER;
    }
}