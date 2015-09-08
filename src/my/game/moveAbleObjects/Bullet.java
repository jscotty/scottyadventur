package my.game.moveAbleObjects;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import my.game.enemy.Enemy;
import my.game.generator.World;
import my.game.state.LevelLoader;
import my.javagame.main.Vector2D;

public class Bullet extends Rectangle{

	Vector2D pos = new Vector2D();
	Vector2D dir = new Vector2D(0,0);
	BufferedImage bullet;
	private int width = 12, height = 12;
	
	private int life = 0;
	private int lifeTime = 60;
	private int damage = 0;
	private boolean alive = true;
	
	public Bullet(Vector2D pos, Vector2D dir, BufferedImage bullet, int damage) {
		this.pos = pos;
		this.dir = dir;
		this.bullet = bullet;
		this.damage = damage;
		setBounds((int)pos.xPos, (int)pos.yPos, width, height);
	}
	
	public void tick(){
		setBounds((int)pos.xPos, (int)pos.yPos, width, height);
		if(alive){
			pos.xPos += dir.xPos;
			pos.yPos += dir.yPos;
			
			if(World.getPlayer().isMoving()){
				if(World.getPlayer().up){
					pos.yPos += World.getPlayer().getSpeed() / 61;
				} else if(World.getPlayer().down){
					pos.yPos -= World.getPlayer().getSpeed() / 61;
				} else if(World.getPlayer().left){
					pos.xPos += World.getPlayer().getSpeed() / 61;
				} else if(World.getPlayer().right){
					pos.xPos -= World.getPlayer().getSpeed() / 61;
				}
			}
			
			life++;
			if(life >= lifeTime){
				alive = false;
			} else {
				alive = true;
			}
			
			for (Enemy enemy: LevelLoader.world.getEnemys()) {
				float difX = pos.xPos - enemy.getPos().xPos;
				float difY = pos.yPos - enemy.getPos().yPos;
				
				if(difX < enemy.getWidth() && difX > -enemy.getWidth() && difY < enemy.getHeight() && difY > -enemy.getHeight()){
					enemy.getEnemyUI().damage(damage);
					alive = false;
				}
			}
		}
	}

	public void render(Graphics2D g){
		if(alive){
			g.drawImage(bullet,(int)pos.xPos,(int)pos.yPos, width,height,null);
		}
	}
	
	public boolean isAlive(){
		return alive;
	}

}
