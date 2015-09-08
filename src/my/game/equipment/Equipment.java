package my.game.equipment;

import java.awt.Graphics2D;
import java.util.concurrent.CopyOnWriteArrayList;

import my.game.inventory.Item;
import my.game.main.Assets;
import my.game.main.SaveLoadData;
import my.javagame.main.Vector2D;

public class Equipment {

	private CopyOnWriteArrayList<eqSlot> slots;
	private boolean toggle;
	
	public Equipment() {

	}
	public void init(){
		slots = new CopyOnWriteArrayList<eqSlot>();
		
		slots.add(new eqSlot(new Vector2D(660,225), Assets.getInvHead(), false));
		slots.add(new eqSlot(new Vector2D(660,290), Assets.getInvBody(), false));
		slots.add(new eqSlot(new Vector2D(660,355), Assets.getInvLegs(), false));
		slots.add(new eqSlot(new Vector2D(723,225), Assets.getInvAmmo(), false));
		slots.add(new eqSlot(new Vector2D(660,420), Assets.getInvBoots(), false));
		slots.add(new eqSlot(new Vector2D(723,290), Assets.getInvShield(), false));
		slots.add(new eqSlot(new Vector2D(598,290), Assets.getInvWeapon(), false));

		SaveLoadData.eq = slots;
	}
	public void toggle(){
		toggle = !toggle;
	}

	public void tick(){
		if(toggle){
			for (eqSlot slot : slots) {
				slot.tick();
			}
		}
	}
	
	public void render(Graphics2D g){
		if(toggle){
			for (eqSlot slot : slots) {
				slot.render(g);
			}
		}
	}
	
	public void addItem(Item item , String eqPlace){
		eqSlot slot;
		if(eqPlace == "head")
			slot = slots.get(0);
		else if(eqPlace == "body")
			slot = slots.get(1);
		else if(eqPlace == "legs")
			slot = slots.get(2);
		else if(eqPlace == "ammo")
			slot = slots.get(3);
		else if(eqPlace == "boots")
			slot = slots.get(4);
		else if(eqPlace == "shield")
			slot = slots.get(5);
		else if(eqPlace == "weapon")
			slot = slots.get(6);
		else
			slot = null;
		
		if(slot!=null){
			if(slot.empty()){
				slot.setItem(item);
				SaveLoadData.eq = slots;
			}
		} else
			System.err.println("this slot place is not avaible");
	}
	
	public void setToggle(boolean toggle) {
		this.toggle = toggle;
	}
	
	public boolean isSameItem(Item item , String eqPlace){
		eqSlot slot;
		if(eqPlace == "head")
			slot = slots.get(0);
		else if(eqPlace == "body")
			slot = slots.get(1);
		else if(eqPlace == "legs")
			slot = slots.get(2);
		else if(eqPlace == "ammo")
			slot = slots.get(3);
		else if(eqPlace == "boots")
			slot = slots.get(4);
		else if(eqPlace == "shield")
			slot = slots.get(5);
		else if(eqPlace == "weapon")
			slot = slots.get(6);
		else
			slot = null;
		if(slot.getItem() == item){
			return true;
		} else {
			return false;
		}
	}
	
}

