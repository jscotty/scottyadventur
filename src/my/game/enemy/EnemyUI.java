package my.game.enemy;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import my.game.generator.World;
import my.game.main.Assets;
import my.javagame.main.Vector2D;

public class EnemyUI {

	private Enemy enemy;
	private EnemyType enemyType;
	private String name;
	private int health;
	private int healthWidth;
	private int healthHeight = 5;
	private Vector2D pos;
	Font f = new Font("arial", Font.BOLD, 18);
	Font font = new Font("arial", Font.BOLD, 12);
	
	private int enemyWidth;
	private int enemyHeight;
	
	private boolean damaging = false, damaged = false;
	private int count;
	
	public EnemyUI(Enemy enemy) {
		this.enemy = enemy;
		enemyType = enemy.getEnemyType();
		name = enemyType.getEnemyName();
		health = enemyType.getHealth();
		healthWidth = health;
		pos = enemy.getPos();
		enemyWidth = enemy.getNpcWidth();
		enemyHeight = enemy.getNpcHeight();
	}
	
	public void tick(){
		pos = enemy.getPos();
		if(enemy.alive){
			if(health <= 0){
				World.dropItem(enemy.getPos(), enemy.getEnemyType().getItem(), 1);
				enemy.alive = false;
			}
		}
	}
	
	public void render(Graphics2D g){
		g.setColor(Color.red);
		g.fillRect((int)pos.xPos, (int)(pos.yPos - enemyHeight), healthWidth, healthHeight);
		g.setColor(Color.GREEN);
		g.fillRect((int)pos.xPos, (int)(pos.yPos - enemyHeight), health, healthHeight);
		g.setColor(Color.black);
		g.drawRect((int)pos.xPos, (int)(pos.yPos - enemyHeight), healthWidth, healthHeight);
		
		//enemy name
		g.setFont(font);
		g.setColor(Color.black);
		g.drawString(name, (int)(pos.xPos - enemyWidth/2) + 1, (int)(pos.yPos - enemyHeight-5) + 1);
		g.setColor(Color.yellow);
		g.drawString(name, (int)pos.xPos - enemyWidth/2, (int)(pos.yPos - enemyHeight-5));
		g.setFont(f);
		
		if(damaging) {
			g.drawImage(Assets.getDamagePlayer(), (int)(pos.xPos - enemyWidth/2), (int)(pos.yPos - enemyHeight/2),(int)(enemyWidth*1.5f), (int)(enemyHeight*1.5f), null);
			count++;
			if(count >= 10){
				damaging = false;
			}
		} else {
			count = 0;
		}
			
	}
	
	public void damage(int damage){
		health -= damage;
		damaging = true;
		damaged = true;
	}
	
	public void setDamaged(boolean damaged) {
		this.damaged = damaged;
	}
	
	public boolean isDamaged(){
		return damaged;
	}

}
