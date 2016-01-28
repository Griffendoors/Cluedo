package squares;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import pieces.Weapon;

/**
 *This class represents a room on the board
 *
 * @author northleon
 *
 */
public class Room extends Square {

	//List of weapons
	List<Weapon> weapons;

	/**
	 * Initialises the weapons list
	 *
	 * @param room
	 */
	public Room(String room) {
		super(room);
		weapons = new ArrayList<Weapon>();
	}

	/**
	 * Add weapon to this square.
	 * Change the square the weapon holds.
	 * @param Weapon
	 */
	public void addWeapon(Weapon weapon){
		weapons.add(weapon);
		weapon.setSquare(this);
	}

	/**
	 * Return the list of weapons
	 *
	 * @return Weapons
	 */
	public List<Weapon> getWeapons() {
		return weapons;
	}

	/**
	 * Set the list of weapons
	 * @param weapons
	 */
	public void setWeapons(List<Weapon> weapons) {
		this.weapons = weapons;
	}

	/**
	 * Draw the square representing the room on the board
	 */
	public void draw(Graphics g, int x, int y){
		//Draw a green square
		g.setColor(new Color(135, 214, 146));
		g.fillRect(x+1, y+1, 25, 25);//+1 so it doesn't draw over the black squares.
	}

	/**
	 * Draw each actor in the room on the board.
	 *
	 * @param Graphics g
	 * @param int x
	 * @param int y
	 */
	public void drawActors(Graphics g, int x, int y) {
		for(int i = 0; i < actors.size(); i++){
			actors.get(i).draw(g, (i+x) * 25, y * 25);
		}
	}

	/**
	 * Draw each weapon on the board
	 *
	 * @param Graphics g
	 * @param int x
	 * @param int y
	 */
	public void drawWeapons(Graphics g, int x, int y){
		for(int i = 0; i < weapons.size(); i++){
			weapons.get(i).draw(g, (i+x) * 25, (y+1) * 25);
		}
	}

	/**
	 * Add a door to the list of doors.
	 * set the room of the door to this
	 *
	 * @param Door
	 */
	public void addDoor(Door door){
		doors.add(door);
		door.setRoom((Room)this);
	}

	/**
	 * Draws the name of the room to the board
	 *
	 * @param Graphics g
	 * @param int x
	 * @param int y
	 */
	public void drawNames(Graphics g, int x, int y) {
		g.setFont(new Font("Monospaced", 1, 14));
		g.drawString(name, (x+1)*25 + 2, (y*25));

	}


}
