package my.game.inventory;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import my.game.generator.World;
import my.game.main.Assets;
import my.game.managers.MouseManager;
import my.game.moveAbleObjects.PlayerStats;
import my.game.references.Spot;
import my.game.references.Tags;
import my.javagame.main.Vector2D;

public class InvSlot extends Rectangle{

	private Item item;
	private int slotID;
	
	private Vector2D pos = new Vector2D();
	private int size = 32;
	
	private boolean heldOver;
	private boolean rightClicked;
	
	private int maxStack = 99;
	private int currStack = 0;
	private int count = 0;
	Font f = new Font("arial", Font.BOLD, 16);
	Font f2 = new Font("arial", Font.BOLD, 20);
	
	public InvSlot(Vector2D pos) {
		this.pos = pos;
		
		setBounds((int)pos.xPos, (int)pos.yPos, size, size);
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
			count++;
			if(count == 1){
				InvMouseManager.leftClicked = false;
			}
			
			if(leftClicked()){
				if(!rightClicked){
					if(item != null){
						System.out.println("oh");
						useItem(item);
					}
				}
			} else if(rightClicked()){
				if(item != null){
					rightClicked = true;
					
					itemInfo();
				}
			}
		} else {
			heldOver = false;
			count = 0;
		}
		
	}
	
	private void itemInfo() {
		if(item.getTag() == Tags.USE){
			World.getItemUse().changeUsage(new Vector2D((int)pos.xPos, (int)pos.yPos - 4), "use",this);
		} else if(item.getTag() == Tags.WEAR){
			World.getItemUse().changeUsage(new Vector2D((int)pos.xPos, (int)pos.yPos - 4), "equip",this);
		}
		
	}

	public void useItem(Item item) {
		if(item == Item.HEALTH_POT){
			if(!PlayerStats.isFullHealth()){
				currStack--;
				World.getHud().getpStats().heal(20);
			}
		} else if(item == Item.STAMINA_POT){
			if(!PlayerStats.isFullStamina()){
				currStack--;
				World.getHud().getpStats().addStamina(50);
			}
		} else if(item == Item.HELM_RED){
			if(!World.getPlayer().getEquipment().isSameItem(Item.HELM_RED, Spot.HEAD)){
				currStack --;
				World.getPlayer().getEquipment().addItem(Item.HELM_RED, Spot.HEAD);
			}
		} else if(item == Item.BODY_RED){
			if(!World.getPlayer().getEquipment().isSameItem(Item.BODY_RED, Spot.BODY)){
				currStack --;
				World.getPlayer().getEquipment().addItem(Item.BODY_RED, Spot.BODY);
			}
		} else if(item == Item.LEGS_RED){
			if(!World.getPlayer().getEquipment().isSameItem(Item.LEGS_RED, Spot.LEGS)){
				currStack --;
				World.getPlayer().getEquipment().addItem(Item.LEGS_RED, Spot.LEGS);
			}
		} else if(item == Item.BOOTS_RED){
			if(!World.getPlayer().getEquipment().isSameItem(Item.BOOTS_RED, Spot.BOOTS)){
				currStack --;
				World.getPlayer().getEquipment().addItem(Item.BOOTS_RED, Spot.BOOTS);
			}
		}
	}

	public void render(Graphics2D g){
		g.drawImage(Assets.getInvBackground(),(int)pos.xPos,(int)pos.yPos,size,size,null);
		if(item != null){
			g.drawImage(item.getItemImage(),(int)pos.xPos,(int)pos.yPos,size,size,null);
			
			if(isStackable(item)){
				g.setColor(Color.black);
				
				g.drawString(currStack+"", (int)pos.xPos + 2, (int)pos.yPos + size - 2);
				
				g.setColor(Color.yellow);
				
				g.drawString(currStack+"", (int)pos.xPos, (int)pos.yPos + size - 4);

				g.setColor(Color.white);
			}
			
		}
		
		if(heldOver){
			g.setColor(Color.white);
			g.drawRect((int)pos.xPos, (int)pos.yPos, size-1, size-1);

			if(item != null){
				g.setColor(Color.black);
				g.drawString(item.getItemName(), 8, 92);
				g.setColor(Color.yellow);
				g.drawString(item.getItemName(), 6, 90);
				g.setColor(Color.white);
			}
		}
		if(rightClicked){
			Rectangle boxRect = new Rectangle((int)pos.xPos, (int)pos.yPos,size, 66);
			
			if(boxRect.contains(MouseManager.mouse)){

			} else {
				rightClicked = false;
				World.getItemUse().toggleOff();
			}
		} else {
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
	public boolean isStackable(Item item){
		if (item.getTag() == "use") {
			return true;
		} else {
			return false;
		}
	}
	
	public void addItem(Item item){
		currStack += 1;
	}
	public void removeItem(){
		currStack -= 1;
	}
	public void dropItem(Vector2D pos){
		World.dropItem(pos, item, 1);
		currStack -= 1;
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
