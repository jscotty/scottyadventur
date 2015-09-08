package my.game.inventory;

import java.awt.image.BufferedImage;

import my.game.main.Assets;

public enum Item {
	NULL("NULL",1,Assets.getCrate_01(), "null"),
	STAMINA_POT("Stamina Potion",3,Assets.getStaminaPotion(), "use"),
	HELM_RED("Red Helm",4,Assets.getHelm_red(), "wear"),
	BODY_RED("Red Body",5,Assets.getBody_red(), "wear"),
	LEGS_RED("Red Legs",6,Assets.getLegs_red(), "wear"),
	BOOTS_RED("Red Boots",7,Assets.getBoots_red(), "wear"),
	HEALTH_POT("Health Potion",2,Assets.getHealthPotion(), "use");
	
	private int itemID;
	private BufferedImage itemImage;
	private String itemName;
	private String tag;
	
	public static Item[] itemPack_01 = {
		Item.HEALTH_POT, Item.BODY_RED, Item.HELM_RED, Item.STAMINA_POT, Item.NULL, Item.NULL, Item.NULL, Item.NULL, Item.NULL, Item.NULL, Item.NULL
	};
	
	public static Item[] itemPack_02 = {
		Item.HEALTH_POT, Item.BOOTS_RED, Item.LEGS_RED, Item.STAMINA_POT, Item.NULL, Item.NULL, Item.NULL, Item.NULL, Item.NULL, Item.NULL, Item.NULL
	};
	
	private Item(String itemName,int itemID, BufferedImage itemImage, String tag) {
		this.itemName = itemName;
		this.itemID = itemID;
		this.itemImage = itemImage;
		this.tag = tag;
	}
	
	public int getItemID() {
		return itemID;
	}
	public BufferedImage getItemImage() {
		return itemImage;
	}
	public String getItemName() {
		return itemName;
	}
	public String getTag() {
		return tag;
	}
}
