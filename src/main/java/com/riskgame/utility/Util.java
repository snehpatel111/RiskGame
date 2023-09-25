package main.java.com.riskgame.utility;

/**
 * Helper method to for game.
 */
public class Util {

    /**
     * Validates if given string is a valid alphanumerical value.
     * @param p_arg String to validate.
     * @return True if string contains only alphabets, false otherwise.
     */
    public static boolean isAlphabetic(String p_arg) {
        return p_arg != null && p_arg.matches("^[a-zA-Z-_]*$");
    }
}
