package com.riskgame.model;

import com.riskgame.model.Country;
import com.riskgame.model.Player;
import com.riskgame.utility.Constant;

/**
 * Class containing logic for implementation of advance order
 *
 */
public class Advance implements Order {

	private int d_armyCount;
	private String d_sourceCountryId, d_targetCountryId;
	private Player d_attackPlayer, d_targetPlayer;

	/**
	 * Game state object which will be used to store game state.
	 */
	public GameState d_gameState;

	/**
	 * Advance.java
	 * Constructor of advance class
	 *
	 * @param p_player          source player who is advancing armies
	 * @param p_sourceCountryId source country Id
	 * @param p_targetCountryId target country Id
	 * @param p_armyCount       number of armies
	 * @param p_targetPlayer    target player on whom advance is to be performed
	 */
	public Advance(Player p_player, String p_sourceCountryId, String p_targetCountryId, int p_armyCount,
			Player p_targetPlayer) {
		this.d_attackPlayer = p_player;
		this.d_sourceCountryId = p_sourceCountryId;
		this.d_targetCountryId = p_targetCountryId;
		this.d_armyCount = p_armyCount;
		this.d_targetPlayer = p_targetPlayer;
		this.d_gameState = new GameState();
	}

	/**
	 * Set game state.
	 */
	public void setGameState(GameState p_gameState) {
		this.d_gameState = p_gameState;
	}

	/**
	 * Contain the implementation logic of advance order
	 * 
	 * @return true if executed successfully else false if it fails
	 */
	@Override
	public boolean execute() {
		if (this.d_attackPlayer.getOwnedCountries().size() == 0) {
			this.d_gameState.updateLog(
					this.d_attackPlayer.getPlayerName() + " does not have any country to advance armies",
					"effect");
			System.out.println(Constant.ERROR_COLOR
					+ this.d_attackPlayer.getPlayerName() + " does not any country to advance armies"
					+ Constant.RESET_COLOR);
			return false;
		}
		if (!this.d_attackPlayer.getOwnedCountries().containsKey(this.d_sourceCountryId.toLowerCase())) {
			this.d_gameState.updateLog(
					this.d_attackPlayer.getPlayerName() + " does not own" + this.d_sourceCountryId
							+ " country to advance armies",
					"effect");
			System.out.println(Constant.ERROR_COLOR
					+ this.d_attackPlayer.getPlayerName() + " does not own" + this.d_sourceCountryId
					+ " country to advance armies" + Constant.RESET_COLOR);
			return false;
		}

		if (!this.d_attackPlayer.validateTargetCountry(this.d_targetCountryId)) {
			this.d_gameState.updateLog(
					this.d_targetCountryId + " country does not exist on map",
					"effect");
			System.out.println(Constant.ERROR_COLOR
					+ this.d_targetCountryId + " country does not exist on map" + Constant.RESET_COLOR);
			return false;
		}
		if (this.d_targetPlayer.getPlayerName().equalsIgnoreCase("neutral")) {
			Country attackingCountry = this.d_attackPlayer.getOwnedCountries()
					.get(this.d_sourceCountryId.toLowerCase());
			Country defendingCountry = attackingCountry.getNeighbors().get(d_targetCountryId.toLowerCase());
			this.d_attackPlayer.getOwnedCountries().put(this.d_targetCountryId.toLowerCase(), defendingCountry);
			defendingCountry.setOwnerPlayer(this.d_attackPlayer);
			int l_defendArmy = defendingCountry.getNumberOfArmies();
			if (this.d_armyCount > l_defendArmy) {
				this.d_attackPlayer.getOwnedCountries().put(this.d_targetCountryId.toLowerCase(),
						defendingCountry);
				defendingCountry.setOwnerPlayer(this.d_attackPlayer);
				defendingCountry.setNumberOfArmies(this.d_armyCount - l_defendArmy);
				attackingCountry.setNumberOfArmies(
						((this.d_attackPlayer.getOwnedCountries().get(this.d_sourceCountryId.toLowerCase())
								.getNumberOfArmies())
								- this.d_armyCount));
				this.d_attackPlayer.addCard();
			} else if (this.d_armyCount == l_defendArmy) {
				defendingCountry.setNumberOfArmies(0);
				attackingCountry.setNumberOfArmies(
						((this.d_attackPlayer.getOwnedCountries().get(this.d_sourceCountryId.toLowerCase())
								.getNumberOfArmies())
								- this.d_armyCount));
			} else {
				defendingCountry.setNumberOfArmies(l_defendArmy - this.d_armyCount);
				attackingCountry.setNumberOfArmies(
						((this.d_attackPlayer.getOwnedCountries().get(this.d_sourceCountryId.toLowerCase())
								.getNumberOfArmies())
								- this.d_armyCount));
			}
			return true;
		} else {
			if (this.d_attackPlayer.getOwnedCountries().containsKey(this.d_targetCountryId.toLowerCase())) {
				int fromArmies = this.d_attackPlayer.getOwnedCountries().get(this.d_sourceCountryId.toLowerCase())
						.getNumberOfArmies();
				fromArmies -= this.d_armyCount;
				this.d_attackPlayer.getOwnedCountries().get(this.d_sourceCountryId.toLowerCase())
						.setNumberOfArmies(fromArmies);
				int toArmies = this.d_attackPlayer.getOwnedCountries().get(d_targetCountryId.toLowerCase())
						.getNumberOfArmies();
				toArmies += this.d_armyCount;
				this.d_attackPlayer.getOwnedCountries().get(this.d_targetCountryId.toLowerCase())
						.setNumberOfArmies(toArmies);
				return true;
			} else {
				if (this.d_attackPlayer.d_negotiatePlayers != null
						&& this.d_attackPlayer.d_negotiatePlayers.contains(this.d_targetPlayer)) {
					this.d_gameState.updateLog(this.d_attackPlayer.getPlayerName() + " has negotiated with "
							+ this.d_targetPlayer.getPlayerName(), "effect");
					System.out.println(
							Constant.ERROR_COLOR + this.d_attackPlayer.getPlayerName() + " has negotiated with "
									+ this.d_targetPlayer.getPlayerName()
									+ Constant.RESET_COLOR);
					this.d_attackPlayer.d_negotiatePlayers.remove(this.d_targetPlayer);
					return false;
				} else {
					Country attackingCountry = this.d_attackPlayer.getOwnedCountries()
							.get(this.d_sourceCountryId.toLowerCase());
					Country defendingCountry = attackingCountry.getNeighbors().get(d_targetCountryId.toLowerCase());

					int l_defendArmy = defendingCountry.getNumberOfArmies();
					if (l_defendArmy < this.d_armyCount) {

						this.d_attackPlayer.getOwnedCountries().put(this.d_targetCountryId.toLowerCase(),
								defendingCountry);
						defendingCountry.setOwnerPlayer(this.d_attackPlayer);
						this.d_targetPlayer.getOwnedCountries().remove(this.d_targetCountryId.toLowerCase());
						defendingCountry.setNumberOfArmies(this.d_armyCount - l_defendArmy);
						attackingCountry.setNumberOfArmies(
								((this.d_attackPlayer.getOwnedCountries().get(this.d_sourceCountryId.toLowerCase())
										.getNumberOfArmies())
										- this.d_armyCount));
						this.d_attackPlayer.addCard();

					} else if (l_defendArmy == this.d_armyCount) {
						this.d_targetPlayer.getOwnedCountries().remove(this.d_targetCountryId.toLowerCase());
						defendingCountry.setNumberOfArmies(0);
						attackingCountry.setNumberOfArmies(
								((this.d_attackPlayer.getOwnedCountries().get(this.d_sourceCountryId.toLowerCase())
										.getNumberOfArmies())
										- this.d_armyCount));
					} else {
						defendingCountry.setNumberOfArmies(l_defendArmy - this.d_armyCount);
						attackingCountry.setNumberOfArmies(
								((this.d_attackPlayer.getOwnedCountries().get(this.d_sourceCountryId.toLowerCase())
										.getNumberOfArmies())
										- this.d_armyCount));

					}
					return true;
				}
			}
		}

	}

