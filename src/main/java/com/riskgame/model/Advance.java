package com.riskgame.model;

/**
 * Class containing logic for implementation of advance order
 *
 */
public class Advance implements Order {

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
		d_attackPlayer = p_player;
		d_sourceCountryId = p_sourceCountryId;
		d_targetCountryId = p_targetCountryId;
		d_armyCount = p_armyCount;
		d_targetPlayer = p_targetPlayer;
	}

	/**
	 * Contain the implementation logic of advance order
	 * 
	 * @return true if executed successfully else false if it fails
	 */
	@Override
	public boolean execute() {
		// if (d_attackPlayer.getOwnedCountries().containsKey(d_targetCountryId.toLowerCase())) {

		// 	// advance logic
		// 	int fromArmies = d_attackPlayer.getOwnedCountries().get(d_sourceCountryId.toLowerCase()).getNumberOfArmies();
		// 	fromArmies -= d_armyCount;
		// 	d_attackPlayer.getOwnedCountries().get(d_sourceCountryId.toLowerCase()).setNumberOfArmies(fromArmies);
		// 	int toArmies = d_attackPlayer.getOwnedCountries().get(d_targetCountryId.toLowerCase()).getNumberOfArmies();
		// 	toArmies += d_armyCount;
		// 	d_attackPlayer.getOwnedCountries().get(d_targetCountryId.toLowerCase()).setNumberOfArmies(toArmies);
		// 	return true;

		// } else {
		// 	// System.out.println(d_targetCountryId + " is not owned by the player");

		// 	if (d_attackPlayer.d_negotiatePlayers.contains(d_targetPlayer)) {
		// 		// skip execute
		// 		return false;
		// 	} else {
		// 		// attack logic
		// 		System.out.println("Attack Occur between: " + d_targetCountryId + " and " + d_sourceCountryId);

		// 		// fetching the countries and its armies
		// 		Country attackingCountry = d_attackPlayer.getOwnedCountries().get(d_sourceCountryId.toLowerCase());
		// 		Country defendingCountry = attackingCountry.getNeighbours().get(d_targetCountryId.toLowerCase());

		// 		int defendArmy = defendingCountry.getNumberOfArmies();

		// 		// if defending country has less armies
		// 		if (defendArmy < d_armyCount) {
		// 			d_attackPlayer.getOwnedCountries().put(d_targetCountryId, defendingCountry);
		// 			d_targetPlayer.getOwnedCountries().remove(d_targetCountryId);
		// 			defendingCountry.setNumberOfArmies(d_armyCount - defendArmy);
		// 			attackingCountry.setNumberOfArmies(
		// 					((d_attackPlayer.getOwnedCountries().get(d_sourceCountryId.toLowerCase()).getNumberOfArmies())
		// 							- d_armyCount));
		// 			// If Attack Successful and new territory added to Player
		// 			// Generate a random Card from {'BOMB', 'AIRLIFT', 'BLOCKADE', 'DIPLOMACY'}
		// 			d_attackPlayer.addCard();

		// 		} else if (defendArmy == d_armyCount) {
		// 			d_targetPlayer.getOwnedCountries().remove(d_targetCountryId);
		// 			defendingCountry.setNumberOfArmies(0);
		// 			attackingCountry.setNumberOfArmies(
		// 					((d_attackPlayer.getOwnedCountries().get(d_sourceCountryId.toLowerCase()).getNumberOfArmies())
		// 							- d_armyCount));
		// 		}
		// 		// if defending coutry has more armies
		// 		else {
		// 			defendingCountry.setNumberOfArmies(defendArmy - d_armyCount);
		// 			attackingCountry.setNumberOfArmies(
		// 					((d_attackPlayer.getOwnedCountries().get(d_sourceCountryId.toLowerCase()).getNumberOfArmies())
		// 							- d_armyCount));

		// 		}
		// 		return true;
		// 	}
		// }
		return false;
	}

	/**
	 * Getter for current player
	 *
	 * @return d_player
	 */
	public Player getD_player() {
		return d_attackPlayer;
	}

	/**
	 * Setter for current player
	 *
	 * @param p_player player
	 */
	public void setD_player(Player p_player) {
		this.d_attackPlayer = p_player;
	}

	/**
	 * Getter for ID of Source Country
	 *
	 * @return d_sourceCountryId
	 */
	public String getD_sourceCountryId() {
		return d_sourceCountryId;
	}

	/**
	 * Setter for ID of source Country
	 *
	 * @param p_sourceCountryId source country ID
	 */
	public void setD_sourceCountryId(String p_sourceCountryId) {
		this.d_sourceCountryId = p_sourceCountryId;
	}

	/**
	 * Getter for ID of Target Country
	 *
	 * @return d_targetCountryId
	 */
	public String getD_targetCountryId() {
		return d_targetCountryId;
	}

	/**
	 * Setter for ID of Target Country
	 *
	 * @param p_targetCountryId country ID
	 */
	public void setD_targetCountryId(String p_targetCountryId) {
		this.d_sourceCountryId = p_targetCountryId;
	}

	/**
	 * Setter for number of armies
	 *
	 * @return d_armyCount
	 */
	public int getD_numArmies() {
		return d_armyCount;
	}

	/**
	 * Setter for number of armies
	 *
	 * @param p_armyCount number of armies
	 */
	public void setD_numArmies(int p_armyCount) {
		this.d_armyCount = p_armyCount;
	}

}
