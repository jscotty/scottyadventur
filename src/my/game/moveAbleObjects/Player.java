package my.game.moveAbleObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import my.game.equipment.Equipment;
import my.game.generator.World;
import my.game.handler.Loading;
import my.game.references.Animations;
import my.game.references.Tags;
import my.game.references.WorldData;
import my.game.references.WorldNames;
import my.game.state.ButtonState;
import my.game.inventory.Inventory;
import my.game.inventory.Item;
import my.game.inventory.ItemDrop;
import my.game.main.Animator;
import my.game.main.Assets;
import my.game.main.Check;
import my.game.main.Main;
import my.game.main.SaveLoadData;
import my.game.managers.GUIManager;
import my.game.state.LevelLoader;
import my.javagame.main.Vector2D;


public class Player implements KeyListener {
	
	Vector2D pos;
	public World world;
	private int width = 30;
	private int height = 30;
	private float scale = 2.5f;
	public static boolean up,down,left,right,running,damaging;
	private static boolean debug;
	
	private float speed = 0f;
	private float maxSpeed = 4*32f;
	private float fixedDeltaTime = 1f/60f;
	private long animSpeed = 50;
	private float slowDown = 16.1f;

	private boolean moving = false;
	private boolean mapMove = true;
	private static boolean spawned;
	
	
	private int renderDistanceWidth = 18;
	private int renderDistanceHeight = 20;
	public static Rectangle render;
	
	private int animationState = 0;
	/*
	 * 0 up
	 * 1 down
	 * 2 left
	 * 3 right
	 * 4 idle
	 * */
	Animator animUp;
	Animator animDown;
	Animator animLeft;
	Animator animRight;
	Animator animIdleUp;
	Animator animIdleDown;
	Animator animIdleLeft;
	Animator animIdleRight;
	
	private GUIManager guiManager;
	
	private PlayerActions playerActions;
	private static String lastDir = "down";
	private boolean attack;
	
	public static Inventory inv = new Inventory(new Vector2D(595,225), 6, 8);
	public static Equipment equipment = new Equipment();
	
	private float stamina = 0.002f;
	
	private static boolean colliding = false,itemCollision = false, enter = false;
	private String message = "";
	private ItemDrop dropItem;
	
	public Point p1;
	public Point p2;
	
	private static int count;
	
	public Player(){
		pos  = new Vector2D((Main.width / 2) - (width / 2) - 123, (Main.height / 2) - (height / 2) + 20);
	}
	
	public void init(World world){
		inv.init();
		equipment.init();
		playerActions = new PlayerActions(world);
		playerActions.init();
		this.world = world;
		guiManager = new GUIManager();
		
		render = new Rectangle();
		

			animUp = Animations.getAnimUp();
			animDown = Animations.getAnimDown();
			animLeft = Animations.getAnimLeft();
			animRight = Animations.getAnimRight();
			animIdleDown = Animations.getAnimIdleDown();
			animIdleUp = Animations.getAnimIdleUp();
			animIdleLeft = Animations.getAnimIdleLeft();
			animIdleRight = Animations.getAnimIdleRight();
			
			spawned = true;

			Loading.isLoading = false;

	}
	
	public void tick(double deltaTime){
		p1 = new Point(	(int)	(pos.xPos + world.getWorldXPos()), 
						(int)	(pos.yPos + world.getWorldYPos() + height/2));
		
		p2 = new Point(	(int)	(pos.xPos + world.getWorldXPos() + width/2), 
						(int)	(pos.yPos + world.getWorldYPos() + height/2));
		inv.tick();
		equipment.tick();
		render = new Rectangle(	(int)(pos.xPos - pos.getWorldLocation().xPos + pos.xPos - renderDistanceWidth*16 + width / 2),
								(int)(pos.yPos - pos.getWorldLocation().yPos + pos.yPos - renderDistanceHeight*16 + height / 2),
								renderDistanceWidth*32,renderDistanceHeight*32);
		playerActions.tick();
		moveUpdate();
		
		if(World.getHud().getInvButton().state == ButtonState.state){
			inv.setToggle(true);
		} else {
			inv.setToggle(false);
		}

		if(World.getHud().getEqButton().state == ButtonState.state){
			equipment.setToggle(true);
		} else {
			equipment.setToggle(false);
		}
		
		collisionCheck();
		
	}

