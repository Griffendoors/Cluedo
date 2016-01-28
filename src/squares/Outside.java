package squares;

import java.awt.Color;
import java.awt.Graphics;

/**
 * This class represents an outside square which cannot be occupied by an actor or a weapon
 *
 * @author northleon
 *
 */
public class Outside extends Square {

	public Outside(String room) {
		super(room);
	}

	public void draw(Graphics g, int x, int y){
		//Draw a green square
		g.setColor(new Color(0, 0, 0));
		g.fillRect(x, y, 25, 25);
		//Draw a black box around it
		g.setColor(new Color(0, 0, 0));
		g.drawRect(x, y, 25, 25);
	}

}
