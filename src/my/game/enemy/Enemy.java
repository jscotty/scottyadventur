package my.game.enemy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import my.game.generator.Block;
import my.game.generator.World;
import my.game.handler.Loading;
import my.game.main.Animator;
import my.game.main.Assets;
import my.game.main.Check;
import my.game.managers.MouseManager;
import my.game.moveAbleObjects.Player;
import my.game.pathfinding.AStar;
import my.game.state.LevelLoader;
import my.javagame.main.Vector2D;

public class Enemy extends Rectangle {

	public boolean alive = true, following = false, loading = true, attack = false;
	
	private Vector2D pos;
	private EnemyType enemyType;
	private World world;
	private EnemyUI enemyUI;
	
	private boolean pushInfo;
	
	private int width = 30, height = 30, npcWidth, npcHeight;
	
	private ArrayList<BufferedImage> listUp;
	Animator animUp;
	private ArrayList<BufferedImage> listDown;
	Animator animDown;
	private ArrayList<BufferedImage> listLeft;
	Animator animLeft;
	private ArrayList<BufferedImage> listRight;
	Animator animRight;
	private ArrayList<BufferedImage> listIdleUp;
	Animator animIdleUp;
	private ArrayList<BufferedImage> listIdleDown;
	Animator animIdleDown;
	private ArrayList<BufferedImage> listIdleLeft;
	Animator animIdleLeft;
	private ArrayList<BufferedImage> listIdleRight;
	Animator animIdleRight;
	
	private int animSpeed = 50;
	private int animationState = 0;
	
	private float scale = 1.5f;
	
	private int timeCount = 0, count = 0;
	private int maxTime = 100;

	private float speed = 0f;
	private float maxSpeed = 12f;
	private float slowDown = 16.1f;
	private float fixedDeltaTime = 1f/60f;
	
	public Point p1,p2;
	
	private int moveNum =5;//= (int)Math.floor(Math.random()*8);
	private String lastDir = "right";
	private AStar astar = new AStar();
	
	private int index = 0, dX,dY,pX,pY,maxPX,maxPY;
	
	private CopyOnWriteArrayList<Block> path = new CopyOnWriteArrayList<Block>();
	
	public Enemy(Vector2D pos, EnemyType enemyType, World world,int width, int height,int pX, int pY) {
		this.pos = pos;
		this.enemyType = enemyType;
		this.world = world;
		
		this.npcWidth = width;
		this.npcHeight = height;
		
		this.pX = pX;
		this.pY = pY;
		
		this.maxPX = 0;
		this.maxPY = 0;

		newPath();
	}
	
	public Enemy(Vector2D pos, EnemyType enemyType, World world,int width, int height,int pX, int pY, int maxPX, int maxPY) {
		this.pos = pos;
		this.enemyType = enemyType;
		this.world = world;
		
		this.npcWidth = width;
		this.npcHeight = height;
		
		this.pX = pX;
		this.pY = pY;
		
		this.maxPX = maxPX;
		this.maxPY = maxPY;
		
		newPath();
	}
	
	public void init(){
		this.setBounds((int)(pos.xPos + LevelLoader.world.getWorldXPos()), (int)(pos.yPos+ LevelLoader.world.getWorldYPos()) , npcWidth, npcHeight);
		
		listUp = new ArrayList<BufferedImage>();
		listDown = new ArrayList<BufferedImage>();
		listLeft = new ArrayList<BufferedImage>();
		listRight = new ArrayList<BufferedImage>();
		listIdleUp = new ArrayList<BufferedImage>();
		listIdleDown = new ArrayList<BufferedImage>();
		listIdleLeft = new ArrayList<BufferedImage>();
		listIdleRight = new ArrayList<BufferedImage>();
		
		if(enemyType == EnemyType.SLUCK_LVL_01 || enemyType == EnemyType.SLUCK_LVL_02 || enemyType == EnemyType.SLUCK_LVL_05 || enemyType == EnemyType.SLUCK_LVL_09){
			addSluck();
		} else if(enemyType == EnemyType.MAN_LVL_01){
			addMan();
		}
		
		anim();
		enemyUI = new EnemyUI(this);
	}
	