	private void collisionCheck() {
		if(Check.collisionObjectWithTag(Tags.LADDER_SAND_01, p1,p2)){
				if(enter){
					Loading.isLoading = true;
					count++;
					System.out.println("count");
					if(count >= 2){
						WorldData.changeWorldPos(24, 13);
						LevelLoader.world.changeToWorld(WorldNames.WORLD_02,"map2",2);
					}
				}
				colliding = true;
				message = "ladder";
		} else if(Check.collisionObjectWithTag(Tags.LADDER_DIRT_01, p1,p2)){
				if(enter){
					Loading.isLoading = true;
					count++;
					if(count >= 3){
						WorldData.changeWorldPos(24, 17);
						LevelLoader.world.changeToWorld(WorldNames.WORLD_01,"map",1);
					}
					
				}
				colliding = true;
				message = "ladder";
		} else if(Check.collisionObjectWithTag(Tags.LADDER_DIRT_01, p1,p2)){
			if(enter){
				Loading.isLoading = true;
				count++;
				if(count >= 3){
					WorldData.changeWorldPos(24, 17);
					LevelLoader.world.changeToWorld(WorldNames.WORLD_01,"map",1);
				}
				
			}
			colliding = true;
			message = "ladder";
		} else if(Check.collisionObjectWithItem(pos.xPos,pos.yPos)!= null){
			dropItem = Check.collisionObjectWithItem(pos.xPos,pos.yPos);
			if(enter && !dropItem.picked){
				inv.addItem(dropItem.getItem());
				dropItem.picked = true;
			}
			itemCollision = true;
		} else {
			itemCollision = false;
			colliding = false;
		}
		
	}

