package com.riskgame.utility;

/**
 * Describes the current phase of the game.
 */
public enum Phase {
    /**
     * Initialization phase when players join the game using CLI.
     * Phase ends when <code>editmap<code> or <code>loadmap</code> command is given.
     */
    NULL,

    /**
     * Map editing phase. Allows players to add/remove continents, countries, and
     * neighbors.
     * Phase ends when <code>loadmap</code> command is encountered.
     */
    EDITMAP,

    /**
     * Individual turn of player. Players add orders to the list in their turns.
     * Phase ends when all players provide orders for their turns.
     */
    ISSUE_ORDERS,
}
