package my.game.state;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import my.game.handler.Loading;
import my.game.main.Assets;
import my.game.managers.GameStateManager;
import my.game.managers.MouseManager;
import my.javagame.main.Vector2D;

public class GameStateButton extends Rectangle{

	private GameState gameState;
	private Vector2D pos = new Vector2D();
	private GameStateManager gameStateManager;
	private BufferedImage img_normal;
	private BufferedImage img_hover;
	private BufferedImage img_click;
	
	private int width = 32*3 *2;
	private int height = 32 *2;
	
	private boolean clicked;
	private boolean hoover;
	private int count = 0;
	
	public GameStateButton(float xPos, float yPos, GameState gameState, GameStateManager gsm, BufferedImage img_normal,BufferedImage img_hover,BufferedImage img_click) {
		pos.xPos = xPos;
		pos.yPos = yPos;
		this.gameState = gameState;
		this.gameStateManager = gsm;
		this.img_normal = img_normal;
		this.img_hover = img_hover;
		this.img_click = img_click;
		setBounds((int)pos.xPos, (int)pos.yPos, width, height);
	}
	
	public GameStateButton(float xPos, float yPos, BufferedImage img_normal,BufferedImage img_hover,BufferedImage img_click) {
		pos.xPos = xPos;
		pos.yPos = yPos;
		this.img_normal = img_normal;
		this.img_hover = img_hover;
		this.img_click = img_click;
		setBounds((int)pos.xPos, (int)pos.yPos, width, height);
	}
	
	public void tick(double deltaTime){
		setBounds((int)pos.xPos, (int)pos.yPos, width, height);
		if(getBounds().contains(new Point(MouseManager.mouse))){
			hoover = true;
		} else
			hoover = false;
		
		if(gameState != null){
			if(hoover){
				if(isPressed()){
					Loading.isLoading = true;
					//clicked function
					if(Loading.isLoading){
						count++;
						if(count >= 2){
							hoover = false;
							clicked = false;
							MouseManager.pressed = false;
							gameStateManager.states.push(gameState);
							gameStateManager.states.peek().init();
						}
					}
					
				}
			}
		}
	}
	
	public void render(Graphics2D g){
		if(!hoover){
			g.drawImage(img_normal, (int)pos.xPos, (int)pos.yPos, width, height, null);
		} else {
			if(isPressed()){
				g.drawImage(img_click, (int)pos.xPos, (int)pos.yPos, width, height, null);
			} else {
				g.drawImage(img_hover, (int)pos.xPos, (int)pos.yPos, width, height, null);
			}
		}
		
	}
	
	public boolean isClicked(){
		return clicked;
	}
	public boolean isHoover(){
		return hoover;
	}
	public boolean isPressed(){
		return MouseManager.pressed;
	}

}
