package my.game.handler;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import my.game.managers.MouseManager;
import my.game.state.ButtonState;
import my.javagame.main.Vector2D;

public class ButtonHandler extends Rectangle{

	private BufferedImage buttonImg;
	private BufferedImage buttonImgActive;
	private Vector2D pos = new Vector2D();
	
	private boolean active;
	private boolean heldOver;
	private int width, height;
	
	private int count = 0;
	private boolean clicked;
	
	public int state;

	public ButtonHandler(BufferedImage buttonImg, Vector2D pos, int width, int height, int state) {
		this.buttonImg = buttonImg;
		this.pos = pos;
		this.width = width;
		this.height = height;
		this.state = state;
		setBounds((int)pos.xPos, (int)pos.yPos, width, height);
		
		init();
	}

	public ButtonHandler(BufferedImage buttonImg,BufferedImage buttonImgActive, Vector2D pos, int width, int height, int state) {
		this.buttonImg = buttonImg;
		this.buttonImgActive = buttonImgActive;
		this.pos = pos;
		this.width = width;
		this.height = height;
		this.state = state;
		setBounds((int)pos.xPos, (int)pos.yPos, width, height);
		
		init();
	}
	
	private void init() {
		MouseManager.clicked = false;
	}
	
	public void tick(){
		setBounds((int)pos.xPos, (int)pos.yPos, width, height);
		if(!this.contains(MouseManager.mouse)){
			heldOver = false;
			clicked = false;
		} else {
			clicked = MouseManager.clicked;
			heldOver = true;
			if(clicked){
				active = !active;
				clicked = false;
				ButtonState.state = state;
				MouseManager.clicked = false;
			}
		}
	}
	
	public void render(Graphics2D g){
		if(state != ButtonState.state){
			g.drawImage(buttonImg, (int)pos.xPos,(int)pos.yPos, width, height, null);
		} else {
			g.drawImage(buttonImgActive, (int)pos.xPos,(int)pos.yPos, width, height, null);
		}
	}
	
	public boolean isActive(){
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public void active() {
		active = !active;
	}

}
