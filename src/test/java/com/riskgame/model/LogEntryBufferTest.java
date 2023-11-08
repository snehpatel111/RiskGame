package com.riskgame.model;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

public class LogEntryBufferTest {
    
    private LogEntryBuffer d_logEntryBuffer;
    private ByteArrayOutputStream d_outputStream;


    /**
     * set up the context
     */
    @Before
    public void setUp() {
        d_logEntryBuffer = new LogEntryBuffer();
        d_outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(d_outputStream));
    }


    /**
     * To test Current Log: Command
     */
    @Test
    public void testCurrentLogCommand() {
        d_logEntryBuffer.currentLog("Move armies to Europe", "Command");
        String expectedOutput = System.lineSeparator() + "Command Entered: Move armies to Europe" + System.lineSeparator();
        assertEquals(expectedOutput, d_logEntryBuffer.getLogMessage());
    }

    /**
     * To Test Current Log: Order
     */
    @Test
    public void testCurrentLogOrder() {
        d_logEntryBuffer.currentLog("Attack North America", "Order");
        String expectedOutput = System.lineSeparator() + "Order Issued: Attack North America" + System.lineSeparator();
        assertEquals(expectedOutput, d_logEntryBuffer.getLogMessage());
    }


    /**
     * To Test Current Log: Phase
     */
    @Test
    public void testCurrentLogPhase() {
        d_logEntryBuffer.currentLog("Reinforcement Phase", "Phase");
        String expectedOutput = System.lineSeparator() + "=======Reinforcement Phase=======" + System.lineSeparator()
                + System.lineSeparator();
        assertEquals(expectedOutput, d_logEntryBuffer.getLogMessage());
    }

    
    /**
     * To Test CurrentLog: Effect
     */
    @Test
    public void testCurrentLogEffect() {
        d_logEntryBuffer.currentLog("Player received reinforcement", "Effect");
        String expectedOutput = "Log: Player received reinforcement" + System.lineSeparator();
        assertEquals(expectedOutput, d_logEntryBuffer.getLogMessage());
    }

    /**
     * To Test CurrentLog: start
     */
    @Test
    public void testCurrentLogStart() {
        d_logEntryBuffer.currentLog("Game started", "Start");
        String expectedOutput = "Game started" + System.lineSeparator();
        assertEquals(expectedOutput, d_logEntryBuffer.getLogMessage());
    }

    /**
     * To Test CurrentLog: End
     */
    @Test
    public void testCurrentLogEnd() {
        d_logEntryBuffer.currentLog("Game ended", "End");
        String expectedOutput = "Game ended" + System.lineSeparator();
        assertEquals(expectedOutput, d_logEntryBuffer.getLogMessage());
    }

   

}
