package my.game.moveAbleObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import my.javagame.main.Vector2D;

public class BounceText {

	Vector2D pos = new Vector2D();
	private String text;
	
	private boolean isAlive,jump,right,left,damaging;
	private float lifeTime = 1f;
	private float maxJump = 2.5f;
	private float currentJump;
	
	public BounceText(float xPos, float yPos, String text, boolean damaging) {
		this.pos.xPos = xPos;
		this.pos.yPos = yPos;
		this.text = text;
		isAlive = true;
		jump = true;
		this.damaging = damaging;
		Random random = new Random();
		int ran = random.nextInt(2);
		
		switch (ran) {
		case 0:
			right = true;
			left = false;
			break;
		case 1:
			right = false;
			left = true;
			break;
		}
	}
	
	public void tick(){
		if(isAlive){
			if(lifeTime != 0){
				lifeTime -= 0.03f;
			}
			if(lifeTime <= 0){
				isAlive = false;
			}
			
			if(jump){
				if(currentJump != maxJump){
					currentJump += 0.2f;
					pos.yPos += currentJump;
				}
				if(currentJump >= maxJump){
					jump = false;
				}
				
				if(right){
					pos.xPos += currentJump * new Random().nextFloat()*maxJump;
				} else if(left){
					pos.xPos -= currentJump * new Random().nextFloat()*maxJump;
				}
			}
			
			if(!jump){
				if(currentJump != 0){
					currentJump -= 0.1f;
					pos.yPos += currentJump;
				}
				if(currentJump <= 0){
					maxJump -= 0.2;
					jump = true;
				}
			}
		}
	}
	
	public void render(Graphics2D g){
		if(isAlive){
			if(damaging)
				g.setColor(Color.red);
			else	
				g.setColor(Color.green);
			g.drawString(text, pos.xPos, pos.yPos);
		}
	}

}
