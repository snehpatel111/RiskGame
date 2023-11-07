package com.riskgame.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import com.riskgame.utility.Phase;
import com.riskgame.utility.Constant;
import com.riskgame.model.Order;
import com.riskgame.model.StartUpPhase;

/**
 * Represents a player in the game.
 */
public class Player {
    private String d_playerName;
    private HashMap<String, Continent> d_ownedContinents;
    private HashMap<String, Country> d_ownedCountries;
    private int d_ownedArmyCount;
    private int d_remainingArmyCount;
    private String d_countryId;
    private Order d_order;
    private Queue<Order> d_executionOrderList;
    ArrayList<Player> d_negotiatePlayers;
    ArrayList<Card> d_ownedCards;
    public String[] d_args;

    /**
     * This constructor assigns name to the player.
     *
     * @param p_playerName name of the player
     */
    public Player(String p_playerName) {
        this.d_playerName = p_playerName;
        this.d_ownedContinents = new HashMap<>();
        this.d_ownedCountries = new HashMap<>();
        this.d_ownedArmyCount = 0;
        this.d_executionOrderList = new ArrayDeque<>();
    }

    /**
     * Setter method to set command line arguments.
     * 
     * @param p_args Command line arguments separated by space
     */
    public void setArgs(String[] p_args) {
        this.d_args = p_args;
    }
    

     /**
     * Getter method for Negotiators
     * @return d_NegotiateList
     */
    public ArrayList<Player> getD_NegotiateList() {
        return d_negotiatePlayers;
    }

    /**
     * Adds Player to Negotiator list
     * @param p_player target player to be added
     */
    void addPlayerToNegotiateList(Player p_player) {
        d_negotiatePlayers.add(p_player);
    }

    /**
     * flush lists after Turn
     */
    public void flushNegotiators() {
        d_negotiatePlayers.clear();
    }

    /**
     * Returns the player name
     * 
     * @return The player name
     */
    public String getPlayerName() {
        return this.d_playerName;
    }

    /**
     * Set the name of the player
     * 
     * @param p_playerName Name of the player
     */
    public void setPlayerName(String p_playerName) {
        this.d_playerName = p_playerName;
    }

    public void addOwnedCountry(Country country) {
        d_ownedCountries.put(country.getCountryId(), country);
    }

    /**
     * Getter method to return the countries owned by a player.
     * 
     * @return The countries owned by the player
     */
    public HashMap<String, Country> getOwnedCountries() {
        return this.d_ownedCountries;
    }

    /**
     * Seta the countries owned by the player
     * 
     * @param p_countries Countries owned by player
     */
    public void setOwnedCountries(HashMap<String, Country> p_countries) {
        this.d_ownedCountries = p_countries;
    }

    /**
     * Getter method to return the continents owned by a player.
     * 
     * @return The continents owned by the player
     */
    public HashMap<String, Continent> getOwnedContinents() {
        return this.d_ownedContinents;
    }

    /**
     * Setter method to set the continents owned by the player
     * 
     * @param p_continents Continents owned by player
     */
    public void setOwnedContinents(HashMap<String, Continent> p_continents) {
        this.d_ownedContinents = p_continents;
    }

    /**
     * Getter method to return number of armies owned by player
     * 
     * @return Number of armies owned
     */
    public int getOwnedArmyCount() {
        return this.d_ownedArmyCount;
    }

    /**
     * Setter method to set number of armies owned by player
     * 
     * @param p_ownedArmyCount Number of armies owned
     */
    public void setOwnedArmyCount(int p_ownedArmyCount) {
        this.d_ownedArmyCount = p_ownedArmyCount;
    }

    /**
     * This function sets the created Object to Players Object
     *
     * @param p_order created Order
     */
    public void addOrder(Order p_order) {
        this.d_order = p_order;
    }

    /**
     * Getter method to get the list of execution orders
     * 
     * @return List of execution order
     */
    public Queue<Order> getExecutionOrderList() {
        return this.d_executionOrderList;
    }


    /**
     * If a player conquers a territory,a card will be added to that player
     */
    public void addCard() {
        Card l_card = new Card();
        l_card.createCard();
        d_ownedCards.add(l_card);
    }

