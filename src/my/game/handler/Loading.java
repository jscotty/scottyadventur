package my.game.handler;

import java.awt.Color;
import java.awt.Graphics2D;

import my.game.main.Assets;

public class Loading {
	
	public static boolean isLoading = false;

	public Loading() {
		
	}
	
	public void render(Graphics2D g){
		//System.out.println(isLoading);
		if(isLoading){
			g.setColor(Color.black);
			g.fillRect(0, 0, 800, 600);
			g.drawImage(Assets.getLoading(), 400 - 32*3,300 - 32,32*6,32*2,null);
		}
	}

}
