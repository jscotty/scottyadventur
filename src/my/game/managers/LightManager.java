package my.game.managers;

import java.awt.Graphics2D;
import java.util.concurrent.CopyOnWriteArrayList;

import my.game.generator.Block;
import my.game.generator.World;

public class LightManager {
	
	private CopyOnWriteArrayList<LightSource> lights;
	private CopyOnWriteArrayList<Block> loadedBlocks;
	
	LightSource light_01 = new LightSource(300, 400, 10);
	
	public LightManager(CopyOnWriteArrayList<Block> loadedBlocks){
		this.loadedBlocks = loadedBlocks;
	}
	
	public void init(){
		lights = new CopyOnWriteArrayList<LightSource>();
		lights.add(light_01);
	}
	
	public void tick(double deltaTime){
		for(LightSource light : lights){
			light.tick(deltaTime);
		}
		
		light_01.getLightLocation().xPos = World.getPlayer().getWorld().getWorldPos().xPos + (16*16);
		light_01.getLightLocation().yPos = World.getPlayer().getWorld().getWorldPos().yPos  + (16*18);

	}

	
	public void addNewLight(int xPos, int yPos, int lightDis){
		lights.add(new LightSource(xPos, yPos, lightDis));
	}
	
	public void render(Graphics2D g){
		for(LightSource light : lights){
			light.render(g);
		}
	}
	
}
