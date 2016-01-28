package squares;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import pieces.Actor;
/**
 * This class represents a generic square, all other squares extend this
 * @author northleon
 *
 */
public class Square {

	String name;
	List<Actor> actors;
	List<Door> doors;

	/**
	 *
	 *
	 * @param String
	 */
	public Square(String name){
		this.name = name;
		actors = new ArrayList<Actor>();
		doors = new ArrayList<Door>();
	}

	public void draw(Graphics g, int x, int y){
		//Draw a filled yellow square
		g.setColor(new Color(255, 248, 168));
		g.fillRect(x, y, 25, 25);
		//Draw a black box around it
		g.setColor(new Color(0, 0, 0));
		g.drawRect(x, y, 25, 25);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actors == null) ? 0 : actors.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Square other = (Square) obj;
		if (actors == null) {
			if (other.actors != null)
				return false;
		} else if (!actors.equals(other.actors))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/**
	 * Returns the name of the square
	 *
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Adds actor to the list of actors.
	 * sets the actors square to this
	 * @param actor
	 */
	public void addActor(Actor actor){
		actors.add(actor);
		actor.setSquare(this);
	}

	/**
	 * returns the list of actors on the square.
	 * @return List<Actor>
	 */
	public List<Actor> getActors() {
		return actors;
	}

	/**
	 * Sets the actors to a new list and adds the actor given in the argument
	 * to that list
	 *
	 * @param Actor
	 */
	public void setActors(Actor actor) {
		this.actors = new ArrayList<Actor>();
		if (actor != null)
			actors.add(actor);
	}

	public String toString(){
		if (actors.size() < 1){
			return name.substring(0,2)+" ";
		}
		//gets the first letter of the first actor in the square
		return name.substring(0,2)+ actors.get(0).getActorName().substring(0,1);
	}

	/**
	 * Adds a door to the room.
	 * sets the doors room to this.
	 * @param door
	 */
	public void addDoor(Door door){
		doors.add(door);
		door.setRoom((Room)this);
	}

	/**
	 * Remove the actor given as an argument from the list of actors in this room.
	 *
	 * @param Actor currentActor
	 */
	public void removeActor(Actor currentActor) {
		actors.remove(currentActor);
	}

	/**
	 * Returns the list of doors in the room
	 * @return List<Door>
	 */
	public List<Door> getDoors(){
		return doors;
	}


}
