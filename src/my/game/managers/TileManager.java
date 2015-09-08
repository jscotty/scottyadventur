package my.game.managers;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import my.game.generator.Block;
import my.game.generator.World;
import my.game.moveAbleObjects.Player;
import my.javagame.main.Vector2D;

public class TileManager {

	public static CopyOnWriteArrayList<Block> blocks = new CopyOnWriteArrayList<Block>();
	public static CopyOnWriteArrayList<Block> loadedBlocks = new CopyOnWriteArrayList<Block>();
	
	

	public TileManager() {
		
	}

	public void tick(double deltaTime){
		for(Block block:blocks){
			block.tick(deltaTime);
			
			if(Player.render.intersects(block)){
				block.setLoading(true);
				
				
			} else {

				if(loadedBlocks.contains(block)){
					loadedBlocks.remove(block);
				}
				
				block.setLoading(false);

			}
		}
		if(!loadedBlocks.isEmpty()){
			//loadedBlocks.clear();
		}
	}
	
	public void render(Graphics2D g){
		for(Block block:blocks){
			block.render(g);
		}
	}
	
	public CopyOnWriteArrayList<Block> getBlocks() {
		return blocks;
	}
	
	public CopyOnWriteArrayList<Block> getLoadedBlocks() {
		return loadedBlocks;
	}
}
