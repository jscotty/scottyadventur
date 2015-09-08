package my.game.inventory;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import my.game.generator.World;
import my.game.managers.MouseManager;
import my.javagame.main.Vector2D;

public class ItemUsage{

	private InvSlot slot;
	private Vector2D pos;
	private int width,height;
	private boolean toggle, heldOverUse, heldOverCancel, heldOverDrop;
	private String cancel = "cancel", drop = "drop";
	Font f = new Font("arial", Font.BOLD, 12);
	
	private String usageMessage;
	
	public ItemUsage(Vector2D pos, int width, int height,String usageMessage) {
		this.pos = pos;
		this.width = width;
		this.height = height;
		this.usageMessage = usageMessage;
	}
	
	public void tick(double deltaTime){
		//System.out.println("use: " + heldOverUse + " drop: " + heldOverDrop + " cancel: " + heldOverCancel);
		if(heldOverUse){
			if(leftClicked()){
				System.out.println("nee");
				slot.useItem(slot.getItem());
				toggleOff();
				heldOverUse = false;
			}
			heldOverDrop = false;
			heldOverCancel = false;
		}
		if(heldOverDrop){
			if(leftClicked()){
				slot.dropItem(new Vector2D(World.getPlayer().getPos().xPos,World.getPlayer().getPos().yPos));
				toggleOff();
				heldOverDrop = false;
			}
			heldOverUse = false;
			heldOverCancel = false;
		}
		if(heldOverCancel){
			heldOverUse = false;
			heldOverDrop = false;
			if(leftClicked()){
				toggleOff();
				heldOverCancel = false;
			}
		}
	}
	
	public void render(Graphics2D g){
		if(toggle){
	        Rectangle useBounds = new Rectangle((int)pos.xPos, (int)pos.yPos + width, width,12);
	        Rectangle dropBounds = new Rectangle((int)pos.xPos, (int)pos.yPos + width + 12, width,12);
	        Rectangle cancelBounds = new Rectangle((int)pos.xPos, (int)pos.yPos + width + 24, width,12);
	        
			g.setFont(f);
			g.setColor(Color.black);
			g.fillRect((int)pos.xPos, (int)pos.yPos + width - 4, width + 5, height);

			if(useBounds.contains(MouseManager.mouse)){
		       g.setColor(Color.yellow);
		       heldOverUse = true;
		    } else {
			       heldOverUse = false;
		       g.setColor(Color.white);
		    }
			g.drawString(usageMessage, (int)pos.xPos, (int)pos.yPos + width + 6);
			

	        if(dropBounds.contains(MouseManager.mouse)){
	        	g.setColor(Color.yellow);
	        	heldOverDrop = true;
	        } else {
	        	heldOverDrop = false;
	        	g.setColor(Color.white);
	        }
	        
			g.drawString(drop, (int)pos.xPos, (int)pos.yPos + width + 20);

	        if(cancelBounds.contains(MouseManager.mouse)){
	        	g.setColor(Color.yellow);
	        	heldOverCancel = true;
	        } else {
	        	heldOverCancel = false;
	        	g.setColor(Color.white);
	        }
			g.drawString(cancel, (int)pos.xPos, (int)pos.yPos + width + 34);
		}
		
	}
	
	public void toggleOn(){
		toggle = true;
	}
	public void toggleOff(){
		toggle = false;
	}
	public void changeUsage(Vector2D pos,String usageMessage, InvSlot slot){
		this.pos = pos;
		this.usageMessage = usageMessage;
		this.slot = slot;
		toggle = true;
	}

	public boolean leftClicked(){
		if(heldOverCancel || heldOverDrop || heldOverUse){
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
		if(heldOverCancel || heldOverDrop || heldOverUse){
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

}
