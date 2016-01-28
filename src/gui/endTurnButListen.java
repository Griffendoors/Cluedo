package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The endTurnButListen is an ActionListener implementation that responds to the pressing of one button, the 'End Turn' button,
 * in the games main interface. It calls the associated Boards, endTurn() method (if the required precondition is met).
 * The method endTurn() is then called from the Board class.
 *
 * @author griffiryan
 */
public class endTurnButListen implements ActionListener {


	gui game;

	public endTurnButListen(gui g){
		game = g;

	}



	/**
	 * Check the player has moved, before allowing them to end their turn.
	 * If the player is eligible to end their turn, the associated Board's endTurn()
	 * method is called.
	 */
	public void actionPerformed(ActionEvent e) {
		if (!game.getPlayingBoard().getCurrentActor().moved()) return;
		game.endTurn();

	}





}
