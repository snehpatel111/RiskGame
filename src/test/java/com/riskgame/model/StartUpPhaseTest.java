package com.riskgame.model;

import org.junit.Before;
import org.junit.Test;

import com.riskgame.utility.Phase;

import static org.junit.Assert.*;

public class StartUpPhaseTest {

    @Test
    public void testValidArguments() {
        String[] args = { "arg1", "arg2", "arg3" };
        int expectedLength = 3;
        assertTrue(StartUpPhase.isValidCommandArgument(args, expectedLength));
    }

    @Test
    public void testNullArguments() {
        String[] args = null;
        int expectedLength = 3;
        assertFalse(StartUpPhase.isValidCommandArgument(args, expectedLength));
    }

    @Test
    public void testInvalidArguments() {
        String[] args = { "arg1", "arg2" };
        int expectedLength = 3;
        assertFalse(StartUpPhase.isValidCommandArgument(args, expectedLength));
    }

    @Test
    public void testEmptyArguments() {
        String[] args = {};
        int expectedLength = 0;
        assertTrue(StartUpPhase.isValidCommandArgument(args, expectedLength));
    }
}
