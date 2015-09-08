package my.game.generator;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import my.game.main.Assets;
import my.game.managers.LightSource;
import my.javagame.main.Vector2D;

public class Block extends Rectangle {
	
	Vector2D pos = new Vector2D();
	private int blockSize = 32;
	private BlockType blockType;
	private BufferedImage block;
	private boolean isSolid;
	private boolean isLoading;
	private boolean dropped = false;
	private String tag;
	
	public double g = 0;
	public double h = 0;
	public double f = 0;
	
	private Block parent;
	
	public boolean isOpen = false;
	public boolean isClosed = false;

	public CopyOnWriteArrayList<Block> neighbors = new CopyOnWriteArrayList<Block>();
	
	private float lightLevel = 0.85f;

	public Block(Vector2D pos) {
		setBounds((int)pos.xPos, (int)pos.yPos, blockSize, blockSize);
		this.pos = pos;
	}
	
	public Block(Vector2D pos, BlockType blockType) {
		setBounds((int)pos.xPos, (int)pos.yPos, blockSize, blockSize);
		this.pos = pos;
		this.blockType = blockType;
		
		init();
	}
	

	public Block isSolid(boolean isSolid){
		this.isSolid = isSolid;
		return this;
	}
	
	public Block tag(String tag){
		this.tag = tag;
		return this;
	}
	
	public void init(){
		switch (blockType) {
		case STONE_01:
			block = Assets.getStone_01();
			break;
		case STONE_02:
			block = Assets.getStone_02();
			break;
		case WALL_01:
			block = Assets.getWall_01();
			break;
		case WALL_02:
			block = Assets.getWall_02();
			break;
		case WATER_01:
			block = Assets.getWater_01();
			break;
		case SAND_01:
			block = Assets.getSand_01();
			break;
		case SAND_02:
			block = Assets.getSand_02();
			break;
		case SAND_DARK_01:
			block = Assets.getSand_dark_01();
			break;
		case SAND_DARK_02:
			block = Assets.getSand_dark_02();
			break;
		case SAND_CL:
			block = Assets.getSandLCorner();
			break;
		case SAND_CR:
			block = Assets.getSandRCorner();
			break;
		case SAND_CU:
			block = Assets.getSandUCorner();
			break;
		case SAND_CD:
			block = Assets.getSandDCorner();
			break;
		case SAND_CUL:
			block = Assets.getSandULCorner();
			break;
		case SAND_CUR:
			block = Assets.getSandURCorner();
			break;
		case SAND_CDL:
			block = Assets.getSandDLCorner();
			break;
		case SAND_CDR:
			block = Assets.getSandDRCorner();
			break;
		case TREE_01:
			block = Assets.getTree_01();
			break;
		case CRATE_01:
			block = Assets.getCrate_01();
			break;
		case LADDER_SAND:
			block = Assets.getLadder();
			break;
		case LADDER_DIRT:
			block = Assets.getLadder_02();
			break;
		case WALL_03:
			block = Assets.getWall_03();
			break;
		}
	}
	
	public void tick(double deltaTime){
		if(isLoading){
			setBounds((int)pos.xPos, (int)pos.yPos, blockSize, blockSize);
		}
		
		if(lightLevel > 0.125f && lightLevel < 0.35f){
			lightLevel = 0.125f;
		}
	}
	
	public void renderBlockLightLevel(Graphics2D g){
		if(isLoading){
			if(lightLevel > 0){
				g.setColor(Color.black);
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, lightLevel));
				g.fillRect((int)pos.getWorldLocation().xPos, (int)pos.getWorldLocation().yPos, blockSize, blockSize);
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
			}
		}
	}
	
	public void render(Graphics2D g){
		if(isLoading){
			if(block != null){
				
				g.drawImage(block,(int)pos.getWorldLocation().xPos, (int)pos.getWorldLocation().yPos, blockSize, blockSize,null);
				
			} else {
				//g.fillRect((int)pos.getWorldLocation().xPos, (int)pos.getWorldLocation().yPos, blockSize, blockSize);
			}
			//g.drawRect((int)pos.getWorldLocation().xPos, (int)pos.getWorldLocation().yPos, blockSize, blockSize);
		} else {
			if(!dropped){
				
				
				dropped = true;
			}
		}
	}
	
	public enum BlockType{
		STONE_01,STONE_02,
		WATER_01,
		SAND_01,SAND_02,SAND_DARK_01, SAND_DARK_02,
		SAND_CL,SAND_CR,SAND_CU,SAND_CD,SAND_CUL,SAND_CUR,SAND_CDL,SAND_CDR,
		WALL_01,WALL_02,WALL_03,
		TREE_01,
		CRATE_01,
		LADDER_SAND, LADDER_DIRT
	}
	
	public boolean isSolid(){
		return isSolid;
	}
	
	public boolean isLoading(){
		return isLoading;
	}
	public void setLoading(boolean value){
		isLoading = value;
	}
	public Vector2D getBlockLocation() {
		return pos;
	}
	public void addShadow(LightSource lightSource, float amount){
		if(lightLevel != 1)
			if(!this.getBounds().intersects(lightSource.getLightDetection())){
				if(lightLevel < 0.981000f)
					lightLevel+=amount;
			}
	}
	public void removeShadow(float amount){
		if(lightLevel > 0.001000f)
			lightLevel-=amount;
	}
	public float getLightLevel() {
		return lightLevel;
	}
	public String getTag() {
		return tag;
	}
	public Vector2D getPos() {
		return pos;
	}
	public BlockType getBlockType() {
		return blockType;
	}

	public Block getParent() {
		return parent;
	}
	public void setParent(Block parent) {
		this.parent = parent;
	}
}
