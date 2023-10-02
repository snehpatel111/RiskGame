package com.riskgame.model;

import com.riskgame.utility.Constant;
import com.riskgame.utility.MapValidator;
import com.riskgame.utility.Phase;

import com.riskgame.model.Continent;
import com.riskgame.model.GameMap;
import com.riskgame.model.MapHelper;
import com.riskgame.model.Player;

import java.util.ArrayList;

/**
 * Implements parsing of initial commands during startup phase.
 */
public class StartUpPhase {

    public Phase d_gamePhase;
    public GameMap d_gameMap;
    public ArrayList<Player> d_playerList;

    /**
     * Initialize StartUpPhase
     */
    public StartUpPhase() {
        this.d_gamePhase = Phase.NULL;
        this.d_playerList = new ArrayList<Player>();
    }

    /**
     * Parses the command received from player during startup phase
     * 
     * @param p_player  Player who is playing.
     * @param p_command Command to be executed.
     * @return Returns next game phase.
     */
    public Phase parseCommand(Player p_player, String p_command) {
        try {
            String[] l_data = p_command.split("\\s+");
            String l_commandName = l_data[0];

            // Parse initial command
            if (this.d_gamePhase.equals(Phase.NULL)) {
                switch (l_commandName) {
                    case "editmap":
                        try {
                            if (!isValidCommandArgument(l_data, 2)) {
                                System.out.println(Constant.ERROR_COLOR
                                        + "Invalid command! Try command -> editmap sample.map" + Constant.RESET_COLOR);
                                break;
                            }
                            String l_mapFileName = l_data[1];
                            MapHelper l_gameMap = new MapHelper();
                            this.d_gameMap = l_gameMap.editMap(l_mapFileName);
                            System.out.println("Editing for Map: " + l_mapFileName + "\n");
                            System.out.println("See the selected map using " + Constant.SUCCESS_COLOR
                                    + "showmap"
                                    + Constant.RESET_COLOR);
                            System.out.println("Edit continent using " + Constant.SUCCESS_COLOR
                                    + "editcontinent -add <continentId> <continentValue> or editcontinent -remove <continentId>"
                                    + Constant.RESET_COLOR);
                            System.out.println("Edit country using " + Constant.SUCCESS_COLOR
                                    + "editcountry -add <countryId> <continentId> or editcountry -remove <countryId>"
                                    + Constant.RESET_COLOR);
                            System.out.println("Edit neighbor using " + Constant.SUCCESS_COLOR
                                    + "editneighbor -add <countryId> <neighborCountryId> or editneighbor -remove <countryId> <neighborCountryId>"
                                    + Constant.RESET_COLOR + "\n");

                            this.d_gamePhase = Phase.EDITMAP;
                            break;
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out
                                    .println(Constant.ERROR_COLOR + "Invalid command! Try command -> editmap sample.map"
                                            + Constant.RESET_COLOR);
                        }
                        break;
                    case "loadmap":
                        try {
                            if (!isValidCommandArgument(l_data, 2)) {
                                System.out.println(Constant.ERROR_COLOR
                                        + "Invalid number of arguments for loadmap command" + Constant.RESET_COLOR);
                                break;
                            }
                            String l_mapFileName = l_data[1];
                            MapHelper l_gameMapHelper = new MapHelper();
                            this.d_gameMap = l_gameMapHelper.loadMap(l_mapFileName);
                            if (this.d_gameMap == null) {
                                this.d_gamePhase = Phase.NULL;
                            } else {
                                this.d_gamePhase = Phase.STARTUP;
                            }
                        } catch (Exception e) {
                            System.out.println(Constant.ERROR_COLOR
                                    + "Invalid command! Try command -> loadmap <mapName>"
                                    + Constant.RESET_COLOR);
                        }
                        break;
                    default:
                        System.out.println(Constant.ERROR_COLOR
                                + "Invalid command! Try command -> editmap <mapName> or loadmap <mapName>"
                                + Constant.RESET_COLOR);
                        break;
                }
            }

            // Edit phase.
            // Edit phase commands: editcontinent, editcountry, editneighbor, savemap,
            // showmap, editmap, loadmap, validatemap
            else if (this.d_gamePhase.equals(Phase.EDITMAP)) {
                Continent continent = new Continent();
                switch (l_commandName) {
                    case "editcontinent":
                        continent.editContinent(this.d_gameMap, this.d_gamePhase, l_data);
                        break;
                    case "editcountry":
                        continent.editCountry(this.d_gameMap, this.d_gamePhase, l_data);
                        break;
                    case "editneighbor":
                        continent.editNeighborCountry(this.d_gameMap, this.d_gamePhase, l_data);
                        break;
                    case "showmap":
                        if (!isValidCommandArgument(l_data, 1)) {
                            System.out.println(Constant.ERROR_COLOR
                                    + "Invalid number of arguments for showmap command"
                                    + Constant.RESET_COLOR);
                            break;
                        }
                        MapHelper l_gameMap = new MapHelper();
                        l_gameMap.showMap(this.d_gameMap);
                        this.d_gamePhase = Phase.EDITMAP;
                        break;
                    case "loadmap":
                        try {
                            if (!isValidCommandArgument(l_data, 2)) {
                                System.out.println(Constant.ERROR_COLOR
                                        + "Invalid command! Try command -> loadmap <mapName>"
                                        + Constant.RESET_COLOR);
                                break;
                            }
                            String l_mapFileName = l_data[1];
                            MapHelper l_gameMapHelper = new MapHelper();
                            this.d_gameMap = l_gameMapHelper.loadMap(l_mapFileName);
                            if (this.d_gameMap == null) {
                                this.d_gamePhase = Phase.NULL;
                            } else {
                                this.d_gamePhase = Phase.STARTUP;
                            }
                        } catch (Exception e) {
                            System.out.println(Constant.ERROR_COLOR
                                    + "Invalid command! Try command -> loadmap <mapName>"
                                    + Constant.RESET_COLOR);
                        }
                        break;
                    case "validatemap":
                        try {
                            if (!isValidCommandArgument(l_data, 1)) {
                                System.out.println(Constant.ERROR_COLOR
                                        + "Invalid number of arguments for validatemap command"
                                        + Constant.RESET_COLOR);
                                break;
                            }
                            MapValidator l_mapValidator = new MapValidator();
                            if (l_mapValidator.isValidMap(this.d_gameMap)) {
                                System.out.println(
                                        Constant.SUCCESS_COLOR + "Map is validated successfully!"
                                                + Constant.RESET_COLOR);
                            }
                        } catch (Exception e) {
                            System.out.println(Constant.ERROR_COLOR + "Invalid Map!"
                                    + Constant.RESET_COLOR);
                        }
                        break;
                    default:
                        System.out.println(Constant.ERROR_COLOR + "Invalid command!" + Constant.RESET_COLOR);
                        System.out.println(Constant.ERROR_COLOR +
                                "Try any of following command: editcontinent, editcountry, editneighbor, savemap, showmap, editmap, loadmap, validatemap"
                                + Constant.RESET_COLOR);
                        break;
                }
            }
            // Staup phase commands: gameplayer, assigncountries, showmap
            else if (this.d_gamePhase.equals(Phase.STARTUP)) {
                switch (l_commandName) {
                    case "gameplayer":
                        if (!isValidCommandArgument(l_data, 3)) {
                            System.out.println(Constant.ERROR_COLOR
                                    + "Invalid number of arguments for gamemap command" + Constant.RESET_COLOR);
                            System.out.println(Constant.ERROR_COLOR
                                    + "Try gameplayer -add playername or gameplayer -remove playername"
                                    + Constant.RESET_COLOR);
                            break;
                        }
                        Player l_player = new Player(l_data[1]);
                        l_player.managePlayer(this.d_playerList, this.d_gamePhase, l_data);
                        break;

                    // case "assigncountries":
                    //     boolean l_check = d_StartUp.assignCountries(d_Map, d_Players);
                    //     if (l_check) {
                    //         System.out.println("Countries allocated randomly amongst Players");
                    //         d_GamePhase = Phase.ISSUE_ORDERS;
                    //     }
                    //     d_GamePhase = Phase.ISSUE_ORDERS;
                    //     break;

                    case "showmap":
                        if (!isValidCommandArgument(l_data, 1)) {
                            System.out.println(Constant.ERROR_COLOR
                                    + "Invalid number of arguments for showmap command"
                                    + Constant.RESET_COLOR);
                            break;
                        }
                        MapHelper l_gameMap = new MapHelper();
                        l_gameMap.showMap(this.d_gameMap);
                        break;
                    default:
                        System.out.println(Constant.ERROR_COLOR +
                                "Invalid command - use gameplayer command or assigncountries command or showmap command in the start up phase!"
                                + Constant.RESET_COLOR);
                        break;
                }
            }
            return this.d_gamePhase;
        } catch (

        Exception e) {
            System.out.println(Constant.ERROR_COLOR +
                    "Invalid command!"
                    + Constant.RESET_COLOR);
            return Phase.NULL;
        }
    }

    /**
     * Checks if the command arguments are valid for the command
     * 
     * @param p_commandArgs        Command line arguments
     * @param p_expectedArgsLength Expected number of arguments
     * @return Returns true if number of arguments is valid, false otherwise
     */
    public static boolean isValidCommandArgument(String[] p_commandArgs, int p_expectedArgsLength) {
        return p_commandArgs != null && p_commandArgs.length == p_expectedArgsLength;
    }
}
