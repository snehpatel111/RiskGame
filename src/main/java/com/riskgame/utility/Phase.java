package com.riskgame.utility;

/**
 * Describes the current phase of the game.
 */
public enum Phase {
    /**
     * Initialization phase when players join the game using CLI.
     * Phase ends when editmap or loadmap command is given.
     */
    NULL,

    /**
     * Map editing phase. Allows players to add/remove continents, countries, and
     * neighbors.
     * Phase ends when loadmap command is encountered.
     */
    EDITMAP,

    /**
     * Starting phase of the game. Players will be added in this phase and assigned
     * armies.
     * This phase starts after "loadmap" and ends when all players have been
     * assigned armies which is after "assignedarmies" command.
     */
    STARTUP,

    /**
     * Execution of orders phase. Orders from previous phase are executed
     * sequentially from the pool of orders.
     * Phase ends when all armies are placed on countries after order execution.
     */
    EXECUTE_ORDERS,

    /**
     * Ends the turn of current player and give signal to other player to start
     * their turn.
     * Phase ends when player ends his turn and next start his move.
     */
    SWITCH_TURN,

    /**
     * Individual turn of player. Players add orders to the list in their turns.
     * Phase ends when all players provide orders for their turns.
     */
    ISSUE_ORDERS,
    
    /**
     * End of game when all armies are deployed on countries.
     */
    END
}
