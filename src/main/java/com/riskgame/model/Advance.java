package com.riskgame.model;

import com.riskgame.model.Country;
import com.riskgame.model.Player;

/**
 * Class containing logic for implementation of advance order
 *
 */
public class Advance implements Order {

	private boolean status = false;
	private int d_armyCount;
	private String d_sourceCountryId, d_targetCountryId;
	private Player d_attackPlayer, d_targetPlayer;

	/**
	 * Advance.java
	 * Constructor of advance class
	 *
	 * @param p_player          source player who is advancing armies
	 * @param p_sourceCountryId source country Id
	 * @param p_targetCountryId target country Id
	 * @param p_numArmies       number of armies
	 * @param p_targetPlayer    target player on whom advance is to be performed
	 */
	public Advance(Player p_player, String p_sourceCountryId, String p_targetCountryId, int p_armyCount,
			Player p_targetPlayer) {
		this.d_attackPlayer = p_player;
		this.d_sourceCountryId = p_sourceCountryId;
		this.d_targetCountryId = p_targetCountryId;
		this.d_armyCount = p_armyCount;
		this.d_targetPlayer = p_targetPlayer;
	}

	/**
	 * Contain the implementation logic of advance order
	 * 
	 * @param p_gameState The current game state.
	 * @return true if executed successfully else false if it fails
	 */
	@Override
	public boolean execute() {
		// if(this.status){
		// return true;
		// }
		// this.status = true;
		System.out.println("-----------Advance Order Execution inside---------");
		System.out.println("lol d_attackPlayer owns country: "
				+
				this.d_attackPlayer.getOwnedCountries().containsKey(d_targetCountryId.toLowerCase()));

		System.out.println("lol player: " + this.d_attackPlayer.getPlayerName());
		for (Country country : this.d_attackPlayer.getOwnedCountries().values()) {
			System.out
					.println("lol Country: " + country.getCountryId() + " Number of armies: "
							+ country.getNumberOfArmies());
		}

		boolean l_test = this.d_attackPlayer.getOwnedCountries().containsKey(d_targetCountryId.toLowerCase());
		System.out.println("lol l_test: " + l_test);
		if (l_test) {

			System.out.println("lol-in if 1-" +
					this.d_attackPlayer.getOwnedCountries().values());

			// advance logic
			int fromArmies = this.d_attackPlayer.getOwnedCountries().get(this.d_sourceCountryId.toLowerCase())
					.getNumberOfArmies();
			fromArmies -= this.d_armyCount;
			this.d_attackPlayer.getOwnedCountries().get(this.d_sourceCountryId.toLowerCase())
					.setNumberOfArmies(fromArmies);
			int toArmies = this.d_attackPlayer.getOwnedCountries().get(d_targetCountryId.toLowerCase())
					.getNumberOfArmies();
			toArmies += this.d_armyCount;
			this.d_attackPlayer.getOwnedCountries().get(d_targetCountryId.toLowerCase()).setNumberOfArmies(toArmies);
			System.out.println("lol return if 1-");
			return true;
		} else {
			System.out.println(
					"lol-- is not owned by the player " +
							this.d_attackPlayer.getNegotiateList().contains(this.d_targetPlayer));

			if (this.d_attackPlayer.getNegotiateList().contains(this.d_targetPlayer)) {
				System.out.println("lol---negotiate if");
				// skip execute
				return false;
			} else {
				// attack logic
				System.out.println("lol Attack Occur between: " + d_targetCountryId + " and "
						+ this.d_sourceCountryId);

				// fetching the countries and its armies
				Country attackingCountry = this.d_attackPlayer.getOwnedCountries()
						.get(this.d_sourceCountryId.toLowerCase());
				Country defendingCountry = attackingCountry.getNeighbors().get(d_targetCountryId.toLowerCase());

				int l_defendArmy = defendingCountry.getNumberOfArmies();
				System.out.println("lol - defend " + l_defendArmy + " - d_army " +
						this.d_armyCount);

				// if defending country has less armies
				if (l_defendArmy < this.d_armyCount) {

					this.d_attackPlayer.getOwnedCountries().put(d_targetCountryId.toLowerCase(),
							defendingCountry);
					System.out.println(this.d_attackPlayer.getOwnedCountries().values());
					System.out.println("lol-----");
					System.out.println(this.d_targetPlayer.getOwnedCountries().values());
					this.d_targetPlayer.getOwnedCountries().remove(d_targetCountryId.toLowerCase());
					System.out.println(this.d_targetPlayer.getOwnedCountries().values());
					System.out.println(defendingCountry.getNumberOfArmies());
					defendingCountry.setNumberOfArmies(this.d_armyCount - l_defendArmy);
					System.out.println(defendingCountry.getNumberOfArmies());
					System.out.println(attackingCountry.getNumberOfArmies());
					attackingCountry.setNumberOfArmies(
							((this.d_attackPlayer.getOwnedCountries().get(this.d_sourceCountryId.toLowerCase())
									.getNumberOfArmies())
									- this.d_armyCount));
					System.out.println(attackingCountry.getNumberOfArmies());
					// If Attack Successful and new territory added to Player
					// Generate a random Card from {'BOMB', 'AIRLIFT', 'BLOCKADE', 'DIPLOMACY'}
					this.d_attackPlayer.addCard();

				} else if (l_defendArmy == this.d_armyCount) {
					System.out.println("lol---if equal army");

					// this.d_targetPlayer.getOwnedCountries().remove(d_targetCountryId);
					defendingCountry.setNumberOfArmies(0);
					attackingCountry.setNumberOfArmies(
							((this.d_attackPlayer.getOwnedCountries().get(this.d_sourceCountryId.toLowerCase())
									.getNumberOfArmies())
									- this.d_armyCount));
				}
				// if defending coutry has more armies
				else {
					System.out.println("lol--defend army win");

					defendingCountry.setNumberOfArmies(l_defendArmy - this.d_armyCount);
					attackingCountry.setNumberOfArmies(
							((this.d_attackPlayer.getOwnedCountries().get(this.d_sourceCountryId.toLowerCase())
									.getNumberOfArmies())
									- this.d_armyCount));

				}
				System.out.println("lol final return");
				return true;
			}
		}
	}

