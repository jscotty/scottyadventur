package my.game.state;

import java.awt.Graphics2D;
import java.util.jar.Attributes.Name;

import my.game.enemy.EnemyType;
import my.game.generator.World;
import my.game.handler.Loading;
import my.game.managers.GameStateManager;
import my.game.moveAbleObjects.Player;
import my.game.references.WorldNames;
import my.javagame.main.Vector2D;


public class LevelLoader extends GameState {
	
	public static World world;
	private String worldName;
	private String mapName;
	private int level;
	
	public static int wSpawnX, wSpawnY;
	
	public LevelLoader(GameStateManager gsm) {
		super(gsm);
	}
	public LevelLoader(GameStateManager gsm, String worldName, String mapName, int level) {
		super(gsm);
		this.worldName = worldName;
		this.mapName = mapName;
		this.level = level;
	}

	@Override
	public void init() {
		Loading.isLoading = true;
		
		if(worldName == null){
			worldName = WorldNames.WORLD_01;
			mapName = "map";
			level = 1;
			
			wSpawnX = 14; wSpawnY = 15;
			
		}
		
		world = new World(worldName,this,gsm,level);
		world.setSize(100,100);
		world.setWorldSpawn();
		world.addPlayer(new Player());
		world.init();
		world.generate(mapName);
		if(worldName == WorldNames.WORLD_01){
			//				pos,enemyType,width,height,a*width range,a*height range,startpoint a* x,startpoint a* y
			world.addEnemy(new Vector2D(16, 21), EnemyType.MAN_LVL_01, 32, 40,15,16,13,10);
			world.addEnemy(new Vector2D(23, 13), EnemyType.MAN_LVL_01, 32, 40,15,16,13,10);
			
			world.addEnemy(new Vector2D(17, 40), EnemyType.SLUCK_LVL_01, 32, 32, 9,14,15,40);
			world.addEnemy(new Vector2D(49, 13), EnemyType.SLUCK_LVL_01, 32, 32, 22,7,48,11); 
			
			world.addEnemy(new Vector2D(44, 80), EnemyType.MAN_LVL_01, 32, 40,22,17,39,75);
			world.addEnemy(new Vector2D(58, 83), EnemyType.MAN_LVL_01, 32, 40,22,17,39,75);
			world.addEnemy(new Vector2D(47, 88), EnemyType.MAN_LVL_01, 32, 40,22,17,39,75);
			world.addEnemy(new Vector2D(50, 76), EnemyType.SLUCK_LVL_01, 32, 40,22,17,39,75);
		}
	}

	@Override
	public void tick(double deltaTime) {
		if(world.hasGenerated())
			world.tick(deltaTime);
	}

	@Override
	public void render(Graphics2D g) {
		if(world.hasGenerated())
			world.render(g);
	}

}
