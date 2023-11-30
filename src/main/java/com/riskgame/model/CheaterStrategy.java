package com.riskgame.model;

import java.io.Serializable;
import java.util.HashMap;

import com.riskgame.controller.GameEngine;
import com.riskgame.model.Order;

import com.riskgame.utility.Constant;

/**
 * CheaterStrategy is a strategy class which is used to implement cheater
 * strategy.
 */
public class CheaterStrategy extends PlayerStrategy implements Serializable {
  /**
   * Represents object of RunGameEngine class
   */
  Player d_cheaterPlayer;

  /**
   * Creates a player with the argument player name and sets default value for
   * rest of the player fields.
   * 
   * @param p_player     Player object
   * @param p_gameEngine GameEngine object
   */
  public CheaterStrategy(Player p_player, GameEngine p_gameEngine) {
    super(p_player, p_gameEngine);
  }

  /**
   * {@inheritDoc}
   */
  public Order createOrder() {
    this.d_player.setOwnedArmyCount(0);

    HashMap<String, Country> l_countryList = new HashMap<String, Country>();
    for (String l_countryId : this.d_player.getOwnedCountries().keySet()) {
      l_countryList.put(l_countryId, this.d_player.getOwnedCountries().get(l_countryId));
    }

    for (Country l_country : l_countryList.values()) {
      for (Country l_neighbors : l_country.getNeighbors().values()) {
        if (!this.d_player.getOwnedCountries().containsKey(l_neighbors.getCountryId().toLowerCase())) {
          this.d_cheaterPlayer = l_neighbors.getOwnerPlayer();
          this.d_player.getOwnedCountries().put(l_neighbors.getCountryId().toLowerCase(), l_neighbors);
          this.d_cheaterPlayer.getOwnedCountries().remove(l_neighbors.getCountryId().toLowerCase());
          this.d_player.addCard();
          l_neighbors.setOwnerPlayer(this.d_player);
          this.d_gameEngine.getCurrentGamePhase().getGameState().updateLog(
              "Cheater has owned country: " + l_neighbors.getCountryId(),
              "effect");
          System.out.println(Constant.SUCCESS_COLOR + "Cheater has owned country: " + l_neighbors.getCountryId()
              + Constant.RESET_COLOR);

        }
      }
    }

    for (Country l_country : this.d_player.getOwnedCountries().values()) {
      for (Country l_neighbors : l_country.getNeighbors().values()) {
        if (!(this.d_player.getOwnedCountries().containsKey(l_neighbors.getCountryId().toLowerCase()))) {
          l_country.setNumberOfArmies(l_country.getNumberOfArmies() * 2);
          this.d_gameEngine.getCurrentGamePhase().getGameState().updateLog(
              "Cheater has doubled army on " + l_country.getCountryId() + " country",
              "effect");
          System.out.println(Constant.SUCCESS_COLOR + "Cheater has doubled army on " + l_country.getCountryId()
              + " country" + Constant.RESET_COLOR);
        }

      }
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  protected Country attackFromCountry() {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  protected Country attackToCountry() {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  protected Country moveArmyFromCountry() {
    return null;
  }

}
