package my.game.managers;

import java.awt.Graphics2D;
import java.util.Stack;

import my.game.state.GameState;
import my.game.state.LevelLoader;
import my.game.state.MenuState;

public class GameStateManager {

	public static Stack<GameState> states;
	
	public GameStateManager(){
		states = new Stack<GameState>();
		states.push(new MenuState(this));
	}
	
	public void tick(double deltaTime){
		states.peek().tick(deltaTime);
	}
	
	
	public void render(Graphics2D g){
		states.peek().render(g);
	}

	public void init() {
		states.peek().init();
		
	}
}
