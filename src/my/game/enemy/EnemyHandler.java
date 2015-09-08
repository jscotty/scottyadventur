package my.game.enemy;

import java.util.concurrent.CopyOnWriteArrayList;

import my.game.moveAbleObjects.Player;

public class EnemyHandler {
	
	public CopyOnWriteArrayList<Enemy> enemys = new CopyOnWriteArrayList<Enemy>();
	
	public EnemyHandler() {
		
	}
	
	public void tick(double deltaTime){
		for(Enemy enemy:enemys){
			
			if(Player.render.intersects(enemy)){
				enemy.loading = true;
			} else {
				enemy.loading = false;
			}
		}
	}
	

}
