package board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pieces.Actor;
import pieces.Weapon;
import cards.ActorCard;
import cards.Card;
import cards.RoomCard;
import cards.WeaponCard;
import squares.Corridor;
import squares.Door;
import squares.Outside;
import squares.Room;
import squares.Spawn;
import squares.Square;

/**
 * This class is only used to set up the board
 * @author northleon
 *
 */
public class SetupBoard {


	/**
	 * Takes a list of names from the user(s) and returns a list of actors.
	 * @param names
	 * @return List of actors
	 */
	public List<Actor> makeActors(List<String> names) {
		//we are required to have 6 actors, actors with no names will
		//have null or "" as a name
		if(names.size() != 6){
			for(int i = names.size(); i < 6; i++){
				names.add(null);
			}
		}
		List<Actor> actors = new ArrayList<Actor>();

		Actor scarlett = new Actor("Miss Scarlett",names.get(0));
		Actor mustard = new Actor("Colonel Mustard", names.get(1));
		Actor white = new Actor("Mrs White", names.get(2));
		Actor green = new Actor("Reverend Green", names.get(3));
		Actor peacock = new Actor("Mrs Peacock", names.get(4));
		Actor plum = new Actor("Professor Plum",names.get(5));

		actors.add(scarlett);
		actors.add(mustard);
		actors.add(white);
		actors.add(green);
		actors.add(peacock);
		actors.add(plum);

		return actors;
	}

