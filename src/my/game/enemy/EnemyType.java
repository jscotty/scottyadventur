package my.game.enemy;

import java.util.concurrent.CopyOnWriteArrayList;

import my.game.inventory.Item;

public enum EnemyType {
	
	SLUCK_LVL_01("Sluck Lvl 1", 1, 2,20, Item.itemPack_01),
	SLUCK_LVL_02("Sluck Lvl 2", 2, 2,20, Item.itemPack_01),
	SLUCK_LVL_05("Sluck Lvl 5", 5, 2,20, Item.itemPack_01),
	SLUCK_LVL_09("Sluck Lvl 9", 9, 2,20, Item.itemPack_01),
	MAN_LVL_01("MAN Lvl 1", 1, 2,20, Item.itemPack_02);
	
	
	private int enemyLevel;
	private String enemyName;
	private int damage;
	private int health;
	
	private Item[] item;
	
	
	private EnemyType (String enemyName, int enemyLevel, int damage, int health, Item[] item){
		this.enemyName = enemyName;
		this.enemyLevel = enemyLevel;
		this.health = health * enemyLevel;
		this.damage = damage*enemyLevel;
		
		this.item = item;
	}
	
	public int getEnemyLevel() {
		return enemyLevel;
	}
	public String getEnemyName() {
		return enemyName;
	}
	public int getDamage() {
		return damage;
	}
	public int getHealth() {
		return health;
	}
	
	public Item getItem() {
		Item dropItem;
		int l = item.length;
		int i = (int)(Math.random()*l);
		if(i > item.length){
			dropItem = Item.NULL;
		} else {
			dropItem = item[i];
		}
		return dropItem;
	}
}
