// package com.riskgame.view;

// import static org.junit.Assert.assertEquals;

// import java.io.IOException;
// import java.nio.charset.StandardCharsets;
// import java.nio.file.Files;
// import java.nio.file.Paths;
// import java.util.Observable;

// import org.junit.Before;
// import org.junit.Test;

// import com.riskgame.model.LogEntryBuffer;

// public class GameLoggerTest {

// private GameLogger gameLogger;
// private LogEntryBuffer logEntryBuffer;

// /**
// * Setting up GameLogger and LogEntryBuffer instances before each test.
// */
// @Before
// public void setup() {
// gameLogger = new GameLogger();
// logEntryBuffer = new LogEntryBuffer();
// }

// /**
// * Test method to verify if the GameLogger updates the log file properly based
// on LogEntryBuffer logs.
// */
// @Test
// public void testGameLoggerUpdatesLogFile() {
// LogEntryBuffer logEntryBuffer = new LogEntryBuffer();

// String logMessage = "This is a log entry.";

// gameLogger.update(logEntryBuffer, null);

// try {
// String content = new String(Files.readAllBytes(Paths.get("LogFile.txt")),
// StandardCharsets.US_ASCII);
// assertEquals(logMessage, content.trim());
// } catch (Exception e) {
// e.printStackTrace();
// }
// }
// }
