package my.game.equipment;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import my.game.inventory.InvMouseManager;
import my.game.inventory.Item;
import my.game.managers.MouseManager;
import my.javagame.main.Vector2D;

public class eqSlot extends Rectangle {

	private Item item;
	private BufferedImage backgroundImg;
	private boolean ammo;
	private int slotID;
	
	private Vector2D pos = new Vector2D();
	private int size = 60;
	
	private boolean heldOver;
	
	private int maxStack = 999;
	private int currStack = 0;
	
	public eqSlot(Vector2D pos, BufferedImage backgroundImg, boolean ammo) {
		this.pos = pos;
		this.backgroundImg = backgroundImg;
		this.ammo = ammo;
	}
	
	public void tick(){
		setBounds((int)pos.xPos, (int)pos.yPos, size, size);
		
		if(currStack == 0){
			if(item != null){
				clear();
			}
		}
		
		if(this.contains(MouseManager.mouse)){
			heldOver = true;
			
			
		} else {
			heldOver = false;
		}
		
	}
	
	public void render(Graphics2D g){
		g.drawImage(backgroundImg,(int)pos.xPos,(int)pos.yPos,size,size,null);
		if(item != null){
			g.drawImage(item.getItemImage(),(int)pos.xPos,(int)pos.yPos,size,size,null);

			if(ammo){
				g.setColor(Color.black);
				
				g.drawString(currStack+"", (int)pos.xPos + 2, (int)pos.yPos + size - 2);
				
				g.setColor(Color.yellow);
				
				g.drawString(currStack+"", (int)pos.xPos, (int)pos.yPos + size - 4);
			}
			
			g.setColor(Color.white);
		}
		
		if(heldOver){
			g.setColor(Color.white);
			g.drawRect((int)pos.xPos, (int)pos.yPos, size-1, size-1);
		}
		
	}
	
	public void setItem(Item item){
		if(item != null){
			this.item = item;
			slotID = item.getItemID();
			currStack++;
		}
	}

	private void clear() {
		slotID = 0;
		item = null;
		currStack = 0;
	}
	
	public Item getItem() {
		return item;
	}
	
	public boolean empty(){
		return (item == null && slotID == 0);
	}
	
	public boolean hasSameID(Item item){
		if (item.getItemID() == slotID) {
			return true;
		} else {
			return false;
		}
	}
	
	public void addItem(Item item){
		currStack += 1;
	}
	
	public boolean isFull(){
		if(currStack < maxStack){
			return false;
		} else {
			return true;
		}
	}
	
	public int getCurrStack() {
		return currStack;
	}
	
	public void setCurrStack(int currStack) {
		this.currStack = currStack;
	}
	
	public boolean leftClicked(){
		if(heldOver){
			if(InvMouseManager.leftClicked){
				InvMouseManager.leftClicked = false;
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public boolean rightClicked(){
		if(heldOver){
			if(InvMouseManager.rightClicked){
				InvMouseManager.rightClicked = false;
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public boolean hasItem(){
		if(item != null){
			if(slotID != 0){
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	

}
