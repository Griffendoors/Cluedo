package board;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pieces.Actor;
import pieces.Weapon;
import cards.Card;
import squares.Corridor;
import squares.Door;
import squares.Outside;
import squares.Room;
import squares.Square;
/**
 * This represents the board that the game is played on. Most of the game logic happens
 * here.
 * @author northleon
 *
 */
public class Board {

	private PathFinder pathFinder;

	private List<Actor> actors;          //List of all actors
	private List<Weapon> weapons;        //list of all weapons
	private List<Room> rooms;            //only rooms that suggestions can be made in
	private Square squares[][];          //representation of the board

	private List<int[]> validMoves;		 //list of valid moves dictated by the dice roll

	private List<Actor> activeActors;    //subset of actors that are playing
	private int currentActor;			 //relative position to number of active players

	private List<Card> deck;             //deck of cards, gets created and dealt at beginning of each game

	private List<Card> solution;         //list of cards in the solution

	private Map<String, int[]> roomCoordinates; //coordinates to put actors into rooms, used for weapons too.

	/**
	 * List of string is in order: Miss Scarlett, Colonel Mustard, Mrs. White,
	 * The Reverend Green, Mrs. Peacock, Professor Plum
	 */
	public Board(List<String> names){

		//make sure there is the correct number of actors
		if (names.size() < 3 || names.size() > 6){throw new IllegalArgumentException();}

		//create new setup object
		SetupBoard setup = new SetupBoard();

		//create list of actors
		actors = setup.makeActors(names);

		//create the important rooms
		rooms = setup.makeRooms();

		//make 2d array representing the board
		squares = setup.makeSquares(rooms);

		//put actors onto board
		setup.spawnActors(squares, actors);

		roomCoordinates = setup.MakeRoomCoordinates();

		//make deck
		deck = new ArrayList<Card>();
		List<Card> weaponCards = setup.makeWeaponCards();
		List<Card> actorCards = setup.makeActorCards();
		List<Card> roomCards = setup.makeRoomCards();

		//add cards to solution
		solution = setup.makeSolution(weaponCards, actorCards, roomCards); //pick solution

		//add all cards to deck, remove solution
		deck = setup.removeSolution(weaponCards, actorCards, roomCards, solution);

		//find active actors, deal the cards out to them
		activeActors = setup.getActiveActors(actors);
		setup.dealCards(deck, activeActors);

		//create weapons
		weapons = setup.makeWeapons();
		//place weapons in rooms
		setup.spawnWeapons(weapons, rooms);

		pathFinder = new PathFinder(squares, this);

		validMoves = new ArrayList<int[]>();
	}

	/**
	 * Sets the next actor as the current actor and then returns the current actor
	 * @return Actor
	 */
	public Actor getNextActor(){
		currentActor = (currentActor + 1) % activeActors.size();
		return activeActors.get(currentActor);
	}

	/**
	 * Returns the list of current actors in play
	 * @return List of current actors
	 */
	public List<Actor> getActiveActors(){
		return activeActors;
	}

	/**
	 * Returns the actor whose turn it is
	 * @return Actor
	 */
	public Actor getCurrentActor(){
		if (currentActor >= activeActors.size()){
			currentActor = activeActors.size()-1;
			}
		return activeActors.get(currentActor);
	}

	/**
	 * Returns the 3 cards that are the solution
	 * @return List<Card>
	 */
	public List<Card> getSolution() {
		return solution;
	}

	/**
	 * Removes the actor from play, this is used when a user makes a false accusation
	 * @param actor
	 */
	public void removeActor(Actor actor){
		if (activeActors.size() <= 1){ return;}
		activeActors.remove(actor);

		if (currentActor != 0){
			currentActor = currentActor--;
		}
		else {
			currentActor = activeActors.size()-1;
		}
	}

	/**
	 * used to prevent the player from moving after an accusation or suggestion has been made.
	 * Resets the valid moves list.
	 */
	public void resetValidMoves(){
		this.validMoves = new ArrayList<int[]>();
	}


	/**
	 * Creates a list of coordinates that are valid moves for the actor from the roll
	 * @param roll
	 */
	public void checkValidMoves(int roll){
		Square curSquare = getCurrentActor().getSquare();
		int coordinates[] = getSquareCoordinates(curSquare);
		validMoves = pathFinder.checkValidMove(roll, curSquare, coordinates);
	}

	/**
	 * Draws dots on all the valid moves for an actor
	 * @param g
	 */
	public void highlightValidMoves(Graphics g) {
		g.setColor(new Color(255,183,168));
		//for each valid move, draw a pink dot on the square
		for (int[] s: validMoves){
			g.fillOval((s[1] * 25)+5, (s[0] * 25)+5, 15, 15);
		}
	}

