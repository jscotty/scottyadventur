package my.game.state;

import java.awt.Color;
import java.awt.Graphics2D;

import my.game.main.Assets;
import my.game.managers.GameStateManager;
import my.game.managers.MouseManager;

public class MenuState extends GameState {

	GameStateButton startButton;
	GameStateButton optionsButton;
	GameStateButton quitButton;
	MouseManager mouseManager;
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void init() {
		mouseManager = new MouseManager();
		startButton = new GameStateButton((800/2) - (32*3+16), 200,new LevelLoader(gsm),gsm,
				Assets.startButton_normal,Assets.startButton_hover,Assets.startButton_clicked);
		optionsButton = new GameStateButton((800/2) - (32*3+16), 300,new LevelLoader(gsm),gsm,
				Assets.optionsButton_normal,Assets.optionsButton_hover,Assets.optionsButton_clicked);
		quitButton = new GameStateButton((800/2) - (32*3+16), 400,new QuitState(gsm),gsm,
				Assets.quitButton_normal,Assets.quitButton_hover,Assets.quitButton_clicked);
		
	}

	@Override
	public void tick(double deltaTime) {
		mouseManager.tick();
		startButton.tick(deltaTime);
		optionsButton.tick(deltaTime);
		quitButton.tick(deltaTime);
		
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(new Color(99,167,226));
		g.fillRect(0, 0, 800, 600);
		g.drawImage(Assets.title, (800/2)-(32*6), 50, (32*6)*2,32*3,null);
		startButton.render(g);
		optionsButton.render(g);
		quitButton.render(g);
		mouseManager.render(g);
		
		g.clipRect(0, 0, 800, 600);
		
	}

}
