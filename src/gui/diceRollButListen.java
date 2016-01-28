package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The diceRollButListen is an ActionListener implementation that responds to the pressing of one button, the 'Roll Dice' button,
 * in the games main interface. It calls the associated Boards, rollDice() method (which generates an aritrary value between 2-12).
 * This value is then sent to the gui as the current dice roll value.
 *
 * @author griffiryan
 *
 */
public class diceRollButListen implements ActionListener{


	gui game;

	public diceRollButListen(gui g){
		game = g;

	}


/**
 * Check the current player has not already rolled the dice.
 * generates a random dice roll.
 * update the guis current dice roll value.
 */
	public void actionPerformed(ActionEvent e) {
		// Check the dice has not been previously rolled this turn
		if(game.getCurrentRoll()!=0) return;

		// roll the dice by calling rollDice() on the guis associated Board class
		int currentRoll = game.getPlayingBoard().rollDice();

		//update the guis roll value to the value rolled by pressing the button
		game.setCurrentRoll(currentRoll);

	}

}
