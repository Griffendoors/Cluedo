package interaction;

import gui.gui;
import gui.suggestSelectMouseListen;

import java.awt.BorderLayout;
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
import pieces.Weapon;
import squares.Room;
import squares.Square;
import cards.Card;
/**
 * The Suggestion class implements all of the logic that is present in game, for a player to make a suggestion.
 * The suggestion recieves 3 strings from the game interface.
 * The Suggestion determines the current player, and cycles through the remaining players to the current players left.
 * Each of the remaining players in turn, have their hand checked for any of the cards nominated in the suggestion.
 * If a card is contained, this Player is prompted to select one of the cards in their hand that is also contained in the suggestion, to display to the suggestor.
 * The events that transpire in the suggestion are displayed in the main game windows events list, save the card that was actually shown to the suggestor.
 * 
 * @author griffiryan
 *
 */
public class Suggestion {

	private String suggestedWeapon;
	private String suggestedCharacter;
	private String suggestedRoom;

	private String shownCard;

	private List<String>suggestionList;

	private List<Card> otherActorsCards = new ArrayList<Card>();

	private final JFrame selectFrame = new JFrame("Select a card by Double Clicking");

	private gui game;

	private Actor proceeding;

	public Suggestion(String w, String r, String c, gui g) {
		suggestedWeapon = w;
		suggestedCharacter = c;
		suggestedRoom = r;
		game = g;

		suggestionList = new ArrayList<String>();
		suggestionList.add(w);
		suggestionList.add(r);
		suggestionList.add(c);


		//Once a suggestion is made/submitted, a players option to move / change their mind is forfeit - thus fixing the position that they have moved to.
		game.getPlayingBoard().resetValidMoves();


		checkOthersForCards();
		bringTokensToRoom();
	}


	/**
	 * When a suggestion is made in cluedo, The character and room contained in the suggestion are brought to the room where the suggestion is made
	 */
	private void bringTokensToRoom() {
		//get suggested actor
		Actor suggestionActor = null;
		for (Actor act: game.getPlayingBoard().getActors()){
			if (act.getActorName().equals(this.suggestedCharacter)){
				suggestionActor = act;
			}
		}

		//only move actor if im not suggesting myself
		Room suggestionRoom = (Room)game.getPlayingBoard().getCurrentActor().getSquare();
		if (!suggestionActor.equals(game.getPlayingBoard().getCurrentActor())){
			//clear room actor was in
			Square oldRoom = suggestionActor.getSquare();
			oldRoom.getActors().remove(suggestionActor);

			//bring in actor to room
			//Room suggestionRoom = (Room)game.getPlayingBoard().getCurrentActor().getSquare();
			suggestionRoom.addActor(suggestionActor);
		}


		//get suggested weapon
		Weapon suggestionWeapon = null;
		for (Weapon weap: game.getPlayingBoard().getWeapons()){
			if (weap.getName().equals(this.suggestedWeapon)){
				suggestionWeapon = weap;
			}
		}
		//clear weapon from the room it was in
		Room oldWeaponRoom = (Room)suggestionWeapon.getSquare();
		oldWeaponRoom.getWeapons().remove(suggestionWeapon);
		//bring weapon into room;
		suggestionRoom.addWeapon(suggestionWeapon);
	}

	/**
	 * This Method cycles through players to the left of the player that made the suggestion.
	 * The first player to have a card in their hand that is a part of the suggestion is selected to show the suggestor one of:
	 * The cards in their hand, that is also in the suggestion.
	 */
	private void checkOthersForCards() {
		int pos = 0;
		boolean cardFound = false;
		//6 is number of actors
		for (int i = 0; i < 6; i++){
			if (game.getPlayingBoard().getCurrentActor().equals(game.getPlayingBoard().getActors().get(i))){
				pos = i;
			}
		}
		//6 is number of actors, starting from 1 because we only need to check the 5 other actors
		for (int i = 1; i < 6; i++){
			proceeding = game.getPlayingBoard().getActors().get((pos + i) % 6);

			if(compareCards(proceeding)){
				cardFound = true;
				nextPlayerWithCard(proceeding);
				break;
			}
		}
		// no other players had any of the cards that the suggestion made
		if (!cardFound){
			failedSuggestion();
		}
	}

	/**
	 * The card that will be shown to the suggestor is set as 'ShownCard'.
	 * @param c
	 */
	public void setShownCard(String c) {
		shownCard = c;
	}