	private void moveUpdate() {
	
		float moveAmount = (float) (speed * fixedDeltaTime);
		
		if(mapMove){
			if(up){
	
				if(!Check.collisionPlayer(
						new Point(	(int)	(pos.xPos + world.getWorldXPos()), 
									(int)	(pos.yPos + world.getWorldYPos() - moveAmount)), 	
									
						new Point(	(int)	(pos.xPos + world.getWorldXPos() + width), 
									(int)	(pos.yPos + world.getWorldYPos() - moveAmount))	)){
					move();
					lastDir = "up";
					world.mapPos.yPos -=moveAmount;
				} else {
					moving = false;
				}
				animationState = 0;
			} else if (down) {
				if(!Check.collisionPlayer(
						new Point(	(int)	(pos.xPos + world.getWorldXPos()), 
									(int)	(pos.yPos + world.getWorldYPos() + height + moveAmount)), 	
							
						new Point(	(int)	(pos.xPos + world.getWorldXPos() + width), 
									(int)	(pos.yPos + world.getWorldYPos() + height + moveAmount))	)){
				move();
				lastDir = "down";
				world.mapPos.yPos+=moveAmount;
			} else {
				moving = false;
			}
				animationState = 1;
			} else if (left) {
				if(!Check.collisionPlayer(
						new Point(	(int)	(pos.xPos + world.getWorldXPos() - moveAmount), 
									(int)	(pos.yPos + world.getWorldYPos() + height)), 	
									
						new Point(	(int)	(pos.xPos + world.getWorldXPos() - moveAmount), 
									(int)	(pos.yPos + world.getWorldYPos()))	)){
					move();
					lastDir = "left";
					world.mapPos.xPos -=moveAmount;
				} else {
					moving = false;
				}
				animationState = 2;
			} else if (right) {
				if(!Check.collisionPlayer(
						new Point(	(int)	(pos.xPos + world.getWorldXPos() + width + moveAmount), 
									(int)	(pos.yPos + world.getWorldYPos())), 	
									
						new Point(	(int)	(pos.xPos + world.getWorldXPos() + width + moveAmount), 
									(int)	(pos.yPos + world.getWorldYPos() + height))	)){
					move();
					lastDir = "right";
					world.mapPos.xPos +=moveAmount;
				} else {
					moving = false;
				}
				animationState = 3;
			} else {
				World.getHud().getpStats().addStamina(stamina*3);

				moving = false;
				if(lastDir == "up"){
					animationState = 4;
				} else if(lastDir == "down"){
					animationState = 5;
				} else if(lastDir == "left"){
					animationState = 6;
				} else if(lastDir == "right"){
					animationState = 7;
				}
				speed = 0;
			}
		} 
		
		
		//Player movement:
		
		
		else {
			if(up){
				if(!Check.collisionPlayer(
						new Point(	(int)	(pos.xPos + world.getWorldXPos()), 
									(int)	(pos.yPos + world.getWorldYPos() - moveAmount)), 	
									
						new Point(	(int)	(pos.xPos + world.getWorldXPos() + width), 
									(int)	(pos.yPos + world.getWorldYPos() - moveAmount))	)){
					move();
					pos.yPos-=moveAmount;
				} else {
					
				}
				animationState = 0;
			} else if (down) {
				if(!Check.collisionPlayer(
						new Point(	(int)	(pos.xPos + world.getWorldXPos()), 
									(int)	(pos.yPos + world.getWorldYPos() + height + moveAmount)), 	
							
						new Point(	(int)	(pos.xPos + world.getWorldXPos() + width), 
									(int)	(pos.yPos + world.getWorldYPos() + height  + moveAmount))	)){
				move();
				pos.yPos+=moveAmount;
			} else {
			
			}
				animationState = 1;
			} else if (left) {
				if(!Check.collisionPlayer(
						new Point(	(int)	(pos.xPos + world.getWorldXPos() - moveAmount), 
									(int)	(pos.yPos + world.getWorldYPos() + height)), 	
									
						new Point(	(int)	(pos.xPos + world.getWorldXPos() - moveAmount), 
									(int)	(pos.yPos + world.getWorldYPos()))	)){
					move();
					pos.xPos-=moveAmount;
				} else {
					
				}
				animationState = 2;
			} else if (right) {
				if(!Check.collisionPlayer(
						new Point(	(int)	(pos.xPos + world.getWorldXPos() + width + moveAmount), 
									(int)	(pos.yPos + world.getWorldYPos())), 	
									
						new Point(	(int)	(pos.xPos + world.getWorldXPos() + width + moveAmount), 
									(int)	(pos.yPos + world.getWorldYPos() + height))	)){
					move();
					pos.xPos+=moveAmount;
				} else {
					
				}
				animationState = 3;
			} else {
				speed = 0;
				moving = false;
				World.getHud().getpStats().addStamina(stamina*3);
				
				if(lastDir == "up"){
					animationState = 4;
				} else if(lastDir == "down"){
					animationState = 5;
				} else if(lastDir == "left"){
					animationState = 6;
				} else if(lastDir == "right"){
					animationState = 7;
				}
			}
			
		}
		
		
	}

