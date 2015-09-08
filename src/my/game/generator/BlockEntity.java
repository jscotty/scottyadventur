package my.game.generator;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import my.javagame.main.Vector2D;

public class BlockEntity extends Rectangle {

	private Vector2D pos;
	private BufferedImage blockImg;
	private double rot;
	private double rotSpeed = 0.8;
	private double blockSize = 32;
	private boolean isLoading;
	private boolean stopLoading;
	private int lifeTime;
	private float lifeFade = 1;
	
	public BlockEntity(Vector2D pos, BufferedImage blockImg) {
		this.pos = pos;
		this.blockImg = blockImg;
		
		rot = new Random().nextInt(180);
		lifeTime = new Random().nextInt(200);
		if(lifeTime <= 50){
			lifeTime = new Random().nextInt(200);
		}
		
		setBounds((int)pos.xPos,(int)pos.yPos,(int)blockSize,(int)blockSize);
		isLoading = true;
	}
	
	public void tick(double deltaTime){
		if(isLoading){
			setBounds((int)pos.xPos,(int)pos.yPos,(int)blockSize,(int)blockSize);
			rot -= rotSpeed;
			if(!stopLoading){
				if(lifeTime != 0){
					lifeTime--;
				}
				if(lifeTime <= 0){
					stopLoading = true;
				}
			} else if(stopLoading){
				if(lifeFade != 0.000010000){
					lifeFade -= 0.01;
				} 
				if(lifeFade <= 0.000010000){
					World.removeDroppedEnitity(this);
					isLoading = false;
				}
			}
		}
	}
	
	public void render(Graphics2D g){

		if(isLoading){
			
			if(stopLoading){
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, lifeFade));
			}
			
			g.rotate(Math.toRadians(rot), 
					pos.getWorldLocation().xPos + blockSize / 2, 
					pos.getWorldLocation().yPos + blockSize / 2);
			
			g.drawImage(blockImg, 
					(int)pos.getWorldLocation().xPos, 
					(int)pos.getWorldLocation().yPos, 
					(int)blockSize, (int)blockSize,null);
			
			g.rotate(-Math.toRadians(rot), 
					pos.getWorldLocation().xPos + blockSize / 2, 
					pos.getWorldLocation().yPos + blockSize / 2);

			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		}
	}
	
	public Vector2D getPos() {
		return pos;
	}
	public void setPos(Vector2D pos) {
		this.pos = pos;
	}
	public void setLoading(boolean isLoading) {
		this.isLoading = isLoading;
	}

}
