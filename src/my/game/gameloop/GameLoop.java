package my.game.gameloop;

import java.awt.Component;
import java.awt.Graphics2D;

import my.game.handler.Loading;
import my.game.main.Assets;
import my.game.managers.GameStateManager;
import my.game.references.Animations;
import my.javagame.main.IDGameLoop;
import my.javagame.main.Vector2D;

public class GameLoop extends IDGameLoop {

	GameStateManager gsm;
	public static Assets assets = new Assets();
	private Animations anims = new Animations();
	private Loading load;
	
	public GameLoop(int width, int height) {
		super(width, height);
	}
	
	@Override
	public void init() {
		assets.init();
		anims.init();
		gsm = new GameStateManager();
		gsm.init();
		load = new Loading();
		super.init();
	}
	
	@Override
	public void tick(double deltaTime) {
		gsm.tick(deltaTime);
		assets.tick();
		//System.out.println("fps: " + getFps() + " tps: " + getTps());
	}
	
	@Override
	public void render() {
		gsm.render(graphics2D);
		load.render(graphics2D);
		clear();
		super.render();
	}
	
	@Override
	public void clear() {
		super.clear();
	}

}
