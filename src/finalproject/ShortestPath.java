package finalproject;


import finalproject.system.Tile;
import finalproject.tiles.MetroTile;

import java.util.ArrayList;

public class ShortestPath extends PathFindingService {
    //TODO level 4: find distance prioritized path
    public ShortestPath(Tile start) {
        super(start);
        generateGraph();
    }

	@Override
	public void generateGraph() {
        Tile t= super.source;
        ArrayList<Tile> reachableTiles=GraphTraversal.BFS(t);
        this.g= new Graph(reachableTiles);

        while(!reachableTiles.isEmpty()){
            Tile tile=reachableTiles.get(0);
            reachableTiles.remove(tile);
            for(Tile j: tile.neighbors) {
                if(tile instanceof MetroTile && j instanceof MetroTile) {
                    ((MetroTile) j).fixMetro(tile);
                    g.addEdge(tile, j, ((MetroTile) j).metroDistanceCost);
                }
                else if(j.isWalkable()) g.addEdge(tile, j, j.distanceCost);
            }
        }
	}
    
}