package my.game.generator;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;

import my.game.enemy.Enemy;
import my.game.enemy.EnemyHandler;
import my.game.enemy.EnemyType;
import my.game.generator.Block.BlockType;
import my.game.handler.Loading;
import my.game.handler.TextHandler;
import my.game.inventory.Item;
import my.game.inventory.ItemDrop;
import my.game.inventory.ItemUsage;
import my.game.main.Assets;
import my.game.main.SaveLoadData;
import my.game.managers.GameStateManager;
import my.game.managers.HUDManager;
import my.game.managers.LightManager;
import my.game.managers.MouseManager;
import my.game.managers.TileManager;
import my.game.moveAbleObjects.MiniMap;
import my.game.moveAbleObjects.Player;
import my.game.references.Tags;
import my.game.references.WorldData;
import my.game.sprites.Sprites;
import my.game.state.LevelLoader;
import my.javagame.main.Vector2D;
import my.javagame.main.loadImageFrom;

public class World {

	public static CopyOnWriteArrayList<BlockEntity> blockEnts;
	
	private static CopyOnWriteArrayList<ItemDrop> dropItems = new CopyOnWriteArrayList<ItemDrop>();
	
	private String worldName;
	private BufferedImage map;
	public TileManager tiles;
	public EnemyHandler enemyH;
	private static Player player;
	public static Vector2D mapPos = new Vector2D();
	private int worldWidth;
	private int worldHeight;
	private int blockSize = 32;
	private MiniMap miniMap;
	private static TextHandler txtHandler;
	private static ItemUsage itemUse = new ItemUsage(new Vector2D(0,0), 32, 40, "use");
	private int loadCount;
	
	Block spawn;
	
	private boolean hasSize;
	private boolean generated;
	
	private GameStateManager gsm;
	private LightManager lm;
	private static HUDManager hud;
	
	
	MouseManager mouseManager = new MouseManager();
	
	public World(String name, LevelLoader levelLoader, GameStateManager gsm, int level){
		worldName = name;

		this.gsm = gsm;
		
		hud = new HUDManager(this);
		miniMap = new MiniMap(this);
		txtHandler = new TextHandler(this);
	}

	public void init() {
		blockEnts = new CopyOnWriteArrayList<BlockEntity>();
		tiles = new TileManager();
		enemyH = new EnemyHandler();
		
		lm = new LightManager(tiles.getBlocks());
		lm.init();
		
		mapPos.xPos = spawn.getBlockLocation().xPos - player.getPos().xPos;
		mapPos.yPos = spawn.getBlockLocation().yPos - player.getPos().yPos;
		
		Vector2D.setWorldVaribles(mapPos.xPos, mapPos.yPos);
		
		if(player != null)
			player.init(this);
		SaveLoadData.load();
	}

	public void tick(double deltaTime) {
		miniMap.tick();
		if(player.isMoving())
			Vector2D.setWorldVaribles(mapPos.xPos, mapPos.yPos);
		
		tiles.tick(deltaTime);
		enemyH.tick(deltaTime);
		lm.tick(deltaTime);
		
		if(!player.hasSpawned())
			spawn.tick(deltaTime);

		if(!blockEnts.isEmpty()){
			for (BlockEntity entity : blockEnts) {
				if(Player.render.intersects(entity)){
					entity.tick(deltaTime);
					
					entity.setLoading(true);
				} else 
					entity.setLoading(false);
					
			}
		}
		itemUse.tick(deltaTime);
		if(player != null)
			player.tick(deltaTime);
		for (Enemy enemy : enemyH.enemys) {
			enemy.tick();
			
			if(!enemy.alive){
				enemyH.enemys.remove(enemy);
			}
		}
		for(ItemDrop drop: dropItems){
			drop.tick();
			if(drop.picked){
				dropItems.remove(drop);
			}
		}
		mouseManager.tick();
		

	}

	public void render(Graphics2D g) {
		miniMap.render(g);
		tiles.render(g);
		
		for(ItemDrop drop: dropItems){
			drop.render(g);
		}

		for (Enemy enemy : enemyH.enemys) {
			if(!enemy.front())
				enemy.render(g);
		}
		lm.render(g);
		if(!player.hasSpawned())
			spawn.render(g);
		
		if(!blockEnts.isEmpty()){
			for (BlockEntity entity : blockEnts) {
				if(player.render.intersects(entity)){
					entity.render(g);
				}
			}
		}

		if(player != null)
			player.render(g);

		for (Enemy enemy : enemyH.enemys) {
			if(enemy.front())
				enemy.render(g);
		}
		for (Block block : TileManager.blocks) {
			block.renderBlockLightLevel(g);
		}
		
		hud.render(g);
		Player.inv.render(g);
		Player.equipment.render(g);
		itemUse.render(g);
		

		
		mouseManager.render(g);
	}

