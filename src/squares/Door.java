package squares;

import java.awt.Color;
import java.awt.Graphics;

import pieces.Actor;
import squares.Room;


/**
 * This class represents doors on the board which allows actors to
 * get in and out of rooms
 *
 * @author northleon
 *
 */
public class Door extends Square {

	Room room;

	/**
	 * Creates the Door object
	 * @param room
	 */
	public Door(String room) {
		super(room);
	}

	/**
	 * Draws the square to the board
	 */
	public void draw(Graphics g, int x, int y){
		//Draw a square
		g.setColor(new Color(219, 255, 245));
		g.fillRect(x, y, 25, 25);
		//Draw a black box around it
		g.setColor(new Color(0, 0, 0));
		g.drawRect(x, y, 25, 25);
	}

	/**
	 * Sets the room to the argument given
	 * @param Room
	 */
	public void setRoom(Room room) {
		this.room = room;
	}

	/**
	 * Adds the actor given to the list of actors on a square
	 * @param Actor
	 */
	public void addActor(Actor actor){
		room.addActor(actor);
	}


}
