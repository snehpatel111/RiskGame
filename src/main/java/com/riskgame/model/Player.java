package com.riskgame.model;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

import com.riskgame.controller.GameEngine;
import com.riskgame.model.Advance;
import com.riskgame.model.Airlift;
import com.riskgame.model.Blockade;
import com.riskgame.model.Bomb;
import com.riskgame.model.Card;
import com.riskgame.model.Deploy;
import com.riskgame.model.Diplomacy;
import com.riskgame.model.GameState;
import com.riskgame.model.Order;

import com.riskgame.utility.Constant;
import com.riskgame.utility.Util;

/**
 * Represents a player in the game.
 */
public class Player implements Serializable {
    private String d_playerName;
    private HashMap<String, Continent> d_ownedContinents;
    private HashMap<String, Country> d_ownedCountries;
    private int d_ownedArmyCount;
    private int d_remainingArmyCount;
    private String d_countryId;
    private Order d_order;
    private Queue<Order> d_executionOrderList;
    private PlayerStrategy d_strategy;

    /**
     * d_isHuman check whether the player type is human or not
     */
    public boolean d_isHuman;

    /**
     * List of players who are going to negotiate with the current player.
     */
    public ArrayList<Player> d_negotiatePlayers;

    /**
     * List of cards owned by the player.
     */
    public ArrayList<Card> d_ownedCards;

    /**
     * Command line input from user
     */
    public String[] d_args;

    /**
     * Countries owned by player
     */
    private Map<String, Country> ownedCountries = new HashMap<>();

    public GameState d_gameState = new GameState();