	private void newPath(){
		index = 0;
		//System.out.println((int)(pos.xPos + LevelLoader.world.getWorldXPos()));
		//path = astar.search(new Point((int)(pos.xPos + LevelLoader.world.getWorldXPos()),(int)(pos.yPos + LevelLoader.world.getWorldYPos())), new Point((int)(Math.random()*25+10)*32,(int)(Math.random()*20+10)*32));
		path = astar.search(new Point((int)(pos.xPos + LevelLoader.world.getWorldXPos()),(int)(pos.yPos + LevelLoader.world.getWorldYPos())), new Point((int)((Math.random()*pX)+maxPX)*32,(int)((Math.random()*pY)+maxPY)*32));
		
		if(path.size() == 0){
			newPath();
		}
		if(path != null){
			dX = (int)((pos.xPos+ LevelLoader.world.getWorldXPos() + 16) - (path.get(index).getPos().xPos))/16;
			dY = (int)((pos.yPos+ LevelLoader.world.getWorldYPos() + 16) - (path.get(index).getPos().yPos))/16;
		}
		
		Loading.isLoading = false;
	}
	
	private void pathToPlayer(){
		index = 0;
		int xPos = (int)(World.getPlayer().getPos().xPos + LevelLoader.world.getWorldXPos());
		int yPos = (int)(World.getPlayer().getPos().yPos + LevelLoader.world.getWorldYPos());
		path = astar.search(new Point((int)(pos.xPos + LevelLoader.world.getWorldXPos()),(int)(pos.yPos + LevelLoader.world.getWorldYPos())), new Point(xPos,yPos));

		if(path != null){
			dX = (int)((pos.xPos+ LevelLoader.world.getWorldXPos() + 16) - (path.get(index).getPos().xPos))/16;
			dY = (int)((pos.yPos+ LevelLoader.world.getWorldYPos() + 16) - (path.get(index).getPos().yPos))/16;
		}
	}
	
	private void anim() {
		animUp = new Animator(listUp);
		animUp.setSpeed(animSpeed);
		animUp.play();

		animDown = new Animator(listDown);
		animDown.setSpeed(animSpeed);
		animDown.play();

		animLeft = new Animator(listLeft);
		animLeft.setSpeed(animSpeed);
		animLeft.play();
		
		animRight = new Animator(listRight);
		animRight.setSpeed(animSpeed);
		animRight.play();

		animIdleDown = new Animator(listIdleDown);
		animIdleDown.setSpeed(250);
		animIdleDown.play();
		
		animIdleUp = new Animator(listIdleUp);
		animIdleUp.setSpeed(250);
		animIdleUp.play();

		animIdleLeft = new Animator(listIdleLeft);
		animIdleLeft.setSpeed(250);
		animIdleLeft.play();

		animIdleRight = new Animator(listIdleRight);
		animIdleRight.setSpeed(250);
		animIdleRight.play();
	}

	public void tick(){
		this.setBounds((int)(pos.xPos + LevelLoader.world.getWorldXPos()), (int)(pos.yPos+ LevelLoader.world.getWorldYPos()) , npcWidth, npcHeight);
		//System.out.println(getBounds() + " pb:" + Player.render.getBounds());
		if(alive){
			p1 = new Point(	(int)	(pos.xPos + world.getWorldXPos()), 
					(int)	(pos.yPos + world.getWorldYPos()));	
					
			p2 = new Point(	(int)	(pos.xPos + world.getWorldXPos()), 
					(int)	(pos.yPos + world.getWorldYPos()));
			follow();
			moveUpdate();
			enemyUI.tick();
			
		}
	}
	
