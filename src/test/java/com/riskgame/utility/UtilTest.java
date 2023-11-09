package com.riskgame.utility;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test for UtilTest.
 */
public class UtilTest {

    /**
     * Test cases for checking alphabet letter in name
     */
    @Test
    public void isAlphabeticTest() {
        boolean l_check = Util.isAlphabetic("abc");
        assertTrue(l_check);

        l_check = Util.isAlphabetic("ABC");
        assertTrue(l_check);

        l_check = Util.isAlphabetic("aAa");
        assertTrue(l_check);

        l_check = Util.isAlphabetic("B_b");
        assertTrue(l_check);

        l_check = Util.isAlphabetic("_bB");
        assertTrue(l_check);

        l_check = Util.isAlphabetic("@#*");
        assertFalse(l_check);
    }
}