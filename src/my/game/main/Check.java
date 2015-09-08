package my.game.main;

import java.awt.Point;

import my.game.generator.Block;
import my.game.generator.World;
import my.game.inventory.Item;
import my.game.inventory.ItemDrop;
import my.game.managers.TileManager;

public class Check {
	
	public static int count;
	
	public static boolean collisionPlayer(Point p1, Point p2){
		
		for(Block block : TileManager.blocks){
			//int playerX = p1.x / 16;
			//int playerY = p2.y / 16;

			if(block.isSolid()){
				if(block.contains(p1) || block.contains(p2)){
					return true;
				}
			}
			
		}
		
		return false;
	}
	
	public static boolean collisionObjectWithTag(String tag,Point p1, Point p2){
		
		for(Block block : TileManager.blocks){
			if(block.getTag() == tag){
				if(block.contains(p1) || block.contains(p2)){
					return true;
				}
			}
		}
		return false;
	}
	
	public static ItemDrop collisionObjectWithItem(float pX, float pY){
		for(ItemDrop drop : World.getDropItems()){
				float difX = drop.getPos().xPos - pX;
				float difY = drop.getPos().yPos - pY;

				if(difX <= 30 && difX >= 0 && difY <= 30 && difY >= 0){
					return drop;
				}
		}
		return null;
	}
}