	/**
	 * Given a list of the main rooms, adds these, plus other non main
	 * rooms to a 2d array
	 * @param rooms
	 * @return
	 */
	public Square[][] makeSquares(List<Room> rooms) {

		Square kit = null;
		Square bal = null;
		Square con = null;
		Square din = null;
		Square bil = null;
		Square lib = null;
		Square lou = null;
		Square hal = null;
		Square stu = null;
		Square kitDo = new Door("kitDoor");
		Square balDo = new Door("balDoor");
		Square conDo = new Door("conDoor");
		Square dinDo = new Door("dinDoor");
		Square bilDo = new Door("bilDoor");
		Square libDo = new Door("libDoor");
		Square louDo = new Door("louDoor");
		Square halDo = new Door("halDoor");
		Square stuDo = new Door("stuDoor");

		Square cel = new Room("cellar");
		Square out = new Outside("Outside");

		for(Room r: rooms){
			if(r.getName().equals("Kitchen"))      {
				kit = r;
				kit.addDoor((Door)kitDo);
			}
			if(r.getName().equals("Ball Room"))    {
				bal = r;
				bal.addDoor((Door)balDo);
			}
			if(r.getName().equals("Conservatory")) {
				con = r;
				con.addDoor((Door)conDo);
			}
			if(r.getName().equals("Dining Room"))  {
				din = r;
				din.addDoor((Door)dinDo);
				}
			if(r.getName().equals("Billiard Room")){
				bil = r;
				bil.addDoor((Door)bilDo);
				}
			if(r.getName().equals("Library"))      {
				lib = r;
				 lib.addDoor((Door)libDo);
			}
			if(r.getName().equals("Lounge"))       {
				lou = r;
				 lou.addDoor((Door)louDo);
			}
			if(r.getName().equals("Hall"))         {
				hal = r;
				hal.addDoor((Door)halDo);
			}
			if(r.getName().equals("Study"))        {
				stu = r;
				stu.addDoor((Door)stuDo);
			}
		}

		Square squares[][] = new Square[][]
				{
				{kit  ,kit  ,kit  ,kit  ,kit  ,kitDo,out  ,out  ,out  ,spa(),out  ,out  ,out  ,out  ,spa(),out  ,out  ,out  ,con  ,con  ,con  ,con  ,con  ,conDo},
				{kit  ,kit  ,kit  ,kit  ,kit  ,kit  ,out  ,cor(),cor(),cor(),bal  ,bal  ,bal  ,bal  ,cor(),cor(),cor(),out  ,con  ,con  ,con  ,con  ,con  ,con  },
				{kit  ,kit  ,kit  ,kit  ,kit  ,kit  ,cor(),cor(),bal  ,bal  ,bal  ,bal  ,bal  ,bal  ,bal  ,bal  ,cor(),cor(),con  ,con  ,con  ,con  ,con  ,con  },
				{kit  ,kit  ,kit  ,kit  ,kit  ,kit  ,cor(),cor(),bal  ,bal  ,bal  ,bal  ,bal  ,bal  ,bal  ,bal  ,cor(),cor(),con  ,con  ,con  ,con  ,con  ,con  },
				{kit  ,kit  ,kit  ,kit  ,kit  ,kit  ,cor(),cor(),bal  ,bal  ,bal  ,bal  ,bal  ,bal  ,bal  ,bal  ,cor(),cor(),conDo,con  ,con  ,con  ,con  ,con  },
				{kit  ,kit  ,kit  ,kit  ,kit  ,kit  ,cor(),cor(),balDo,bal  ,bal  ,bal  ,bal  ,bal  ,bal  ,balDo,cor(),cor(),cor(),con  ,con  ,con  ,con  ,out  },
				{out  ,kit  ,kit  ,kit  ,kitDo,kit  ,cor(),cor(),bal  ,bal  ,bal  ,bal  ,bal  ,bal  ,bal  ,bal  ,cor(),cor(),cor(),cor(),cor(),cor(),cor(),spa()},
				{cor(),cor(),cor(),cor(),cor(),cor(),cor(),cor(),bal  ,balDo,bal  ,bal  ,bal  ,bal  ,balDo,bal  ,cor(),cor(),cor(),cor(),cor(),cor(),cor(),out  },
				{out  ,cor(),cor(),cor(),cor(),cor(),cor(),cor(),cor(),cor(),cor(),cor(),cor(),cor(),cor(),cor(),cor(),cor(),bil  ,bil  ,bil  ,bil  ,bil  ,bil  },
				{din  ,din  ,din  ,din  ,din  ,cor(),cor(),cor(),cor(),cor(),cor(),cor(),cor(),cor(),cor(),cor(),cor(),cor(),bilDo,bil  ,bil  ,bil  ,bil  ,bil  },
				{din  ,din  ,din  ,din  ,din  ,din  ,din  ,din  ,cor(),cor(),cel  ,cel  ,cel  ,cel  ,cel  ,cor(),cor(),cor(),bil  ,bil  ,bil  ,bil  ,bil  ,bil  },
				{din  ,din  ,din  ,din  ,din  ,din  ,din  ,din  ,cor(),cor(),cel  ,cel  ,cel  ,cel  ,cel  ,cor(),cor(),cor(),bil  ,bil  ,bil  ,bil  ,bil  ,bil  },
				{din  ,din  ,din  ,din  ,din  ,din  ,din  ,dinDo,cor(),cor(),cel  ,cel  ,cel  ,cel  ,cel  ,cor(),cor(),cor(),bil  ,bil  ,bil  ,bil  ,bilDo,bil  },
				{din  ,din  ,din  ,din  ,din  ,din  ,din  ,din  ,cor(),cor(),cel  ,cel  ,cel  ,cel  ,cel  ,cor(),cor(),cor(),cor(),cor(),cor(),cor(),cor(),out  },
				{din  ,din  ,din  ,din  ,din  ,din  ,din  ,din  ,cor(),cor(),cel  ,cel  ,cel  ,cel  ,cel  ,cor(),cor(),cor(),lib  ,lib  ,libDo,lib  ,lib  ,out  },
				{din  ,din  ,din  ,din  ,din  ,din  ,dinDo,din  ,cor(),cor(),cel  ,cel  ,cel  ,cel  ,cel  ,cor(),cor(),lib  ,lib  ,lib  ,lib  ,lib  ,lib  ,lib  },
				{out  ,cor(),cor(),cor(),cor(),cor(),cor(),cor(),cor(),cor(),cel  ,cel  ,cel  ,cel  ,cel  ,cor(),cor(),libDo,lib  ,lib  ,lib  ,lib  ,lib  ,lib  },
				{spa(),cor(),cor(),cor(),cor(),cor(),cor(),cor(),cor(),cor(),cor(),cor(),cor(),cor(),cor(),cor(),cor(),lib  ,lib  ,lib  ,lib  ,lib  ,lib  ,lib  },
				{out  ,cor(),cor(),cor(),cor(),cor(),cor(),cor(),cor(),hal  ,hal  ,halDo,halDo,hal  ,hal  ,cor(),cor(),cor(),lib  ,lib  ,lib  ,lib  ,lib  ,out  },
				{lou  ,lou  ,lou  ,lou  ,lou  ,lou  ,louDo,cor(),cor(),hal  ,hal  ,hal  ,hal  ,hal  ,hal  ,cor(),cor(),cor(),cor(),cor(),cor(),cor(),cor(),spa()},
				{louDo,lou  ,lou  ,lou  ,lou  ,lou  ,lou  ,cor(),cor(),hal  ,hal  ,hal  ,hal  ,hal  ,halDo,cor(),cor(),cor(),cor(),cor(),cor(),cor(),cor(),out  },
				{lou  ,lou  ,lou  ,lou  ,lou  ,lou  ,lou  ,cor(),cor(),hal  ,hal  ,hal  ,hal  ,hal  ,hal  ,cor(),cor(),stuDo,stu  ,stu  ,stu  ,stu  ,stu  ,stuDo},
				{lou  ,lou  ,lou  ,lou  ,lou  ,lou  ,lou  ,cor(),cor(),hal  ,hal  ,hal  ,hal  ,hal  ,hal  ,cor(),cor(),stu  ,stu  ,stu  ,stu  ,stu  ,stu  ,stu  },
				{lou  ,lou  ,lou  ,lou  ,lou  ,lou  ,lou  ,cor(),cor(),hal  ,hal  ,hal  ,hal  ,hal  ,hal  ,cor(),cor(),stu  ,stu  ,stu  ,stu  ,stu  ,stu  ,stu  },
				{lou  ,lou  ,lou  ,lou  ,lou  ,lou  ,out  ,spa(),out  ,hal  ,hal  ,hal  ,hal  ,hal  ,hal  ,out  ,cor(),out  ,stu  ,stu  ,stu  ,stu  ,stu  ,stu  },
				};
		return squares;
	}

