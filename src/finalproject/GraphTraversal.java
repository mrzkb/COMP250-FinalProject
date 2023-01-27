package finalproject;

import finalproject.system.Tile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class GraphTraversal
{


	//TODO level 1: implement BFS traversal starting from s
	public static ArrayList<Tile> BFS(Tile s) {
		ArrayList<Tile> bfs= new ArrayList<>(); // this is the arrayList that will be returned,with visited tiles
		HashSet<Tile>visitedTiles= new HashSet<>(); //Tiles added to this hashset if they have been visited
		ArrayList<Tile> toVisit= new ArrayList<>(); //tiles that need to be visited

		toVisit.add(s);

		while(!toVisit.isEmpty()){
			Tile t= toVisit.get(0);
			toVisit.remove(0);

			if(!visitedTiles.contains(t) && t.isWalkable()){
				bfs.add(t);
				toVisit.addAll(t.neighbors);
				visitedTiles.add(t);
				if(t.isDestination && toVisit.isEmpty()) break;
			}
		}
		return bfs;
	}



	//TODO level 1: implement DFS traversal starting from s
	public static ArrayList<Tile> DFS(Tile s) {
		ArrayList<Tile> dfs= new ArrayList<>(); // this is the arrayList that will be returned,with visited tiles
		HashSet<Tile>visitedTiles= new HashSet<>(); //Tiles added to this hashset if they have been visited
		ArrayList<Tile> toVisit= new ArrayList<>(); //tiles that need to be visited

		toVisit.add(s);

		while(!toVisit.isEmpty()){
			Tile t= toVisit.get(toVisit.size()-1);
			toVisit.remove(toVisit.size()-1);

			if(!visitedTiles.contains(t) && t.isWalkable()){
				dfs.add(t);
				toVisit.addAll(t.neighbors);
				visitedTiles.add(t);
				if(t.isDestination && toVisit.isEmpty()) break;
			}
		}
		return dfs;
	}

}  