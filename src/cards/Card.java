package cards;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
/**
 * This is the top level abstraction of all the cards, it represents a generic card
 * @author northleon
 *
 */
public abstract class Card {
	private String name;

	/**
	 * Creates the card object, sets the name to to string given.
	 * @param String name
	 */
	public Card(String name){
		this.name = name;
	}

	/**
	 * Returns the card name
	 * @return String name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the card to the name given
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * draws the card to the coordinates given. The image name is the same as the card name
	 * @param int x
	 * @param int y
	 * @param Graphics g
	 */
	public void draw(int x, int y, Graphics g){
		//get image url
		java.net.URL imageURL = Card.class.getResource("cardImages/"+name+".png");
		Image img = null;
		try {
			//assign img to the image on disk
			img = ImageIO.read(imageURL);
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.drawImage(img, x, y, 100, 115, null);
	}

	/**
	 * returns the name of the card
	 *
	 * @return String
	 */
	public String toString(){
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Card other = (Card) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}



}
