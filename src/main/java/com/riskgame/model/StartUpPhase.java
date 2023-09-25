package com.riskgame.model;

import main.java.com.riskgame.model.Continent;
import main.java.com.riskgame.model.GameMap;
import main.java.com.riskgame.model.MapHelper;
import main.java.com.riskgame.model.Phase;
import main.java.com.riskgame.model.Player;
import main.java.com.riskgame.utility.Constant;

/**
 * Implements parsing of initial commands during startup phase.
 */
public class StartUpPhase {

    public Phase d_gamePhase;
    public GameMap d_gameMap;

    /**
     * Initialize StartUpPhase
     */
    public StartUpPhase() {
        this.d_gamePhase = Phase.NULL;
    }

    /**
     * Parses the command received from player during startup phase
     * 
     * @param p_player  Player who is playing.
     * @param p_command Command to be executed.
     * @return Returns next game phase.
     */
    public Phase parseCommand(Player p_player, String p_command) {
        String[] l_data = p_command.split("\\s+");
        String l_commandName = l_data[0];

        // Parse initial command
        if (this.d_gamePhase.equals(Phase.NULL)) {
            switch (l_commandName) {
                case "editmap":
                    try {
                        String l_mapFileName = l_data[1];
                        MapHelper l_gameMap = new MapHelper();
                        this.d_gameMap = l_gameMap.editMap(l_mapFileName);
                        System.out.println("Editing for Map: " + l_mapFileName + "\n");
                        System.out.println("Edit continent using " + Constant.SUCCESS_COLOR
                                + "editcontinent -add <continentId> <continentvalue> or editcontinent -remove <continentId>"
                                + Constant.RESET_COLOR);
                        System.out.println("Edit country using " + Constant.SUCCESS_COLOR
                                + "editcountry -add <countryId> <continentId> or editcountry -remove <countryId>"
                                + Constant.RESET_COLOR);
                        System.out.println("Edit neighbor using " + Constant.SUCCESS_COLOR
                                + "editneighbor -add <countryId> <neighborcountryId> or editneighbor -remove <countryId> <neighborcountryId>"
                                + Constant.RESET_COLOR + "\n");

                        this.d_gamePhase = Phase.EDITMAP;
                        break;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println(Constant.ERROR_COLOR + "Invalid command! Try command -> editmap sample.map"
                                + Constant.RESET_COLOR);
                    }
                    break;
                default:
                    System.out.println(Constant.ERROR_COLOR + "Invalid command! Try command -> editmap <mapName> or loadmap <mapName>"
                            + Constant.RESET_COLOR);
                    break;
            }
        }

        // Edit phase.
        // Edit phase commands: editcontinent, editcountry, editneighbour, savemap,
        // showmap, editmap, loadmap, validatemap
        else if (this.d_gamePhase.equals(Phase.EDITMAP)) {
            Continent continent = new Continent();
            switch (l_commandName) {
                case "editcontinent":
                    continent.editContinent(this.d_gameMap, this.d_gamePhase, l_data);
                    break;
                default:
                    System.out.println(Constant.ERROR_COLOR + "Invalid command!" + Constant.RESET_COLOR);
                    System.out.println(
                            "Try any of following command: editcontinent, editcountry, editneighbour, savemap, showmap, editmap, loadmap, validatemap");
                    break;
            }
        }
        return this.d_gamePhase;
    }
}
