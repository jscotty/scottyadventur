package my.game.inventory;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import my.game.generator.World;
import my.game.moveAbleObjects.Player;
import my.javagame.main.Vector2D;

public class ItemDrop extends Rectangle {

	public boolean picked = false;
	
	private Vector2D pos = new Vector2D(0, 0);
	private ItemPickUp itemPick;
	private Item item;
	private int width = 20, height = 20;
	private int amount;
	
	public ItemDrop(float xPos, float yPos, Item item, int amount) {
		pos.xPos = xPos;
		pos.yPos = yPos;
		this.item = item;
		this.amount = amount;
		init();
	}
	public ItemDrop(Vector2D pos, Item item, int amount) {
		this.pos = pos;
		this.item = item;
		this.amount = amount;
		init();
	}
	
	public void init(){
		setBounds((int)pos.xPos*2, (int)pos.yPos*2, width, height);
		itemPick = new ItemPickUp(this);
	}
	
	public void tick(){
		if(!picked && item != null){
			
			itemPick.tick();
			if(World.getPlayer().isMoving()){
				float divide = 61;
				if(Player.up){
					pos.yPos += (World.getPlayer().getSpeed() )/ divide;
				}
				if(Player.down){
					pos.yPos -= (World.getPlayer().getSpeed() ) / divide;
				}
				if(Player.right){
					pos.xPos -= (World.getPlayer().getSpeed() ) / divide;
				}
				if(Player.left){
					pos.xPos += (World.getPlayer().getSpeed() ) / divide;
				}
			}
		}
	}
	
	public void render(Graphics2D g){
		if(!picked && item != null){
			itemPick.render(g);
			g.drawImage(item.getItemImage(), (int)pos.xPos - width/2, (int)pos.yPos - height/2,width,height,null);
		}
	}
	
	public int getAmount() {
		return amount;
	}
	public Vector2D getPos() {
		return pos;
	}
	public Item getItem() {
		return item;
	}

}
