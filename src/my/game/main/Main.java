package my.game.main;

//import java.awt.GraphicsDevice;
//import java.awt.GraphicsEnvironment;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;

import my.game.gameloop.GameLoop;
import my.game.inventory.InvMouseManager;
import my.game.managers.MouseManager;
import my.game.moveAbleObjects.Player;
import my.javagame.main.GameWindow;

public class Main {
	
	//public static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	public static int width = 800;//gd.getDisplayMode().getWidth();
	public static int height = 600;//gd.getDisplayMode().getHeight();
	
	public static void main(String[] args) {
		GameWindow gameWindow = new GameWindow("Scotty's Adventure", width, height);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Cursor cursor = toolkit.createCustomCursor(toolkit.getImage(""), new Point(0,0), "Cursor");
		
		gameWindow.addMouseListener(new MouseManager());
		gameWindow.addMouseListener(new InvMouseManager());
		gameWindow.addMouseMotionListener(new MouseManager());
		gameWindow.addMouseWheelListener(new MouseManager());
		
		gameWindow.setCursor(cursor);
		gameWindow.addKeyListener(new Player());
		gameWindow.add(new GameLoop(800,600));
		gameWindow.setVisible(true);
	}

}
