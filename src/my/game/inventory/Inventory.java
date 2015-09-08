package my.game.inventory;

import java.awt.Graphics2D;
import java.util.concurrent.CopyOnWriteArrayList;

import my.game.main.SaveLoadData;
import my.javagame.main.Vector2D;

public class Inventory {

	private Vector2D pos = new Vector2D();
	private int dimentionX,dimentionY;
	private CopyOnWriteArrayList<InvSlot> slots;
	private boolean toggle, added;
	
	public Inventory(Vector2D pos, int dimentionX, int dimentionY) {
		this.pos = pos;
		this.dimentionX = dimentionY;
		this.dimentionY = dimentionX;
	}
	
	public void init(){
		slots = new CopyOnWriteArrayList<InvSlot>();
		
		for (int x = 0; x < dimentionX; x++) {
			for (int y = 0; y < dimentionY; y++) {
				slots.add(new InvSlot(new Vector2D(pos.xPos+y*32,pos.yPos+x*32)));
			}
		}
		SaveLoadData.slots = slots;
	}
	
	public void toggle(){
		toggle = !toggle;
	}

	public void tick(){
		if(toggle){
			for (InvSlot slot : slots) {
				slot.tick();
			}
		}
	}
	
	public void render(Graphics2D g){
		if(toggle){
			for (InvSlot slot : slots) {
				slot.render(g);
			}
		}
	}
	
	public void addItem(Item item){
		for (InvSlot slot : slots) {
			for(InvSlot s : slots){
				if(s.hasSameID(item) && s.isStackable(item)){
					if(!s.isFull()){
						System.out.println(item);
						s.addItem(item);
						added = true;
						break;
					} else {
						break;
					}
				} 
			}
				if(added){
					added = false;
					break;
				} else if(slot.empty()){
					slot.setItem(item);
					break;
				}
			
		}
	}
	
	public void addItem(Item item, int slotNum){
		if(slotNum < slots.size()){
			InvSlot slot = slots.get(slotNum);
			if(slot.empty()){
				slot.setItem(item);
			} else {
				if(slot.hasSameID(item) && slot.isStackable(item)){
					if(!slot.isFull()){
						slot.addItem(item);
					} else {
						
					}
				}
			}
		} else {
			System.err.println("NOP... Inventory is out of bounds!");
		}
	}
	
	public void setToggle(boolean toggle) {
		this.toggle = toggle;
	}
	
}