	/**
	 * Getter for current player
	 *
	 * @return d_player
	 */
	public Player getPlayer() {
		return this.d_attackPlayer;
	}

	/**
	 * Setter for current player
	 *
	 * @param p_player player
	 */
	public void setAttackPlayer(Player p_player) {
		this.d_attackPlayer = p_player;
	}

	/**
	 * Getter for ID of Source Country
	 *
	 * @return d_sourceCountryId
	 */
	public String getSourceCountryId() {
		return this.d_sourceCountryId;
	}

	/**
	 * Setter for ID of source Country
	 *
	 * @param p_sourceCountryId source country ID
	 */
	public void setSourceCountryId(String p_sourceCountryId) {
		this.d_sourceCountryId = p_sourceCountryId;
	}

	/**
	 * Getter for ID of Target Country
	 *
	 * @return d_targetCountryId
	 */
	public String getTargetCountryId() {
		return this.d_targetCountryId;
	}

	/**
	 * Setter for ID of Target Country
	 *
	 * @param p_targetCountryId country ID
	 */
	public void setTargetCountryId(String p_targetCountryId) {
		this.d_sourceCountryId = p_targetCountryId;
	}

	/**
	 * Setter for number of armies
	 *
	 * @return d_armyCount
	 */
	public int getNumArmies() {
		return this.d_armyCount;
	}

	/**
	 * Setter for number of armies
	 *
	 * @param p_armyCount number of armies
	 */
	public void setNumArmies(int p_armyCount) {
		this.d_armyCount = p_armyCount;
	}

}