	private void follow() {
		if(enemyUI.isDamaged())
			following = true;
		if(following){
			count++;
			if(count == 1)
				pathToPlayer();
			float difX = pos.xPos - World.getPlayer().getPos().xPos;
			float difY = pos.yPos - World.getPlayer().getPos().yPos;
			
			if(path.size() >= 2){
				attack = true;
			}
			if(difX >= -160 && difX <= 182 && difY >= -160 && difY <= 139){
				System.out.println("dx: " + (difX) + " dy: " + difY);
				if(difX >= -50 && difX <= 45 && difY >= -60 && difY <= 47){
					if(difX > difY){
						lastDir = "left";
					} else if(difX < difY){
						lastDir = "right";
					}
					attack = true;
					moveNum = 8;
				} else {
					attack = false;
				}
			} else {
				timeCount ++;
				if(timeCount >= maxTime*2){
					following = false;
					enemyUI.setDamaged(false);
					//moveNum = (int)Math.floor(Math.random()*8);
					timeCount = 0;
				}
			}
		} else {
			timeCount ++;
			//newPath();
			if(timeCount >= maxTime){
				//moveNum = (int)Math.floor(Math.random()*8);
				timeCount = 0;
			}
		}
		//System.out.println("x:" + dX + " y:" + dY + " i:" + index);
		if(path != null && !attack){
			if(dX > 0 ){//left
				moveLeft();
			} else if(dX < 0 ){//right
				moveRight();
			} else if(dY < 0 ){//down
				moveDown();
			} else if(dY > 0 ){//up
				moveUp();
				
			} else {
				moveNum = 8;
				dX = (int)((pos.xPos+ LevelLoader.world.getWorldXPos() - 16) - (path.get(index).getPos().xPos))/16;
				dY = (int)((pos.yPos+ LevelLoader.world.getWorldYPos() - 16) - (path.get(index).getPos().yPos))/16;
				
				if(following){
					if(index >= path.size()-2){
							pathToPlayer();
					} else
						index++;
				} else {
					if(index >= path.size()-1){
							newPath();
					} else{
						index++;
						count = 0;
					}
				}
			}
		}
		
	}
	
	private void moveUp(){
		moveNum = 0;

		dX = (int)((pos.xPos+ LevelLoader.world.getWorldXPos() - 16) - (path.get(index).getPos().xPos))/16;
		dY = (int)((pos.yPos+ LevelLoader.world.getWorldYPos() - 16) - (path.get(index).getPos().yPos))/16;
	}
	private void moveDown(){
		moveNum = 1;

		dX = (int)((pos.xPos+ LevelLoader.world.getWorldXPos() - 16) - (path.get(index).getPos().xPos))/16;
		dY = (int)((pos.yPos+ LevelLoader.world.getWorldYPos() + 16) - (path.get(index).getPos().yPos))/16;
	}
	private void moveLeft(){
		moveNum = 2;

		dX = (int)((pos.xPos+ LevelLoader.world.getWorldXPos() + 16) - (path.get(index).getPos().xPos))/16;
		dY = (int)((pos.yPos+ LevelLoader.world.getWorldYPos() - 16) - (path.get(index).getPos().yPos))/16;
	}
	private void moveRight(){
		moveNum = 3;

		dX = (int)((pos.xPos+ LevelLoader.world.getWorldXPos() - 16) - (path.get(index).getPos().xPos))/16;
		dY = (int)((pos.yPos+ LevelLoader.world.getWorldYPos() + 16) - (path.get(index).getPos().yPos))/16;
	}