    public void addOwnedCountry(Country country) {
        this.ownedCountries.put(country.getCountryId(), country);
    }

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
        this.d_negotiatePlayers = new ArrayList<>();
        this.d_ownedCards = new ArrayList<>();
    }

    /**
     * setter methos to set game state
     * 
     * @param p_gameState gamestate
     */
    public void setGameState(GameState p_gameState) {
        this.d_gameState = p_gameState;
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
     * Getter method for negotiators
     * 
     * @return List of negotiators
     */
    public ArrayList<Player> getNegotiatePlayerList() {
        return this.d_negotiatePlayers;
    }

    /**
     * Adds Player to Negotiator list
     * 
     * @param p_player target player to be added
     */
    void addPlayerToNegotiateList(Player p_player) {
        this.d_negotiatePlayers.add(p_player);
    }

    /**
     * flush lists after Turn
     */
    public void flushNegotiators() {
        this.d_negotiatePlayers.clear();
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
     * This function adds Order object to the list of Orders for a non-human Player
     * 
     * @return true if Order added else false
     */
    public boolean issueOrder() {
        Order order;
        order = this.d_strategy.createOrder();
        if (order != null) {
            this.d_executionOrderList.add(order);
            this.d_gameState.getUnexecutedOrders().add(order);
            return true;
        }
        return false;
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
        this.d_ownedCards.add(l_card);
    }

    /**
     * Added a clone of addCard inorder to test custom cards
     * 
     * @param test custom card
     */
    public void addCard(String test) {
        Card l_card = new Card();
        l_card.createCard(test);
        this.d_ownedCards.add(l_card);
    }

    /**
     * If a player uses a card,it will be removed from deck of cards.
     * 
     * @param p_card String representation of card to be used
     */
    public void removeCard(String p_card) {
        // remove card from deck
        Iterator<Card> l_iter = this.d_ownedCards.iterator();
        while (l_iter.hasNext()) {
            Card l_card = (Card) l_iter.next();
            if (l_card.getCardType() == p_card) {
                this.d_ownedCards.remove(l_card);
                break;
            }
        }
    }

    /**
     * Before using a card, we can check if player has that card
     * 
     * @param p_card String representation of card to be used
     * @return true if card exists else false
     */
    public boolean doesCardExists(String p_card) {

        int l_existsCount = 0;
        Iterator<Card> l_iter = this.d_ownedCards.iterator();
        while (l_iter.hasNext()) {
            Card l_card = (Card) l_iter.next();
            if (l_card.getCardType() == p_card)
                l_existsCount++;
        }
        if (l_existsCount > 0)
            return true;
        else
            return false;
    }

    /**
     * show the particular card owned by player
     */
    public void showCards() {
        if (this.d_ownedCards.isEmpty()) {
            System.out.println("Player " + this.getPlayerName() + " owns no cards!");

        } else {
            Iterator<Card> l_iter = this.d_ownedCards.iterator();
            System.out.print("Player " + this.getPlayerName() + "'s cards: ");
            while (l_iter.hasNext()) {
                Card l_card = (Card) l_iter.next();
                String l_endString = l_iter.hasNext() ? ", " : "";
                System.out.println(l_card.getCardType() + l_endString);
            }
        }
    }

    /**
     * Getter for Player's Cards Deck
     * 
     * @return d_ownedCards
     */
    public ArrayList<Card> getCardDeck() {
        return this.d_ownedCards;
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
     * getter method to get current player by name
     * 
     * @param p_playerName player name
     * @param p_gs         game state
     * @return player object
     */
    public Player getPlayerByName(String p_playerName, GameState p_gs) {
        for (Player l_p : p_gs.getPlayerList()) {
            if (l_p.getPlayerName().equalsIgnoreCase(p_playerName)) {
                return l_p;
            }
        }
        return null;
    }

    /**
     * Manage player related operations based on input arguments.
     * 
     * @param p_gameEngine GameEngine object
     * @param p_gameState  GameState object
     * @param p_args       Command line arguments
     */
    public void managePlayer(GameEngine p_gameEngine, GameState p_gameState, String[] p_args) {
        try {
            ArrayList<Player> l_playerList = p_gameState.getPlayerList();
            String l_playerName = p_args[2];
            if (p_args[1].equals("-add")) {
                String l_playerStrategy = p_args[3];
                if (this.isValidPlayerName(l_playerName)) {
                    boolean l_isPlayerAdded = this.addPlayer(p_gameEngine, l_playerList, l_playerName, p_gameState,
                            l_playerStrategy);

                    if (l_isPlayerAdded) {
                        System.out
                                .println(Constant.SUCCESS_COLOR + "Player " + l_playerName + " added successfully!"
                                        + Constant.RESET_COLOR);
                        p_gameState.updateLog("Player " + l_playerName + " added successfully!", "effect");
                    }
                } else {
                    p_gameState.updateLog("Invalid player name", "effect");
                    System.out.println(Constant.ERROR_COLOR + "Invalid player name" + Constant.RESET_COLOR);
                }
            } else if (p_args[1].equals("-remove")) {
                if (this.isValidPlayerName(l_playerName)) {
                    boolean l_isPlayerRemoved = this.removePlayer(l_playerList, l_playerName);
                    if (l_isPlayerRemoved) {
                        p_gameState.updateLog("Player removed successfully!", "effect");
                        System.out.println(
                                Constant.SUCCESS_COLOR + "Player removed successfully!" + Constant.RESET_COLOR);
                    } else {
                        p_gameState.updateLog("Player doesn't exist", "effect");
                        System.out.println(Constant.ERROR_COLOR + "Player doesn't exist" + Constant.RESET_COLOR);
                    }
                } else {
                    p_gameState.updateLog("Invalid player name", "effect");
                    System.out.println(Constant.ERROR_COLOR + "Invalid player name" + Constant.RESET_COLOR);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {

            p_gameState.updateLog(
                    "Invalid command - it should be of the form gameplayer -add playername or gameplayer -remove playername",
                    "effect");
            System.out.println(Constant.ERROR_COLOR
                    + "Invalid command - it should be of the form gameplayer -add playername or gameplayer -remove playername"
                    + Constant.RESET_COLOR);
        } catch (Exception e) {

            p_gameState.updateLog(
                    "Invalid command - it should be of the form gameplayer -add playername or gameplayer -remove playername",
                    "effect");
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
     * @param p_gameState  The current game state.
     * @return Return true if player added successfully, false otherwise
     */
    public boolean addPlayer(GameEngine p_gameEngine, ArrayList<Player> p_playerList, String p_playerName,
            GameState p_gameState, String p_playerStrategy) {
        if (p_playerList.size() == 6) {
            p_gameState.updateLog(
                    "Max number of players (6) reached!",
                    "effect");
            System.out.println(Constant.ERROR_COLOR + "Max number of players (6) reached!" + Constant.RESET_COLOR);
            return false;
        }
        if (this.isPlayerExist(p_playerList, p_playerName)) {
            p_gameState.updateLog(
                    "Player already exists!",
                    "effect");
            System.out.println(Constant.ERROR_COLOR + "Player already exists!" + Constant.RESET_COLOR);
            return false;
        }
        Player l_p = new Player(p_playerName);
        switch (p_playerStrategy) {
            case "human":
                l_p.d_isHuman = true;
                break;
            case "aggressive":
                l_p.d_isHuman = false;
                l_p.d_strategy = new AggressiveStrategy(l_p, p_gameEngine);
                break;
            case "random":
                l_p.d_isHuman = false;
                l_p.d_strategy = new RandomStrategy(l_p, p_gameEngine);
                break;
            case "cheater":
                l_p.d_isHuman = false;
                l_p.d_strategy = new CheaterStrategy(l_p, p_gameEngine);
                break;
            case "benevolent":
                l_p.d_isHuman = false;
                l_p.d_strategy = new BenevolentStrategy(l_p, p_gameEngine);
                break;

            default:
                return false;
        }
        p_playerList.add(l_p);
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
     * @param p_gameEngine The game engine object
     * @param p_gameState  The game state object
     * 
     * @return Returns true if countries assigned successfully to players, false
     *         otherwise
     */
    public static boolean assignCountries(GameEngine p_gameEngine, GameState p_gameState) {
        try {
            GameMap l_gameMap = p_gameState.getGameMap();
            ArrayList<Player> l_playerList = p_gameState.getPlayerList();
            if (l_playerList.size() < 2) {
                p_gameState.updateLog("Minimum two players are required to play the game.", "effect");
                System.out.println(Constant.ERROR_COLOR + "Minimum two players are required to play the game."
                        + Constant.RESET_COLOR);
                return false;
            }
            Collections.shuffle(l_playerList);
            for (Country l_country : l_gameMap.getCountries().values()) {
                int l_randomIndex = new Random().nextInt(l_playerList.size());
                Player l_randomPlayer = l_playerList.get(l_randomIndex);
                l_randomPlayer.getOwnedCountries().put(l_country.getCountryId().toLowerCase(), l_country);
                l_country.setOwnerPlayer(l_randomPlayer);
            }
            p_gameState.updateLog("Countries assigned randomly to all players!", "effect");
            System.out.println(
                    Constant.SUCCESS_COLOR + "Countries assigned randomly to all players!" + Constant.RESET_COLOR);
            MapHelper l_mapHelper = new MapHelper();
            l_mapHelper.showMap(l_gameMap, p_gameState);
            return true;
        } catch (Exception e) {
            p_gameState.updateLog("Error assigning countries!", "effect");
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
            if (!Util.isValidCommandArgument(this.d_args, 3)) {
                this.d_gameState.updateLog("Invalid number of arguments for deploy command", "effect");

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
                this.d_gameState.updateLog("Player " + this.getPlayerName() + " does not own "
                        + l_countryId + " country", "effect");
                System.out.println(Constant.ERROR_COLOR + "Player " + this.getPlayerName() + " does not own "
                        + l_countryId + " country" + Constant.RESET_COLOR);
                return;
            }
            if (!l_hasValidArmy) {
                this.d_gameState.updateLog("Player " + this.getPlayerName()
                        + " does not have sufficient army", "effect");
                System.out.println(Constant.ERROR_COLOR + "Player " + this.getPlayerName()
                        + " does not have sufficient army" + Constant.RESET_COLOR);
                return;
            }

            this.addOrder(new Deploy(this, l_countryId, l_armyCount));
            System.out.println(this.d_executionOrderList.size());
            this.d_gameState.getUnexecutedOrders().add(this.d_order);
            this.d_executionOrderList.add(this.d_order);
            this.setOwnedArmyCount(this.getOwnedArmyCount() - l_armyCount);
            this.d_gameState.updateLog("Player " + this.getPlayerName() + " has " + this.getOwnedArmyCount()
                    + " army left in the reinforcement pool", "effect");

            System.out.println("Player " + this.getPlayerName() + " has " + this.getOwnedArmyCount()
                    + " army left in the reinforcement pool");
            System.out.println("-------------------------------------------------------------------");
        } catch (Exception e) {
            this.d_gameState.updateLog("Invalid command. Try -> deploy <countryId> <numberOfArmy>", "effect");
            System.out.println(Constant.ERROR_COLOR
                    + "Invalid command. Try -> deploy <countryId> <numberOfArmy>" + Constant.RESET_COLOR);
        }
    }

    /**
     * Takes Advance order from user and add it to the execution order list.
     * 
     */
    public void issue_advanceOrder() {
        try {
            if (!Util.isValidCommandArgument(this.d_args, 4)) {
                this.d_gameState.updateLog("Invalid number of arguments for deploy command", "effect");

                System.out.println(Constant.ERROR_COLOR
                        + "Invalid number of arguments for deploy command" + Constant.RESET_COLOR);
                System.out.println(Constant.ERROR_COLOR
                        + "Try -> advance <sourceCountryId> <targetCountryId> <numberOfArmy>" + Constant.RESET_COLOR);
                return;
            }

            String l_sourceCountry = this.d_args[1];
            String l_targetCountry = this.d_args[2];
            int l_moveArmies = Integer.parseInt(this.d_args[3]);

            boolean l_isPlayerOwnsSourceCountry = this.getOwnedCountries().containsKey(l_sourceCountry.toLowerCase());
            if (!l_isPlayerOwnsSourceCountry) {
                this.d_gameState.updateLog("Player " + this.getPlayerName() + " does not own "
                        + l_sourceCountry + " country", "effect");
                System.out.println(Constant.ERROR_COLOR + "Player " + this.getPlayerName() + " does not own "
                        + l_sourceCountry + " country" + Constant.RESET_COLOR);
                return;
            }
            boolean l_isNeighBorCountry = this.getOwnedCountries().get(l_sourceCountry.toLowerCase()).getNeighbors()
                    .containsKey(l_targetCountry.toLowerCase());
            boolean l_isTargetCountryExist = this.validateTargetCountry(l_targetCountry);
            if (!l_isNeighBorCountry && !l_isTargetCountryExist) {
                this.d_gameState.updateLog("No player owns " + l_targetCountry + " country", "effect");
                System.out.println(
                        Constant.ERROR_COLOR + "No player owns " + l_targetCountry + " country" + Constant.RESET_COLOR);
                return;
            }

            boolean l_hasSufficientArmy = (this.getOwnedCountries().get(l_sourceCountry.toLowerCase())
                    .getNumberOfArmies() - l_moveArmies) >= 0;
            boolean l_areBothCountriesNeighbors = new Country().isNeighbor(this.d_gameState.getGameMap(),
                    l_sourceCountry, l_targetCountry);

            if (!l_areBothCountriesNeighbors) {
                this.d_gameState.updateLog(l_sourceCountry + " and " + l_targetCountry
                        + " are not neighbors.", "effect");
                System.out.println(Constant.ERROR_COLOR + l_sourceCountry + " and " + l_targetCountry
                        + " are not neighbors." + Constant.RESET_COLOR);
                return;
            }
            if (!l_hasSufficientArmy) {
                this.d_gameState.updateLog("Player " + this.getPlayerName()
                        + " does not have sufficient army to advance.", "effect");
                System.out.println(Constant.ERROR_COLOR + "Player " + this.getPlayerName()
                        + " does not have sufficient army to advance." + Constant.RESET_COLOR);
                return;
            }
            Player l_targetPlayer = new Player("neutral");
            for (Player l_p : this.d_gameState.getPlayerList()) {
                // checking which player contains targetCountry
                if (l_p.getOwnedCountries().containsKey(l_targetCountry.toLowerCase())) {
                    l_targetPlayer = l_p;
                    break;
                }
            }
            this.addOrder(new Advance(this, l_sourceCountry, l_targetCountry, l_moveArmies, l_targetPlayer));
            this.d_gameState.getUnexecutedOrders().add(this.d_order);
            this.d_executionOrderList.add(this.d_order);
            this.d_gameState.updateLog("Advance order successfully issued by " + this.getPlayerName()
                    + " and added to execution list", "effect");
            System.out.println(Constant.SUCCESS_COLOR + "Advance order successfully issued by " + this.getPlayerName()
                    + " and added to execution list" + Constant.RESET_COLOR);
            System.out.println("-------------------------------------------------------------------");

        } catch (Exception e) {
            this.d_gameState.updateLog(
                    "Invalid command. Try -> advance <sourceCountryId> <targetCountryId> <numberOfArmy>",
                    "effect");
            System.out.println(Constant.ERROR_COLOR
                    + "Invalid command. Try -> advance <sourceCountryId> <targetCountryId> <numberOfArmy>"
                    + Constant.RESET_COLOR);
        }
    }

    /**
     * Takes Airlift order from user and add it to the execution order list.
     * 
     */
    public void issue_airliftOrder() {
        try {
            if (!Util.isValidCommandArgument(this.d_args, 4)) {
                this.d_gameState.updateLog("Invalid number of arguments for deploy command",
                        "effect");
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
            boolean l_hasSufficientArmy = (this.getOwnedCountries().get(l_sourceCountry.toLowerCase())
                    .getNumberOfArmies() >= l_moveArmies);
            boolean l_hasCard = this.doesCardExists("Airlift");

            if (!l_hasCard) {
                this.d_gameState.updateLog("Player " + this.getPlayerName()
                        + " does not have `Airlift` card.",
                        "effect");
                System.out.println(Constant.ERROR_COLOR + "Player " + this.getPlayerName()
                        + " does not have `Airlift` card." + Constant.RESET_COLOR);
                return;
            }

            if (!l_isPlayerOwnsSourceCountry) {
                this.d_gameState.updateLog("Player " + this.getPlayerName() + " does not own "
                        + l_sourceCountry + " country",
                        "effect");
                System.out.println(Constant.ERROR_COLOR + "Player " + this.getPlayerName() + " does not own "
                        + l_sourceCountry + " country" + Constant.RESET_COLOR);
                return;
            }
            if (!l_isPlayerOwnsTargetCountry) {
                this.d_gameState.updateLog("Player " + this.getPlayerName() + " does not own "
                        + l_sourceCountry + " country",
                        "effect");
                System.out.println(Constant.ERROR_COLOR + "Player " + this.getPlayerName() + " does not own "
                        + l_targetCountry + " country" + Constant.RESET_COLOR);
                return;
            }
            if (!l_hasSufficientArmy) {
                System.out.println(Constant.ERROR_COLOR + "Player " + this.getPlayerName()
                        + " does not have sufficient army to airlift." + Constant.RESET_COLOR);
                return;
            }

            this.addOrder(new Airlift(this, l_sourceCountry, l_targetCountry, l_moveArmies));
            this.d_executionOrderList.add(this.d_order);
            this.d_gameState.updateLog("Airlift order successfully issued by " + this.getPlayerName()
                    + " and added to execution list",
                    "effect");
            this.d_gameState.getUnexecutedOrders().add(this.d_order);
            System.out.println(Constant.SUCCESS_COLOR + "Airlift order successfully issued by " + this.getPlayerName()
                    + " and added to execution list" + Constant.RESET_COLOR);
            this.removeCard("Airlift");
            System.out.println("-------------------------------------------------------------------");

        } catch (Exception e) {
            this.d_gameState.updateLog(
                    "Invalid command. Try -> airlift <sourceCountryId> <targetCountryId> <numberOfArmy>",
                    "effect");
            System.out.println(Constant.ERROR_COLOR
                    + "Invalid command. Try -> airlift <sourceCountryId> <targetCountryId> <numberOfArmy>"
                    + Constant.RESET_COLOR);
        }
    }

    /**
     * Takes Diplomacy order from user and add it to the execution order list.
     */
    public void issue_diplomacyOrder() {
        try {
            if (!Util.isValidCommandArgument(this.d_args, 2)) {
                System.out.println(Constant.ERROR_COLOR
                        + "Invalid number of arguments for deploy command" + Constant.RESET_COLOR);
                System.out.println(Constant.ERROR_COLOR
                        + "Try -> negotiate <PlayerID>" + Constant.RESET_COLOR);
                return;
            }
            Player l_targetPlayer = getPlayerByName(this.d_args[1], this.d_gameState);
            if (this.doesCardExists("Diplomacy")) {
                this.addOrder(new Diplomacy(this, l_targetPlayer));
                this.d_executionOrderList.add(this.d_order);
                this.d_gameState.getUnexecutedOrders().add(this.d_order);
                System.out.println(
                        Constant.SUCCESS_COLOR + "Diplomacy order successfully issued by " + this.getPlayerName()
                                + " and added to execution list" + Constant.RESET_COLOR);
                this.removeCard("Diplomacy");
                System.out.println("-------------------------------------------------------------------");
            } else {
                System.out.println(Constant.ERROR_COLOR + "Player " + this.getPlayerName()
                        + " does not have Diplomacy card OR invalid target player name" +
                        Constant.RESET_COLOR);
            }

        } catch (Exception e) {
            System.out.println(Constant.ERROR_COLOR
                    + "Invalid command. Try -> negotiate <PlayerID>" + Constant.RESET_COLOR);
        }
    }

    /**
     * Takes Bomb order from user and add it to the execution order list.
     */
    public void issue_bombOrder() {
        try {
            if (!Util.isValidCommandArgument(this.d_args, 2)) {
                System.out.println(Constant.ERROR_COLOR
                        + "Invalid number of arguments for deploy command" + Constant.RESET_COLOR);
                System.out.println(Constant.ERROR_COLOR
                        + "Try -> bomb <countryID>" + Constant.RESET_COLOR);
                return;
            }

            String l_targetCountry = this.d_args[1];
            boolean l_isTargetCountryOwnedByCurPlayer = this.getOwnedCountries()
                    .containsKey(l_targetCountry.toLowerCase());
            if (l_isTargetCountryOwnedByCurPlayer) {
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
            for (Country l_country : this.getOwnedCountries().values()) {
                if (l_country.getNeighbors().containsKey(l_targetCountry.toLowerCase())) {
                    l_isTargetCountryNeighbor = true;
                    break;
                }
            }
            if (!l_isTargetCountryNeighbor) {
                System.out.println(Constant.ERROR_COLOR
                        + "Target country not adjacent to any country owned by current player" +
                        Constant.RESET_COLOR);
                return;
            }

            Player l_targetPlayer = new Player("neutral");
            for (Player p : this.d_gameState.getPlayerList()) {
                // checking which player contains targetCountry
                if (p.getOwnedCountries().containsKey(l_targetCountry.toLowerCase())) {
                    l_targetPlayer = p;
                    break;
                }
            }
            boolean l_isTargetCountryExist = this.validateTargetCountry(l_targetCountry);
            if (!l_isTargetCountryExist) {
                System.out.println(Constant.ERROR_COLOR
                        + l_targetCountry + " country does not exist on map" + Constant.RESET_COLOR);
                return;
            }
            this.addOrder(new Bomb(this, l_targetPlayer, l_targetCountry));
            this.d_executionOrderList.add(this.d_order);
            this.d_gameState.getUnexecutedOrders().add(this.d_order);
            System.out.println(Constant.SUCCESS_COLOR + "Bomb order successfully issued by " + this.getPlayerName()
                    + " and added to execution list" + Constant.RESET_COLOR);
            this.removeCard("Bomb");
            System.out.println("-------------------------------------------------------------------");

        } catch (Exception e) {
            System.out.println(Constant.ERROR_COLOR
                    + "Invalid command. Try -> bomb <countryID>" + Constant.RESET_COLOR);
        }
    }

    /**
     * Takes Blockade order from user and add it to the execution order list.
     * 
     */
    public void issue_blockadeOrder() {
        try {
            if (!Util.isValidCommandArgument(this.d_args, 2)) {
                this.d_gameState.updateLog("Invalid number of arguments for deploy command", "effect");
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
                this.d_gameState.updateLog("Player " + this.getPlayerName() + " does not own "
                        + l_country + " country", "effect");

                System.out.println(Constant.ERROR_COLOR + "Player " + this.getPlayerName() + " does not own "
                        + l_country + " country" + Constant.RESET_COLOR);
                return;
            }

            if (!l_hasCard) {
                this.d_gameState.updateLog("Player " + this.getPlayerName()
                        + " does not have Blockade card.", "effect");
                System.out.println(Constant.ERROR_COLOR + "Player " + this.getPlayerName()
                        + " does not have Blockade card." + Constant.RESET_COLOR);
                return;
            }

            this.addOrder(new Blockade(this, l_country));
            this.d_executionOrderList.add(this.d_order);
            this.d_gameState.updateLog("Blockade order successfully issued by " + this.getPlayerName()
                    + " and added to execution list", "effect");
            this.d_gameState.getUnexecutedOrders().add(this.d_order);
            System.out.println(Constant.SUCCESS_COLOR + "Blockade order successfully issued by " + this.getPlayerName()
                    + " and added to execution list" + Constant.RESET_COLOR);
            this.removeCard("Blockade");
            System.out.println("-------------------------------------------------------------------");

        } catch (Exception e) {
            this.d_gameState.updateLog("Invalid command. Try -> blockade <countryID>", "effect");
            System.out.println(Constant.ERROR_COLOR
                    + "Invalid command. Try -> blockade <countryID>" + Constant.RESET_COLOR);
        }
    }

    /**
     * Validate if target country exist on map.
     * 
     * @param p_targetCountry Target country name.
     * @return True if target country exist on map.
     */
    public boolean validateTargetCountry(String p_targetCountry) {
        for (Country l_c : this.d_gameState.getGameMap().getCountries().values()) {
            if (l_c.getCountryId().equalsIgnoreCase(p_targetCountry)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets strategy for a specific player
     * 
     * @param p_strategy strategy
     */
    public void setStrategy(PlayerStrategy p_strategy) {
        this.d_strategy = p_strategy;
    };

    public boolean isWinner() {
        boolean l_winner = true;
        for (String l_countryName : this.d_gameState.getGameMap().getCountries().keySet()) {
            if (this.getOwnedCountries().getOrDefault(l_countryName, null) == null) {
                l_winner = false;
            }
        }
        return l_winner;
    }

    /**
     * Sets Player as human
     * 
     * @param d_isHuman true if human else false
     */
    public void setIsHuman(boolean d_isHuman) {
        this.d_isHuman = d_isHuman;
    }
}
