package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import interaction.Suggestion;

public class suggestSelectMouseListen implements MouseListener{

	Suggestion suggestSelect;


	public suggestSelectMouseListen(Suggestion s){
		suggestSelect = s;

	}



	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2){		// Players Must Double click to select the card to be shown to the suggestor
			int x = e.getX();
			int y = e.getY();
			if((x >= 700)||(y >= 200)){
				System.out.println("Out of area");
				return;
			}
			selectCard(x,y);
		}
	}


	/**
	 *
	 * @param xPos
	 * @param yPos
	 */
	private void selectCard(int xPos, int yPos) {
		int selectedCardIndex = Math.min((int)xPos/100,suggestSelect.getOtherActorsCards().size()-1);
		suggestSelect.setShownCard(suggestSelect.getOtherActorsCards().get(selectedCardIndex).getName());
		suggestSelect.displayShownCard();
		//TODO ugly code fix after cleaned suggestion
	}



	
	public void mousePressed(MouseEvent e) {

	}

	
	public void mouseReleased(MouseEvent e) {


	}

	
	public void mouseEntered(MouseEvent e) {


	}

	
	public void mouseExited(MouseEvent e) {


	}

}
