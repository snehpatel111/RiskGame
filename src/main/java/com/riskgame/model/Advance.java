package com.riskgame.model;

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
	 * @return true if executed successfully else false if it fails
	 */
	@Override
	public boolean execute() {
		// if(this.status){
		// 	return true;
		// }
		// this.status = true;
		System.out.println("-----------Advance Order Execution inside---------");
		System.out.println("lol--" + this.d_attackPlayer.getOwnedCountries().values());
		if (this.d_attackPlayer.getOwnedCountries().containsKey(d_targetCountryId.toLowerCase())) {

			System.out.println("lol-in if 1-" + this.d_attackPlayer.getOwnedCountries().values());

			// advance logic
			int fromArmies = this.d_attackPlayer.getOwnedCountries().get(d_sourceCountryId.toLowerCase()).getNumberOfArmies();
			fromArmies -= d_armyCount;
			this.d_attackPlayer.getOwnedCountries().get(d_sourceCountryId.toLowerCase()).setNumberOfArmies(fromArmies);
			int toArmies = this.d_attackPlayer.getOwnedCountries().get(d_targetCountryId.toLowerCase()).getNumberOfArmies();
			toArmies += d_armyCount;
			this.d_attackPlayer.getOwnedCountries().get(d_targetCountryId.toLowerCase()).setNumberOfArmies(toArmies);
			return true;

		} else {
			System.out.println("lol-- is not owned by the player");

			if (this.d_attackPlayer.getD_NegotiateList().contains(d_targetPlayer)) {
				System.out.println("lol---negotiate if");
				// skip execute
				return false;
			} else {
				// attack logic
				System.out.println("Attack Occur between: " + d_targetCountryId + " and " + d_sourceCountryId);

				// fetching the countries and its armies
				Country attackingCountry = this.d_attackPlayer.getOwnedCountries().get(d_sourceCountryId.toLowerCase());
				Country defendingCountry = attackingCountry.getNeighbors().get(d_targetCountryId.toLowerCase());

				int defendArmy = defendingCountry.getNumberOfArmies();
				System.out.println("lol - defend " + defendArmy + " - d_army " + d_armyCount);

				// if defending country has less armies
				if (defendArmy < d_armyCount) {

					this.d_attackPlayer.getOwnedCountries().put(d_targetCountryId.toLowerCase(), defendingCountry);
					System.out.println(this.d_attackPlayer.getOwnedCountries().values());
					System.out.println("lol-----");
					System.out.println(d_targetPlayer.getOwnedCountries().values());
					d_targetPlayer.getOwnedCountries().remove(d_targetCountryId.toLowerCase());
					System.out.println(d_targetPlayer.getOwnedCountries().values());
					System.out.println(defendingCountry.getNumberOfArmies());
					defendingCountry.setNumberOfArmies(d_armyCount - defendArmy);
					System.out.println(defendingCountry.getNumberOfArmies());
					System.out.println(attackingCountry.getNumberOfArmies());
					attackingCountry.setNumberOfArmies(
							((this.d_attackPlayer.getOwnedCountries().get(d_sourceCountryId.toLowerCase()).getNumberOfArmies())
									- d_armyCount));
					System.out.println(attackingCountry.getNumberOfArmies());
					// If Attack Successful and new territory added to Player
					// Generate a random Card from {'BOMB', 'AIRLIFT', 'BLOCKADE', 'DIPLOMACY'}
					this.d_attackPlayer.addCard();

				} else if (defendArmy == d_armyCount) {
					System.out.println("lol---if equal army");

					// d_targetPlayer.getOwnedCountries().remove(d_targetCountryId);
					defendingCountry.setNumberOfArmies(0);
					attackingCountry.setNumberOfArmies(
							((this.d_attackPlayer.getOwnedCountries().get(d_sourceCountryId.toLowerCase()).getNumberOfArmies())
									- d_armyCount));
				}
				// if defending coutry has more armies
				else {
					System.out.println("lol--defend army win");

					defendingCountry.setNumberOfArmies(defendArmy - d_armyCount);
					attackingCountry.setNumberOfArmies(
							((this.d_attackPlayer.getOwnedCountries().get(d_sourceCountryId.toLowerCase()).getNumberOfArmies())
									- d_armyCount));

				}
				return true;
			}
		}
	}

	/**
	 * Getter for current player
	 *
	 * @return d_player
	 */
	public Player getD_player() {
		return this.d_attackPlayer;
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
