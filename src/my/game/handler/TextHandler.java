package my.game.handler;

import my.game.generator.World;

public class TextHandler {

	World world;
	public TextHandler(World world) {
		this.world = world;
	}
	
	public void type(String arg){
		World.getHud().setBorder(true);
		char[] cra = arg.toCharArray();
		for (int i = 0; i < cra.length; i++) {

			World.getHud().typeText(arg);
			try {
				Thread.sleep(50);
			} catch (InterruptedException ie) {
				// TODO: handle exception
			}
		}
	}

}