	public void generate(String worldMapImg) {
		map = null;
		if(hasSize){
			try {
				map = loadImageFrom.LoadImageFrom(Sprites.class, worldMapImg);
			} catch (Exception e) {

			}
			

			for (int x = 0; x < worldWidth; x++) {
				for (int y = 0; y < worldHeight; y++) {
					int col = map.getRGB(x, y);
					
					int randNum = (int)Math.floor(Math.random()*20);
					switch (col & 0xFFFFFF) {
					case 0xEFD96B :
						if(randNum <= 12)
							tiles.blocks.add(new Block(new Vector2D(x*blockSize,y*blockSize),BlockType.SAND_01).isSolid(false).tag("sand_01"));
						else
							tiles.blocks.add(new Block(new Vector2D(x*blockSize,y*blockSize),BlockType.SAND_02).isSolid(false));
						break;
					case 0x706532 :
						tiles.blocks.add(new Block(new Vector2D(x*blockSize,y*blockSize),BlockType.SAND_DARK_01).isSolid(false).tag("sand_01"));
						break;
					case 0x60572B :
						tiles.blocks.add(new Block(new Vector2D(x*blockSize,y*blockSize),BlockType.SAND_DARK_02).isSolid(false).tag("sand_01"));
						break;
					case 0x5E501A :
							tiles.blocks.add(new Block(new Vector2D(x*blockSize,y*blockSize),BlockType.LADDER_SAND).isSolid(false).tag(Tags.LADDER_SAND_01));
						break;
					case 0x514924:
						tiles.blocks.add(new Block(new Vector2D(x*blockSize,y*blockSize),BlockType.LADDER_DIRT).isSolid(false).tag(Tags.LADDER_DIRT_01));
						break;
					case 0x808080:
						if(randNum <= 12)
							tiles.blocks.add(new Block(new Vector2D(x*blockSize,y*blockSize),BlockType.STONE_01).isSolid(false));
						else
							tiles.blocks.add(new Block(new Vector2D(x*blockSize,y*blockSize),BlockType.STONE_02).isSolid(false));
						break;
					case 0x404040:
						tiles.blocks.add(new Block(new Vector2D(x*blockSize,y*blockSize),BlockType.WALL_01).isSolid(true));
						break;
					case 0x202020:
						tiles.blocks.add(new Block(new Vector2D(x*blockSize,y*blockSize),BlockType.WALL_02).isSolid(true));
						break;
					case 0xAD9B4E :
						tiles.blocks.add(new Block(new Vector2D(x*blockSize,y*blockSize),BlockType.SAND_CL).isSolid(false));
					break;
					case 0xB29F53 :
						tiles.blocks.add(new Block(new Vector2D(x*blockSize,y*blockSize),BlockType.SAND_CR).isSolid(false));
						break;
					case 0xA3914C :
						tiles.blocks.add(new Block(new Vector2D(x*blockSize,y*blockSize),BlockType.SAND_CU).isSolid(false));
					break;
					case 0x929F52 :
						tiles.blocks.add(new Block(new Vector2D(x*blockSize,y*blockSize),BlockType.SAND_CD).isSolid(false));
					break;
					case 0xC1AD5B :
						tiles.blocks.add(new Block(new Vector2D(x*blockSize,y*blockSize),BlockType.SAND_CUL).isSolid(false));
					break;
					case 0xBFAA57 :
						tiles.blocks.add(new Block(new Vector2D(x*blockSize,y*blockSize),BlockType.SAND_CUR).isSolid(false));
					break;
					case 0x9B8947 :
						tiles.blocks.add(new Block(new Vector2D(x*blockSize,y*blockSize),BlockType.SAND_CDL).isSolid(false));
					break;
					case 0x84733C:
						tiles.blocks.add(new Block(new Vector2D(x*blockSize,y*blockSize),BlockType.SAND_CDR).isSolid(false));
					break;
					case 0x3F7DA3:
							tiles.blocks.add(new Block(new Vector2D(x*blockSize,y*blockSize),BlockType.WATER_01).isSolid(true));
							break;
					case 0x24CC51:
						tiles.blocks.add(new Block(new Vector2D(x*blockSize,y*blockSize),BlockType.TREE_01).isSolid(false));
						break;
					case 0x9B6C15:
						tiles.blocks.add(new Block(new Vector2D(x*blockSize,y*blockSize),BlockType.CRATE_01).isSolid(true));
						break;
					case 0x000001:
						tiles.blocks.add(new Block(new Vector2D(x*blockSize,y*blockSize),BlockType.WALL_03).isSolid(true));
						break;
					}
				}
			}
		}

		generated = true;
		
	}
	
