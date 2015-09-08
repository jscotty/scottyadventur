package my.game.managers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import my.game.generator.Block;
import my.javagame.main.Vector2D;

public class LightSource {

	private Vector2D lightLocation = new Vector2D();
	private double lightDis = 48, lightSize = 32;
	private Rectangle lightDetection;
	
	public LightSource(float xPos, float yPos, double lightDis) {
		this.lightLocation.xPos = xPos;
		this.lightLocation.yPos = yPos;
		this.lightDis = lightDis;
		
		lightDetection = new Rectangle();
	}
	
	public void tick(double deltaTime){
//		lightDetection = new Rectangle(
//			(int)(lightLocation.xPos - lightLocation.getWorldLocation().xPos + lightLocation.xPos - lightDis/2 + lightSize / 2),
//			(int)(lightLocation.yPos - lightLocation.getWorldLocation().yPos + lightLocation.yPos - lightDis/2 + lightSize / 2),
//			(int)lightDis, (int)lightDis);
		lightDetection = new Rectangle(
				(int)(lightLocation.xPos - (42*lightDis/2) + (lightSize / 2)),
				(int)(lightLocation.yPos - (42*lightDis/2) + (lightSize / 2)),
				(int)(42*lightDis), (int)(42*lightDis));
		
		
		
	}

	public void render(Graphics2D g) {
		g.setColor(Color.red);
		//g.fillRect((int)lightLocation.getWorldLocation().xPos, (int)lightLocation.getWorldLocation().yPos, (int)lightSize, (int)lightSize);
		
		
		for(Block blocks : TileManager.blocks){
			if(blocks!=null){
				if(lightDetection!=null){
					if(lightDetection.intersects(blocks.getBounds())){
						float distance = (float) Vector2D.getDistanceOnScreen(lightLocation, blocks.getBlockLocation());
						
						if(distance > 42*8){
							blocks.removeShadow(0.045f);
						}
						
						for(int d = 48; d < lightDis*42;d++){
							if(distance < d){
								switch(d){
								case 42:
									if(blocks.getLightLevel() > 0.2f){
										blocks.removeShadow(0.045f);
									}
									break;
								case 42*2:
									if(blocks.getLightLevel() > 0.3f){
										blocks.removeShadow(0.035f);
									}
									break;
								case 42*3:
									if(blocks.getLightLevel() > 0.4f){
										blocks.removeShadow(0.025f);
									}
									break;
								case 42*4:
									if(blocks.getLightLevel() > 0.5f){
										blocks.removeShadow(0.015f);
									}
									break;
								case 42*5:
									if(blocks.getLightLevel() > 0.6f){
										blocks.removeShadow(0.0090f);
									}
									break;
								case 42*6:
									if(blocks.getLightLevel() > 0.7f){
										blocks.removeShadow(0.0080f);
									}
									break;
								case 42*7:
									if(blocks.getLightLevel() > 0.8f){
										blocks.removeShadow(0.0070f);
									}
									break;
								case 42*8:
									if(blocks.getLightLevel() > 0.9f){
										blocks.removeShadow(0.0060f);
									}
									break;
								}
							}
						}
					}
				}
			}
		}
		
	}
	
	public Vector2D getLightLocation() {
		return lightLocation;
	}
	
	public Rectangle getLightDetection() {
		return lightDetection;
	}

}
