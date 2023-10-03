package com.riskgame.utility;

import org.junit.Test;

import static org.junit.Assert.*;

public class UtilTest{

    /**
     * Test cases for adding continent
     */
    @Test
    public void isAlphabeticTest(){
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