	/**
	 * Simple method that creates and returns a new Door object
	 * @return new Door object
	 */
	private Square doo(){
		return new Door("Door");
	}
	/**
	 * Simple method that creates and returns a new Spawn object
	 * @return new Door object
	 */
	private Square spa(){
		return new Spawn("Spawn");
	}

	/**
	 * Simple method that creates and returns a new Corridor object
	 * @return new Door object
	 */
	int corridorID = 0;
	private Square cor(){
		return new Corridor("Corridor"+corridorID++);
	}

	/**
	 * Puts Actors into spawn points on the board
	 * @param squares
	 * @param actors
	 */
	public void spawnActors(Square[][] squares, List<Actor> actors) {

		squares[24][7].addActor(actors.get(0)); //add scarlett in bottom spawn
		squares[17][0].addActor(actors.get(1)); //add mustard
		squares[0][9].addActor(actors.get(2));  //add white
		squares[0][14].addActor(actors.get(3));	//add green
		squares[6][23].addActor(actors.get(4)); //add peacock
		squares[19][23].addActor(actors.get(5)); //add plum

	}

	/**
	 * Makes a list of weapon cards
	 * @return List of weapon cards
	 */
	public List<Card> makeWeaponCards() {
		List<Card> weaponCards = new ArrayList<Card>();

		weaponCards.add(new WeaponCard("Candlestick"));
		weaponCards.add(new WeaponCard("Dagger"));
		weaponCards.add(new WeaponCard("Lead Pipe"));
		weaponCards.add(new WeaponCard("Revolver"));
		weaponCards.add(new WeaponCard("Rope"));
		weaponCards.add(new WeaponCard("Spanner"));

		return weaponCards;
	}

	/**
	 * Makes a list of actor cards
	 * @return List of actor cards
	 */
	public List<Card> makeActorCards() {
		List<Card> actorCards = new ArrayList<Card>();

		actorCards.add(new ActorCard("Miss Scarlett"));
		actorCards.add(new ActorCard("Colonel Mustard"));
		actorCards.add(new ActorCard("Mrs White"));
		actorCards.add(new ActorCard("Reverend Green"));
		actorCards.add(new ActorCard("Mrs Peacock"));
		actorCards.add(new ActorCard("Professor Plum"));

		return actorCards;
	}

	/**
	 * Makes a list of Room cards
	 * @return list of room cards
	 */
	public List<Card> makeRoomCards() {
		List<Card> roomCards = new ArrayList<Card>();

		roomCards.add(new RoomCard("Kitchen"));
		roomCards.add(new RoomCard("Ball Room"));
		roomCards.add(new RoomCard("Conservatory"));
		roomCards.add(new RoomCard("Dining Room"));
		roomCards.add(new RoomCard("Billiard Room"));
		roomCards.add(new RoomCard("Library"));
		roomCards.add(new RoomCard("Study"));
		roomCards.add(new RoomCard("Hall"));
		roomCards.add(new RoomCard("Lounge"));

		return roomCards;
	}

