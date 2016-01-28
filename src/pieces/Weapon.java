package pieces;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import cards.Card;
import squares.Square;

/**
 * Represents the weapon token on the board.
 *
 * @author northleon
 *
 */
public class Weapon extends Piece{
	private String name;
	private Square square;

	/**
	 * Creates a weapon object with the name given.
	 * @param name
	 */
	public Weapon(String name){
		this.name = name;
	}

	/**
	 * Returns the square the weapon is on.
	 * @return Square
	 */
	public Square getSquare() {
		return square;
	}

	/**
	 * Sets the square the weapon is in.
	 * @param square
	 */
	public void setSquare(Square square) {
		this.square = square;
	}

	/**
	 * Returns the name of the weapon.
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Draws the weapon image to the board, the image is saved on the disk under the name of the weapon
	 */
	public void draw(Graphics g, int x, int y){
		java.net.URL imageURL = Weapon.class.getResource("weaponIcons/"+name+".png");
		Image img = null;
		try {
			img = ImageIO.read(imageURL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.drawImage(img, x, y, null);
	}


}
