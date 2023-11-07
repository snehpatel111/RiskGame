// package com.riskgame.model;

// import java.util.List;

// /**
//  * This class is used to test functionality of GameState class functions.
//  */
// public class GameLogger {

//   /**
//    * The map object.
//    */
//   Map d_map;

//   /**
//    * Log Entries for the existing game state.
//    */
//   LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();

//   /**
//    * The list of players.
//    */
//   ArrayList<Player> d_playerList;

//   /**
//    * The list of unexecuted orders.
//    */
//   ArrayList<Order> d_unexecutedOrderList;

//   /**
//    * The error message.
//    */
//   String d_error;

//   /**
//    * Checks if the user has used the load command.
//    */
//   boolean d_loadCommand = false;

//   /**
//    * Getter method to get the map.
//    * 
//    * @return The map object.
//    */
//   public Map getMap() {
//     return this.d_map;
//   }

//   /**
//    * Setter method to set the map.
//    * 
//    * @param p_map The map object.
//    */
//   public void setMap(Map p_map) {
//     this.d_map = p_map;
//   }

//   /**
//    * Getter method to get the list of players.
//    * 
//    * @return The list of players.
//    */
//   public ArrayList<Player> getPlayerList() {
//     return this.d_playerList;
//   }

//   /**
//    * Setter method to set the players.
//    * 
//    * @param p_players The list of players.
//    */
//   public void setPlayerList(ArrayList<Player> p_players) {
//     this.d_playerList = p_players;
//   }

//   /**
//    * Getter method to get the list of orders which are yet to be executed.
//    * 
//    * @return The list of orders.
//    */
//   public ArrayList<Order> getUnexecutedOrderList() {
//     return this.d_unexecutedOrderList;
//   }

//   /**
//    * Setter method to set the unexecuted orders.
//    * 
//    * @param p_unexecutedOrders The list of unexecuted orders.
//    */
//   public void setUnexecutedOrderList(ArrayList<Order> p_unexecutedOrders) {
//     this.d_unexecutedOrderList = p_unexecutedOrders;
//   }

//   /**
//    * Getter method to get the error message.
//    * 
//    * @return The error message.
//    */
//   public String getError() {
//     return this.d_error;
//   }

//   /**
//    * Setter method to set the error message.
//    * 
//    * @param p_error The error message.
//    */
//   public void setError(String p_error) {
//     this.d_error = p_error;
//   }

//   /**
//    * Message to be added in the log.
//    *
//    * @param p_logMessage The log message to be set in the object.
//    * @param p_logType    The type of log message to be added.
//    */
//   public void updateLog(String p_logMessage, String p_logType) {
//     this.d_logEntryBuffer.currentLog(p_logMessage, p_logType);
//   }

//   /**
//    * Fetches the most recent log in the current GameState.
//    *
//    * @return The recent log message.
//    */
//   public String getRecentLog() {
//     return this.d_logEntryBuffer.getLogMessage();
//   }

//   /**
//    * Sets the boolean load map variable.
//    */
//   public void setD_loadCommand() {
//     this.d_loadCommand = true;
//   }

//   /**
//    * Returns if the load command is used.
//    *
//    * @return A boolean value indicating if the map is loaded.
//    */
//   public boolean getLoadCommand() {
//     return this.d_loadCommand;
//   }

// }