	/**
	 * Shuffles deck, gets the first weapon, room, actor, puts them into a list and returns said list
	 * @param List<card> weaponCards
	 * @param List<card> actorCards
	 * @param List<card> roomCards
	 * @return List<Card> Solution
	 */
	public List<Card> makeSolution(List<Card> weaponCards, List<Card> actorCards, List<Card> roomCards) {
		List<Card> solution = new ArrayList<Card>();

		Collections.shuffle(actorCards);
		solution.add(actorCards.get(0));

		Collections.shuffle(roomCards);
		solution.add(roomCards.get(0));

		Collections.shuffle(weaponCards);
		solution.add(weaponCards.get(0));

		return solution;
	}

	/**
	 * Takes in all the weapon, character, room, solution cards. Makes
	 * the deck and removes the solution from it then returns the deck
	 * less the solution
	 *
	 * @param weaponCards
	 * @param actorCards
	 * @param roomCards
	 * @param solution
	 * @return List<card> complete deck
	 */
	public List<Card> removeSolution(List<Card> weaponCards,
			List<Card> actorCards, List<Card> roomCards, List<Card> solution) {

		List<Card> deck = new ArrayList<Card>();

		deck.addAll(weaponCards);
		deck.addAll(actorCards);
		deck.addAll(roomCards);

		deck.removeAll(solution);

		Collections.shuffle(deck);

		return deck;
	}

	/**
	 * Returns all the actors that are active
	 * @param actors
	 * @return List<Actor> activeActors
	 */
	public List<Actor> getActiveActors(List<Actor> actors) {
		List<Actor> active = new ArrayList<Actor>();

		for(Actor a: actors){
			if(!(a.getUser() == null) && a.getUser().length() > 0){
				active.add(a);
			}
		}
		return active;
	}

	/**
	 * Deals all cards to the active actors.
	 * @param deck
	 * @param actors
	 */
	public void dealCards(List<Card> deck, List<Actor> actors) {
		int count = 0;

		while (!deck.isEmpty()){
			for(Actor a : actors){
				if (!deck.isEmpty()){
						a.addCard(deck.get(0));
						deck.remove(0);
					}
				}
		}
	}

	/**
	 * create weapon objects and returns a list of said weapons
	 * @return List<Weapon> weapons
	 */
	public List<Weapon> makeWeapons() {
		List<Weapon> weapons = new ArrayList<Weapon>();

		weapons.add(new Weapon("Candlestick"));
		weapons.add(new Weapon("Dagger"));
		weapons.add(new Weapon("Lead Pipe"));
		weapons.add(new Weapon("Revolver"));
		weapons.add(new Weapon("Rope"));
		weapons.add(new Weapon("Spanner"));

		return weapons;
	}

	/**
	 * Creates room objects, returns list of said rooms
	 * @return List<Room> rooms
	 */
	public List<Room> makeRooms() {

		List<Room> rooms = new ArrayList<Room>();

		rooms.add( new Room("Kitchen"));
		rooms.add( new Room("Ball Room"));
		rooms.add( new Room("Conservatory"));
		rooms.add( new Room("Dining Room"));
		rooms.add( new Room("Billiard Room"));
		rooms.add( new Room("Library"));
		rooms.add( new Room("Lounge"));
		rooms.add( new Room("Hall"));
		rooms.add( new Room("Study"));

		return rooms;
	}

	/**
	 * Makes a map of coordinates for drawing actors, weapons and the name
	 * of a room, in the room.
	 * @return Map<String, int[]> roomCoordinates
	 */
	public Map<String, int[]> MakeRoomCoordinates() {
		Map<String, int[]> roomCoordinates = new HashMap<String, int[]>();

		roomCoordinates.put("Kitchen", new int[]{1, 0});
		roomCoordinates.put("Ball Room", new int[]{2, 9});
		roomCoordinates.put("Conservatory", new int[]{1, 18});
		roomCoordinates.put("Dining Room", new int[]{11, 0});
		roomCoordinates.put("Billiard Room", new int[]{11, 18});
		roomCoordinates.put("Library", new int[]{16, 18});
		roomCoordinates.put("Lounge", new int[]{22, 0});
		roomCoordinates.put("Hall", new int[]{22, 9});
		roomCoordinates.put("Study", new int[]{22, 18});
		return roomCoordinates;
	}

    /**
     * places weapons randomly into rooms, no more than 1 weapon per room
     * @param weapons
     * @param rooms
     */
	public void spawnWeapons(List<Weapon> weapons, List<Room> rooms) {
		Collections.shuffle(weapons);
		Collections.shuffle(rooms);
		for(int i = 0; i < weapons.size(); i++){
			rooms.get(i).addWeapon(weapons.get(i));
		}
	}



}
