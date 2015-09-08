package my.game.managers;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import my.game.generator.World;
import my.game.main.Assets;

public class MouseManager implements MouseListener, MouseMotionListener, MouseWheelListener {

	public static int mouseMovedX, mouseMovedY;
	public static Point mouse;
	public static boolean pressed;
	public static boolean clicked;
	
	public void tick() {
		mouse = new Point(mouseMovedX,mouseMovedY);
	}
	
	public void render(Graphics2D g){
		//g.fillRect(mouseMovedX, mouseMovedY, 4, 4);
		
		if(pressed){
			g.drawImage(Assets.mouseCursor_pressed, mouseMovedX, mouseMovedY, 20,20,null);
		} else {
			g.drawImage(Assets.mouseCursor_released, mouseMovedX, mouseMovedY, 20,20,null);
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseMovedX = e.getX() - 0;
		mouseMovedY = e.getY() - 30;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){
			pressed = true;
			clicked = true;
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){
			pressed = false;
			clicked = false;
		}
		
	}

}
