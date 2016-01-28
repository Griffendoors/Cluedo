package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import interaction.Accusation;
import interaction.Suggestion;

/**
 * The startAcuButListen class is an ActionListener implementation that responds to the pressing of one button, the 'Accuse' button,
 * in the games main interface. It sets up an alternative window for the user to select JList values to make a game 'Accusation'.
 * This class retrieves selected values from JLists, constructs a new Accusation class passing the selected JList values to it as strings.
 *
 * @author griffiryan
 */

public class startAcuButListen implements ActionListener {

	private String[] characters = {"Miss Scarlett", "Colonel Mustard", "Mrs White", "Reverend Green", "Mrs Peacock", "Professor Plum"};
	private String[] weapons = {"Candlestick","Dagger", "Lead Pipe", "Revolver","Rope","Spanner"};
	private String[] rooms = {"Kitchen","Ballroom", "Conservatory", "Dining Room","Billiard Room","Library","Lounge","Hall","Study"};

	private String accuseWeapon;
	private String accuseCharacter;
	private String accuseRoom;

	private int acuWindowHeight;
	private int acuWindowWidth;


	private JFrame accusationWindow;
	private JPanel accusationPanel;
	private JButton accusationSubmit;

	private JList charactersList;
	private JList weaponsList;
	private JList roomsList;


	private gui game;


	public startAcuButListen(gui g){

		game = g;

		acuWindowHeight = g.getAltWindowHeight();
		acuWindowWidth = g.getAltWindowWidth();

		createJLists();

	}

	/**
	 * The Method that is called when the 'Accuse' Button in the games main interface is called
	 * A new JFrame is created which contains 3 JLists representing Characters, Rooms and Weapons, as well as a Button
	 * for the user to submit and make their accusation to the game
	 * @param ActionEvent
	 */
	public void actionPerformed(ActionEvent e) {

		accusationWindow = new JFrame("Accuse!");
		accusationPanel = new JPanel(new GridLayout(1,4));
		accusationSubmit = new JButton("Submit Accusation");

		accusationSubmit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(!getAccusedValues()) return;
				Accusation accusation = new Accusation(accuseWeapon,accuseRoom,accuseCharacter,game);
				resetAccusedValues();
				accusationWindow.dispose();

			}
		});

		accusationWindow.setVisible(true);
		accusationWindow.setSize(acuWindowWidth,acuWindowHeight);
		accusationPanel.setBorder(BorderFactory.createTitledBorder("Make an Accusation"));

		accusationPanel.add(weaponsList);
		accusationPanel.add(charactersList);
		accusationPanel.add(roomsList);
		accusationPanel.add(accusationSubmit);
		accusationWindow.add(accusationPanel);
	}

	/**
	 * Populate the JLists with the possible values for rooms and characters and weapons, so that the current player may select values from the JLists.
	 * Give the JLists titled borders
	 */
	private void createJLists() {
		charactersList = new JList(characters);
		weaponsList = new JList(weapons);
		roomsList = new JList(rooms);
		weaponsList.setBorder(BorderFactory.createTitledBorder("Characters"));
		charactersList.setBorder(BorderFactory.createTitledBorder("Weapons"));
		roomsList.setBorder(BorderFactory.createTitledBorder("Rooms"));

	}


	/**
	 * To ensure the next player cannot see the accusation the previous player made,
	 * The values the JList are currently selecting are reset
	 */
	protected void resetAccusedValues() {
		weaponsList.clearSelection();
		charactersList.clearSelection();
		roomsList.clearSelection();
	}


	/**
	 * Accused values are taken from the users input.
	 * The User selects values from the Provided JLists (Characters, Weapons and Room).
	 * These are passed to the Accusation class as strings.
	 */
	protected boolean getAccusedValues() {
		accuseWeapon = (String) weaponsList.getSelectedValue();
		accuseCharacter = (String)charactersList.getSelectedValue();
		accuseRoom = (String)roomsList.getSelectedValue();
		if((accuseWeapon==null)||(accuseCharacter==null)||(accuseRoom==null)) return false;
		return true;
	}





}
