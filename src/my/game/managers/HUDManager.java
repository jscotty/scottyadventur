package my.game.managers;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import my.game.enemy.Enemy;
import my.game.equipment.Equipment;
import my.game.generator.World;
import my.game.handler.ButtonHandler;
import my.game.inventory.ItemDrop;
import my.game.main.Assets;
import my.game.main.Main;
import my.game.main.SaveLoadData;
import my.game.moveAbleObjects.Player;
import my.game.moveAbleObjects.PlayerStats;
import my.game.state.ButtonState;
import my.game.state.GameStateButton;
import my.game.state.LevelLoader;
import my.javagame.main.Light;
import my.javagame.main.Vector2D;
import my.javagame.main.loadImageFrom;

public class HUDManager extends Rectangle{
	
	
	private BufferedImage lightMap = new BufferedImage(100*32, 100*32, BufferedImage.TYPE_INT_ARGB);
	private ArrayList<Light> lights = new ArrayList<Light>();
	private Vector2D lightVec = new Vector2D();
	private World world;
	private PlayerStats pStats;
	private TextManager txtManager;
	private boolean done = true, border = false;
	private String message;
	private ButtonHandler invButton;
	private ButtonHandler statButton;
	private ButtonHandler eqButton;
	Font f = new Font("arial", Font.BOLD, 18);
	
	private static Polygon up;
	private static Polygon down;
	private static Polygon left;
	private static Polygon right;
	
	private ItemDrop dropItem;
	
	
	public HUDManager(World world) {
		this.world = world;
		pStats = new PlayerStats(World.getPlayer());
		addLights();
		txtManager = new TextManager();
		
		invButton = new ButtonHandler(Assets.getInventoryButton_normal(), Assets.getInventoryButton_active(), new Vector2D(595,496), 50, 50,1);
		eqButton = new ButtonHandler(Assets.getEquipmentButton_normal(), Assets.getEquipmentButton_Active(), new Vector2D(660,496), 50, 50,2);
		statButton = new ButtonHandler(Assets.getStatsButton_normal(), Assets.getStatsButton_Active(), new Vector2D(721,496), 50, 50,3);
		
		
	}
	
	private void addLights() {
		//lights.add(new Light(400,400, 220, 120));
	}
	public void lightUpdate(){

		Graphics2D g = null;
		if(g == null){
			g = (Graphics2D) lightMap.getGraphics();
		}
		
		g.setColor(new Color(0,0,0,255));
		g.fillRect(0, 0, lightMap.getWidth(), lightMap.getHeight());
		g.setComposite(AlphaComposite.DstOut);
		
		for (Light light : lights) {
			light.render(g);
		}
		g.dispose();
		
	}
	
	public void render(Graphics2D g){
		//lightUpdate();
		
		g.drawImage(lightMap, (int)lightVec.getWorldLocation().xPos, (int)lightVec.getWorldLocation().yPos, null);
		g.drawImage(Assets.gameBorder, 0, 0, 800,600,null);

		if(World.getPlayer().getColliding()){
			String message = World.getPlayer().getMessage();
			
			g.setColor(Color.black);
			g.drawString("press 'ENTER' to use " + message, 233, 96);
			g.setColor(Color.yellow);
			g.drawString("press 'ENTER' to use " + message, 230, 94);
		} else if(World.getPlayer().getItemColliding()){
			dropItem = World.getPlayer().getDropItem();
			String message = dropItem.getItem().getItemName();
			g.setColor(Color.black);
			g.drawString("press 'ENTER' to pick " + message, 233, 96);
			g.setColor(Color.yellow);
			g.drawString("press 'ENTER' to pick " + message, 230, 94);
		}

		
		pStats.tick();
		invButton.tick();
		eqButton.tick();
		statButton.tick();
		pStats.render(g);
		poligonFields();
		
		if(statButton.state == ButtonState.state){
			g.setColor(Color.black);
			g.fillRect(597, 225, 188, 257);
			g.setColor(Color.white);
			g.drawString("World name: " + world.getWorldName(), 600, 250);
			g.drawString("X: " + (int)Math.floor((world.getWorldXPos() /16) + 10) + "- Y: " + (int)Math.floor((world.getWorldYPos() /16)+14), 600, 280);
		}
		
		
		typeText(g);
		invButton.render(g);
		eqButton.render(g);
		statButton.render(g);
		
		saveButton(Assets.saveButton_normal, Assets.saveButton_active,g);
		
	}
	
	private void saveButton(BufferedImage saveButton_normal, BufferedImage saveButton_active, Graphics2D g) {
		int xPos = 430;int yPos = 10; int width = 32*3; int height = 32;
		setBounds(xPos, yPos, width, height);
		
		if(this.contains(MouseManager.mouse)){
			if(MouseManager.clicked){
				g.drawImage(saveButton_normal, xPos, yPos, width, height, null);
				SaveLoadData.save();
				MouseManager.clicked = false;
			} else {
				g.drawImage(saveButton_active, xPos, yPos, width, height, null);
			}
		} else {
			g.drawImage(saveButton_normal, xPos, yPos, width, height, null);
		}
		
	}

	private void typeText(Graphics2D g) {
		if(!done){
			txtManager.type(message);
			
			done = true;
		}
		if(border){
			g.setColor(Color.BLACK);
			g.fillRect(0, 448, 548, 152);
		}

		
		message = txtManager.text;
		if(message!=null){
			g.setColor(Color.white);
			g.setFont(f);
			g.drawString(message, 60, 490);
		}
		//System.out.println(message);
	}
	
	public void typeText(String message){
		done = false;
		this.message = "";
		this.message = message;
	}

	private void poligonFields(){

		//upX,upY
		int[] ux = new int[]{800,400,400,0};
		int[] uy = new int[]{0,300,300,0};
		up = new Polygon(ux,uy,ux.length);
		
		//downX,downY
		int[] dx = new int[]{800,400,400,0};
		int[] dy = new int[]{600,300,300,600};
		down = new Polygon(dx,dy,dx.length);

		//leftX,leftY
		int[] lx = new int[]{0,400,400,0};
		int[] ly = new int[]{600,300,300,0};
		left = new Polygon(lx,ly,lx.length);

		//rightX,rightY
		int[] rx = new int[]{800,400,400,800};
		int[] ry = new int[]{600,300,300,0};
		right = new Polygon(rx,ry,rx.length);
	}
	
	public static Polygon getUpPolygon() {
		return up;
	}
	
	public static Polygon getDownPolygon() {
		return down;
	}
	
	public static Polygon getLeftPolygon() {
		return left;
	}
	
	public static Polygon getRightPolygon() {
		return right;
	}
	
	public PlayerStats getpStats() {
		return pStats;
	}
	public void setBorder(boolean border) {
		this.border = border;
	}
	public ButtonHandler getInvButton() {
		return invButton;
	}
	public ButtonHandler getEqButton() {
		return eqButton;
	}
	public ButtonHandler getStatButton() {
		return statButton;
	}

}
