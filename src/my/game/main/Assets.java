package my.game.main;

import java.awt.image.BufferedImage;

import my.game.sprites.Sprites;
import my.javagame.main.SpriteSheet;
import my.javagame.main.loadImageFrom;

public class Assets {

	SpriteSheet blocks = new SpriteSheet();
	SpriteSheet border = new SpriteSheet();
	SpriteSheet map = new SpriteSheet();
	SpriteSheet items = new SpriteSheet();
	public static SpriteSheet enemy = new SpriteSheet();
	public static SpriteSheet player = new SpriteSheet();
	public static boolean initialize = true;
	
	public static String mapName;
	
	public static BufferedImage sand_01;
	public static BufferedImage sand_02;
	public static BufferedImage sandLCorner;
	public static BufferedImage sandRCorner;
	public static BufferedImage sandUCorner;
	public static BufferedImage sandDCorner;
	public static BufferedImage sandULCorner;
	public static BufferedImage sandURCorner;
	public static BufferedImage sandDLCorner;
	public static BufferedImage sandDRCorner;
	
	private static BufferedImage sand_dark_01;
	private static BufferedImage sand_dark_02;
	
	public static BufferedImage stone_01;
	public static BufferedImage stone_02;
	
	public static BufferedImage water_01;
	
	public static BufferedImage wall_01;
	public static BufferedImage wall_02;
	public static BufferedImage wall_03;

	public static BufferedImage tree_01;

	public static BufferedImage crate_01;
	
	public static BufferedImage title;
	
	public static BufferedImage mouseCursor_released;
	public static BufferedImage mouseCursor_pressed;
	
	public static BufferedImage startButton_normal;
	public static BufferedImage startButton_hover;
	public static BufferedImage startButton_clicked;
	
	public static BufferedImage optionsButton_normal;
	public static BufferedImage optionsButton_hover;
	public static BufferedImage optionsButton_clicked;
	
	public static BufferedImage quitButton_normal;
	public static BufferedImage quitButton_hover;
	public static BufferedImage quitButton_clicked;
	
	public static BufferedImage yesButton_normal;
	public static BufferedImage yesButton_hover;
	public static BufferedImage yesButton_clicked;
	
	public static BufferedImage noButton_normal;
	public static BufferedImage noButton_hover;
	public static BufferedImage noButton_clicked;
	
	public static BufferedImage inventoryButton_normal;
	public static BufferedImage inventoryButton_active;
	
	public static BufferedImage equipmentButton_normal;
	public static BufferedImage equipmentButton_Active;
	
	public static BufferedImage statsButton_normal;
	public static BufferedImage statsButton_Active;

	public static BufferedImage saveButton_normal;
	public static BufferedImage saveButton_active;
	
	public static BufferedImage lifeBar;
	public static BufferedImage statBar;
	
	public static BufferedImage damagePlayer;
	public static BufferedImage gameBorder;
	public static BufferedImage mapImage;

	private static BufferedImage healthPotion;
	private static BufferedImage staminaPotion;
	
	private static BufferedImage helm_red;
	private static BufferedImage body_red;
	private static BufferedImage legs_red;
	private static BufferedImage boots_red;

	public static BufferedImage invBackground;
	public static BufferedImage invHead;
	public static BufferedImage invBody;
	public static BufferedImage invLegs;
	public static BufferedImage invAmmo;
	public static BufferedImage invBoots;
	public static BufferedImage invShield;
	public static BufferedImage invWeapon;
	
	public static BufferedImage bullet;

	private static BufferedImage ladder_01;
	private static BufferedImage ladder_02;
	
	private static BufferedImage loading;
	
