package my.game.state;

import java.awt.Color;
import java.awt.Graphics2D;

import my.game.main.Assets;
import my.game.managers.GameStateManager;
import my.game.managers.MouseManager;

public class QuitState extends GameState {

	GameStateButton yesButton;
	GameStateButton noButton;
	MouseManager mouseManager;
	
	public QuitState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void init() {
		mouseManager = new MouseManager();
		yesButton = new GameStateButton((800/2) - (32*3+16), 200,
				Assets.yesButton_normal,Assets.yesButton_hover,Assets.yesButton_clicked);
		noButton = new GameStateButton((800/2) - (32*3+16), 300,new MenuState(gsm),gsm,
				Assets.noButton_normal,Assets.noButton_hover,Assets.noButton_clicked);
		
	}

	@Override
	public void tick(double deltaTime) {
		mouseManager.tick();
		yesButton.tick(deltaTime);
		noButton.tick(deltaTime);
		
		if(yesButton.isHoover()){
			if(yesButton.isPressed()){
				System.exit(1);
			}
		}
		
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(new Color(0,0,0,0));
		g.fillRect(0, 0, 800, 600);
		//g.drawImage(Assets.title, (800/2)-(32*6), 50, (32*6)*2,32*3,null);
		yesButton.render(g);
		noButton.render(g);
		mouseManager.render(g);
		
		g.setColor(Color.white);
		g.drawString("Are you sure??", 325, 100);
		
		g.clipRect(0, 0, 800, 600);
		
	}


}
