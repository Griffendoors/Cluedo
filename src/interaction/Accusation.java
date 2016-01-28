package interaction;

import gui.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import pieces.Actor;
import cards.Card;

/**
 * The Accusation class implements all of the logic that is present in game, for a player to make a Accusation.
 * The Accusation recieves 3 strings from the game interface.
 * The Accusation retrieves the solution from the board, and compares the made accusation against the solution.
 * If the accusation is correct, the player that made the accusation wins and the game is over.
 * Otherwise, the player is removed from play and the game information is updated accordingly.
 *
 * @author griffiryan
 *
 */
public class Accusation {

	private String weapon;
	private String character;
	private String room;
	private gui game;


	private List<String>accusation;

	int win;


	public Accusation(String w, String r, String c, gui g) {

		win = 0;

		game = g;

		accusation = new ArrayList<String>();
		accusation.add(w);
		accusation.add(r);
		accusation.add(c);

		// Making an accusation fixes a players character in place and prevents further movement
		game.getPlayingBoard().resetValidMoves();

		checkAccusation();
	}
	/**
	 * The solution is retrieved from the associated board.
	 * All cards contained in the accusation must match the solution entirely.
	 */
	private void checkAccusation() {
		List<Card>solution = game.getPlayingBoard().getSolution();
		for(Card c : solution){
			if(accusation.contains(c.getName())) win++;
		}
		if(win==3)	{
			win();}
		else lose();

	}
	/**
	 * Correct accusation:
	 * Minimal window appears describing a correct guess and the game is over.
	 */
	public void win(){
		final JFrame winFrame = new JFrame();
		final JPanel winContent = new JPanel(new FlowLayout());

		winFrame.setContentPane(winContent);
		winFrame.setSize(new Dimension(500,500));
		winFrame.setVisible(true);

		JButton continueGame = new JButton("Correct Accusation! You have Won!");
		continueGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				winFrame.dispose();
				game.reset();

			}});

		winContent.add(continueGame);
		winFrame.pack();


		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - winFrame.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - winFrame.getHeight()) / 2);
		winFrame.setLocation(x, y);






	}

	/**
	 * incorrect guess:
	 * minimal window appears describing incorrect guess and game play continues.
	 * game information is updated to remove player that made accusation from play.
	 */
	public void lose(){
		//add events of this to the events list that is displayed
		String user = game.getPlayingBoard().getCurrentActor().getUser();
		game.addToEvents(user+" made a false accusation.\n\n");

		final JFrame loseFrame = new JFrame();
		final JPanel loseContent = new JPanel(new FlowLayout());

		loseFrame.setContentPane(loseContent);
		loseFrame.setSize(new Dimension(500,500));
		loseFrame.setVisible(true);

		JButton button = null;
		if (game.getPlayingBoard().getActiveActors().size() == 1){//last player makes a wrong accusation, end game, no winner
			button = new JButton("Incorrect accusation! No players left. Click to restart");
		}
		else{
			button = new JButton("Incorrect accusation! You're removed from play.");
		}

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (game.getPlayingBoard().getActiveActors().size() == 1){//last player makes a wrong accusation, end game, no winner
					loseFrame.dispose();
					game.reset();
				}
				else{

					Actor loser = game.getPlayingBoard().getCurrentActor();
					game.getPlayingBoard().removeActor(loser);

					loseFrame.dispose();
					game.endTurn();
				}



			}});

		loseContent.add(button);
		loseFrame.pack();


		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - loseFrame.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - loseFrame.getHeight()) / 2);
		loseFrame.setLocation(x, y);

	}

}