	public void init(){
		if(initialize){
			if(mapName==null){
				mapName = "map";
			}
			
			blocks.setSpriteSheet(loadImageFrom.LoadImageFrom(Sprites.class, "spritesheet"));
			border.setSpriteSheet(loadImageFrom.LoadImageFrom(Sprites.class, "border"));
			player.setSpriteSheet(loadImageFrom.LoadImageFrom(Sprites.class, "playersheet"));
			enemy.setSpriteSheet(loadImageFrom.LoadImageFrom(Sprites.class, "enemysheet"));
			items.setSpriteSheet(loadImageFrom.LoadImageFrom(Sprites.class, "itemSheet"));
			map.setSpriteSheet(loadImageFrom.LoadImageFrom(Sprites.class, mapName));
			System.out.println(mapName);
			
			sand_01 = blocks.getTile(0, 0, 16, 16);
			sand_02 = blocks.getTile(16, 0, 16, 16);

			sandLCorner = blocks.getTile(16*6, 0, 16, 16);
			sandRCorner = blocks.getTile(16*7, 0, 16, 16);
			sandUCorner = blocks.getTile(16*9, 0, 16, 16);
			sandDCorner = blocks.getTile(16*8, 0, 16, 16);
			sandULCorner = blocks.getTile(16*11, 0, 16, 16);
			sandURCorner = blocks.getTile(16*13, 0, 16, 16);
			sandDLCorner = blocks.getTile(16*10, 0, 16, 16);
			sandDRCorner = blocks.getTile(16*12, 0, 16, 16);
			
			sand_dark_01 = blocks.getTile(16*4, 16*2, 16, 16);
			sand_dark_02 = blocks.getTile(16*5, 16*2, 16, 16);
			
			stone_01 = blocks.getTile(16*4, 0, 16, 16);
			stone_02 = blocks.getTile(16*5, 0, 16, 16);
			
			water_01 = blocks.getTile(16*3, 0, 16, 16);
			
			wall_01 = blocks.getTile(0, 16, 16, 16);
			wall_02 = blocks.getTile(16, 16, 16, 16);
			wall_03 = blocks.getTile(16*5, 16, 16, 16);
			
			tree_01 = blocks.getTile(16*2, 16, 16, 16);
			
			crate_01 = blocks.getTile(16*2, 16*2, 16, 16);
			

			title = blocks.getTile(0, 16*4, 16*6, 16*2);
			
			mouseCursor_released = blocks.getTile(0, 16*2, 16, 16);
			mouseCursor_pressed = blocks.getTile(16, 16*2, 16, 16);

			startButton_normal = blocks.getTile(0, 16*3, 16*3, 16);
			startButton_hover = blocks.getTile(16*3, 16*3, 16*3, 16);
			startButton_clicked = blocks.getTile(16*6, 16*3, 16*3, 16);

			optionsButton_normal = blocks.getTile(0, 16*6, 16*3, 16);
			optionsButton_hover = blocks.getTile(16*3, 16*6, 16*3, 16);
			optionsButton_clicked = blocks.getTile(16*6, 16*6, 16*3, 16);

			quitButton_normal = blocks.getTile(0, 16*7, 16*3, 16);
			quitButton_hover = blocks.getTile(16*3, 16*7, 16*3, 16);
			quitButton_clicked = blocks.getTile(16*6, 16*7, 16*3, 16);

			yesButton_normal = blocks.getTile(0, 16*8, 16*3, 16);
			yesButton_hover = blocks.getTile(16*3, 16*8, 16*3, 16);
			yesButton_clicked = blocks.getTile(16*6, 16*8, 16*3, 16);

			noButton_normal = blocks.getTile(0, 16*9, 16*3, 16);
			noButton_hover = blocks.getTile(16*3, 16*9, 16*3, 16);
			noButton_clicked = blocks.getTile(16*6, 16*9, 16*3, 16);
			
			inventoryButton_normal = blocks.getTile(0, 16*11, 16, 16);
			inventoryButton_active = blocks.getTile(16, 16*11, 16, 16);
			
			equipmentButton_normal = blocks.getTile(0, 16*12, 16, 16);
			equipmentButton_Active = blocks.getTile(16, 16*12, 16, 16);
			
			statsButton_normal = blocks.getTile(0, 16*13, 16, 16);
			statsButton_Active = blocks.getTile(16, 16*13, 16, 16);
			
			saveButton_normal = blocks.getTile(16*2, 16*11, 16*3, 16);
			saveButton_active = blocks.getTile(16*5, 16*11, 16*3, 16);

			ladder_01 = blocks.getTile(16*3, 16*2, 16, 16);
			ladder_02 = blocks.getTile(16*4, 16, 16, 16);

			damagePlayer = player.getTile(0, 16*5, 16, 16);
			
			lifeBar = blocks.getTile(0, 16*10, 16*5, 16);
			statBar = blocks.getTile(16*5, 16*10, 16*4, 16);
			
			gameBorder = border.getTile(0, 0, 800, 600);
			mapImage = map.getTile(0, 0, 100, 100);
			
			healthPotion = items.getTile(0, 0, 16, 16);
			staminaPotion = items.getTile(0, 16, 16, 16);

			helm_red = items.getTile(0, 16*2, 16, 16);
			body_red = items.getTile(16, 16*2, 16, 16);
			legs_red = items.getTile(16*2, 16*2, 16, 16);
			boots_red = items.getTile(16*3, 16*2, 16, 16);

			invBackground = blocks.getTile(16*2, 16*12, 16, 16);
			invHead= blocks.getTile(16*3, 16*12, 16, 16);
			invBody = blocks.getTile(16*4, 16*12, 16, 16);
			invLegs = blocks.getTile(16*5, 16*12, 16, 16);
			invAmmo = blocks.getTile(16*6, 16*12, 16, 16);
			invBoots = blocks.getTile(16*7, 16*12, 16, 16);
			invShield= blocks.getTile(16*8, 16*12, 16, 16);
			invWeapon = blocks.getTile(16*9, 16*12, 16, 16);
			
			bullet = player.getTile(0, 16*6, 16, 16);
			
			loading = blocks.getTile(16*6, 16*4, 16*6, 16*2);
			
			initialize = false;
		}
	}
	
