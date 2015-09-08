package my.game.moveAbleObjects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.concurrent.CopyOnWriteArrayList;

import my.game.main.Assets;
import my.game.main.SaveLoadData;
import my.game.references.WorldData;
import my.game.references.WorldNames;
import my.game.state.LevelLoader;

public class PlayerStats {

	private static float damage;
	private static float defence;
	private static int playerLevel = 1;
	
	private Player player;
	private float minHealth = 1;
	private float health = 150;
	private float healthScale = health / minHealth;
	private int healthBarWidth = 150;
	private int healthBarHeight = 30;

	private float minStat = 1;
	private float stats = 100;
	private float statsScale = stats / minStat;
	private int statsBarWidth = 100;
	private int statsBarHeight = 10;
	
	private float minExp = 1;
	private float exp = 100;
	private float expScale = exp / minExp;
	private int expBarWidth = 350;
	private int expBarHeight = 10;
	
	private float dmrt = health / minHealth;
	private int waitTime = 5;
	
	private double dmgTime = 1;
	
	private static boolean fullHealth = false;
	private static boolean fullStamina = false;
	Font f = new Font("arial", Font.BOLD, 25);
	Font font = new Font("arial", Font.BOLD, 18);
	
	CopyOnWriteArrayList<BounceText> list = new CopyOnWriteArrayList<BounceText>();
	
	public PlayerStats(Player player) {
		this.player = player;
	}

	public void tick(){
		healthScale = health / minHealth;
		statsScale = stats / minStat;
		expScale = exp / minExp;
		
		if(health >= 150){
			fullHealth = true;
		}if(health <= 0){
			resetHealth();
			WorldData.changeWorldPos(21, 17);
			LevelLoader.world.playerDied("World","map",1);
		} else {
			fullHealth = false;
		}
		
		if(stats >= 100){
			fullStamina = true;
		} else {
			fullStamina = false;
		}
		
		if(player.isDamaging()){
			if(dmgTime != 0){
				dmgTime -= 0.2;
			}
			if(dmgTime <= 0){
				Player.setDamaging(false);
				dmgTime = 1;
			}
		} else {
			
		}
		

		for(BounceText text: list){
			text.tick();
		}
	}
	
	public void render(Graphics2D g){
		g.setColor(new Color(237,111,9));
		if(!player.isDamaging()){
			if(dmrt > healthScale){
				if(waitTime != 0){
					waitTime--;
				}
				if(waitTime == 0){
					dmrt-=1f;
					if(dmrt<=healthScale){
						waitTime = 2;
					}
				}
			}
		}
		g.fillRect(50,10,(int)dmrt,healthBarHeight);
		
		g.setColor(Color.green);
		g.fillRect(50,10,(int)(minHealth * healthScale),healthBarHeight);
		
		g.drawImage(Assets.getLifeBar(), 50,10,healthBarWidth,healthBarHeight,null);
		
		g.setColor(Color.yellow);
		g.fillRect(50,40,(int)(minStat * statsScale),statsBarHeight);
		
		g.drawImage(Assets.getStatBar(), 50,40,statsBarWidth,statsBarHeight,null);

		g.setColor(Color.BLACK);
		g.drawRect(10, 10, 40, 40);
		g.setFont(f);
		g.drawString(playerLevel + "", 20, 40);
		g.setFont(font);
		
		
		for(BounceText text: list){
			text.render(g);
		}
		
		
	}
	
	public void damage(float amount){
		if(health > 0 && !Player.isDamaging()){
			health -= amount;
			Player.setDamaging(true);
			
			list.add(new BounceText(80, 35, String.valueOf(amount), true));
			
		}
		SaveLoadData.health = health;
	}
	public void staminaIncrease(float amount){
		if(stats > 0){
			stats -= amount;
		}
		SaveLoadData.stamina = stats;
	}
	public void addStamina(float amount){
		if(stats < 100){
			stats += amount;
		}
		if(stats >= 100){
			stats = 100;
		}
		SaveLoadData.stamina = stats;
	}
	public void heal(float amount){
		if(health > 0){
			health += amount;
			if(health >= 150){
				health = 150;
			}
			healthScale = health;
			list.add(new BounceText(80, 35, String.valueOf(amount), false));
			dmrt = healthScale;
		}
		SaveLoadData.health = health;
	}
	public void resetHealth(){
		health = 150;
		healthScale = 150;
		SaveLoadData.health = health;
	}

	public float getHealthScale() {
		return healthScale;
	}
	
	public static float getDamage() {
		return damage;
	}
	public static float getDefence() {
		return defence;
	}
	public static boolean isFullHealth(){
		return fullHealth;
	}
	public static boolean isFullStamina(){
		return fullStamina;
	}
	public float getStats() {
		return stats;
	}
	public void setHealth(float health) {
		this.health = health;
		healthScale = health;
		SaveLoadData.health = health;
	}
	public void setStats(float stamina) {
		this.stats = stamina;
	}
}