	// public boolean execute() {
	// if
	// (this.d_attackPlayer.getOwnedCountries().containsKey(d_targetCountryId.toLowerCase()))
	// {
	// System.out.println("lol if 1");
	// // Advance logic
	// Country sourceCountry =
	// this.d_attackPlayer.getOwnedCountries().get(this.d_sourceCountryId.toLowerCase());
	// Country targetCountry =
	// this.d_attackPlayer.getOwnedCountries().get(d_targetCountryId.toLowerCase());

	// int fromArmies = sourceCountry.getNumberOfArmies();
	// fromArmies -= this.d_armyCount;
	// sourceCountry.setNumberOfArmies(fromArmies);

	// int toArmies = targetCountry.getNumberOfArmies();
	// toArmies += this.d_armyCount;
	// targetCountry.setNumberOfArmies(toArmies);

	// return true;
	// } else if
	// (this.d_attackPlayer.getNegotiateList().contains(this.d_targetPlayer)) {
	// System.out.println("lol else if getNegotiateList");
	// // Skip execute
	// return false;
	// } else {
	// System.out.println("lol else");
	// // Attack logic
	// Country attackingCountry =
	// this.d_attackPlayer.getOwnedCountries().get(this.d_sourceCountryId.toLowerCase());
	// Country defendingCountry =
	// attackingCountry.getNeighbors().get(d_targetCountryId.toLowerCase());

	// int defendArmy = defendingCountry.getNumberOfArmies();

	// if (defendArmy < this.d_armyCount) {
	// // If defending country has less armies
	// this.d_attackPlayer.getOwnedCountries().put(d_targetCountryId.toLowerCase(),
	// defendingCountry);
	// this.d_targetPlayer.getOwnedCountries().remove(d_targetCountryId.toLowerCase());

	// defendingCountry.setNumberOfArmies(this.d_armyCount - defendArmy);
	// // attackingCountry.decreaseNumberOfArmies(this.d_armyCount);
	// attackingCountry.setNumberOfArmies(
	// ((this.d_attackPlayer.getOwnedCountries().get(this.d_sourceCountryId.toLowerCase()).getNumberOfArmies())
	// - this.d_armyCount));

	// // If Attack Successful and new territory added to Player
	// // Generate a random Card from {'BOMB', 'AIRLIFT', 'BLOCKADE', 'DIPLOMACY'}
	// this.d_attackPlayer.addCard();
	// } else if (defendArmy == this.d_armyCount) {
	// // If defending country has equal armies
	// defendingCountry.setNumberOfArmies(0);
	// // attackingCountry.decreaseNumberOfArmies(this.d_armyCount);
	// attackingCountry.setNumberOfArmies(
	// ((this.d_attackPlayer.getOwnedCountries().get(this.d_sourceCountryId.toLowerCase()).getNumberOfArmies())
	// - this.d_armyCount));
	// } else {
	// // If defending country has more armies
	// // defendingCountry.decreaseNumberOfArmies(this.d_armyCount);
	// // attackingCountry.decreaseNumberOfArmies(this.d_armyCount);
	// defendingCountry.setNumberOfArmies(defendingCountry.getNumberOfArmies() -
	// this.d_armyCount);
	// attackingCountry.setNumberOfArmies(
	// ((this.d_attackPlayer.getOwnedCountries().get(this.d_sourceCountryId.toLowerCase()).getNumberOfArmies())
	// - this.d_armyCount));

	// }
	// return true;
	// }
	// }

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
		return d_targetCountryId;
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
