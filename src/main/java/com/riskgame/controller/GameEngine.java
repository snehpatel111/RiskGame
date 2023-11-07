package com.riskgame.controller;

import com.riskgame.model.GameState;
import com.riskgame.model.IssueOrderPhase;
import com.riskgame.model.OrderExecutionPhase;
import com.riskgame.model.Phase;
import com.riskgame.model.StartUpPhase;

/**
 * This is the entry point of the Game and keeps the track of current Game
 * State.
 */
public class GameEngine {
	/**
	 * d_gameState stores the information about current GamePlay.
	 */
	GameState d_gameState = new GameState();

	/**
	 * It is the current game play phase as per state pattern.
	 */
	Phase d_currentGamePhase = new StartUpPhase(this, this.d_gameState);

	/**
	 * It's used to update context.
	 *
	 * @param p_phase new Phase to set in Game context
	 */
	private void setCurrentGamePhase(Phase p_phase) {
		this.d_currentGamePhase = p_phase;
	}

	/**
	 * Updates the current phase to Issue Order Phase as per State Pattern.
	 */
	public void setIssueOrderPhase() {
		System.out.println("lol playerList size is " + this.d_gameState.getPlayerList().size());
		this.setGameEngineLog("Issue Order Phase", "phase");
		this.setCurrentGamePhase(new IssueOrderPhase(this, this.d_gameState));
		this.getCurrentGamePhase().initPhase();
	}

	// /**
	//  * this methods updates the current phase to Order Execution Phase as per State
	//  * Pattern.
	//  */
	// public void setOrderExecutionPhase() {
	// 	this.setGameEngineLog("Order Execution Phase", "phase");
	// 	this.setCurrentGamePhase(new OrderExecutionPhase(this, d_gameState));
	// 	this.getCurrentGamePhase().initPhase();
	// }

	/**
	 * This method is getter for current Phase of Game Context.
	 *
	 * @return current Phase of Game Context
	 */
	public Phase getCurrentGamePhase() {
		return this.d_currentGamePhase;
	}

	/**
	 * Shows and Writes GameEngine Logs.
	 *
	 * @param p_gameEngineLog String of Log message.
	 * @param p_logType       Type of Log.
	 */
	public void setGameEngineLog(String p_gameEngineLog, String p_logType) {
		this.d_currentGamePhase.getGameState().updateLog(p_gameEngineLog, p_logType);
		// String l_consoleLogger = p_logType.toLowerCase().equals("phase")
		// 		? "\n************ " + p_gameEngineLog + " ************\n"
		// 		: p_gameEngineLog;
		// System.out.println(l_consoleLogger);
	}

	/**
	 * The main method responsible for accepting command from users and redirecting
	 * those to corresponding logical flows.
	 *
	 * @param p_args the program doesn't use default command line arguments
	 */
	public static void main(String[] p_args) {
		GameEngine l_game = new GameEngine();

		l_game.getCurrentGamePhase().getGameState().updateLog("Initializing the Game ......" + System.lineSeparator(),
				"start");
		l_game.setGameEngineLog("Game Startup Phase", "phase");
		l_game.getCurrentGamePhase().initPhase();
	}
}
