package my.game.main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import my.game.equipment.eqSlot;
import my.game.generator.World;
import my.game.inventory.InvSlot;
import my.game.inventory.Item;

public class SaveLoadData {

	public static float xPos = 0;
	public static float yPos= 0;
	public static String worldName = "";
	public static boolean intro = false;

	public static CopyOnWriteArrayList<InvSlot> slots = new CopyOnWriteArrayList<InvSlot>();
	public static CopyOnWriteArrayList<eqSlot> eq = new CopyOnWriteArrayList<eqSlot>();
	public static float health;
	public static float stamina;
	public static Item[] invSlots = new Item[48];
	public static Item[] eqsItems = new Item[7];
	public static int[] invSlotsCurrStack = new int[48];

	public SaveLoadData() {
		load();
	}

	public static void save() {
		System.out.println("eq: "+eq);
		for (int i = 0; i < slots.size(); i++) {
			invSlots[i] = slots.get(i).getItem();
			invSlotsCurrStack[i] = slots.get(i).getCurrStack();
		}
		System.out.println(eq.size());
		for (int i = 0; i < eq.size(); i++) {
			eqsItems[i] = eq.get(i).getItem();
		}
		System.out.println(eqsItems);
		try {
			FileOutputStream saveFile = new FileOutputStream("Save.sav");
			
			ObjectOutputStream save = new ObjectOutputStream(saveFile);
			save.writeObject(xPos);
			save.writeObject(yPos);
			save.writeObject(worldName);
			save.writeObject(invSlots);
			save.writeObject(invSlotsCurrStack);
			save.writeObject(eqsItems);
			save.writeObject(health);
			save.writeObject(stamina);
			
			save.close();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void load() {

		try {
			FileInputStream saveFile = new FileInputStream("Save.sav");
			
			ObjectInputStream save = new ObjectInputStream(saveFile);
			
			xPos = (float) save.readObject();
			yPos = (float) save.readObject();
			worldName = (String) save.readObject();
			invSlots = (Item[]) save.readObject();
			invSlotsCurrStack = (int[]) save.readObject();
			eqsItems = (Item[]) save.readObject();
			health = (float) save.readObject();
			stamina = (float) save.readObject();
			save.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < invSlots.length; i++) {
			
			slots.get(i).setItem(invSlots[i]);
			//System.out.println(slots.get(i));
			slots.get(i).setCurrStack(invSlotsCurrStack[i]);
		}
		if(eq != null){
			System.out.println(eq);
			for (int j = 0; j < eq.size(); j++) {
				//System.out.println(eqsItems[j].getItemName());
				if(eqsItems != null){
					eq.get(j).setItem(eqsItems[j]);
				}
			}
		}
		
		World.getHud().getpStats().setHealth(health);
		World.getHud().getpStats().setStats(stamina);
		
	}

}
