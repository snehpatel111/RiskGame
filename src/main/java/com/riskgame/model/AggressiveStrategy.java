package com.riskgame.model;

import java.util.Random;

import com.riskgame.controller.GameEngine;
import com.riskgame.model.Advance;
import com.riskgame.model.Airlift;
import com.riskgame.model.Bomb;
import com.riskgame.model.Deploy;
import com.riskgame.model.Order;

import com.riskgame.utility.Constant;

/**
 * This class is used to implement Aggressive strategy.
 */
public class AggressiveStrategy extends PlayerStrategy {
  private int d_orderValue, d_maxArmyCount;
  Country d_strongestCountry, d_defendingCountry, d_moveArmyFromCountry, d_armyDeployableCountry;
  boolean d_isTest;
  public int d_testReinforceArmies;

  /**
   * Constructor for AggressiveStrategy
   *
   * @param p_player     Player object
   * @param p_gameEngine GameEngine object
   */
  public AggressiveStrategy(Player p_player, GameEngine p_gameEngine) {
    super(p_player, p_gameEngine);
    this.d_strongestCountry = null;
    this.d_defendingCountry = null;
    this.d_armyDeployableCountry = null;
    this.d_maxArmyCount = 0;
    this.d_isTest = false;
  }

  /**
   * Find the strongest country which is owned by the player.
   */
  private void findStrongestCountry() {
    int l_maxArmyCount = 0, l_armyCount;
    for (Country l_country : this.d_player.getOwnedCountries().values()) {
      l_armyCount = l_country.getNumberOfArmies();
      if (l_armyCount > l_maxArmyCount) {
        l_maxArmyCount = l_armyCount;
        this.d_strongestCountry = l_country;
      }
    }
    if (l_maxArmyCount == 0) {
      this.setArmyDeployableCountry();
      this.d_strongestCountry = this.d_armyDeployableCountry;
    }
  }

