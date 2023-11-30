package com.riskgame.model;

import java.io.Serializable;
import java.util.Random;

import com.riskgame.controller.GameEngine;
import com.riskgame.model.Advance;
import com.riskgame.model.Airlift;
import com.riskgame.model.Blockade;
import com.riskgame.model.Bomb;
import com.riskgame.model.Deploy;
import com.riskgame.model.Diplomacy;
import com.riskgame.model.Order;

import com.riskgame.utility.Constant;

/**
 * RandomStrategy class is used to implement Random Strategy
 */
public class RandomStrategy extends PlayerStrategy implements Serializable {
  Random d_random = new Random();
  Country d_randomCountry, d_randomNeighborCountry, d_randomCountryWithArmy;
  Country l_attackingCountry, l_defendingCountry, l_advanceCountry;

  /**
   * Constructor for RandomStrategy
   *
   * @param p_player     Player object
   * @param p_gameEngine GameEngine object
   */
  public RandomStrategy(Player p_player, GameEngine p_gameEngine) {
    super(p_player, p_gameEngine);
    this.d_randomCountry = null;
    this.d_randomNeighborCountry = null;
    this.d_randomCountryWithArmy = null;
  }

  /**
   * Finds random Country to Bomb from map which is not owned by current player
   * 
   * @param p_country Country object
   * 
   * @return Country to deploy bomb on
   */
  private Country findCountryToDeployBomb(Country p_country) {
    Country l_country = null;
    if (this.d_randomCountryWithArmy == null) {
      return null;
    } else {
      for (Country l_neighborCountry : p_country.getNeighbors().values()) {
        if (!this.d_player.getOwnedCountries().containsKey(l_neighborCountry.getCountryId().toLowerCase())) {
          l_country = l_neighborCountry;
          return l_country;
        }
      }
    }
    return null;
  }

  /**
   * Finds random Country to deploy
   *
   * @return Country
   */
  private Country findRandomCountry() {
    this.d_randomCountry = null;
    Object[] l_ownedCountryList = this.d_player.getOwnedCountries().values().toArray();
    int l_totalCount = l_ownedCountryList.length - 1;
    if (this.d_player.getOwnedCountries().size() != 0) {
      Object l_randomCountry = l_ownedCountryList[this.d_random.nextInt(l_totalCount + 1)];
      this.d_randomCountry = (Country) l_randomCountry;
    }
    return this.d_randomCountry;
  }

  /**
   * Finds random Country to perform airlift on
   *
   * @return Country
   */
  private Country findRandomCountryForAirlift() {
    this.d_randomCountry = null;
    if (this.d_randomCountryWithArmy == null) {
      return this.d_randomCountry;
    } else {
      Country l_country = null;
      this.d_randomCountry = this.findRandomCountry();
      if (this.d_randomCountry != this.d_randomCountryWithArmy) {
        l_country = this.d_randomCountry;
      }
      return l_country;
    }
  }

  /**
   * {@inheritDoc}
   */
  protected Country attackToCountry(Country p_country) {
    if (p_country == null) {
      return null;
    } else {
      this.d_randomNeighborCountry = null;
      for (Country l_neighborCountry : p_country.getNeighbors().values()) {
        if (!this.d_player.getOwnedCountries().containsKey(l_neighborCountry.getCountryId())) {
          this.d_randomNeighborCountry = l_neighborCountry;
          return this.d_randomNeighborCountry;
        }
      }
      return this.d_randomNeighborCountry;
    }
  }

  /**
   * This method will find the country to advance.
   * 
   * @param p_country Country object
   * @return Country to advance
   */
  protected Country advanceArmyCountry(Country p_country) {
    if (p_country == null) {
      return null;
    } else {
      this.d_randomNeighborCountry = null;
      for (Country l_neighborCountry : p_country.getNeighbors().values()) {
        if (this.d_player.getOwnedCountries().containsKey(l_neighborCountry.getCountryId().toLowerCase())) {
          this.d_randomNeighborCountry = l_neighborCountry;
          break;
        }
      }
      return this.d_randomNeighborCountry;
    }
  }

