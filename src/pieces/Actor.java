package pieces;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JList;

import squares.Square;
import cards.ActorCard;
import cards.Card;

/**
 * This class represents the actor token that the user will play as
 * @author northleon
 *
 */
public class Actor extends Piece{
	private String actorName; //the name of the token in cluedo, Miss Scarlett etc.
	private String user; //The name of the person who is playing the game, Leon, Ryan etc.
	private Square oldSquare; //The previous square the actor was on
	private Square square; //the square the actor is currently on
	private List<Card> cards; //list of cards in the users hand
	private List<String> detectiveNotes; //list of notes that the user can update and modify to help them win the game




	/**
	 * Assigns the values and initialises the fields
	 * @param String actorName
	 * @param String user
	 */
	public Actor(String actorName, String user){
		cards = new ArrayList<Card>();
		this.actorName = actorName;
		this.user = user;

		detectiveNotes = new ArrayList<String>();

		String characters = "Miss Scarlett \nColonel Mustard \nMrs. White \nRev. Green \nMrs. Peacock \nProfessor Plum";
		String weapons = "Candlestick \nDagger \nLead Pipe \nRevolver \nRope \nSpanner";
		String rooms = "Kitchen \nBallroom \nConservatory \nDining Room \nBilliard Room \nLibrary \nLounge \nHall \nStudy";

		detectiveNotes.add(characters);
		detectiveNotes.add(weapons);
		detectiveNotes.add(rooms);

	}

	/**
	 * Checks if the actor has any of the suggestion cards in there hand
	 * @param List<String> suggestion
	 * @return bool
	 */
	public boolean checkSuggestion(List<String> suggestion){
		if (cards.size() != 3){throw new IllegalArgumentException();}
		for(String s: suggestion){
			for(Card c: cards){
				if (s.equals(c.getName())){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Draws the token representation of the actor to the board
	 */
	public void draw(Graphics g, int x, int y) {
		if (actorName.equals("Miss Scarlett")){
			g.setColor(new Color(240, 22, 22));
		}
		if (actorName.equals("Colonel Mustard")){
			g.setColor(new Color(240, 236, 125));
		}
		if (actorName.equals("Mrs White")){
			g.setColor(new Color(255, 255, 255));
		}
		if (actorName.equals("Reverend Green")){
			g.setColor(new Color(101, 194, 68));
		}
		if (actorName.equals("Mrs Peacock")){
			g.setColor(new Color(31, 24, 171));
		}
		if (actorName.equals("Professor Plum")){
			g.setColor(new Color(151, 79, 171));
		}
		g.fillOval(x+5, y+1, 15, 15);
		g.fillArc(x, y, 25, 24, 225, 90);
		g.setColor(new Color(0,0,0));
		g.drawOval(x+5, y+1, 15, 15);
	}

	/**
	 * Returns the square the actor is on
	 * @return Square
	 */
	public Square getSquare() {
		return square;
	}

	/**
	 * Returns the square the actor was on previously
	 * @return
	 */
	public Square getOldSquare() {
		return oldSquare;
	}

	/**
	 * Sets the old square to the argument given
	 * @param square
	 */
	public void setOldSquare(Square square) {
		this.oldSquare = square;
	}

	/**
	 * Sets the square the actor is on to the argument given.
	 * Sets the square the actor was on to the oldSquare
	 * @param square
	 */
	public void setSquare(Square square) {
		oldSquare = this.square;
		this.square = square;
	}

	/**
	 * Returns the actors name
	 * @return String
	 */
	public String getActorName() {
		return actorName;
	}

	/**
	 * Returns the name of the user
	 * @return String
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Adds the given card to the list of cards
	 * @param Card
	 */
	public void addCard(Card card) {
		cards.add(card);
	}

	/**
	 * Returns the list of cards
	 * @return
	 */
	public List<Card> getCards() {
		return cards;
	}

	/**
	 * Replaces the set of cards
	 * @param List<Card>
	 */
	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	/**
	 * Returns the actors name, and if they are in a square, ads the square to the string
	 * being returned
	 *
	 * @return String
	 */
	public String toString(){
		if(square != null){
			return "Actor: " + actorName + ". User: " + user + ". Square: "+square.toString();
		}
		return "Actor: " + actorName + ". User: " + user;
	}

	/**
	 * Sets the detectiveNotes field to the list given
	 * @param List<String>
	 */
	public void setDetectiveNotes(List<String> detNotes) {
		detectiveNotes = detNotes;
	}

	/**
	 * Returns the list of detective notes
	 * @return
	 */
	public List<String> getDetectiveNotes() {
		return detectiveNotes;
	}

	/**
	 * Draws the corresponding actors card and the users name
	 * @param Graphics g
	 * @param int width
	 * @param int height
	 */
	public void drawPortrait(Graphics g, int width, int height) {
		ActorCard portrait = new ActorCard(actorName);
		portrait.draw(37, 1, g); //37,1 are just number to center the picture on the panel
		g.setColor(Color.black);
		g.drawString(user, 49, 130);//draws the users name
	}

	/**
	 * Returns true if the actor has moved this turn, false if not
	 * @return bool
	 */
	public boolean moved(){
		return (oldSquare != null && !(oldSquare.equals(square)));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Actor other = (Actor) obj;
		if (actorName == null) {
			if (other.actorName != null)
				return false;
		} else if (!actorName.equals(other.actorName))
			return false;
		if (cards == null) {
			if (other.cards != null)
				return false;
		} else if (!cards.equals(other.cards))
			return false;
		if (square == null) {
			if (other.square != null)
				return false;
		} else if (!square.equals(other.square))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

}