	private void moveUpdate() {
		float moveAmount = (float) (speed * fixedDeltaTime);
		if(World.getPlayer().isMoving()){
			float divide = 61;
			if(Player.up){
				pos.yPos += (World.getPlayer().getSpeed() )/ divide;
			}
			if(Player.down){
				pos.yPos -= (World.getPlayer().getSpeed() ) / divide;
			}
			if(Player.right){
				pos.xPos -= (World.getPlayer().getSpeed() ) / divide;
			}
			if(Player.left){
				pos.xPos += (World.getPlayer().getSpeed() ) / divide;
			}
		}
		
		if(moveNum == 0){ // up
			if(!Check.collisionPlayer(
					new Point(	(int)	(pos.xPos + world.getWorldXPos()), 
								(int)	(pos.yPos + world.getWorldYPos() + moveAmount)), 	
								
					new Point(	(int)	(pos.xPos + world.getWorldXPos() + width/2), 
								(int)	(pos.yPos + world.getWorldYPos() + moveAmount))	)){
				move();
				lastDir  = "up";
				pos.yPos -= moveAmount;
			} else {
				
			}
			animationState = 0;
		} else if(moveNum == 1){ // down
			if(!Check.collisionPlayer(
					new Point(	(int)	(pos.xPos + world.getWorldXPos()+ width), 
								(int)	(pos.yPos + world.getWorldYPos() + width  + moveAmount)), 	
						
					new Point(	(int)	(pos.xPos + world.getWorldXPos() + width), 
								(int)	(pos.yPos + world.getWorldYPos()   + moveAmount))	)){
			move();
			lastDir  = "down";
			pos.yPos += moveAmount;
		} else {
		
		}
			animationState = 1;
		} else if(moveNum == 2){ // left
			if(!Check.collisionPlayer(
					new Point(	(int)	(pos.xPos + world.getWorldXPos() - moveAmount), 
								(int)	(pos.yPos + height + world.getWorldYPos() )), 	
								
					new Point(	(int)	(pos.xPos + world.getWorldXPos() + moveAmount), 
								(int)	(pos.yPos + height + world.getWorldYPos()))	)){
				move();
				lastDir  = "left";
				pos.xPos-=moveAmount;
			} else {
				
			}
			animationState = 2;
		} else if(moveNum == 3){ // right
			if(!Check.collisionPlayer(
					new Point(	(int)	(pos.xPos + world.getWorldXPos() + width + moveAmount), 
								(int)	(pos.yPos + height + world.getWorldYPos())), 	
								
					new Point(	(int)	(pos.xPos + world.getWorldXPos() + width + moveAmount), 
								(int)	(pos.yPos + height + world.getWorldYPos() ))	)){
				move();
				lastDir  = "right";
				pos.xPos+=moveAmount;
			} else {
				
			}
			animationState = 3;
		} else { // idle
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

	private void move() {
		if(speed <= maxSpeed){
			speed += slowDown;
		} else {
			speed = maxSpeed;
		}
	}

	public void render(Graphics2D g){
		if(alive && loading){
			g.setColor(Color.BLACK);
			if(path != null && Player.isDebug()){
				for (int i = 0; i < path.size(); i++) {
					g.fillRect((int)(path.get(i).getPos().xPos - LevelLoader.world.getWorldXPos()), (int)(path.get(i).getPos().yPos- LevelLoader.world.getWorldYPos()), 32, 32);
				}
			}
			//g.fillRect((int)(pos.xPos - npcWidth /3f), (int)pos.yPos , npcWidth, npcHeight);
			switch (animationState) {
			case 0:
				//up
				g.drawImage(animUp.sprite, (int)pos.xPos - (int)(npcWidth/2.5f), (int)pos.yPos - (int)(npcHeight/1.3f), (int)(npcWidth*scale),(int)(npcHeight*scale),null);
			
					animUp.update(System.currentTimeMillis());
				break;
			case 1:
				//down
				g.drawImage(animDown.sprite, (int)pos.xPos - (int)(npcWidth/2.5f), (int)pos.yPos - (int)(npcHeight/1.3f), (int)(npcWidth*scale),(int)(npcHeight*scale),null);
				
					animDown.update(System.currentTimeMillis());
				break;
			case 2:
				//left
				g.drawImage(animLeft.sprite, (int)pos.xPos - (int)(npcWidth/2.5f), (int)pos.yPos - (int)(npcHeight/1.3f), (int)(npcWidth*scale),(int)(npcHeight*scale),null);
				
					animLeft.update(System.currentTimeMillis());
				break;
			case 3:
				//right
				g.drawImage(animRight.sprite, (int)pos.xPos - (int)(npcWidth/2.5f), (int)pos.yPos - (int)(npcHeight/1.3f), (int)(npcWidth*scale),(int)(npcHeight*scale),null);
				
					animRight.update(System.currentTimeMillis());
				break;
			case 4:
				//idleUp
				g.drawImage(animIdleUp.sprite, (int)pos.xPos - (int)(npcWidth/2.5f), (int)pos.yPos - (int)(npcHeight/1.3f), (int)(npcWidth*scale),(int)(npcHeight*scale),null);
				
					animIdleUp.update(System.currentTimeMillis());
				break;
			case 5:
				//idleDown
				g.drawImage(animIdleDown.sprite, (int)pos.xPos - (int)(npcWidth/2.5f), (int)pos.yPos - (int)(npcHeight/1.3f), (int)(npcWidth*scale),(int)(npcHeight*scale),null);
				
					animIdleDown.update(System.currentTimeMillis());
				break;
			case 6:
				//idleLeft
				g.drawImage(animIdleLeft.sprite, (int)pos.xPos - (int)(npcWidth/2.5f), (int)pos.yPos - (int)(npcHeight/1.3f), (int)(npcWidth*scale),(int)(npcHeight*scale),null);
				
					animIdleLeft.update(System.currentTimeMillis());
				break;
			case 7:
				//idleRight
				g.drawImage(animIdleRight.sprite, (int)pos.xPos - (int)(npcWidth/2.5f), (int)pos.yPos - (int)(npcHeight/1.3f), (int)(npcWidth*scale),(int)(npcHeight*scale),null);
				
					animIdleRight.update(System.currentTimeMillis());
				break;
			}
			enemyUI.render(g);
		}
	}
	
	private void addMan() {
		listUp.add(Assets.enemy.getTile(0, 16*4, 16, 16));
		listUp.add(Assets.enemy.getTile(16, 16*4, 16, 16));

		listDown.add(Assets.enemy.getTile(0, 16*5, 16, 16));
		listDown.add(Assets.enemy.getTile(16, 16*5, 16, 16));
		
		listLeft.add(Assets.enemy.getTile(0, 16*6, 16, 16));
		listLeft.add(Assets.enemy.getTile(16, 16*6, 16, 16));
		
		listRight.add(Assets.enemy.getTile(0, 16*7, 16, 16));
		listRight.add(Assets.enemy.getTile(16, 16*7, 16, 16));

		listIdleUp = listUp;
		listIdleDown = listDown;
		listIdleLeft = listLeft;
		listIdleRight = listRight;
		
	}

	private void addSluck(){
		listUp.add(Assets.enemy.getTile(0, 0, 16, 16));
		listUp.add(Assets.enemy.getTile(16, 0, 16, 16));

		listDown.add(Assets.enemy.getTile(0, 16, 16, 16));
		listDown.add(Assets.enemy.getTile(16, 16, 16, 16));
		
		listLeft.add(Assets.enemy.getTile(0, 16*2, 16, 16));
		listLeft.add(Assets.enemy.getTile(16, 16*2, 16, 16));
		
		listRight.add(Assets.enemy.getTile(0, 16*3, 16, 16));
		listRight.add(Assets.enemy.getTile(16, 16*3, 16, 16));

		listIdleUp = listUp;
		listIdleDown = listDown;
		listIdleLeft = listLeft;
		listIdleRight = listRight;
	}
	
	public boolean front(){
		float difY = pos.yPos - World.getPlayer().getPos().yPos;
		if(difY > 0){
			return true;
		} else {
			return false;
		}
	}
	
	public Vector2D getPos() {
		return pos;
	}
	public boolean pushInfo(){
		return pushInfo;
	}
	public EnemyType getEnemyType() {
		return enemyType;
	}
	public int getNpcWidth() {
		return npcWidth;
	}
	public int getNpcHeight() {
		return npcHeight;
	}
	public EnemyUI getEnemyUI() {
		return enemyUI;
	}
	public void setMoveNum(int moveNum) {
		this.moveNum = moveNum;
	}
	
	public static class EnemyActions{
		
		private Enemy enemy;
		
		public EnemyActions(){
			
		}
		
		public void init(double deltaTime){
			
		}
		
		public void render(Graphics2D g){
			
		}
		
	}

	
}