  /**
   * {@inheritDoc}
   */
  protected Country moveArmyFromCountry() {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  protected Country attackFromCountry() {
    this.d_randomCountryWithArmy = null;
    int l_maxArmies = 0, l_numArmies;
    if (this.d_player.getOwnedCountries().size() != 0) {
      for (Country l_countryDetails : this.d_player.getOwnedCountries().values()) {
        l_numArmies = l_countryDetails.getNumberOfArmies();
        if (l_numArmies > l_maxArmies) {
          this.d_randomCountryWithArmy = l_countryDetails;
          break;
        }
      }
    }
    return this.d_randomCountryWithArmy;
  }

  /**
   * {@inheritDoc}
   */
  protected Country attackToCountry() {
    return null;
  }

  /**
   * Get random player
   * 
   * @return Player
   */
  protected Player getRandomPlayer() {
    Player l_player = null;
    boolean l_boolean = true;
    while (l_boolean) {
      Object[] l_countryList = this.d_gameEngine.getCurrentGamePhase().getGameState().getGameMap().getCountries()
          .values()
          .toArray();
      Object l_randomValue = l_countryList[this.d_random.nextInt(l_countryList.length)];
      this.d_randomCountry = (Country) l_randomValue;
      if (!this.d_player.getOwnedCountries().containsValue(this.d_randomCountry.getCountryId().toLowerCase())
          && this.d_randomCountry.getOwnerPlayer() != null) {
        l_player = this.d_randomCountry.getOwnerPlayer();
        l_boolean = false;
      }
    }
    return l_player;
  }

  /**
   * {@inheritDoc}
   */
  public Order createOrder() {
    Country l_attackingCountry = this.attackFromCountry();
    Country l_defendingCountry = this.attackToCountry(l_attackingCountry);
    Country l_advanceCountry = this.advanceArmyCountry(l_attackingCountry);

    int l_randomOrder = this.d_random.nextInt(7);
    int l_armyCount = this.d_player.getOwnedArmyCount();
    switch (l_randomOrder) {
      case (0):
        if (this.d_player.getOwnedArmyCount() != 0) {
          this.d_player.setOwnedArmyCount(0);
          Country l_country = this.findRandomCountry();
          if (l_country != null) {
            return new Deploy(this.d_player, l_country.getCountryId(), l_armyCount);
          } else
            return null;
        } else {
          return null;
        }
      case (1):
        if (l_defendingCountry != null)
          return new Advance(this.d_player, l_attackingCountry.getCountryId(), l_defendingCountry.getCountryId(),
              this.d_random.nextInt(l_attackingCountry.getNumberOfArmies()), l_defendingCountry.getOwnerPlayer());
        else {
          this.d_gameEngine.getCurrentGamePhase().getGameState().updateLog(
              "Neighbor does not exist for this country or Source Country doesn't have Armies",
              "effect");
          System.out.println(
              Constant.ERROR_COLOR + "Neighbor does not exist for this country or Source Country doesn't have Armies"
                  + Constant.RESET_COLOR);
        }
        return null;
      case (2):
        if (l_advanceCountry != null)
          return new Advance(this.d_player, l_attackingCountry.getCountryId(), l_advanceCountry.getCountryId(),
              this.d_random.nextInt(l_attackingCountry.getNumberOfArmies()), l_advanceCountry.getOwnerPlayer());
        else {
          this.d_gameEngine.getCurrentGamePhase().getGameState().updateLog(
              "Neighbor does not exist for this country or Source Country doesn't have Armies",
              "effect");
          System.out.println(
              Constant.ERROR_COLOR + "Neighbor does not exist for this country or Source Country doesn't have Armies"
                  + Constant.RESET_COLOR);
        }
        return null;
      case (3):
        if (this.d_player.doesCardExists("Blockade")) {
          this.d_player.removeCard("Blockade");
          return new Blockade(this.d_player, findRandomCountry().getCountryId());
        } else {
          this.d_gameEngine.getCurrentGamePhase().getGameState().updateLog(
              "Player doesn't own Card Blockade",
              "effect");
          System.out.println(Constant.ERROR_COLOR + "Player doesn't own Card Blockade" + Constant.RESET_COLOR);
        }
        return null;
      case (4):
        if (l_attackingCountry != null) {
          Country findOther = findRandomCountryForAirlift();
          if (this.d_player.doesCardExists("Airlift") && findOther != null) {
            this.d_player.removeCard("Airlift");
            return new Airlift(this.d_player, l_attackingCountry.getCountryId(), findOther.getCountryId(),
                this.d_random.nextInt(l_attackingCountry.getNumberOfArmies()));
          }
        } else {
          this.d_gameEngine.getCurrentGamePhase().getGameState().updateLog(
              "Player doesn't own card airlift or source country has no armies to move",
              "effect");
          System.out.println(Constant.ERROR_COLOR
              + "Player doesn't own card airlift or source country has no armies to move" + Constant.RESET_COLOR);
        }
        return null;

      case (5):
        if (this.d_player.doesCardExists("Diplomacy")) {
          this.d_player.removeCard("Diplomacy");
          return new Diplomacy(this.d_player, getRandomPlayer());
        } else {
          this.d_gameEngine.getCurrentGamePhase().getGameState().updateLog(
              "Player doesn't own card diplomacy",
              "effect");
          System.out.println(Constant.ERROR_COLOR + "Player doesn't own card diplomacy" + Constant.RESET_COLOR);
        }
        return null;
      default:
        Country tmp = findCountryToDeployBomb(l_attackingCountry);
        if (this.d_player.doesCardExists("Bomb") && l_attackingCountry != null && tmp != null) {
          this.d_player.removeCard("Bomb");
          return new Bomb(this.d_player, tmp.getOwnerPlayer(), tmp.getCountryId());
        } else {
          this.d_gameEngine.getCurrentGamePhase().getGameState().updateLog(
              "Player doesn't own card Bomb",
              "effect");
          System.out.println(Constant.ERROR_COLOR + "Player doesn't own card Bomb" + Constant.RESET_COLOR);
        }
        return null;
    }
  }
}