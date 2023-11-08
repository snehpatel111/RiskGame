package com.riskgame.view;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Observable;
import java.util.Observer;

import com.riskgame.model.LogEntryBuffer;

/**
 * The `GameLogger` class is responsible for updating a log file based on logs
 * provided by the `LogEntryBuffer` class.
 */
public class GameLogger implements Observer {

  /**
   * The `LogEntryBuffer` object that provides updated log entries.
   */
  LogEntryBuffer d_logEntryBuffer;

  /**
   * Writes log entries from the `LogEntryBuffer` object to the log file.
   *
   * @param p_observable The `LogEntryBuffer` object that is observed.
   * @param p_object     An object (not used in this method).
   */
  @Override
  public void update(Observable p_observable, Object p_object) {
    this.d_logEntryBuffer = (LogEntryBuffer) p_observable;
    // File l_logfile = new File("LogFile.txt");
    String l_logMessage = this.d_logEntryBuffer.getLogMessage();

    try {
      if (l_logMessage.equals("Initializing the Game ......" + System.lineSeparator() + System.lineSeparator())) {
        Files.newBufferedWriter(Paths.get("LogFile.txt"), StandardOpenOption.TRUNCATE_EXISTING).write(" ");
      }
      Files.write(Paths.get("LogFile.txt"), l_logMessage.getBytes(StandardCharsets.US_ASCII), StandardOpenOption.CREATE,
          StandardOpenOption.APPEND);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