	public void tick(){
		if(initialize)
			init();
	}

	public static BufferedImage getWall_03() {
		return wall_03;
	}
	public static BufferedImage getSand_dark_01() {
		return sand_dark_01;
	}
	public static BufferedImage getSand_dark_02() {
		return sand_dark_02;
	}
	public static BufferedImage getLadder_02() {
		return ladder_02;
	}
	public static BufferedImage getBody_red() {
		return body_red;
	}
	public static BufferedImage getBoots_red() {
		return boots_red;
	}
	public static BufferedImage getLegs_red() {
		return legs_red;
	}
	public static BufferedImage getHelm_red() {
		return helm_red;
	}
	public static BufferedImage getStone_01() {
		return stone_01;
	}
	public static BufferedImage getStone_02() {
		return stone_02;
	}
	public static BufferedImage getWater_01() {
		return water_01;
	}
	public static BufferedImage getSand_01() {
		return sand_01;
	}
	public static BufferedImage getSand_02() {
		return sand_02;
	}
	public static BufferedImage getSandLCorner() {
		return sandLCorner;
	}
	public static BufferedImage getSandRCorner() {
		return sandRCorner;
	}
	public static BufferedImage getSandUCorner() {
		return sandUCorner;
	}
	public static BufferedImage getSandDCorner() {
		return sandDCorner;
	}
	public static BufferedImage getSandULCorner() {
		return sandULCorner;
	}
	public static BufferedImage getSandURCorner() {
		return sandURCorner;
	}
	public static BufferedImage getSandDLCorner() {
		return sandDLCorner;
	}
	public static BufferedImage getSandDRCorner() {
		return sandDRCorner;
	}
	public static BufferedImage getWall_01() {
		return wall_01;
	}
	public static BufferedImage getWall_02() {
		return wall_02;
	}
	public static BufferedImage getTree_01() {
		return tree_01;
	}
	public static BufferedImage getCrate_01() {
		return crate_01;
	}
	public static BufferedImage getLifeBar() {
		return lifeBar;
	}
	public static BufferedImage getDamagePlayer() {
		return damagePlayer;
	}
	public static BufferedImage getGameBorder() {
		return gameBorder;
	}
	public static BufferedImage getMapImage() {
		return mapImage;
	}
	public static BufferedImage getMouseCursorReleased() {
		return mouseCursor_released;
	}
	public static BufferedImage getHealthPotion() {
		return healthPotion;
	}
	public static BufferedImage getInventoryButton_normal() {
		return inventoryButton_normal;
	}
	public static BufferedImage getInventoryButton_active() {
		return inventoryButton_active;
	}
	public static BufferedImage getEquipmentButton_Active() {
		return equipmentButton_Active;
	}
	public static BufferedImage getEquipmentButton_normal() {
		return equipmentButton_normal;
	}
	public static BufferedImage getStatsButton_normal() {
		return statsButton_normal;
	}
	public static BufferedImage getStatsButton_Active() {
		return statsButton_Active;
	}
	public static BufferedImage getMouseCursorPressed() {
		return mouseCursor_pressed;
	}
	public static BufferedImage getStaminaPotion() {
		return staminaPotion;
	}
	public static BufferedImage getInvBackground() {
		return invBackground;
	}
	public static BufferedImage getStatBar() {
		return statBar;
	}
	public static BufferedImage getLadder() {
		return ladder_01;
	}
	public static BufferedImage getSaveButton_active() {
		return saveButton_active;
	}
	public static BufferedImage getSaveButton_normal() {
		return saveButton_normal;
	}
	public static BufferedImage getInvAmmo() {
		return invAmmo;
	}
	public static BufferedImage getInvBody() {
		return invBody;
	}
	public static BufferedImage getInvBoots() {
		return invBoots;
	}
	public static BufferedImage getInvHead() {
		return invHead;
	}
	public static BufferedImage getInvLegs() {
		return invLegs;
	}
	public static BufferedImage getInvShield() {
		return invShield;
	}
	public static BufferedImage getInvWeapon() {
		return invWeapon;
	}
	public static BufferedImage getBullet() {
		return bullet;
	}
	public static BufferedImage getLoading() {
		return loading;
	}
	
}
