package com.riskgame.model;

import java.util.Collection;
import java.util.Random;

import com.riskgame.controller.GameEngine;
import com.riskgame.model.Advance;
import com.riskgame.model.Deploy;
import com.riskgame.model.Order;

import com.riskgame.utility.Constant;

/**
 * This class is used to implement Benevolent strategy.
 */
public class BenevolentStrategy extends PlayerStrategy {

  Country d_sourceCountry, d_weakCountry;
  Random d_random = new Random();

  /**
   * Constructor for BenevolentStrategy
   *
   * @param p_player     Player object
   * @param p_gameEngine GameEngine object
   */
  public BenevolentStrategy(Player p_player, GameEngine p_gameEngine) {
    super(p_player, p_gameEngine);
    this.d_sourceCountry = null;
    this.d_weakCountry = null;
  }

  /**
   * Set the weakest country with least number of armies
   * 
   */
  private void setWeakestCountry() {
    Collection<Country> l_countryList = this.d_player.getOwnedCountries().values();
    int l_minArmyCount = 100;
    for (Country l_country : l_countryList) {
      int l_numArmies = l_country.getNumberOfArmies();
      if (l_numArmies < l_minArmyCount) {
        l_minArmyCount = l_numArmies;
        this.d_weakCountry = l_country;
      }
    }
    if (this.d_weakCountry == null) {
      this.d_weakCountry = this.d_player.getOwnedCountries().get(0);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Country moveArmyFromCountry() {
    Object[] values = this.d_player.getOwnedCountries().values().toArray();
    int totalCount = values.length - 1;
    if (this.d_player.getOwnedCountries().size() != 0) {
      Object l_randomValue = values[d_random.nextInt(totalCount + 1)];
      this.d_sourceCountry = (Country) l_randomValue;
    }
    return this.d_sourceCountry;
  }

  /**
   * This method will find the country to advance.
   * 
   * @return Country to advance
   */
  protected Country advanceArmyCountry() {
    if (this.d_sourceCountry != null) {
      this.setWeakestCountry();
      for (Country l_neighborCountry : this.d_sourceCountry.getNeighbors().values()) {
        if (this.d_player.getOwnedCountries().containsKey(l_neighborCountry.getCountryId().toLowerCase())
            && (l_neighborCountry == this.d_weakCountry)) {
          return this.d_weakCountry;
        }
      }
    }
    return null;
  }

  /**
   * {@inheritDoc}
   * 
   */
  @Override
  public Order createOrder() {
    Country l_sourceCountry, l_advanceCountry;
    l_sourceCountry = this.moveArmyFromCountry();
    l_advanceCountry = this.advanceArmyCountry();

    int l_randomOrder = this.d_random.nextInt(2);
    int l_armyCount = this.d_player.getOwnedArmyCount();
    this.setWeakestCountry();
    Country l_country = this.d_weakCountry;

    switch (l_randomOrder) {
      case 0:
        if (l_country != null) {
          this.d_player.setOwnedArmyCount(0);
          return new Deploy(this.d_player, l_country.getCountryId(), l_armyCount);
        } else {
          this.d_gameEngine.getCurrentGamePhase().getGameState().updateLog(
              "Cannot be deployed on weak country",
              "effect");
          System.out.println(Constant.ERROR_COLOR + "Cannot be deployed on weak country" + Constant.RESET_COLOR);
        }
        break;
      case 1:
        if (l_sourceCountry != null) {
          if (l_sourceCountry.getNumberOfArmies() == 0) {
            this.d_gameEngine.getCurrentGamePhase().getGameState().updateLog(
                "The number of armies in strongest country is 0, deploy before advance",
                "effect");
            System.out.println(Constant.ERROR_COLOR
                + "The number of armies in strongest country is 0, deploy before advance" + Constant.RESET_COLOR);
            return null;
          }
          if (l_advanceCountry != null) {
            int l_randomValue;
            if (l_sourceCountry.getNumberOfArmies() > 0)
              l_randomValue = this.d_random.nextInt(l_sourceCountry.getNumberOfArmies());
            else
              return null;

            if (l_randomValue != 0)
              return new Advance(this.d_player, l_sourceCountry.getCountryId(), l_advanceCountry.getCountryId(),
                  l_randomValue,
                  l_advanceCountry.getOwnerPlayer());
            else {
              this.d_gameEngine.getCurrentGamePhase().getGameState().updateLog(
                  "Neighbor does not exist for country " + l_sourceCountry.getCountryId(),
                  "effect");
              System.out.println(Constant.ERROR_COLOR + "Neighbor does not exist for country "
                  + l_sourceCountry.getCountryId() + Constant.RESET_COLOR);
            }
            break;
          }
        } else {
          return null;
        }
    }
    return null;
  }

  @Override
  protected Country attackFromCountry() {
    return null;
  }

  @Override
  protected Country attackToCountry() {
    return null;
  }
}
