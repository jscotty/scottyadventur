package my.game.pathfinding;

import java.awt.Point;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;


import my.game.generator.Block;
import my.game.generator.World;
import my.game.state.LevelLoader;

public class AStar{

	public static double horizontalScore = 1;

	public static double diagonalScore = 1.414;
	
	private Block currentBlock;
	private Block neighbor;
	
	
	
	public AStar() {
		
	}
	
	public CopyOnWriteArrayList<Block> search(Point start, Point end){
		CopyOnWriteArrayList<Block> openList = new CopyOnWriteArrayList<Block>();
		CopyOnWriteArrayList<Block> closedList = new CopyOnWriteArrayList<Block>();
		CopyOnWriteArrayList<Block> neighbors = new CopyOnWriteArrayList<Block>();
		double gScore;
		boolean gScoreBest;
		
		LevelLoader.world.resetBlocks();
		
		openList.add(LevelLoader.world.getBlock(start.x / 32, start.y / 32));
		currentBlock = LevelLoader.world.getBlock(start.x / 32, start.y / 32);
		while(openList.size() > 0){
			
			//sorting the arrayList
			Collections.sort(openList , new Comparator<Block>() {
				public int compare(Block a, Block b){
					if(a.f > b.f || a.f == b.f && a.h > b.h){
						return 1;
					} else {
						return -1;
					}
				}
			});
			currentBlock = openList.get(0);
			
			//found path
			if(currentBlock.contains(end)){
				return getPath(currentBlock);
			}
			openList.remove(0);
			
			closedList.add(currentBlock);
			currentBlock.isClosed = true;
			currentBlock.isOpen = false;
			/*count++;
			System.out.println(count + " block: " + currentBlock);*/
			
			if(currentBlock.neighbors.size() <= 0){
				currentBlock.neighbors	=	getNeighbors(LevelLoader.world, currentBlock);
			}
			
			
			neighbors = currentBlock.neighbors;
			
			int l = neighbors.size();
			for (int i = 0; i < l; i++) {
				neighbor = neighbors.get(i);
				if(neighbor.isClosed || neighbor.isSolid()){
					continue;
				}
				
				gScore = currentBlock.g + horizontalScore;
				
				gScoreBest = false;
				
				if(!neighbor.isOpen){
					gScoreBest = true;
					neighbor.h = heuristic(new Point((int)neighbor.getPos().xPos, (int)neighbor.getPos().yPos), end);
					openList.add(neighbor);
					neighbor.isOpen = true;
				} else if(gScore < neighbor.g){
					gScoreBest = true;
				}
				
				if(gScoreBest){
					neighbor.setParent(currentBlock);
					neighbor.g = gScore;
					neighbor.f = neighbor.g + neighbor.h;
				}
			}
		}
		return openList;
		
	}
	
	private CopyOnWriteArrayList<Block> getPath(Block currentBlock){

		CopyOnWriteArrayList<Block> path = new CopyOnWriteArrayList<Block>();

		while(currentBlock.getParent()!=null) {
			path.add(currentBlock);
			currentBlock = currentBlock.getParent();
		}
		Collections.reverse(path);
		return path;
	}
	
	
	private static int heuristic(Point pos0, Point pos1) {
		// This is the Manhattan distance
		int d1 = Math.abs (pos1.x - pos0.x);
		int d2 = Math.abs (pos1.y - pos0.y);
		return d1 + d2;
	}
	
	private CopyOnWriteArrayList<Block> getNeighbors(World world, Block block) {
		CopyOnWriteArrayList<Block> neighbors = new CopyOnWriteArrayList<Block>();
		int x = (int)block.getPos().xPos / 32;
		int y = (int)block.getPos().yPos / 32;
 
		if(world.getBlock(x-1, y) != null) {
			neighbors.add(world.getBlock(x-1, y));
		}
		if(world.getBlock(x+1, y) != null) {
			neighbors.add(world.getBlock(x+1, y));
		}
		if(world.getBlock(x, y-1) != null) {
			neighbors.add(world.getBlock(x, y-1));
		}
		if(world.getBlock(x, y+1) != null) {
			neighbors.add(world.getBlock(x, y+1));
		}

		return neighbors;
	}
	
	public Block getNeighbor() {
		return neighbor;
	}
	

}
