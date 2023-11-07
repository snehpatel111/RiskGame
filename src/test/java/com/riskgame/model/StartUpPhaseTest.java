package com.riskgame.model;

import org.junit.Before;
import org.junit.Test;

import com.riskgame.model.Phase;

import static org.junit.Assert.*;

public class StartUpPhaseTest {

    /**
     * Test valid arguments
     */
    @Test
    public void testValidArguments() {
        String[] args = { "arg1", "arg2", "arg3" };
        int expectedLength = 3;
        assertTrue(Util.isValidCommandArgument(args, expectedLength));
    }

    /**
     * Test null arguments
     */
    @Test
    public void testNullArguments() {
        String[] args = null;
        int expectedLength = 3;
        assertFalse(Util.isValidCommandArgument(args, expectedLength));
    }

    /**
     * Test invalid arguments
     */
    @Test
    public void testInvalidArguments() {
        String[] args = { "arg1", "arg2" };
        int expectedLength = 3;
        assertFalse(Util.isValidCommandArgument(args, expectedLength));
    }

    /**
     * Test empty arguments
     */
    @Test
    public void testEmptyArguments() {
        String[] args = {};
        int expectedLength = 0;
        assertTrue(Util.isValidCommandArgument(args, expectedLength));
    }
}
