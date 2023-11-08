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
	public GameState d_gameState;

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
	 * @param p_gameState The current game state.
	 * @return true if executed successfully else false if it fails
	 */
	@Override
	public boolean execute() {
		// if(this.status){
		// return true;
		// }
		// this.status = true;
		if (this.d_attackPlayer == null || this.d_targetPlayer == null) {
			System.out.println("Invalid players specified.");
			return false;
		}

		Country sourceCountry = this.d_attackPlayer.getOwnedCountries().get(this.d_sourceCountryId.toLowerCase());
		Country targetCountry = this.d_attackPlayer.getOwnedCountries().get(this.d_targetCountryId.toLowerCase());
		Country defendingCountry = this.d_targetPlayer.getOwnedCountries().get(this.d_targetCountryId.toLowerCase());
		
		 // Check if the source and target countries exist
		 if (sourceCountry == null || targetCountry == null || defendingCountry == null) {
			System.out.println("Invalid source, target, or defending country specified.");
			return false;
		}
	
		// Check if the source country is owned by the player
		if (!this.d_attackPlayer.getOwnedCountries().containsKey(this.d_sourceCountryId.toLowerCase())) {
			System.out.println("Source country is not owned by the player.");
			return false;
		}
	
		// Check if the target player is in the negotiate list
		if (this.d_attackPlayer.getNegotiatePlayerList().contains(this.d_targetPlayer)) {
			System.out.println("Negotiate order. Skipping execution.");
			return false;
		}

		System.out.println("-----------Advance Order Execution inside---------");

		if (this.d_attackPlayer == null || this.d_targetPlayer == null) {
			System.out.println("Invalid players specified.");
			return false;
		}

		if (this.d_sourceCountryId == null || this.d_targetCountryId == null) {
			System.out.println("Invalid source or target country specified.");
			return false;
		}

		if (this.d_attackPlayer == null || this.d_targetPlayer == null) {
			System.out.println("Invalid players specified.");
			return false;
		}

		

		if (sourceCountry == null || targetCountry == null) {
			System.out.println("Source or target country not found.");
			return false;
		}
	
		// Check if the source country is owned by the player
		if (!this.d_attackPlayer.getOwnedCountries().containsKey(d_targetCountryId.toLowerCase())) {
			System.out.println("Source country is not owned by the player.");
			return false;
		}
	
		if (targetCountry == null || defendingCountry == null) {
			System.out.println("Invalid source or target country specified.");
			return false;
		}

		
		System.out.println("lol d_attackPlayer owns country: "
				+ this.d_attackPlayer.getOwnedCountries().containsKey(this.d_targetCountryId.toLowerCase()));
		if (this.d_attackPlayer.getOwnedCountries().size() == 0) {
			this.d_gameState.updateLog(
					this.d_attackPlayer.getPlayerName() + " does not any country to advance armies",
					"effect");
			System.out.println(Constant.ERROR_COLOR
					+ this.d_attackPlayer.getPlayerName() + " does not any country to advance armies" + Constant.RESET_COLOR);
			return false;
		}
		if (!this.d_attackPlayer.getOwnedCountries().containsKey(this.d_sourceCountryId.toLowerCase())) {
			this.d_gameState.updateLog(
					this.d_attackPlayer.getPlayerName() + " does not own" + this.d_sourceCountryId + " country to advance armies",
					"effect");
			System.out.println(Constant.ERROR_COLOR
					+ this.d_attackPlayer.getPlayerName() + " does not own" + this.d_sourceCountryId
					+ " country to advance armies" + Constant.RESET_COLOR);
			return false;
		}

		System.out.println("lol player: " + this.d_attackPlayer.getPlayerName());
		if (!this.d_attackPlayer.validateTargetCountry(this.d_targetCountryId)) {
			this.d_gameState.updateLog(
					this.d_targetCountryId + " country does not exist on map",
					"effect");
			System.out.println(Constant.ERROR_COLOR
					+ this.d_targetCountryId + " country does not exist on map" + Constant.RESET_COLOR);
			return false;
		}

		if (this.d_attackPlayer.getOwnedCountries().containsKey(this.d_targetCountryId.toLowerCase())) {

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
			this.d_attackPlayer.getOwnedCountries().get(this.d_targetCountryId.toLowerCase()).setNumberOfArmies(toArmies);
			System.out.println("lol return if 1-");
			return true;
		} else {
			System.out.println(
					"lol-- is not owned by the player " +
							this.d_attackPlayer.d_negotiatePlayers.contains(this.d_targetPlayer));

			if (this.d_attackPlayer.d_negotiatePlayers.contains(this.d_targetPlayer)) {
				this.d_gameState.updateLog("You cannot negotiate with yourself", "effect");
				System.out.println(Constant.ERROR_COLOR + "You cannot negotiate with yourself" + Constant.RESET_COLOR);
				return false;
			} else {
				// attack logic
				System.out.println("lol Attack Occur between: " + this.d_targetCountryId + " and "
						+ this.d_sourceCountryId);

				// fetching the countries and its armies
				Country attackingCountry = this.d_attackPlayer.getOwnedCountries()
						.get(this.d_sourceCountryId.toLowerCase());

				int l_defendArmy = defendingCountry.getNumberOfArmies();
				System.out.println("lol - defend " + l_defendArmy + " - d_army " +
						this.d_armyCount);

				// if defending country has less armies
				if (l_defendArmy < this.d_armyCount) {

					this.d_attackPlayer.getOwnedCountries().put(this.d_targetCountryId.toLowerCase(),
							defendingCountry);
					System.out.println(this.d_attackPlayer.getOwnedCountries().values());
					System.out.println("lol-----");
					System.out.println(this.d_targetPlayer.getOwnedCountries().values());
					this.d_targetPlayer.getOwnedCountries().remove(this.d_targetCountryId.toLowerCase());
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

					// this.d_targetPlayer.getOwnedCountries().remove(this.d_targetCountryId);
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
