package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import board.Board;

/**
 * The class playBoardMouseListen is an implementation of a MouseListener.
 * Its sole function is listening for mouse clicks on the associated guis JComponent - 'BoardDisplay.'
 * This listener is not concerned with any game logic, nor does it test whether an attempted move by clicking the mouse is valid.
 * This class retrieves X and Y coordinates from the location the mouse was clicked on the baord display, and passes them to the associated Boards moveActor() method,
 * as Integers.
 *
 * @author griffiryan
 *
 */
public class playBoardMouseListen implements MouseListener{

	gui game;
	Board playingBoard;

	public playBoardMouseListen(gui g){
		game = g;

		playingBoard = game.getPlayingBoard();

	}

	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		movePlayer(x,y);
	}
	/**
	 *This Method recieves X and Y coordinates from the mouse clicked method, that directly correspond to coordinates on the games playing board.
	 *The associated Boards move actor method is called, with the provided Integer values.
	 *The game interface's repaintAll() method is called, which updates the display of the visible playing board, and thus moving the current playing character.
	 *
	 * @param xMove
	 * @param yMove
	 */
	public void movePlayer(int xMove, int yMove){
		playingBoard.moveActor(xMove,yMove);
		game.repaintAll();

	}



	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}


}