	/**
	 * A window appears to notify the suggesting player that no other palyers contained cards in their hands that matched cards in their suggestion
	 * The events List in the game window is updated to append this notification, thus all other players are aware of the failed suggestion.
	 */
	private void failedSuggestion() {
		//add events of this to the events list that is displayed
		String user = game.getPlayingBoard().getCurrentActor().getUser();
		game.addToEvents(user+" suggested:\n"+suggestedCharacter+"\nin the "+suggestedRoom+"\nwith the "+suggestedWeapon+"\nNo other players\nhave these cards.\n\n");

		final JFrame failSuggestionFrame = new JFrame("Sorry!");
		failSuggestionFrame.setSize(500,500);

		failSuggestionFrame.setVisible(true);

		JPanel failContent = new JPanel(new FlowLayout());
		failSuggestionFrame.setContentPane(failContent);

		JButton cont = new JButton("No other players with these cards");
		cont.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
				game.endTurn();
				failSuggestionFrame.dispose();
			}});

		failContent.add(cont);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - failSuggestionFrame.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - failSuggestionFrame.getHeight()) / 2);
		failSuggestionFrame.setLocation(x, y);

		failSuggestionFrame.pack();
		failSuggestionFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

	}

	/**
	 * This method hides the main game interface to ensure privacy among players cards
	 * The player selected to show a card to the suggestor is prompted to take control.
	 * Once they player holding a suggested card has confirmed, a new window appears that allows the player to select a card to show the suggestor.
	 * @param proceeding
	 */
	private void nextPlayerWithCard(final Actor proceeding) {
		game.getFrameMain().setVisible(false);

		game.getStartUpFrame().setVisible(false);

		String holdingActor = proceeding.getUser();

		final JFrame SuggestionFrame = new JFrame("Sorry!");
		SuggestionFrame.setSize(500,500);

		SuggestionFrame.setVisible(true);

		JPanel content = new JPanel(new FlowLayout());
		SuggestionFrame.setContentPane(content);

		JButton cont = new JButton(holdingActor+" ,Please choose a card to show suggestor");
		cont.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
				game.getFrameMain().setVisible(false);
				selectCards(proceeding);
				SuggestionFrame.dispose();

			}});


		content.add(cont);


		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - SuggestionFrame.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - SuggestionFrame.getHeight()) / 2);
		SuggestionFrame.setLocation(x, y);

		SuggestionFrame.pack();
		SuggestionFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		//add events of this to the events list that is displayed
		String user = game.getPlayingBoard().getCurrentActor().getUser();
		game.addToEvents(user+" suggested:\n"+suggestedCharacter+"\nin the "+suggestedRoom+"\nwith the "+suggestedWeapon+"\nand "+proceeding.getUser()+" had a card.\n\n");
	}

	/**
	 *This method provides a minimal interface for the player described as 'actor' to select one of their cards, that is also contained in the suggestion, to show to the suggestor.
	 * @param actor
	 */

	private void selectCards(final Actor actor){

		final JPanel content = new JPanel(new BorderLayout());
		selectFrame.setContentPane(content);


		JComponent chooseCard = new JComponent(){
			protected void paintComponent(Graphics g){
				selectFrame.setSize(330,150);

				int cardPlaceX = 100; //starting point for card
				int cardPlaceY = 0; //always 0
				int cardGap = 0;	// gap between cards on display


				for(Card card : actor.getCards()){
					if (suggestionList.contains(card.getName()) && !otherActorsCards.contains(card)){
						otherActorsCards.add(card);

					}
				}

				for(int i = 0; i < otherActorsCards.size(); i++){
					otherActorsCards.get(i).draw(cardGap+(cardPlaceX*i),cardPlaceY,g);
				}
			}
		};

		content.add(chooseCard, BorderLayout.CENTER);
		selectFrame.pack();

		chooseCard.addMouseListener(new suggestSelectMouseListen(this));

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - selectFrame.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - selectFrame.getHeight()) / 2);
		selectFrame.setLocation(x, y);

		selectFrame.setVisible(true);
		selectFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		selectFrame.setResizable(false);

	}
/**
 * When a player has nominated a card they wish to show to the suggestor, a minimal interface appears to display that cards information to the suggestor only.
 */
	public void displayShownCard(){
		selectFrame.dispose();

		final String title = game.getPlayingBoard().getCurrentActor().getUser() + ", " + proceeding.getUser() + " had this card: " + shownCard;
		final String instructions = "\nUpdate detective notes then click 'End Turn'";

		final JFrame shownFrame = new JFrame("End of Suggestion");
		shownFrame.setSize(350,110);
		JPanel content = new JPanel(new GridLayout(2,1));
		shownFrame.setContentPane(content);

		JComponent component = new JComponent(){
			protected void paintComponent(Graphics g){
				g.drawString(title,0,12);
				g.drawString(instructions,0,24);
			}
		};

		content.add(component);

		JButton continuePlay = new JButton("OK");
		continuePlay.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				shownFrame.dispose();
				game.getFrameMain().setVisible(true);

			}
		});


		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - shownFrame.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - shownFrame.getHeight()) / 2);
		shownFrame.setLocation(x, y);




		content.add(continuePlay);

		shownFrame.setResizable(false);
		shownFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		shownFrame.setVisible(true);
		//shownFrame.pack();
	}


	/**
	 * Compares the cards in the given actors hands to the cards in the suggestion.
	 * If the actor contains one of the cards, the method returns true.
	 * @param proceeding
	 * @return
	 */
	private boolean compareCards(Actor proceeding) {
		for(Card c: proceeding.getCards()){
			if (suggestionList.contains(c.getName()))
				return true;
		}
		return false;
	}


	public String getShownCard() {
		return shownCard;
	}

	public List<Card> getOtherActorsCards() {
		return otherActorsCards;
	}


}