	private void move() {
		moving = true;
		resetIdle();
			speed = maxSpeed;
		

		if(running){
			if(World.getHud().getpStats().getStats() <= 0){
				running = false;
			}
			if(animSpeed != 20){
				animSpeed = 20;

				animUp.setSpeed(animSpeed);
				animDown.setSpeed(animSpeed);
				animLeft.setSpeed(animSpeed);
				animRight.setSpeed(animSpeed);
				
				speed = 5;
				maxSpeed += 100;
			}
			World.getHud().getpStats().staminaIncrease(0.1f);
		} else{
			World.getHud().getpStats().addStamina(stamina);
			if(animSpeed != 50){
				animSpeed = 50;

				animUp.setSpeed(animSpeed);
				animDown.setSpeed(animSpeed);
				animLeft.setSpeed(animSpeed);
				animRight.setSpeed(animSpeed);
				
				maxSpeed -= 100;
			}
		}
		
	}

	
	public void render(Graphics2D g){
		//g.fillRect((int)pos.xPos, (int)pos.yPos, width, height);
		playerActions.render(g);
		switch (animationState) {
		case 0:
			//up
			g.drawImage(animUp.sprite, (int)pos.xPos - (int)(width /1.2f), (int)pos.yPos - (int)(height*1.5), (int)(width*scale),(int)(height*scale),null);
			if(up){
				animUp.update(System.currentTimeMillis());
			}
			break;
		case 1:
			//down
			g.drawImage(animDown.sprite, (int)pos.xPos - (int)(width /1.2f), (int)pos.yPos - (int)(height*1.5), (int)(width*scale),(int)(height*scale),null);
			if(down)
				animDown.update(System.currentTimeMillis());
			break;
		case 2:
			//left
			g.drawImage(animLeft.sprite, (int)pos.xPos - (int)(width /1.2f), (int)pos.yPos - (int)(height*1.5), (int)(width*scale),(int)(height*scale),null);
			if(left)
				animLeft.update(System.currentTimeMillis());
			break;
		case 3:
			//right
			g.drawImage(animRight.sprite, (int)pos.xPos - (int)(width /1.2f), (int)pos.yPos - (int)(height*1.5), (int)(width*scale),(int)(height*scale),null);
			if(right)
				animRight.update(System.currentTimeMillis());
			break;
		case 4:
			//idleUp
			g.drawImage(animIdleUp.sprite, (int)pos.xPos - (int)(width /1.2f), (int)pos.yPos - (int)(height*1.5), (int)(width*scale),(int)(height*scale),null);
			if(!up&&!down&&!left&&!right)
				animIdleUp.update(System.currentTimeMillis());
			break;
		case 5:
			//idleDown
			g.drawImage(animIdleDown.sprite, (int)pos.xPos - (int)(width /1.2f), (int)pos.yPos - (int)(height*1.5), (int)(width*scale),(int)(height*scale),null);
			if(!up&&!down&&!left&&!right)
				animIdleDown.update(System.currentTimeMillis());
			break;
		case 6:
			//idleLeft
			g.drawImage(animIdleLeft.sprite, (int)pos.xPos - (int)(width /1.2f), (int)pos.yPos - (int)(height*1.5), (int)(width*scale),(int)(height*scale),null);
			if(!up&&!down&&!left&&!right)
				animIdleLeft.update(System.currentTimeMillis());
			break;
		case 7:
			//idleRight
			g.drawImage(animIdleRight.sprite, (int)pos.xPos - (int)(width /1.2f), (int)pos.yPos - (int)(height*1.5), (int)(width*scale),(int)(height*scale),null);
			if(!up&&!down&&!left&&!right)
				animIdleRight.update(System.currentTimeMillis());
			break;
		}
		
		if(damaging){
			g.drawImage(Assets.getDamagePlayer(), (int)pos.xPos - (int)(width /1.2f), (int)pos.yPos - (int)(height*1.5), (int)(width*scale),(int)(height*scale),null);
		}
		
		guiManager.render(g);
		g.setColor(Color.green);
		
		
		//g.translate(pos.xPos, pos.yPos);
		
		//g.drawRect((int)pos.xPos - renderDistanceWidth*16 + width /2, (int)pos.yPos - renderDistanceHeight*16 + height/2, renderDistanceWidth*32, renderDistanceHeight*32);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP){
			up = true;
			down = false;
			left = false;
			right = false;
			
			//LevelLoader.world.getEnemys().get(0).setMoveNum(0);
		} else if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN){
			down = true;
			up = false;
			left = false;
			right = false;
			
			//LevelLoader.world.getEnemys().get(0).setMoveNum(1);
		} else if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT){
			left = true;
			down = false;
			up = false;
			right = false;
			
			//LevelLoader.world.getEnemys().get(0).setMoveNum(2);
		} else if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT){
			right = true;
			down = false;
			up = false;
			left = false;
			
			//LevelLoader.world.getEnemys().get(0).setMoveNum(3);
		} else if(key == KeyEvent.VK_SHIFT){
				running = true;
		} else if(key == KeyEvent.VK_F3){
			if(debug)
				debug = false;
			else if(!debug)
				debug = true;
			
		} else if(key == KeyEvent.VK_F){
			World.getHud().getpStats().damage(15);
		} else if(key == KeyEvent.VK_H){
			World.getHud().getpStats().heal(5);
		} else if(key == KeyEvent.VK_T){
			String msg = "hallo meneer";
			World.getTxtHandler().type(msg);
		} else if(key == KeyEvent.VK_F1){
			inv.toggle();
			World.getHud().getInvButton().active();
		} else if(key == KeyEvent.VK_F3){
			debug = !debug;
		} else if(key == KeyEvent.VK_X){
			inv.addItem(Item.HEALTH_POT);
		} else if(key == KeyEvent.VK_C){
			inv.addItem(Item.STAMINA_POT);
		} else if(key == KeyEvent.VK_K){
			SaveLoadData.save();
		} else if(key == KeyEvent.VK_L){
			SaveLoadData.load();
		} else if(key == KeyEvent.VK_ENTER){
			enter = true;
		}
		/* else if(key == KeyEvent.VK_C){
			inv.addItem(Item.NULL);
		}*/
		
		if(key == KeyEvent.VK_SPACE){
			
			if(World.getPlayer().getPlayerActions().getCompleted()){
				if(lastDir == "up"){
					World.getPlayer().getPlayerActions().attackUp();
				} else if(lastDir == "down"){
					World.getPlayer().getPlayerActions().attackDown();
				} else if(lastDir == "left"){
					World.getPlayer().getPlayerActions().attackLeft();
				} else if(lastDir == "right"){
					World.getPlayer().getPlayerActions().attackRight();
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP){
			lastDir = "up";
			up = false;
		} else if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN){
			lastDir = "down";
			down = false;
		} else if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT){
			lastDir = "left";
			left = false;
		} else if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT){
			lastDir = "right";
			right = false;
		} else if(key == KeyEvent.VK_SHIFT){
			running = false;
		} else if(key == KeyEvent.VK_ENTER){
			enter = false;
			count = 0;
		}
	}

	private void resetIdle() {
//		System.out.println(animIdleUp);
		animIdleUp.reset();
		animIdleDown.reset();
		animIdleLeft.reset();
		animIdleRight.reset();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	/*
	 * Getters and setters
	 * */
	
	public Vector2D getPos() {
		return pos;
	}
	public float getMaxSpeed() {
		return maxSpeed;
	}
	public float getSlowDown() {
		return slowDown;
	}
	public static boolean isDebug(){
		return debug;
	}
	public boolean isMoving(){
		return moving;
	}
	public boolean hasSpawned(){
		return spawned;
	}
	public boolean getAttack(){
		return attack;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public String getLastDir(){
		return lastDir;
	}
	public World getWorld() {
		return world;
	}
	public static boolean isDamaging(){
		return damaging;
	}
	public boolean getColliding(){
		return colliding;
	}
	public boolean getItemColliding(){
		return itemCollision;
	}
	public String getMessage() {
		return message;
	}
	public float getSpeed() {
		return speed;
	}
	public ItemDrop getDropItem() {
		return dropItem;
	}
	public static void setDamaging(boolean damaging) {
		Player.damaging = damaging;
	}
	
	public PlayerActions getPlayerActions() {
		return playerActions;
	}
	
	public static Equipment getEquipment() {
		return equipment;
	}
	
	public static class PlayerActions{
		
		private World world;
		private Player player;
		private Vector2D pos;
		
		Animator animAttack;
		
		private boolean completed = true;
		private boolean attack = false;
		private double attackTime = 4;
		private int count = 0;
		private boolean up,down,left,right;
		private int explosionWidth = 50, explosionHeight = 50;
		private CopyOnWriteArrayList<Bullet> bullets = new CopyOnWriteArrayList<Bullet>();
		
		private float speed = 6;
		
		private Vector2D dirUp = new Vector2D(0, -speed);
		private Vector2D dirDown = new Vector2D(0, speed);
		private Vector2D dirLeft = new Vector2D(-speed, 0);
		private Vector2D dirRight = new Vector2D(speed, 0);
		
		public void init(){
			player = new Player();
			
			animAttack = Animations.getExplosionAnim_01();
		}
		
		public void tick(){
			pos = new Vector2D(player.getPos().xPos + 10, player.getPos().yPos);
			for (Bullet bullet:bullets) {
				bullet.tick();
				
				if(!bullet.isAlive()){
					bullets.remove(bullet);
				}
			}
			if(!completed){
				if(attack){
					startAttack();
				}
			}
			
			
		}
		
		public void render(Graphics2D g){
			for (Bullet bullet:bullets) {
				bullet.render(g);
			}
			if(attack){
				if(up){
					g.drawImage(animAttack.sprite, (int)player.getPos().xPos - (player.getWidth() / 4), (int)player.getPos().yPos - explosionHeight, explosionWidth, explosionHeight,null);
				} else if(down){
					g.drawImage(animAttack.sprite, (int)player.getPos().xPos - (player.getWidth() / 4), (int)player.getPos().yPos + (player.getHeight()-5), explosionWidth, explosionHeight,null);
				} else if(left){
					g.drawImage(animAttack.sprite, (int)player.getPos().xPos - (player.getWidth()+10), (int)player.getPos().yPos - (player.getHeight() / 2), explosionWidth, explosionHeight,null);
				} else if(right){
					g.drawImage(animAttack.sprite, (int)player.getPos().xPos + player.getWidth(), (int)player.getPos().yPos - (player.getHeight() / 2), explosionWidth, explosionHeight,null);
				}
				animAttack.update(System.currentTimeMillis());
			}
		}
		
		private void startAttack() {
			if(attackTime != 0){
				if(count == 0)
					animAttack.play();
				count++;
				attackTime-=0.1;
			}
			if(attackTime <= 0){
				attack = false;
				completed = true;
				attackTime = 4;
				count = 0;
				animAttack.stop();
				animAttack.reset();
				//System.out.println("stop");
			}
		}

		public PlayerActions(World world){
			this.world = world;
		}
		
		public void attackUp(){
			attack = true;
			completed = false;
			
			up = true;
			down = false;
			left = false;
			right = false;
			
			bullets.add(new Bullet(pos, dirUp, Assets.getBullet(), 5));
		}
		
		public void attackDown(){
			attack = true;
			completed = false;
			
			up = false;
			down = true;
			left = false;
			right = false;
			bullets.add(new Bullet(pos, dirDown, Assets.getBullet(), 5));
		}
		
		public void attackLeft(){
			attack = true;
			completed = false;
			
			up = false;
			down = false;
			left = true;
			right = false;
			bullets.add(new Bullet(pos, dirLeft, Assets.getBullet(), 5));
		}
		
		public void attackRight(){
			attack = true;
			completed = false;
			
			up = false;
			down = false;
			left = false;
			right = true;
			bullets.add(new Bullet(pos, dirRight, Assets.getBullet(), 5));
		}
		
		public boolean getCompleted(){
			return completed;
		}
		
		public boolean getAttack(){
			return attack;
		}
		
		public Animator getAnimAttack() {
			return animAttack;
		}
		
	}

}

