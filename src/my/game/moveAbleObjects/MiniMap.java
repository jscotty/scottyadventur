package my.game.moveAbleObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import my.game.enemy.Enemy;
import my.game.generator.World;
import my.game.inventory.ItemDrop;
import my.game.main.Assets;
import my.javagame.main.Vector2D;

public class MiniMap {
	
	Vector2D pos = new Vector2D();
	private World world;
	private BufferedImage map;
	
	public MiniMap(World world){
		this.world = world;
	}
	
	public void tick(){
		map = Assets.getMapImage();
	}
	
	public void render(Graphics2D g){
		int x = (int)(Math.floor((world.getWorldXPos() /16)-8 )*-1)*4+590;
		int y = (int)(Math.floor((world.getWorldYPos() /16)- 5.5f)*-1)*4+13;
		
		g.drawImage(map,x, y,800,800,null);
		
		for (ItemDrop dropItems: World.getDropItems()) {
			int xPos = (int)(Math.floor((dropItems.getPos().xPos /16)-8 ))*4+656;
			int yPos = (int)(Math.floor((dropItems.getPos().yPos /16)-5.5f))*4+60;
			g.setColor(Color.black);
			g.fillRect(xPos,yPos, 6, 6);
			g.setColor(Color.red);
			g.fillRect(xPos+1,yPos+1, 4, 4);
		}
		
		for (Enemy enemy: world.getEnemys()) {
			int xPos = (int)(Math.floor((enemy.getPos().xPos /16)-8 ))*4+658;
			int yPos = (int)(Math.floor((enemy.getPos().yPos /16)-5.5f))*4+62;
			g.setColor(Color.black);
			g.fillRect(xPos,yPos, 8, 8);
			g.setColor(Color.yellow);
			g.fillRect(xPos+1,yPos+1, 6, 6);
		}

		g.setColor(Color.black);
		g.fillOval(689,112, 8, 8);
		g.setColor(Color.white);
		g.fillOval(690,113, 6, 6);

	}
}
