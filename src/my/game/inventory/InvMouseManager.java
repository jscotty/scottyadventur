package my.game.inventory;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InvMouseManager implements MouseListener {

	public static boolean leftClicked;
	public static boolean rightClicked;
	
	public InvMouseManager() {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){
			leftClicked = false;
		}
		if(e.getButton() == MouseEvent.BUTTON3){
			rightClicked = false;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){
			leftClicked = true;
			rightClicked = false;
		}
		if(e.getButton() == MouseEvent.BUTTON3){
			leftClicked = false;
			rightClicked = true;
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

}
