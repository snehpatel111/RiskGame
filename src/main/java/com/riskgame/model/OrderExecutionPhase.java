package com.riskgame.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Queue;
import java.util.Scanner;

import com.riskgame.controller.GameEngine;
import com.riskgame.utility.Constant;

public class OrderExecutionPhase extends Phase {
  public OrderExecutionPhase(GameEngine p_gameEngine, GameState p_GgmeState) {
    super(p_gameEngine, p_GgmeState);
  }

  @Override
  protected void showMap(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    
    this.printInvalidCommandInState();
  }

  @Override
  public void initPhase() {
    
    System.out.println("lol orderExecution initPhase");
    while (this.d_gameEngine.getCurrentGamePhase() instanceof OrderExecutionPhase) {
      try {
        int l_numOfOrders = this.d_gameState.getUnexecutedOrders().size();
        // for(Player l_p : this.d_gameState.getPlayerList()){
        //   Queue<Order> l_orders = l_p.getExecutionOrderList();
        //   l_numOfOrders += l_orders.size();
        // }
        if(l_numOfOrders == 0){
          System.out.println("orders already executed!!");
          break;
        }
         else{
          System.out.println("total orders : " + l_numOfOrders);


          while (!this.d_gameState.getUnexecutedOrders().isEmpty()) {
            Order l_o = this.d_gameState.getUnexecutedOrders().poll();
            l_o.execute();
          }
          // for(Order l_order :this.d_gameState.getUnexecutedOrders()){
          //   l_order.execute();
          // }
          
          System.out.println(Constant.SUCCESS_COLOR + "All orders are executed successfully. " + Constant.RESET_COLOR);

          // Iterator<Player> l_iterator = this.d_gameState.getPlayerList().listIterator();
          // while (l_iterator.hasNext()) {
          //   System.out.println("lol while");
          //   Player l_player = l_iterator.next();
          //   l_player.showCards();
          // }
          // System.out.println("lol out while");
          // for(Player l_p : this.d_gameState.getPlayerList()){
          //   l_p.showCards();
          // }
          this.d_gameEngine.setIssueOrderPhase();
        }
        }catch (Exception e) {
        this.d_gameEngine.setGameEngineLog(e.getMessage(), "effect");
      }
      // this.issueOrders();
    }
  }

  @Override
  protected void deploy(GameState p_gameState, Player p_player, String[] p_args) {
    
    this.printInvalidCommandInState();
  }

  @Override
  protected void assignCountries(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    
    this.printInvalidCommandInState();
  }

  @Override
  protected void editNeighbor(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    
    this.printInvalidCommandInState();
  }

  @Override
  protected void editCountry(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    
    this.printInvalidCommandInState();
  }

  @Override
  protected void validateMap(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    
    this.printInvalidCommandInState();
  }

  @Override
  protected void loadMap(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    
    this.printInvalidCommandInState();
  }

  @Override
  protected void saveMap(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    
    this.printInvalidCommandInState();
  }

  @Override
  protected void editContinent(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
    
    this.printInvalidCommandInState();
  }

  @Override
  protected void editMap(GameEngine p_gameEngine, GameState p_gameState, String[] p_mapFileName) {
    
    this.printInvalidCommandInState();
  }

  @Override
  public void assignReinforcementToPlayer(GameState p_gameState) {
    
    this.printInvalidCommandInState();
  }

  @Override
  protected void managePlayer(GameEngine p_gameEngine, GameState p_gameState, String[] p_mapFileName) {
    
    this.printInvalidCommandInState();
  }

  @Override
  protected void bomb(GameState d_gameState, Player p_player, String[] l_data) {
    this.printInvalidCommandInState();

  }

  @Override
  protected void negotiate(GameState d_gameState, Player p_player, String[] l_data) {
    this.printInvalidCommandInState();

  }

  @Override
  protected void blockade(GameState p_gameState, Player p_player, String[] l_data) {
    this.printInvalidCommandInState();

  }

  @Override
  protected void airLift(GameState p_gameState, Player p_player, String[] l_data) {
    this.printInvalidCommandInState();

  }

  @Override
  protected void advance(GameState p_gameState, Player p_player, String[] l_data) {
    this.printInvalidCommandInState();

  }
}
