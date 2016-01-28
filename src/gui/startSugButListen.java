package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import squares.Room;
import squares.Square;
import interaction.Suggestion;
import board.Board;

/**
 * The startSugButListen class is an ActionListener implementation that responds to the pressing of one button, the 'Suggest' button,
 * in the games main interface. It sets up an alternative window for the user to select JList values to make a game 'Suggestion'.
 * This class retrieves selected values from JLists, constructs a new Suggestion class passing the selected JList values to it as strings.
 *
 * @author griffiryan
 *
 */

public class startSugButListen implements ActionListener {


	private String[] characters = {"Miss Scarlett", "Colonel Mustard", "Mrs White", "Reverend Green", "Mrs Peacock", "Professor Plum"};
	private String[] weapons = {"Candlestick","Dagger", "Lead Pipe", "Revolver","Rope","Spanner"};

	String suggestWeapon;
	String suggestRoom;
	String suggestCharacter;

	private int sugWindowHeight;
	private int sugWindowWidth;

	private JFrame suggestionWindow;
	private JPanel suggestPanel;
	private JButton suggestSubmit;

	private JList charactersList;
	private JList weaponsList;

	private gui game;

	private Board playingBoard;




	public startSugButListen(gui g){

		game = g;
		playingBoard = game.getPlayingBoard();



		sugWindowHeight = g.getAltWindowHeight();
		sugWindowWidth = g.getAltWindowWidth();


		createJLists();

	}
	/**
	 * The Method that is called when the 'Suggest' Button in the games main interface is called
	 * A new JFrame is created which contains 2 JLists representing Characters and Weapons, as well as a Button
	 * for the user to submit and make their suggestion to the game
	 */
	public void actionPerformed(ActionEvent e) {
		JDialog dialog;


		//Check player has moved, if not a dialog box is displayed to tell the user
		if(!checkPlayerMoved()){
			dialog = new JDialog(game.getFrameMain(),"You must Move to make a suggestion!",false);
			dialog.setSize(500, 0);
			dialog.setVisible(true);

			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			int x = (int) ((dimension.getWidth() - dialog.getWidth()) / 2);
			int y = (int) ((dimension.getHeight() - dialog.getHeight()) / 2);
			dialog.setLocation(x, y);

			return;
		}

		//Check player is in a room
		if(!checkPlayerinRoom())return;


		// Suggestion can be made if Both of these tests pass


		suggestionWindow = new JFrame("Suggestion!");
		suggestPanel = new JPanel(new GridLayout(1,4));
		suggestSubmit = new JButton("Submit Suggestion");

		suggestSubmit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
				if(!getSuggestedValues()) return;
				Suggestion suggestion = new Suggestion(suggestWeapon,suggestRoom,suggestCharacter,game);
				resetSuggestedValues();
				suggestionWindow.dispose();

			}});

		suggestionWindow.setVisible(true);
		suggestionWindow.setSize(sugWindowWidth,sugWindowHeight);
		suggestPanel.setBorder(BorderFactory.createTitledBorder("Make a Suggestion"));

		suggestPanel.add(weaponsList);
		suggestPanel.add(charactersList);
		suggestPanel.add(suggestSubmit);
		suggestionWindow.add(suggestPanel);



	}

	/**
	 * Populate the JLists with the possible values for weapons and characters, so that the current player may select values from the JLists.
	 * Give the JLists titled borders
	 */
	private void createJLists() {
		charactersList = new JList(characters);
		weaponsList = new JList(weapons);
		weaponsList.setBorder(BorderFactory.createTitledBorder("Characters"));
		charactersList.setBorder(BorderFactory.createTitledBorder("Weapons"));

	}

	/**
	 * To ensure the next player cannot see the suggestion the previous player made,
	 * The values the JList are currently selecting are reset
	 */
	protected void resetSuggestedValues() {
		weaponsList.clearSelection();
		charactersList.clearSelection();

	}


	/**
	 * Suggested values are taken from the users input.
	 * The User selects values from the Provided JLists (Characters and weapons, The room is dependent on the room the character is in).
	 * These are passed to the suggestion class as strings
	 */
	protected boolean getSuggestedValues() {
		suggestWeapon = (String) weaponsList.getSelectedValue();
		suggestRoom = game.getPlayingBoard().getCurrentActor().getSquare().getName();
		suggestCharacter = (String)charactersList.getSelectedValue();
		if((suggestWeapon==null)||(suggestRoom==null)||(suggestCharacter==null)) return false;
		return true;

	}


	/**
	 * Suggestions can only be made by a player, when that player is in a room.
	 * @return boolean
	 */
	private boolean checkPlayerinRoom() {
		//get the square the actor is in
		Square square = playingBoard.getCurrentActor().getSquare();
		//check if square is a room, if not, return
		if (!(square instanceof Room))return false;
		return true;
	}


	/**
	 * The acting player cannot make a suggestion if they have not moved
	 * ie. not possible to stay in same room and make repeated suggestions
	 *
	 * @return boolean
	 */
	private boolean checkPlayerMoved() {

		if(playingBoard.getCurrentActor().moved()){
			return true;
		}
		return false;
	}

}
