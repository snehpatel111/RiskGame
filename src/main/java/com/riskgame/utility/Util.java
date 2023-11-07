package com.riskgame.utility;

/**
 * Helper method to for game.
 */
public class Util {

    /**
     * Validates if given string is a valid alphanumerical value.
     * 
     * @param p_arg String to validate.
     * @return True if string contains only alphabets, false otherwise.
     */
    public static boolean isAlphabetic(String p_arg) {
        return p_arg != null && p_arg.matches("^[a-zA-Z-_]*$");
    }

    /**
     * Checks if the command arguments are valid for the command
     * 
     * @param p_commandArgs        Command line arguments
     * @param p_expectedArgsLength Expected number of arguments
     * @return Returns true if number of arguments is valid, false otherwise
     */
    public static boolean isValidCommandArgument(String[] p_commandArgs, int p_expectedArgsLength) {
        return p_commandArgs != null && p_commandArgs.length == p_expectedArgsLength;
    }
}