    /**
     * Added a clone of addCard inorder to test custom cards
     * @param test custom card
     */
    public void addCard(String test){
        Card l_card = new Card();
        l_card.createCard(test);
        d_ownedCards.add(l_card);
    }

    /**
     * If a player uses a card,it will be removed from deck of cards.
     * @param p_card String representation of card to be used
     */
    public void removeCard(String p_card)
    {
        //remove card from deck
        Iterator l_iter = d_ownedCards.iterator();
        while (l_iter.hasNext()) {
            Card l_card = (Card) l_iter.next();
            if (l_card.getCardType() == p_card) {
                d_ownedCards.remove(l_card);
                break;
            }
        }
    }

    /**
     * Before using a card, we can check if player has that card
     * @param p_card String representation of card to be used
     * @return true if card exists else false
     */
    public boolean doesCardExists(String p_card) {

        int l_existsCount = 0;
        Iterator l_iter = d_ownedCards.iterator();
        while (l_iter.hasNext()) {
            Card l_card = (Card) l_iter.next();
            if (l_card.getCardType() == p_card)
                l_existsCount++;
        }
        if(l_existsCount>0)
            return true;
        else
            return false;
    }

    /**
     * show the particular card owned by player
     */
    public void showCards()
    {
        Iterator l_iter = d_ownedCards.iterator();
        System.out.println("Player "+this.getPlayerName()+" owns:");
        while (l_iter.hasNext()) {
            Card l_card = (Card) l_iter.next();
            System.out.print(l_card.getCardType() + ",");
        }

    }

    /**
     * Getter for Player's Cards Deck
     * @return d_ownedCards
     */
    public ArrayList<Card> getD_Deck() {
        return d_ownedCards;
    }

    /**
     * Validates player name.
     * 
     * @param p_playerName Player name to validate
     * @return Return true if name is valid, false otherwise
     */
    public boolean isValidPlayerName(String p_playerName) {
        return p_playerName != null && p_playerName.matches("[a-zA-Z0-9]+");
    }

