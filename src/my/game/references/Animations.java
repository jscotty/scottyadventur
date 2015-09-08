package my.game.references;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import my.game.main.Animator;
import my.game.main.Assets;

public class Animations {


	private static ArrayList<BufferedImage> listExplosion_01;
	
	private static ArrayList<BufferedImage> listUp_player;
	private static ArrayList<BufferedImage> listDown_player;
	private static ArrayList<BufferedImage> listLeft_player;
	private static ArrayList<BufferedImage> listRight_player;
	private static ArrayList<BufferedImage> listUp_playerIdle;
	private static ArrayList<BufferedImage> listDown_playerIdle;
	private static ArrayList<BufferedImage> listLeft_playerIdle;
	private static ArrayList<BufferedImage> listRight_playerIdle;

	private static Animator explosionAnim_01;
	
	private static Animator animUp;
	private static Animator animDown;
	private static Animator animLeft;
	private static Animator animRight;
	private static Animator animIdleUp;
	private static Animator animIdleDown;
	private static Animator animIdleLeft;
	private static Animator animIdleRight;
	
	public Animations() {
		
	}
	
	public void init(){
		listExplosion_01 = new ArrayList<BufferedImage>();
		
		listExplosion_01.add(Assets.player.getTile(0, 16*8, 16, 16));
		listExplosion_01.add(Assets.player.getTile(16, 16*8, 16, 16));
		listExplosion_01.add(Assets.player.getTile(16*2, 16*8, 16, 16));
		listExplosion_01.add(Assets.player.getTile(16*3, 16*8, 16, 16));
		listExplosion_01.add(Assets.player.getTile(16*4, 16*8, 16, 16));
		listExplosion_01.add(Assets.player.getTile(16*5, 16*8, 16, 16));
		listExplosion_01.add(Assets.player.getTile(16*6, 16*8, 16, 16));
		listExplosion_01.add(Assets.player.getTile(16*7, 16*8, 16, 16));
		listExplosion_01.add(Assets.player.getTile(16*8, 16*8, 16, 16));
		listExplosion_01.add(Assets.player.getTile(16*9, 16*8, 16, 16));
		listExplosion_01.add(Assets.player.getTile(16*10, 16*8, 16, 16));
		
		explosionAnim_01 = new Animator(listExplosion_01);
		explosionAnim_01.setSpeed(50);
		explosionAnim_01.play();
		
		
		listUp_player = new ArrayList<BufferedImage>();

		listUp_player.add(Assets.player.getTile(0, 0, 16, 16));
		listUp_player.add(Assets.player.getTile(16, 0, 16, 16));
		listUp_player.add(Assets.player.getTile(0, 0, 16, 16));
		listUp_player.add(Assets.player.getTile(16, 0, 16, 16));
		
		
		listDown_player = new ArrayList<BufferedImage>();
		
		listDown_player.add(Assets.player.getTile(0, 16, 16, 16));
		listDown_player.add(Assets.player.getTile(16, 16, 16, 16));
		listDown_player.add(Assets.player.getTile(0, 16, 16, 16));
		listDown_player.add(Assets.player.getTile(16, 16, 16, 16));

		
		listLeft_player = new ArrayList<BufferedImage>();
		
		listLeft_player.add(Assets.player.getTile(0, 16*2, 16, 16));
		listLeft_player.add(Assets.player.getTile(16, 16*2, 16, 16));
		listLeft_player.add(Assets.player.getTile(0, 16*2, 16, 16));
		listLeft_player.add(Assets.player.getTile(16, 16*2, 16, 16));
		
		
		listRight_player = new ArrayList<BufferedImage>();

		listRight_player.add(Assets.player.getTile(0, 16*3, 16, 16));
		listRight_player.add(Assets.player.getTile(16, 16*3, 16, 16));
		listRight_player.add(Assets.player.getTile(0, 16*3, 16, 16));
		listRight_player.add(Assets.player.getTile(16, 16*3, 16, 16));


		listDown_playerIdle = new ArrayList<BufferedImage>();
		
		listDown_playerIdle.add(Assets.player.getTile(0, 16*4, 16, 16));
		listDown_playerIdle.add(Assets.player.getTile(16*3, 16*4, 16, 16));
		listDown_playerIdle.add(Assets.player.getTile(0, 16*4, 16, 16));
		listDown_playerIdle.add(Assets.player.getTile(16*3, 16*4, 16, 16));
		listDown_playerIdle.add(Assets.player.getTile(0, 16*4, 16, 16));
		listDown_playerIdle.add(Assets.player.getTile(16*3, 16*4, 16, 16));
		listDown_playerIdle.add(Assets.player.getTile(0, 16*4, 16, 16));
		listDown_playerIdle.add(Assets.player.getTile(16*4, 16*4, 16, 16));
		listDown_playerIdle.add(Assets.player.getTile(16*3, 16*4, 16, 16));
		listDown_playerIdle.add(Assets.player.getTile(16*5, 16*4, 16, 16));
		listDown_playerIdle.add(Assets.player.getTile(16*3, 16*4, 16, 16));
		listDown_playerIdle.add(Assets.player.getTile(16*3, 16*4, 16, 16));
		listDown_playerIdle.add(Assets.player.getTile(0, 16*4, 16, 16));
		listDown_playerIdle.add(Assets.player.getTile(16*3, 16*4, 16, 16));
		listDown_playerIdle.add(Assets.player.getTile(0, 16*4, 16, 16));
		listDown_playerIdle.add(Assets.player.getTile(16, 16*4, 16, 16));
		listDown_playerIdle.add(Assets.player.getTile(16*2, 16*4, 16, 16));
		listDown_playerIdle.add(Assets.player.getTile(16, 16*4, 16, 16));
		listDown_playerIdle.add(Assets.player.getTile(0, 16*4, 16, 16));
		listDown_playerIdle.add(Assets.player.getTile(16*3, 16*4, 16, 16));
		listDown_playerIdle.add(Assets.player.getTile(0, 16*4, 16, 16));
		listDown_playerIdle.add(Assets.player.getTile(16*3, 16*4, 16, 16));
		listDown_playerIdle.add(Assets.player.getTile(0, 16*4, 16, 16));
		listDown_playerIdle.add(Assets.player.getTile(16*3, 16*4, 16, 16));
		listDown_playerIdle.add(Assets.player.getTile(0, 16*4, 16, 16));
		listDown_playerIdle.add(Assets.player.getTile(16*4, 16*4, 16, 16));
		listDown_playerIdle.add(Assets.player.getTile(16*3, 16*4, 16, 16));
		listDown_playerIdle.add(Assets.player.getTile(16*5, 16*4, 16, 16));
		listDown_playerIdle.add(Assets.player.getTile(16*3, 16*4, 16, 16));


		listUp_playerIdle = new ArrayList<BufferedImage>();

		listUp_playerIdle.add(Assets.player.getTile(16*2, 16*5, 16, 16));
		listUp_playerIdle.add(Assets.player.getTile(16*2, 16*5, 16, 16));
		listUp_playerIdle.add(Assets.player.getTile(16*3, 16*5, 16, 16));
		listUp_playerIdle.add(Assets.player.getTile(16*2, 16*5, 16, 16));
		listUp_playerIdle.add(Assets.player.getTile(16*4, 16*5, 16, 16));
		listUp_playerIdle.add(Assets.player.getTile(16*2, 16*5, 16, 16));
		listUp_playerIdle.add(Assets.player.getTile(16*2, 16*5, 16, 16));
		listUp_playerIdle.add(Assets.player.getTile(16*2, 16*5, 16, 16));
		listUp_playerIdle.add(Assets.player.getTile(16*5, 16*5, 16, 16));
		listUp_playerIdle.add(Assets.player.getTile(16*6, 16*5, 16, 16));
		listUp_playerIdle.add(Assets.player.getTile(16*5, 16*5, 16, 16));
		listUp_playerIdle.add(Assets.player.getTile(16*2, 16*5, 16, 16));
		listUp_playerIdle.add(Assets.player.getTile(16*2, 16*5, 16, 16));
		listUp_playerIdle.add(Assets.player.getTile(16*3, 16*5, 16, 16));
		listUp_playerIdle.add(Assets.player.getTile(16*2, 16*5, 16, 16));
		listUp_playerIdle.add(Assets.player.getTile(16*4, 16*5, 16, 16));
		listUp_playerIdle.add(Assets.player.getTile(16*2, 16*5, 16, 16));
		listUp_playerIdle.add(Assets.player.getTile(16*2, 16*5, 16, 16));
		

		listLeft_playerIdle = new ArrayList<BufferedImage>();
		
		listLeft_playerIdle.add(Assets.player.getTile(16*3, 16*6, 16, 16));
		listLeft_playerIdle.add(Assets.player.getTile(16*3, 16*6, 16, 16));
		listLeft_playerIdle.add(Assets.player.getTile(16*2, 16*6, 16, 16));
		listLeft_playerIdle.add(Assets.player.getTile(16*3, 16*6, 16, 16));
		listLeft_playerIdle.add(Assets.player.getTile(16*4, 16*6, 16, 16));
		

		listRight_playerIdle = new ArrayList<BufferedImage>();
		
		listRight_playerIdle.add(Assets.player.getTile(16*3, 16*7, 16, 16));
		listRight_playerIdle.add(Assets.player.getTile(16*3, 16*7, 16, 16));
		listRight_playerIdle.add(Assets.player.getTile(16*2, 16*7, 16, 16));
		listRight_playerIdle.add(Assets.player.getTile(16*3, 16*7, 16, 16));
		listRight_playerIdle.add(Assets.player.getTile(16*4, 16*7, 16, 16));
		
			animUp = new Animator(listUp_player);
			animUp.setSpeed(50);
			animUp.play();

			animDown = new Animator(listDown_player);
			animDown.setSpeed(50);
			animDown.play();

			animLeft = new Animator(listLeft_player);
			animLeft.setSpeed(50);
			animLeft.play();
			
			animRight = new Animator(listRight_player);
			animRight.setSpeed(50);
			animRight.play();

			animIdleDown = new Animator(listDown_playerIdle);
			animIdleDown.setSpeed(250);
			animIdleDown.play();
			
			animIdleUp = new Animator(listUp_playerIdle);
			animIdleUp.setSpeed(250);
			animIdleUp.play();

			animIdleLeft = new Animator(listLeft_playerIdle);
			animIdleLeft.setSpeed(250);
			animIdleLeft.play();

			animIdleRight = new Animator(listRight_playerIdle);
			animIdleRight.setSpeed(250);
			animIdleRight.play();
	}
	
	public static Animator getExplosionAnim_01() {
		return explosionAnim_01;
	}
	public static Animator getAnimDown() {
		return animDown;
	}
	public static Animator getAnimIdleDown() {
		return animIdleDown;
	}
	public static Animator getAnimIdleLeft() {
		return animIdleLeft;
	}
	public static Animator getAnimIdleRight() {
		return animIdleRight;
	}
	public static Animator getAnimIdleUp() {
		return animIdleUp;
	}
	public static Animator getAnimLeft() {
		return animLeft;
	}
	public static Animator getAnimRight() {
		return animRight;
	}
	public static Animator getAnimUp() {
		return animUp;
	}

}