	public void resetBlocks(){
		for (Block block : tiles.blocks) {
			block.f = 0;
			block.g = 0;
			block.h = 0;
			block.isClosed	=	false;
			block.isOpen		=	false;
			block.setParent(null);
		}
	}
	
	public Block getBlock(int x, int y){
		Block block = new Block(new Vector2D(0,0));
		for(Block blocks : tiles.blocks){
			if(blocks.getPos().xPos/32  == x && blocks.getPos().yPos/32  == y){
				block = blocks;
			} else {
			}
		}
		return block;
	}
	public Point getBlockPoint(int x, int y){
		Point point = new Point();
		for(Block block : tiles.blocks){
			if(block.getPos().xPos/32  == x && block.getPos().yPos/32  == y){
				point = new Point((int)block.getPos().xPos,(int)block.getPos().yPos);
			} else {
			}
		}
		return point;
	}

	public static void removeDroppedEnitity(BlockEntity blockEntity) {
		if(blockEnts.contains(blockEntity)){
			blockEnts.remove(blockEntity);
		}
	}

	public static void dropBlockEntity(Vector2D pos, BufferedImage blockImg){
		BlockEntity entity = new BlockEntity(pos, blockImg);
		if(!blockEnts.contains(entity)){
			blockEnts.add(entity);
		}
	}
	
	public static void dropItem(Vector2D pos, Item item, int amount){
		if(item != Item.NULL)
			dropItems.add(new ItemDrop(pos, item, amount));
	}


	public void setSize(int width, int height) {
		worldWidth = width;
		worldHeight = height;
		
		hasSize = true;
	}

	public void addPlayer(Player player) {
		this.player = player;
		
	}
	
	public void addEnemy(Vector2D pos,EnemyType enemyType, int width, int height, int pX, int pY){
		Vector2D newPos = new Vector2D(pos.xPos*32 - getWorldXPos(),pos.yPos*32 - getWorldYPos());
		enemyH.enemys.add(new Enemy(newPos, enemyType, this, width, height, pX, pY));
		for (Enemy enemy : enemyH.enemys) {
			enemy.init();
		}
	}
	
	public void addEnemy(Vector2D pos,EnemyType enemyType, int width, int height, int pX, int pY, int maxPX, int maxPY){
		Vector2D newPos = new Vector2D(pos.xPos*32 - getWorldXPos(),pos.yPos*32 - getWorldYPos());
		enemyH.enemys.add(new Enemy(newPos, enemyType, this, width, height, pX, pY,maxPX, maxPY));
		for (Enemy enemy : enemyH.enemys) {
			enemy.init();
		}
	}
	
	public void setWorldSpawn(){
		int xPos = WorldData.xPos; int yPos = WorldData.yPos;
		if(xPos < worldWidth){
			if(yPos < worldHeight){
				Block spawn = new Block(new Vector2D(xPos*blockSize, yPos*blockSize));
				this.spawn = spawn;
			}
		}
	}
	
	public void resetWorld(){
		tiles.getBlocks().clear();
		blockEnts.clear();
		dropItems.clear();
		spawn = null;
	}
	
	public void changeToWorld(String worldName, String mapName, int level){
		SaveLoadData.save();


		
				Assets.mapName = mapName;
				resetWorld();
				gsm.states.push(new LevelLoader(gsm,worldName,mapName,level));
				gsm.states.peek().init();
				Assets.initialize = true;

	}

	public void playerDied(String worldName, String mapName, int level) {
		SaveLoadData.save();

		Assets.mapName = mapName;
		resetWorld();
		gsm.states.push(new LevelLoader(gsm,worldName,mapName,level));
		gsm.states.peek().init();
		hud.getpStats().setHealth(150);
		Assets.initialize = true;
		
	}
	
	public Vector2D getWorldSpawn(){
		return spawn.pos;
	}
	
	public String getWorldName() {
		return worldName;
	}
	
	public Vector2D getWorldPos(){
		return mapPos;
	}
	
	public float getWorldXPos(){
		return mapPos.xPos;
	}
	
	public float getWorldYPos(){
		return mapPos.yPos;
	}
	
	public TileManager getTiles() {
		return tiles;
	}
	public CopyOnWriteArrayList<Enemy> getEnemys() {
		return enemyH.enemys;
	}
	
	public static Player getPlayer() {
		return player;
	}
	
	public boolean hasGenerated(){
		return generated;
	}
	
	public static HUDManager getHud() {
		return hud;
	}
	public static TextHandler getTxtHandler() {
		return txtHandler;
	}
	public static ItemUsage getItemUse() {
		return itemUse;
	}
	public static CopyOnWriteArrayList<ItemDrop> getDropItems() {
		return dropItems;
	}
}
