package com.riskgame.model;

import java.util.Observable;
import com.riskgame.view.GameLogger;

/**
 * Records logs for different stages of the game.
 */
public class LogEntryBuffer extends Observable {

  /**
   * Log message to be recorded.
   */
  String d_logMessage;

  /**
   * Initializes an instance of the class by adding a `GameLogger` observer
   * object.
   */
  public LogEntryBuffer() {
    GameLogger l_logWriter = new GameLogger();
    this.addObserver(l_logWriter);
  }

  /**
   * Gets the log message.
   *
   * @return The log message.
   */
  public String getD_logMessage() {
    return d_logMessage;
  }

  /**
   * Sets the log message and notifies the observer objects.
   *
   * @param p_messageToUpdate The log message to set.
   * @param p_logType         The type of log: "Command," "Order," "Effect,"
   *                          "Phase," "Start," or "End."
   */
  public void currentLog(String p_messageToUpdate, String p_logType) {
    switch (p_logType.toLowerCase()) {
      case "command":
        d_logMessage = System.lineSeparator() + "Command Entered: " + p_messageToUpdate + System.lineSeparator();
        break;
      case "order":
        d_logMessage = System.lineSeparator() + "Order Issued: " + p_messageToUpdate + System.lineSeparator();
        break;
      case "phase":
        d_logMessage = System.lineSeparator() + "=======" + p_messageToUpdate + "=======" + System.lineSeparator()
            + System.lineSeparator();
        break;
      case "effect":
        d_logMessage = "Log: " + p_messageToUpdate + System.lineSeparator();
        break;
      case "start":
      case "end":
        d_logMessage = p_messageToUpdate + System.lineSeparator();
        break;
    }
    setChanged();
    notifyObservers();
  }
}
