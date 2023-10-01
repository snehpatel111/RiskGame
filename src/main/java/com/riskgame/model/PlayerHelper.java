package com.riskgame.model;

import java.util.ArrayList;

/**
 * Implementation of PlayerHelper class
 * It includes the functionalities like assigncountries, assignreinforcements and showmap.
 */
public class PlayerHelper {
    
    /**
	 * This function destributes countries among the total number of players.
	 * @param p_mapFileGameMap Game map
	 * @param p_playersList List of players in the game
	 * @return true if successful, else false
	 */
    public boolean assignCountries(GameMap p_mapFilGameMap, ArrayList<Player> p_playersList){
        int l_numberOfPlayers = p_playersList.size();
		if(l_numberOfPlayers < 2) {
			System.out.println("Minimum two players are required to play the game.");
			return false;
		}
		int l_playerCounter = 0;
		for(Country l_countryIterator : p_mapFilGameMap.getCountries().values()) {
			Player l_p = p_playersList.get(l_playerCounter);
			l_p.getOwnedCountries().put(l_countryIterator.getCountryId().toLowerCase(), l_countryIterator);
			if(l_playerCounter>=l_numberOfPlayers-1)
				l_playerCounter = 0;
			else
				l_playerCounter++;
		}
        return true;
    }

    /**
     * Method assigns to each player the correct number of reinforcement armies according to the Warzone rules.
     * @param p_player current player
     */
    public static void assignReinforcementArmies(Player p_player){
        int l_totalControlValue = 0;
        int l_totalReinforcementArmies;
        if(p_player.getOwnedCountries().size() >= 9){
            if(p_player.getOwnedContinents().size()> 0){
                for(Continent l_c:p_player.getOwnedContinents().values()){
                    l_totalControlValue += l_c.getControlValue();
                }
                l_totalReinforcementArmies = (int)(p_player.getOwnedCountries().size()/3) + l_totalControlValue;
            }
            else{
                l_totalReinforcementArmies = (int)(p_player.getOwnedCountries().size()/3);
            }
        }
        else{
            l_totalReinforcementArmies = 3;
        }
        p_player.setOwnedArmies(l_totalReinforcementArmies);
    }

    /**
	 * Shows map with along with Owner and Army units.
	 * @param p_players List of players in the game
	 * @param p_map Game map
	 */
	public void showMap(ArrayList<Player> p_players, GameMap p_map) {
		if(p_map==null)
			return;
		if(p_players.size()==0 || p_players.get(0).getOwnedCountries().size()==0) {
			MapHelper l_mapHelper = new MapHelper();
			l_mapHelper.showMap(p_map);
			return;
		}
		System.out.format("%25s%25s%35s%25s%10s\n", "Owner", "Country", "Neighbors", "Continent", "#Armies");
		System.out.format("%85s\n", "---------------------------------------------------------------------------------------------------------------------------");
		boolean l_printPlayerName = true;
		boolean l_printContinentId = true;
		boolean l_printCountryId = true;
		boolean l_printNumberOfArmies = true;

		for(int i=0; i<p_players.size(); i++){
			Player l_p = p_players.get(i);
			for(Country l_country : l_p.getOwnedCountries().values()) {
				for(Country l_neighbor : l_country.getNeighbors().values()) {
					if(l_printPlayerName && l_printContinentId && l_printCountryId) {
						System.out.format("\n%25s%25s%35s%25s%10d\n", l_p.getPlayerName(), l_country.getCountryId(), l_neighbor.getCountryId(), l_country.getBelongingContinent(), l_country.getNumberOfArmies());
						l_printPlayerName = false;
						l_printContinentId = false;
						l_printCountryId = false;
						l_printNumberOfArmies = false;
					}
					else if(l_printContinentId && l_printCountryId && l_printNumberOfArmies) {
						System.out.format("\n%25s%25s%35s%25s%10d\n", "", l_country.getCountryId(), l_neighbor.getCountryId(), l_country.getBelongingContinent(), l_country.getNumberOfArmies());
						l_printPlayerName = false;
						l_printCountryId = false;
						l_printNumberOfArmies = false;
					}
					else {
						System.out.format("\n%25s%25s%35s%25s%10s\n", "", "", l_neighbor.getCountryId(), "", "");
					}
				}
				l_printContinentId = true;
				l_printCountryId = true;
				l_printNumberOfArmies = true;
			}
			l_printPlayerName = true;
			l_printContinentId = true;
			l_printCountryId = true;
			l_printNumberOfArmies = true;
		}
	}

    public void showPlayers(ArrayList<Player> p_playerList){
        System.out.println("Showing players:");
    }

}