  /**
   * {@inheritDoc}
   */
  protected Country attackToCountry() {
    if (this.d_strongestCountry != null) {
      for (Country l_neighborCountry : this.d_strongestCountry.getNeighbors().values()) {
        if (!this.d_player.getOwnedCountries().containsKey(l_neighborCountry.getCountryId().toLowerCase())) {
          this.d_defendingCountry = l_neighborCountry;
          return this.d_defendingCountry;
        }
      }
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  protected Country attackFromCountry() {
    this.findStrongestCountry();
    return this.d_strongestCountry;
  }

  /**
   * {@inheritDoc}
   */
  protected Country moveArmyFromCountry() {
    int l_maxArmyCount = 0;
    this.findStrongestCountry();

    if (this.d_strongestCountry != null) {
      for (Country l_neighborCountry : this.d_strongestCountry.getNeighbors().values()) {
        if (this.d_player.getOwnedCountries().containsKey(l_neighborCountry.getCountryId().toLowerCase())) {
          int l_countryArmyCount = l_neighborCountry.getNumberOfArmies();
          if (l_countryArmyCount >= l_maxArmyCount) {
            l_maxArmyCount = l_countryArmyCount;
            this.d_moveArmyFromCountry = l_neighborCountry;
            this.d_maxArmyCount = l_maxArmyCount;
          }
        }
      }
    }
    if (l_maxArmyCount == 0) {
      this.setInitialMoveArmyFromCountry();
    }
    return this.d_moveArmyFromCountry;
  }

  /**
   * Sets the order value for the attack order.
   */
  public void setOrderValue() {
    Random l_random = new Random();
    this.d_orderValue = l_random.nextInt(3);
  }

  /**
   * Sets the order value for tests
   * 
   * @param p_random Random integer value
   */
  public void setTestOrderValue(int p_random) {
    this.d_orderValue = p_random;
  }

  /**
   * Set the boolean value for test.
   * 
   * @param p_isTest input boolean value
   */
  public void isTest(boolean p_isTest) {
    this.d_isTest = p_isTest;
  }

  /**
   * Sets the country where armies can be deployed.
   * 
   * @return Country
   */
  private void setArmyDeployableCountry() {
    for (Country l_country : this.d_player.getOwnedCountries().values()) {
      for (Country l_neighborCountry : l_country.getNeighbors().values()) {
        if (!this.d_player.getOwnedCountries().containsKey(l_neighborCountry.getCountryId().toLowerCase())) {
          this.d_armyDeployableCountry = l_country;
        }
      }
    }
  }

  /**
   * Sets the initial country from which armies can be moved.
   * 
   */
  private void setInitialMoveArmyFromCountry() {
    int l_maxArmyCount = 0;
    for (Country l_country : this.d_player.getOwnedCountries().values()) {
      for (Country l_neighborCountry : l_country.getNeighbors().values()) {
        if (this.d_player.getOwnedCountries().containsKey(l_neighborCountry.getCountryId().toLowerCase())) {
          this.d_moveArmyFromCountry = l_neighborCountry;
          this.d_moveArmyFromCountry.setNumberOfArmies(2);
          this.d_moveArmyFromCountry = l_country;
          l_maxArmyCount = this.d_moveArmyFromCountry.getNumberOfArmies();
          this.d_maxArmyCount = l_maxArmyCount;
        }
      }
    }
    this.d_moveArmyFromCountry = null;
  }

  /**
   * {@inheritDoc}
   * 
   */
  @Override
  public Order createOrder() {
    Country l_attackingCountry, l_defendingCountry, l_moveArmyFromCountry;
    l_attackingCountry = this.attackFromCountry();
    l_defendingCountry = this.attackToCountry();
    l_moveArmyFromCountry = this.moveArmyFromCountry();

    this.d_gameEngine.getCurrentGamePhase().getGameState().updateLog(
        "Countries owned by aggressive player " + this.d_player.getOwnedCountries().size(),
        "effect");

    this.d_gameEngine.getCurrentGamePhase().getGameState().updateLog(
        "Armies owned by aggressive player " + this.d_player.getOwnedArmyCount(),
        "effect");

    System.out.println("Countries owned by aggressive player " + this.d_player.getOwnedCountries().size());
    System.out.println("Armies owned by aggressive player " + this.d_player.getOwnedArmyCount());

    // // TODO
    // if (this.d_player.getOwnedCountries().size() == 0) {
    // // TODO
    // }

    Random l_random = new Random();

    if (!this.d_isTest)
      this.setOrderValue();

    switch (this.d_orderValue) {
      case 0:
        if (this.d_player.getOwnedArmyCount() == 0) {
          this.d_gameEngine.getCurrentGamePhase().getGameState().updateLog(
              "Reinforcement armies are used by player",
              "effect");
          System.out.println("Reinforcement armies are used by player");
          break;
        }
        int l_reinforceArmies = this.d_player.getOwnedArmyCount();
        this.d_gameEngine.getCurrentGamePhase().getGameState().updateLog(
            "Aggressive reinforce armies owned by player " + this.d_player.getOwnedArmyCount(),
            "effect");
        System.out.println("Aggressive reinforce armies owned by player " + this.d_player.getOwnedArmyCount());
        this.d_testReinforceArmies = l_reinforceArmies;
        if (this.d_player.getOwnedArmyCount() == 1)
          l_reinforceArmies = 1;
        if (l_reinforceArmies != 0) {
          if (this.d_strongestCountry != null) {
            this.d_gameEngine.getCurrentGamePhase().getGameState().updateLog(
                "Armies deployed on country: " + this.d_strongestCountry.getCountryId() + " " + l_reinforceArmies,
                "effect");
            System.out
                .println(
                    "Armies deployed on country: " + this.d_strongestCountry.getCountryId() + " " + l_reinforceArmies);
            this.d_player.setOwnedArmyCount(this.d_player.getOwnedArmyCount() - l_reinforceArmies);
            return new Deploy(this.d_player, this.d_strongestCountry.getCountryId(), l_reinforceArmies);
          } else {
            this.setArmyDeployableCountry();
            if (this.d_armyDeployableCountry != null) {
              this.d_strongestCountry = this.d_armyDeployableCountry;
              this.d_gameEngine.getCurrentGamePhase().getGameState().updateLog(
                  "Armies deployed on country :" + this.d_armyDeployableCountry.getCountryId() + " "
                      + l_reinforceArmies,
                  "effect");
              System.out
                  .println("Armies deployed on country :" + this.d_armyDeployableCountry.getCountryId() + " "
                      + l_reinforceArmies);
              this.d_player.setOwnedArmyCount(this.d_player.getOwnedArmyCount() - l_reinforceArmies);
              System.out.println("lol case 0 player " + this.d_player.getPlayerName());
              System.out.println("lol case 0 d_armyDeployableCountry " + this.d_armyDeployableCountry.getCountryId());
              System.out.println("lol case 0 d_defendingCountry " + this.d_defendingCountry.getCountryId());
              System.out.println("lol case 0 d_strongestCountry " + this.d_strongestCountry.getCountryId());
              System.out.println("lol case 0 l_attackingCountry " + l_attackingCountry.getCountryId());

              return new Deploy(this.d_player, this.d_armyDeployableCountry.getCountryId(), l_reinforceArmies);
            }
          }
        } else {
          this.d_gameEngine.getCurrentGamePhase().getGameState().updateLog(
              "Invalid value for deploying reinforcement armies: " + l_reinforceArmies,
              "effect");
          System.out.println(Constant.ERROR_COLOR + "Invalid value for deploying reinforcement armies: "
              + l_reinforceArmies + Constant.RESET_COLOR);
        }
        return null;

      case 1:
        if (l_attackingCountry == null) {
          if (this.d_player.getOwnedCountries() == null) {
            System.out.println("The player " + this.d_player.getPlayerName() + " does not own any countries");
            this.d_gameEngine.getCurrentGamePhase().getGameState().updateLog(
                "The player " + this.d_player.getPlayerName() + " does not own any countries",
                "effect");
          }
          return null;
        } else {
          if (l_attackingCountry.getNumberOfArmies() == 0) {
            this.d_gameEngine.getCurrentGamePhase().getGameState().updateLog(
                "The number of armies in strongest country is 0, deploy before advance",
                "effect");
            System.out.println(Constant.ERROR_COLOR
                + "The number of armies in strongest country is 0, deploy before advance" + Constant.RESET_COLOR);
            return null;
          }
          if (l_defendingCountry != null) {
            if (this.d_player.doesCardExists("Bomb")) {
              Random l_randomCard = new Random();
              int l_value = l_randomCard.nextInt(2);
              if (l_value == 0) {
                this.d_player.removeCard("Bomb");
                return new Bomb(this.d_player, l_defendingCountry.getOwnerPlayer(), l_defendingCountry.getCountryId());
              } else
                break;
            }
            int l_randomValue;
            if (l_attackingCountry.getNumberOfArmies() > 0)
              l_randomValue = l_random.nextInt(l_attackingCountry.getNumberOfArmies());
            else
              return null;
            if (l_randomValue != 0)
              return new Advance(this.d_player, l_attackingCountry.getCountryId(), l_defendingCountry.getCountryId(),
                  l_randomValue, this.d_defendingCountry.getOwnerPlayer());
            else
              return null;
          } else {
            this.d_gameEngine.getCurrentGamePhase().getGameState().updateLog(
                "Neighbor does not exist for this country: " + l_attackingCountry.getCountryId(),
                "effect");
            System.out.println(Constant.ERROR_COLOR + "Neighbor does not exist for this country :"
                + l_attackingCountry.getCountryId() + Constant.RESET_COLOR);
          }
          return null;
        }

      case 2:
        if (l_attackingCountry != null) {
          if (this.d_player.doesCardExists("Airlift")) {
            Random l_randomCard = new Random();
            int l_value = l_randomCard.nextInt(2);
            if (l_value == 0) {
              this.d_player.removeCard("Airlift");
              return new Airlift(this.d_player, l_attackingCountry.getCountryId(), l_attackingCountry.getCountryId(),
                  this.d_maxArmyCount);
            } else
              break;
          }
          return new Advance(this.d_player, l_attackingCountry.getCountryId(), l_attackingCountry.getCountryId(),
              this.d_maxArmyCount - 1, l_attackingCountry.getOwnerPlayer());
        } else
          return null;
    }
    return null;
  }
}
