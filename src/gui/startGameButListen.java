package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

/**
 * The startGameButListen is an ActionListener implementation that responds to the press of the button 'Lets go!'.
 * This button is present / visible when the game is first started, and the users of the game are prompted to enter their names.
 * Pressing this button will retrieve text values from JTextAreas that represent the different playable characters.
 * The Text values are retrieved from the JTextAreas as strings. These strings are then passed to back to the gui.
 * The gui then takes these strings and constructs a new Board Object, passing the Strings in.
 * The Board Object now exists containing the users names as strings.
 * @author griffiryan
 *
 */
public class startGameButListen implements ActionListener {

	private String[] characters = {"Miss Scarlett", "Colonel Mustard", "Mrs White", "Reverend Green", "Mrs Peacock", "Professor Plum"};

	private int startWindowHeight;
	private int startWindowWidth;

	private int playersCount;

	private List<String> names;

	private List<JTextArea> activePlayers;

	private gui game;

	public startGameButListen(gui g){
		game = g;

		startWindowHeight = g.getAltWindowHeight();
		startWindowWidth = g.getAltWindowWidth();

		playersCount = 0;

		names = new ArrayList<String>();


	}

/**
 * This method is called when the button 'Lets go!' is pressed in the startGame Window.
 * Initially, it checks that an appropriate number of players have entered their names and are playing the game.
 * If this precondition is not met, an error message is displayed and the name input frame is displayed again.
 * If this precondition is met, the startUp window is removed and game play starts.
 *
 * @param ActionEvent
 *
 */
	public void actionPerformed(ActionEvent e) {
		activePlayers = game.getPlayerNameEntries();
		getActivePlayers();

		if(playersCount<3){
			System.out.println("3-6 Players needed to play !");
			game.getStartUpFrame().dispose();
			game.welcomePlayers();


		}
		else{

			game.getStartUpFrame().dispose();
			game.gameStarted(names);

		}


	}

/**
 * the getActivePlayers method is used to retrieve String values from the JTextAreas, in the initial player name entry window.
 * The constructor for this class retrieves a List of the JText areas from the gui.
 * This method then determines whether the strings entered into the JTextAreas are to be active users playing the game.
 * This is done by comparing the entered strings to a predefined set of Character Strings, and to null.
 * If the entered string is equal to either of these two things, the name string is not regarded as the name of an active player.
 */
	private void getActivePlayers() {
		for(int i = 0; i<activePlayers.size(); i++){
			if((!activePlayers.get(i).getText().equals(characters[i]))
					&&(!activePlayers.get(i).getText().equals(""))){
				names.add(activePlayers.get(i).getText());
				playersCount++;
			}
			else{
				names.add("");
			}
		}
	}
}