	/**
	 * Takes coordinates from a mouse click and finds the square that the actor should move to.
	 * Checks that the square is in the valid moves list.
	 * Moves actor to the square.
	 * @param double x
	 * @param double y
	 * @return bool
	 */
	public boolean moveActor(double x, double y){

		int xi = (int)x/25; //25 is square size
		int yi = (int)y/25; //25 is square size

		if(xi>squares[0].length-1){xi=squares[0].length-1;} //if they click outside the board, select the closest square
		if(yi>squares.length-1){yi=squares.length-1;} //if they click outside the board, select the closest square

		//confirm move is valid
		int [] coords = new int []{xi,yi};
		boolean validmove = false;
		for (int[] move: validMoves){
			if(move[0] == yi && move[1] == xi){
				validmove = true;
				break;
			}
		}
		if (!validmove){return false;}

		//clear actors current square
		getCurrentActor().getSquare().removeActor(getCurrentActor());

		//add actor to square, automatically adds square to actor
		squares[yi][xi].addActor(getCurrentActor());

		return true;

	}

	/**
	 * Iterate over the board until it finds the square, returns it's coordinates.
	 * This only works for corridors due to having duplicates in my squares[][] array
	 * @param Square square
	 * @return int[] coordinates
	 */
	public int[] getSquareCoordinates(Square square){
		for(int i = 0; i < squares.length; i++){
			for(int j = 0; j < squares[i].length; j++){
				if (squares[i][j].equals(square)){
					return new int[]{i,j};
				}
			}
		}
		return null;
	}

	/**
	 * Iterates over the entire board, adding the coordinates of each instance of the
	 * door found. This is because there is only one door object per room and it is in
	 * the squares[][] array multiple times.
	 * @param Door door
	 * @return int[] coords
	 */
	public List<int[]> getDoorCoordinates(Door door) {
		List<int[]> coords = new ArrayList<int[]>();
		for(int i = 0; i < squares.length; i++){
			for(int j = 0; j < squares[i].length; j++){
				if (squares[i][j].equals(door)){
					coords.add( new int[]{i,j});
				}
			}
		}
		return coords;
	}

	/**
	 * find and return the square found at the given coordinates
	 * @param int[] coordinates
	 * @return Square
	 */
	public Square getSquareFromCoordinates(int[] coordinates){
		if (coordinates.length != 2){}//throw exception

		int i = coordinates[0];
		int j = coordinates[1];

		if(i >= squares.length){}//throw exception
		if(j >= squares[i].length){}//throw exception

		return squares[i][j];
	}

	/**
	 * Draws everything on the board
	 * @param g
	 */
	public void draw(Graphics g){

		//Draw all the squares
		for(int i = 0; i < squares.length; i++){
			for(int j = 0; j < squares[i].length; j++){
				int x = 25 * j;
				int y = 25 * i;
				if (squares[i][j] == null){}
				squares[i][j].draw(g, x, y);
			}
		}

		//draw the valid moves
		highlightValidMoves(g);

		//draw all actors in the corridor
		for (Actor a: actors){
			//when the actor is in a corridor or a spawn location, just draw it where it is.
			if (a.getSquare().getName().contains("Corridor") || a.getSquare().getName().equals("Spawn")){
				int coordinates[] = getSquareCoordinates(a.getSquare());
				int y = coordinates[0];
				int x = coordinates[1];
				a.draw(g, x*25, y*25);
			}
		}

		//draw all elements in the room (room name, actors, weapons)
		for(Room r: rooms){
			int coordinates[] = roomCoordinates.get(r.getName());
			int y = coordinates[0];
			int x = coordinates[1];
			r.drawActors(g, x, y);
			r.drawWeapons(g, x, y);
			g.setColor(Color.black);
			r.drawNames(g, x, y);
		}
	}

	/**
	 * Used at the end of the turn to move the current actor to the next actor and reset the current moves
	 */
	public void endTurn() {
		validMoves = new ArrayList<int[]>();
		getNextActor();
	}

	/**
	 * Rolls 2 dice, returns the value
	 * @return int
	 */
	public int rollDice(){
		int d1 = (int)(Math.random() * 6) + 1;
		int d2 = (int)(Math.random() * 6) + 1;
		return d1 + d2;
	}

	/**
	 * returns all actors
	 * @return List<Actor>
	 */
	public List<Actor> getActors() {
		return actors;
	}

	/**
	 * Returns all weapons
	 * @return List<Weapon>
	 */
	public List<Weapon> getWeapons(){
		return weapons;
	}
}