    /**
     * Manage player related operations based on input arguments.
     * 
     * @param p_playerList List of all players
     * @param p_gamPhase   Current game phase
     * @param p_args       Command line arguments
     */
    public void managePlayer(ArrayList<Player> p_playerList, Phase p_gamPhase, String[] p_args) {
        try {
            String l_playerName = p_args[2];
            if (p_args[1].equals("-add")) {
                if (this.isValidPlayerName(l_playerName)) {
                    boolean l_isPlayerAdded = this.addPlayer(p_playerList, l_playerName);
                    if (l_isPlayerAdded) {
                        System.out
                                .println(Constant.SUCCESS_COLOR + "Player " + l_playerName + " added successfully!"
                                        + Constant.RESET_COLOR);
                        p_gamPhase = Phase.STARTUP;
                    }
                    p_gamPhase = Phase.STARTUP;
                } else {
                    System.out.println(Constant.ERROR_COLOR + "Invalid player name" + Constant.RESET_COLOR);
                }
            } else if (p_args[1].equals("-remove")) {
                if (this.isValidPlayerName(l_playerName)) {
                    boolean l_isPlayerRemoved = this.removePlayer(p_playerList, l_playerName);
                    if (l_isPlayerRemoved)
                        System.out.println(
                                Constant.SUCCESS_COLOR + "Player removed successfully!" + Constant.RESET_COLOR);
                    else
                        System.out.println(Constant.ERROR_COLOR + "Player doesn't exist" + Constant.RESET_COLOR);
                    p_gamPhase = Phase.STARTUP;
                } else
                    System.out.println(Constant.ERROR_COLOR + "Invalid player name" + Constant.RESET_COLOR);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(Constant.ERROR_COLOR
                    + "Invalid command - it should be of the form gameplayer -add playername or gameplayer -remove playername"
                    + Constant.RESET_COLOR);
        } catch (Exception e) {
            System.out.println(Constant.ERROR_COLOR
                    + "Invalid command - it should be of the form gameplayer -add playername or gameplayer -remove playername"
                    + Constant.RESET_COLOR);
        }
    }

    /**
     * Adds a player to the game
     * 
     * @param p_playerList List of existing players
     * @param p_playerName Name of player to add
     * @return Return true if player added successfully, false otherwise
     */
    public boolean addPlayer(ArrayList<Player> p_playerList, String p_playerName) {
        if (p_playerList.size() == 6) {
            System.out.println(Constant.ERROR_COLOR + "Max number of players (6) reached!" + Constant.RESET_COLOR);
            return false;
        }
        if (this.isPlayerExist(p_playerList, p_playerName)) {
            System.out.println(Constant.ERROR_COLOR + "Player already exists!" + Constant.RESET_COLOR);
            return false;
        }
        p_playerList.add(new Player(p_playerName));
        return true;
    }

    /**
     * Removes a player from the game.
     * 
     * @param p_playerList List of existing players
     * @param p_playerName Name of player to remove
     * @return Returns true if player removed successfully, false otherwise
     */
    public boolean removePlayer(ArrayList<Player> p_playerList, String p_playerName) {
        Iterator<Player> l_iterator = p_playerList.listIterator();
        while (l_iterator.hasNext()) {
            Player l_player = l_iterator.next();
            if (l_player.getPlayerName().equals(p_playerName)) {
                p_playerList.remove(l_player);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a player exists in the given player list
     * 
     * @param p_playerList List of existing players
     * @param p_playerName Name of player to check
     * @return Returns true if player exists, false otherwise
     */
    public boolean isPlayerExist(ArrayList<Player> p_playerList, String p_playerName) {
        for (Player player : p_playerList) {
            if (player.getPlayerName().equals(p_playerName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This function returns the order object from queue
     *
     * @return Returns first order from the queue
     */
    public Order next_order() {
        return this.d_executionOrderList.poll();
    }

    /**
     * Randomly assigns countries to players from the game map and player list
     * 
     * @param p_gameMap    The game map containing countries
     * @param p_playerList The list of players
     * 
     * @return Returns true if countries assigned successfully to players, false
     *         otherwise
     */
    public static boolean assignCountries(GameMap p_gameMap, List<Player> p_playerList) {
        try {
            if (p_playerList.size() < 2) {
                System.out.println(Constant.ERROR_COLOR + "Minimum two players are required to play the game."
                        + Constant.RESET_COLOR);
                return false;
            }
            Collections.shuffle(p_playerList);
            for (Country l_country : p_gameMap.getCountries().values()) {
                int l_randomIndex = new Random().nextInt(p_playerList.size());
                Player l_randomPlayer = p_playerList.get(l_randomIndex);
                l_randomPlayer.getOwnedCountries().put(l_country.getCountryId().toLowerCase(), l_country);
            }
            System.out.println(
                    Constant.SUCCESS_COLOR + "Countries assigned randomly to all players!" + Constant.RESET_COLOR);
            MapHelper l_gameMap = new MapHelper();
            l_gameMap.showMap(p_playerList, p_gameMap);
            System.out.println(
                    Constant.SUCCESS_COLOR
                            + "Reinforcement assigned to each player! \nBegin to issue order as per turn!"
                            + Constant.RESET_COLOR);
            return true;
        } catch (Exception e) {
            System.out.println(Constant.ERROR_COLOR + "Error assigning countries!" + Constant.RESET_COLOR);
            return false;
        }
    }

    /**
     * Takes deploy order from user and add it to the execution order list.
     * 
     */
    public void issue_deployOrder() {
        try {
            if (!StartUpPhase.isValidCommandArgument(this.d_args, 3)) {
                System.out.println(Constant.ERROR_COLOR
                        + "Invalid number of arguments for deploy command" + Constant.RESET_COLOR);
                System.out.println(Constant.ERROR_COLOR
                        + "Try -> deploy <countryId> <numberOfArmy>" + Constant.RESET_COLOR);
                return;
            }
            String l_countryId = this.d_args[1];
            int l_armyCount = Integer.parseInt(this.d_args[2]);
            boolean l_isPlayerOwnCountry = this.getOwnedCountries().containsKey(l_countryId.toLowerCase());
            boolean l_hasValidArmy = (this.getOwnedArmyCount() >= l_armyCount);
            if (!l_isPlayerOwnCountry) {
                System.out.println(Constant.ERROR_COLOR + "Player " + this.getPlayerName() + " does not own "
                        + l_countryId + " country" + Constant.RESET_COLOR);
                return;
            }
            if (!l_hasValidArmy) {
                System.out.println(Constant.ERROR_COLOR + "Player " + this.getPlayerName()
                        + " does not have sufficient army" + Constant.RESET_COLOR);
                return;
            }
            
            this.addOrder(new Deploy(this, l_countryId, l_armyCount));
            this.d_executionOrderList.add(this.d_order);
            this.setOwnedArmyCount(this.getOwnedArmyCount() - l_armyCount);
            System.out.println("Player " + this.getPlayerName() + " has " + this.getOwnedArmyCount()
                    + " army left in the reinforcement pool");
            System.out.println("-------------------------------------------------------------------");
        } catch (Exception e) {
            System.out.println(Constant.ERROR_COLOR
                    + "Invalid command. Try -> deploy <countryId> <numberOfArmy>" + Constant.RESET_COLOR);
        }
    }

    /**
     * Takes Advance order from user and add it to the execution order list.
     * 
     */
    public void issue_advanceOrder(ArrayList<Player> p_players, GameMap p_map){
        try{
            if (!StartUpPhase.isValidCommandArgument(this.d_args, 4)) {
                System.out.println(Constant.ERROR_COLOR
                        + "Invalid number of arguments for deploy command" + Constant.RESET_COLOR);
                System.out.println(Constant.ERROR_COLOR
                        + "Try -> advance <sourceCountryId> <targetCountryId> <numberOfArmy>" + Constant.RESET_COLOR);
                return;
            }

            String l_sourceCountry = this.d_args[1];
            String l_targetCountry = this.d_args[2];
            int l_moveArmies = Integer.parseInt(this.d_args[3]);
            Player l_targetPlayer = null;
            for(Player p : p_players){
                //checking which player contains targetCountry
                if(p.getOwnedCountries().containsKey(l_targetCountry.toLowerCase())){
                    l_targetPlayer=p;
                    break;
            }
        }
        boolean l_isPlayerOwnsSourceCountry = this.getOwnedCountries().containsKey(l_sourceCountry.toLowerCase());
        boolean l_hasSufficientArmy = (this.getOwnedCountries().get(l_sourceCountry.toLowerCase()).getNumberOfArmies() - l_moveArmies)>=1;
        boolean l_areBothCountriesNeighbors = new Country().isNeighbor(p_map, l_sourceCountry, l_targetCountry);

        if (!l_isPlayerOwnsSourceCountry) {
                System.out.println(Constant.ERROR_COLOR + "Player " + this.getPlayerName() + " does not own "
                        + l_sourceCountry + " country" + Constant.RESET_COLOR);
                return;
            }
        if (!l_hasSufficientArmy) {
            System.out.println(Constant.ERROR_COLOR + "Player " + this.getPlayerName()
                    + " does not have sufficient army to advance." + Constant.RESET_COLOR);
            return;
        }
        if (!l_areBothCountriesNeighbors) {
            System.out.println(Constant.ERROR_COLOR + l_sourceCountry + " and "  + l_targetCountry 
                    + " are not neighbours." + Constant.RESET_COLOR);
            return;
        }
        this.addOrder(new Advance(this, l_sourceCountry, l_targetCountry, l_moveArmies, l_targetPlayer));
        this.d_executionOrderList.add(this.d_order);
        System.out.println(Constant.SUCCESS_COLOR + "Advance order successfully issued by " + this.getPlayerName() 
                    +" and added to execution list"+ Constant.RESET_COLOR);
        System.out.println("-------------------------------------------------------------------");

        } catch(Exception e){
            System.out.println(Constant.ERROR_COLOR
                    + "Invalid command. Try -> advance <sourceCountryId> <targetCountryId> <numberOfArmy>" + Constant.RESET_COLOR);
        }
    }

    /**
     * Takes Airlift order from user and add it to the execution order list.
     */
    public void issue_airliftOrder(){
        try{
            if (!StartUpPhase.isValidCommandArgument(this.d_args, 4)) {
                System.out.println(Constant.ERROR_COLOR
                        + "Invalid number of arguments for deploy command" + Constant.RESET_COLOR);
                System.out.println(Constant.ERROR_COLOR
                        + "Try -> airlift <sourceCountryId> <targetCountryId> <numberOfArmy>" + Constant.RESET_COLOR);
                return;
            }

            String l_sourceCountry = this.d_args[1];
            String l_targetCountry = this.d_args[2];
            int l_moveArmies = Integer.parseInt(this.d_args[3]);
            
            boolean l_isPlayerOwnsSourceCountry = this.getOwnedCountries().containsKey(l_sourceCountry.toLowerCase());
            boolean l_isPlayerOwnsTargetCountry = this.getOwnedCountries().containsKey(l_targetCountry.toLowerCase());
            boolean l_hasSufficientArmy = (this.getOwnedCountries().get(l_sourceCountry.toLowerCase()).getNumberOfArmies() > l_moveArmies);
            boolean l_hasCard = this.doesCardExists("Airlift");

            if (!l_isPlayerOwnsSourceCountry) {
                System.out.println(Constant.ERROR_COLOR + "Player " + this.getPlayerName() + " does not own "
                        + l_sourceCountry + " country" + Constant.RESET_COLOR);
                return;
            }
            if (!l_isPlayerOwnsTargetCountry) {
                System.out.println(Constant.ERROR_COLOR + "Player " + this.getPlayerName() + " does not own "
                        + l_targetCountry + " country" + Constant.RESET_COLOR);
                return;
            }
            if (!l_hasSufficientArmy) {
                System.out.println(Constant.ERROR_COLOR + "Player " + this.getPlayerName()
                        + " does not have sufficient army to advance." + Constant.RESET_COLOR);
                return;
            }
            if (!l_hasCard) {
                System.out.println(Constant.ERROR_COLOR + "Player " + this.getPlayerName()
                        + " does not have Airlift card." + Constant.RESET_COLOR);
                return;
            }

            this.addOrder(new Airlift(this, l_sourceCountry, l_targetCountry, l_moveArmies));
            this.d_executionOrderList.add(this.d_order);
            System.out.println(Constant.SUCCESS_COLOR + "Airlift order successfully issued by " + this.getPlayerName() 
                        +" and added to execution list"+ Constant.RESET_COLOR);
            this.removeCard("Airlift");
            System.out.println("-------------------------------------------------------------------");


        }catch(Exception e){
            System.out.println(Constant.ERROR_COLOR
                    + "Invalid command. Try -> airlift <sourceCountryId> <targetCountryId> <numberOfArmy>" + Constant.RESET_COLOR);
        }
    }

    /**
     * Takes Diplomacy order from user and add it to the execution order list.
     */
    public void issue_diplomacyOrder(){
        try {
            if (!StartUpPhase.isValidCommandArgument(this.d_args, 2)) {
                System.out.println(Constant.ERROR_COLOR
                        + "Invalid number of arguments for deploy command" + Constant.RESET_COLOR);
                System.out.println(Constant.ERROR_COLOR
                        + "Try -> negotiate <PlayerID>" + Constant.RESET_COLOR);
                return;
            }
            Player l_targetPlayer = new StartUpPhase().getPlayerByName(this.d_args[1]);
            if (this.doesCardExists("Diplomacy")) {
                this.addOrder(new Diplomacy(this, l_targetPlayer));
                this.d_executionOrderList.add(this.d_order);
                System.out.println(Constant.SUCCESS_COLOR + "Diplomacy order successfully issued by " + this.getPlayerName() 
                        +" and added to execution list"+ Constant.RESET_COLOR);
                this.removeCard("Diplomacy");
                System.out.println("-------------------------------------------------------------------");
            } else {
                System.out.println(Constant.ERROR_COLOR + "Player " + this.getPlayerName()
                        + " does not have Diplomacy card OR invalid target player name" + Constant.RESET_COLOR);
            }
            
        } catch (Exception e) {
           System.out.println(Constant.ERROR_COLOR
                    + "Invalid command. Try -> negotiate <PlayerID>" + Constant.RESET_COLOR);
        }
    }

    /**
     * Takes Bomb order from user and add it to the execution order list.
     */
    public void issue_bombOrder(ArrayList<Player> p_playerList){
        try {
            if (!StartUpPhase.isValidCommandArgument(this.d_args, 2)) {
                System.out.println(Constant.ERROR_COLOR
                        + "Invalid number of arguments for deploy command" + Constant.RESET_COLOR);
                System.out.println(Constant.ERROR_COLOR
                        + "Try -> bomb <countryID>" + Constant.RESET_COLOR);
                return;
            }

            String l_targetCountry = this.d_args[1];
            boolean l_isTargetCountryOwnedByCurPlayer = this.getOwnedCountries().containsKey(l_targetCountry.toLowerCase());
            if(l_isTargetCountryOwnedByCurPlayer){
                System.out.println(Constant.ERROR_COLOR
                    + "Country owned by current player" + Constant.RESET_COLOR);
                return;
            }

            boolean l_hasCard = this.doesCardExists("Bomb");
            if (!l_hasCard) {
                System.out.println(Constant.ERROR_COLOR + "Player " + this.getPlayerName()
                        + " does not have Bomb card." + Constant.RESET_COLOR);
                return;
            }

            boolean l_isTargetCountryNeighbor = false;
            for(Country l_c : this.getOwnedCountries().values()){
                if( l_c.getNeighbours().containsKey(l_targetCountry.toLowerCase())){
                    l_isTargetCountryNeighbor = true;
                    break;
                }
            }
            if (!l_isTargetCountryNeighbor) {
                System.out.println(Constant.ERROR_COLOR
                        + " target country not adjacent to any country owned by current player" + Constant.RESET_COLOR);
                return;
            }

            Player l_targetPlayer = null;
            for(Player p : p_playerList){
                //checking which player contains targetCountry
                if(p.getOwnedCountries().containsKey(l_targetCountry.toLowerCase())){
                    l_targetPlayer = p;
                    break;
                }
            }

            this.addOrder(new Bomb(this, l_targetPlayer, l_targetCountry));
            this.d_executionOrderList.add(this.d_order);
            System.out.println(Constant.SUCCESS_COLOR + "Bomb order successfully issued by " + this.getPlayerName() 
                        +" and added to execution list"+ Constant.RESET_COLOR);
            this.removeCard("Bomb");
            System.out.println("-------------------------------------------------------------------");

        } catch (Exception e) {
            System.out.println(Constant.ERROR_COLOR
                    + "Invalid command. Try -> bomb <countryID>" + Constant.RESET_COLOR);
        }
    }

    /**
     * Takes Blockade order from user and add it to the execution order list.
     */
    public void issue_blockadeOrder(){
        try {
            if (!StartUpPhase.isValidCommandArgument(this.d_args, 2)) {
                System.out.println(Constant.ERROR_COLOR
                        + "Invalid number of arguments for deploy command" + Constant.RESET_COLOR);
                System.out.println(Constant.ERROR_COLOR
                        + "Try -> blockade <countryID>" + Constant.RESET_COLOR);
                return;
            }
            String l_country = this.d_args[1];
            boolean l_isPlayerOwnsCountry = this.getOwnedCountries().containsKey(l_country.toLowerCase());
            boolean l_hasCard = this.doesCardExists("Blockade");

            if (!l_isPlayerOwnsCountry) {
                System.out.println(Constant.ERROR_COLOR + "Player " + this.getPlayerName() + " does not own "
                        + l_country + " country" + Constant.RESET_COLOR);
                return;
            }
            
            if (!l_hasCard) {
                System.out.println(Constant.ERROR_COLOR + "Player " + this.getPlayerName()
                        + " does not have Blockade card." + Constant.RESET_COLOR);
                return;
            }

            this.addOrder(new Blockade(this, l_country));
            this.d_executionOrderList.add(this.d_order);
            System.out.println(Constant.SUCCESS_COLOR + "Blockade order successfully issued by " + this.getPlayerName() 
                        +" and added to execution list"+ Constant.RESET_COLOR);
            this.removeCard("Blockade");
            System.out.println("-------------------------------------------------------------------");

        } catch (Exception e) {
            System.out.println(Constant.ERROR_COLOR
                    + "Invalid command. Try -> blockade <countryID>" + Constant.RESET_COLOR);
        }
    }
}
